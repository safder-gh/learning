package com.example.hammadhanif.availabilityapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hammadhanif.availabilityapplication.Preference.PreferenceConnector;
import com.example.hammadhanif.availabilityapplication.Utilities.BaseActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.JsonObject;
import com.kmincorp.smartlawcrm.FCM;
import com.kmincorp.smartlawcrm.Parameters;
import com.kmincorp.smartlawcrm.RestClient;
import com.kmincorp.smartlawcrm.Session;
import com.kmincorp.smartlawcrm.Utilities.Common;
import com.kmincorp.smartlawcrm.VolleyResponse;
import com.kmincorp.smartlawcrm.VolleyResponseError;

import org.json.JSONObject;

import java.util.HashMap;

import static com.example.hammadhanif.availabilityapplication.Utilities.Common.hasPermissions;

public class HomeActivity extends BaseActivity implements Button.OnClickListener {
    Button btnAvailability,btnPedingConsult,btnMyAccounts,btnUpdateCaseNote;
    TextView tvPCNumbers,tvMANumbers;
    Session session;
    PreferenceConnector preferenceConnector;
    RestClient restClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnAvailability = findViewById(R.id.btnAvailability);
        btnPedingConsult = findViewById(R.id.btnPedingConsult);
        btnMyAccounts = findViewById(R.id.btnMyAccounts);
        btnUpdateCaseNote = findViewById(R.id.btnUpdateCaseNote);
        btnAvailability.setOnClickListener(this);
        btnPedingConsult.setOnClickListener(this);
        btnMyAccounts.setOnClickListener(this);
        btnUpdateCaseNote.setOnClickListener(this);

        tvPCNumbers = findViewById(R.id.tvPCNumbers);
        tvMANumbers = findViewById(R.id.tvMANumbers);
        preferenceConnector = new PreferenceConnector(this);
        session = preferenceConnector.getSession();
        restClient = new RestClient(HomeActivity.this.session.getUrl(), this,super.getProgressBar());
        getNoOfPendingConsults();
        getNoOfMyConsults();
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.RECORD_AUDIO,
                android.Manifest.permission.INTERNET
       };

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String token = instanceIdResult.getToken();
                if(!HomeActivity.this.session.getFcmToken().equals(token) || com.example.hammadhanif.availabilityapplication.Utilities.Common.fcmTokenRefreshed){
                    HomeActivity.this.session.setFcmToken(token);
                    HashMap<String,String> changeValues = new HashMap<>();
                    if(!HomeActivity.this.session.getSystemId().equals("")){
                        changeValues.put("id",HomeActivity.this.session.getSystemId());
                    }
                    changeValues.put("fcmtoken",token);
                    changeValues.put("os",Build.MANUFACTURER
                            + " " + Build.MODEL + " " + Build.VERSION.RELEASE
                            + " " + Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName());
                    changeValues.put("uuid",HomeActivity.this.session.getUserId());
                    changeValues.put("name",HomeActivity.this.session.getUserName());

                    Parameters parameters = new Parameters(HomeActivity.this.session.getSessionId(), Common.ModuleName.FCM, Common.RequestType.SETENTRY,changeValues);
                    restClient.restRequest(parameters, new VolleyResponse() {
                        @Override
                        public void onSuccessResponse(String result, Session session) {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                //Log.d("Result", jsonObject.getString("id"));
                                HomeActivity.this.session.setSystemId(jsonObject.getString("id"));
                                HomeActivity.this.preferenceConnector.setSession(HomeActivity.this.session);
                                com.example.hammadhanif.availabilityapplication.Utilities.Common.fcmTokenRefreshed = false;
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
                    },HomeActivity.this.session);

                }
            }

        });
    }

    protected void refresh(){
        getNoOfPendingConsults();
        getNoOfMyConsults();
    }
    private void getNoOfMyConsults()
    {
        Parameters parameters = new Parameters(HomeActivity.this.session.getSessionId(), Common.ModuleName.Cases, Common.RequestType.GETENTRYLIST, com.example.hammadhanif.availabilityapplication.Utilities.Common.caseFields,"(cases.assigned_user_id='"+preferenceConnector.getSession().getUserId()+"' and cases_cstm.archive_c=0 and cases_cstm.consultationstatus_c='assigned')","cases.date_modified DESC");
        restClient.restRequest(parameters, new VolleyResponse() {
            @Override
            public void onSuccessResponse(String result, Session session) {
                HomeActivity.this.session = session;
                preferenceConnector.setSession(HomeActivity.this.session);
                try {
                    result = String.valueOf(com.example.hammadhanif.availabilityapplication.Utilities.Common.count(result));
                    result = result.length() == 1 ?  "0"+result : result;
                    tvMANumbers.setText(result);
                    tvMANumbers.setVisibility(View.VISIBLE);
                } catch (org.json.JSONException ex) {
                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new VolleyResponseError() {
            @Override
            public void onResponseError(String result) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        },HomeActivity.this.session);
    }
    private void getNoOfPendingConsults()
    {
        Parameters parameters = new Parameters(HomeActivity.this.session.getSessionId(), Common.ModuleName.Cases, Common.RequestType.GETENTRYLIST, com.example.hammadhanif.availabilityapplication.Utilities.Common.caseFields,"(cases_cstm.consultationstatus_c='pending' and cases_cstm.archive_c=0)","cases.date_modified DESC");
        restClient.restRequest(parameters, new VolleyResponse() {
            @Override
            public void onSuccessResponse(String result,Session session) {
                HomeActivity.this.session = session;
                preferenceConnector.setSession(HomeActivity.this.session);
                try {
                    result = String.valueOf(com.example.hammadhanif.availabilityapplication.Utilities.Common.count(result));
                    result = result.length() == 1 ?  "0"+result : result;
                    tvPCNumbers.setText(result);
                    tvPCNumbers.setVisibility(View.VISIBLE);
                } catch (org.json.JSONException ex) {
                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new VolleyResponseError() {
            @Override
            public void onResponseError(String result) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        },HomeActivity.this.session);
    }
    @Override
    public void onClick(View view) {
        Intent intent = null;
        if(((String)view.getTag()).equalsIgnoreCase(this.getResources().getString(R.string.avilability_info)))
            intent = new Intent(HomeActivity.this, AttorneyRecyclerView.class);
        else if(((String)view.getTag()).equalsIgnoreCase(this.getResources().getString(R.string.pending_consult))) {
            intent = new Intent(HomeActivity.this, PendingAccountsRecyclerView.class);
        }
        else if(((String)view.getTag()).equalsIgnoreCase(this.getResources().getString(R.string.my_account_info))) {
            intent = new Intent(HomeActivity.this, MyAccountsRecyclerView.class);
        }
        else if(((String)view.getTag()).equalsIgnoreCase(this.getResources().getString(R.string.update_case_notes))) {
            intent = new Intent(HomeActivity.this, CaseSearch.class);
        }
        startActivity(intent);
    }
}
