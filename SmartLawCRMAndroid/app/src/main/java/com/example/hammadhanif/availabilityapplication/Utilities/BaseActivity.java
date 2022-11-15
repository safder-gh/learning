package com.example.hammadhanif.availabilityapplication.Utilities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.pm.PackageInfoCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hammadhanif.availabilityapplication.AttorneyRecyclerView;
import com.example.hammadhanif.availabilityapplication.CaseSearch;
import com.example.hammadhanif.availabilityapplication.HomeActivity;
import com.example.hammadhanif.availabilityapplication.MainActivity;
import com.example.hammadhanif.availabilityapplication.MyAccountsRecyclerView;
import com.example.hammadhanif.availabilityapplication.PendingAccountsRecyclerView;
import com.example.hammadhanif.availabilityapplication.Preference.PreferenceConnector;
import com.example.hammadhanif.availabilityapplication.R;
import com.example.hammadhanif.availabilityapplication.VoiceRecorderActivity;
import com.kmincorp.smartlawcrm.Account;
import com.kmincorp.smartlawcrm.CRMCase;
import com.kmincorp.smartlawcrm.Parameters;
import com.kmincorp.smartlawcrm.RestClient;
import com.kmincorp.smartlawcrm.Session;
import com.kmincorp.smartlawcrm.Utilities.Common;
import com.kmincorp.smartlawcrm.VolleyResponse;
import com.kmincorp.smartlawcrm.VolleyResponseError;

import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

public  abstract class BaseActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    FrameLayout frameLayout;
    ProgressBar progressBar;
    Toolbar toolbar;
    Session session;
    PreferenceConnector preferenceConnector;
    private static String versionCode = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        /*((MenuItem)toolbar.findViewById(R.id.refresh)).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                BaseActivity.this.refresh();
                return false;
            }
        });*/
        if(versionCode == null)
            versionCode = getVersionCode();


        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            /** Called when a drawer has settled in a completely closed state.
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // Do whatever you want here
            }*/

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getNoOfPendingConsults();
                getNoOfMyConsults();
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        frameLayout = findViewById(R.id.layout_container);
        preferenceConnector = new PreferenceConnector(getApplicationContext());
        BaseActivity.this.session = preferenceConnector.getSession();
        if(BaseActivity.this.session != null) {
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            ((TextView) navigationView.getHeaderView(0).findViewById(R.id.tvHeaderName)).setText(BaseActivity.this.session.getUserName());
            ((TextView) navigationView.getHeaderView(0).findViewById(R.id.tvVersionNumber)).setText("( v"+versionCode+")");
            //((TextView) navigationView.getHeaderView(0).findViewById(R.id.tvVersionNumber)).setText(String.valueOf((new Timestamp(System.currentTimeMillis()).getTime()-session.getTimestamp().getTime())/60000));
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void setContentView(int id) {
        LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(id, frameLayout);
    }

    /*@Override
    protected void onResume() {

        final PreferenceConnector preferenceConnector;
        final Session session;
        final RestClient restClient;
        preferenceConnector = new PreferenceConnector(getApplicationContext());
        session = preferenceConnector.getSession();
        if(session != null && session.getActive()) {
            restClient = new RestClient(session.getUrl(), this,this.getProgressBar());
            restClient.restRequest(new Parameters(session.getSessionId(), Common.RequestType.GETUSERID), new VolleyResponse() {
                @Override
                public void onSuccessResponse(String result,Session session1) {
                    if (!session.getUserId().equalsIgnoreCase(result.replaceAll("^\"|\"$", ""))) {
                        Parameters parameters = new Parameters(session.getUserName(), session.getPassword());
                        restClient.restRequest(parameters, new VolleyResponse() {
                            @Override
                            public void onSuccessResponse(String result) {
                                //Session session  = preferenceConnector.getSession();
                                try {
                                    JSONObject jsonObj = new JSONObject(result);
                                    if(jsonObj.has("number")) {
                                        Intent intent;
                                        intent = new Intent(BaseActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        BaseActivity.this.finish();
                                    }
                                    session.fillInstance(jsonObj);
                                    preferenceConnector.setSession(session);
                                }
                                catch (org.json.JSONException ex)
                                {
                                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new VolleyResponseError() {
                            @Override
                            public void onResponseError(String result) {
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }, new VolleyResponseError() {
                @Override
                public void onResponseError(String result) {
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                }
            });
        }
        else
        {
            Intent intent;
            intent = new Intent(BaseActivity.this, MainActivity.class);
            startActivity(intent);
            BaseActivity.this.finish();
        }
        super.onResume();
    }*/
    @Override
    public void onBackPressed() {
        if(BaseActivity.this.session != null && BaseActivity.this.session.getActive()) {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.refresh) {
            this.refresh();
            return true;
        }else if(id==R.id.recording)
        {
            startActivity(new Intent(BaseActivity.this, VoiceRecorderActivity.class));
            finish();
        }


        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if(id == R.id.nav_logout)
        {
            final PreferenceConnector preferenceConnector = new PreferenceConnector(getApplicationContext());
            //final Session session = preferenceConnector.getSession();
            Parameters parameters = new Parameters(BaseActivity.this.session.getSessionId(), Common.RequestType.LOGOUT);
            RestClient restClient = new RestClient(BaseActivity.this.session.getUrl(),this,getProgressBar());
            restClient.logoffRequest(parameters, new VolleyResponse() {
                @Override
                public void onSuccessResponse(String result,Session session) {
                    try {
                        BaseActivity.this.session.setPassword("");
                    }
                    catch (NoSuchAlgorithmException ex){

                    }
                    finally {
                        session.setActive(false);
                        preferenceConnector.setSession(session);
                        startActivity(new Intent(BaseActivity.this, MainActivity.class));
                        finish();
                    }
                }
            }, new VolleyResponseError() {
                @Override
                public void onResponseError(String result) {
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                }
            },BaseActivity.this.session);
        }
        else {
            Intent intent = null;
            if (id == R.id.nav_home) {
                intent = new Intent(this, HomeActivity.class);
            } else if (id == R.id.nav_avail_status) {
                intent = new Intent(this, AttorneyRecyclerView.class);
            } else if (id == R.id.nav_pending_consult) {
                intent = new Intent(this, PendingAccountsRecyclerView.class);
            } else if (id == R.id.nav_my_clients) {
                intent = new Intent(this, MyAccountsRecyclerView.class);
            } else if (id == R.id.nav_update_case_notes) {
                intent = new Intent(this, CaseSearch.class);
            }
            startActivity(intent);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    protected  ProgressBar getProgressBar(){
        return this.progressBar;
    }
    protected void setSubTitle(String title){
        toolbar.setSubtitle(title);
    }
    private String getVersionCode()   {
        String versionNumber;
        try {
            PackageInfo pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionNumber = String.valueOf(PackageInfoCompat.getLongVersionCode(pinfo));
        }
        catch (PackageManager.NameNotFoundException ex){
            versionNumber = ex.getMessage();
        }
        return  versionNumber;
    }
    private void setNoOfPendingConsults(int count)
    {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        ((TextView)navigationView.getMenu().findItem(R.id.nav_pending_consult).getActionView().findViewById(R.id.counter)).setText(String.valueOf(count));
        ((ProgressBar)navigationView.getMenu().findItem(R.id.nav_pending_consult).getActionView().findViewById(R.id.progress)).setVisibility(View.GONE);
    }
    private void setNoOfMyConsults(int count)
    {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        ((TextView)navigationView.getMenu().findItem(R.id.nav_my_clients).getActionView().findViewById(R.id.counter)).setText(String.valueOf(count));
        ((ProgressBar)navigationView.getMenu().findItem(R.id.nav_my_clients).getActionView().findViewById(R.id.progress)).setVisibility(View.GONE);
    }
    private void getNoOfPendingConsults()
    {
        RestClient restClient;
        restClient = new RestClient(BaseActivity.this.session.getUrl(), this,getProgressBar());
        Parameters parameters = new Parameters(BaseActivity.this.session.getSessionId(), Common.ModuleName.Cases, Common.RequestType.GETENTRYLIST, com.example.hammadhanif.availabilityapplication.Utilities.Common.caseFields,"(cases_cstm.consultationstatus_c='pending'  and cases_cstm.archive_c=0)","cases.date_modified DESC");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        ((ProgressBar)navigationView.getMenu().findItem(R.id.nav_pending_consult).getActionView().findViewById(R.id.progress)).setVisibility(View.VISIBLE);
        restClient.restRequest(parameters, new VolleyResponse() {
            @Override
            public void onSuccessResponse(String result,Session session) {
                BaseActivity.this.session = session;
                try {
                    setNoOfPendingConsults(CRMCase.count(result));
                } catch (org.json.JSONException ex) {
                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new VolleyResponseError() {
            @Override
            public void onResponseError(String result) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        },BaseActivity.this.session);
    }
    private void getNoOfMyConsults()
    {
        RestClient restClient;
        restClient = new RestClient(BaseActivity.this.session.getUrl(), this,getProgressBar());
        Parameters parameters = new Parameters(BaseActivity.this.session.getSessionId(), Common.ModuleName.Cases, Common.RequestType.GETENTRYLIST, com.example.hammadhanif.availabilityapplication.Utilities.Common.caseFields,"(cases.assigned_user_id='"+preferenceConnector.getSession().getUserId()+"' and cases_cstm.archive_c=0 and cases_cstm.consultationstatus_c='assigned')","cases.date_modified DESC");
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        ((ProgressBar)navigationView.getMenu().findItem(R.id.nav_my_clients).getActionView().findViewById(R.id.progress)).setVisibility(View.VISIBLE);
        restClient.restRequest(parameters, new VolleyResponse() {
            @Override
            public void onSuccessResponse(String result,Session session) {
                BaseActivity.this.session = session;
                try {
                    setNoOfMyConsults(Account.count(result));
                } catch (org.json.JSONException ex) {
                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new VolleyResponseError() {
            @Override
            public void onResponseError(String result) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        },BaseActivity.this.session);
    }
    protected abstract void refresh();
}
