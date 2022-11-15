package com.example.hammadhanif.availabilityapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hammadhanif.availabilityapplication.Adapters.AccountAdapter;
import com.example.hammadhanif.availabilityapplication.Adapters.CaseAdapter;
import com.example.hammadhanif.availabilityapplication.Preference.PreferenceConnector;
import com.example.hammadhanif.availabilityapplication.Utilities.BaseActivity;
import com.kmincorp.smartlawcrm.CRMCase;
import com.kmincorp.smartlawcrm.Session;
import com.kmincorp.smartlawcrm.Account;
import com.kmincorp.smartlawcrm.Parameters;
import com.kmincorp.smartlawcrm.RestClient;
import com.kmincorp.smartlawcrm.Utilities.Common;
import com.kmincorp.smartlawcrm.VolleyResponse;
import com.kmincorp.smartlawcrm.VolleyResponseError;

import java.util.ArrayList;

public class MyAccountsRecyclerView extends BaseActivity implements VolleyResponseError, VolleyResponse {

    private RestClient restClient;
    private Session session;
    private RecyclerView mRecyclerView;
    private View empty_view;
    PreferenceConnector preferenceConnector;
    ArrayList<CRMCase> cases;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts_recycler_view);
        super.setSubTitle(getResources().getString(R.string.my_clients));
        preferenceConnector = new PreferenceConnector(getApplicationContext());
        session = preferenceConnector.getSession();
        restClient = new RestClient(session.getUrl(), MyAccountsRecyclerView.this,super.getProgressBar());
        //Parameters parameters = new Parameters(session.getSessionId(), Common.ModuleName.Accounts, Common.RequestType.GETENTRYLIST, com.example.hammadhanif.availabilityapplication.Utilities.Common.accountsFields,"accounts.assigned_user_id='"+session.getUserId()+"'","accounts.date_modified DESC");
        Parameters parameters = new Parameters(session.getSessionId(), Common.ModuleName.Cases, Common.RequestType.GETENTRYLIST, com.example.hammadhanif.availabilityapplication.Utilities.Common.caseFields,"(cases.assigned_user_id='"+preferenceConnector.getSession().getUserId()+"'  and cases_cstm.archive_c=0 and cases_cstm.consultationstatus_c='assigned')","cases.date_modified DESC");
        restClient.restRequest(parameters, this,this,session);
        mRecyclerView = findViewById(R.id.my_recycler_view);
        empty_view = findViewById(R.id.empty_view);
        // use a linear layout manager
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }
    protected void refresh(){
        Parameters parameters = new Parameters(session.getSessionId(), Common.ModuleName.Cases, Common.RequestType.GETENTRYLIST, com.example.hammadhanif.availabilityapplication.Utilities.Common.caseFields,"(cases.assigned_user_id='"+preferenceConnector.getSession().getUserId()+"'  and cases_cstm.archive_c=0   and cases_cstm.consultationstatus_c='assigned')","cases.date_modified DESC");
        restClient.restRequest(parameters, this,this,session);
    }
    @Override
    public  void onResponseError(String result){
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
    }
    @Override
    public  void onSuccessResponse(String result,Session session) {
        MyAccountsRecyclerView.this.session = session;
        preferenceConnector.setSession(MyAccountsRecyclerView.this.session);
        try {
            if(com.example.hammadhanif.availabilityapplication.Utilities.Common.count(result) == 0)
                cases = new ArrayList<CRMCase>();
            else
                cases = CRMCase.jsonToList(result);
            StringBuilder sb = new StringBuilder();
            for(CRMCase crmCase:cases){
                sb.append('\'');
                sb.append(crmCase.getAccountId());
                sb.append('\'');
                sb.append(',');
            }
            if(sb.toString().length() > 0){
                Parameters parameters = new Parameters(MyAccountsRecyclerView.this.session.getSessionId(), Common.ModuleName.Accounts, Common.RequestType.GETENTRYLIST, com.example.hammadhanif.availabilityapplication.Utilities.Common.accountsFields, "accounts.id in(" + sb.toString().substring(0, sb.toString().length() - 1) + ")", "accounts.date_modified DESC");
                restClient.restRequest(parameters, new VolleyResponse() {
                    @Override
                    public void onSuccessResponse(String result,Session session) {
                        MyAccountsRecyclerView.this.session = session;
                        preferenceConnector.setSession(MyAccountsRecyclerView.this.session);
                        ArrayList<Account> accounts = null;
                        try {
                            if(com.example.hammadhanif.availabilityapplication.Utilities.Common.count(result) == 0)
                                accounts = new ArrayList<>();
                            else
                                accounts = Account.jsonToList(result);
                        }
                        catch (org.json.JSONException ex) {
                            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        if(accounts.size() > 0) {
                            AccountAdapter accountAdapter = new AccountAdapter(accounts, new TextViewClickListner());
                            mRecyclerView.setAdapter(accountAdapter);
                        }
                        else
                        {
                            MyAccountsRecyclerView.this.mRecyclerView.setVisibility(View.GONE);
                            MyAccountsRecyclerView.this.empty_view.setVisibility(View.VISIBLE);
                        }
                    }
                }, MyAccountsRecyclerView.this,MyAccountsRecyclerView.this.session);
            }
            else
            {
                this.mRecyclerView.setVisibility(View.GONE);
                this.empty_view.setVisibility(View.VISIBLE);
            }

        } catch (org.json.JSONException ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
//    public  void oldonSuccessResponse(String result,Session session) {
//        this.session = session;
//        this.preferenceConnector.setSession(this.session);
//        try {
//
//            ArrayList<Account> accounts = Account.jsonToList(result);
//            if(accounts.size() > 0) {
//                AccountAdapter accountAdapter = new AccountAdapter(accounts, new TextViewClickListner());
//                mRecyclerView.setAdapter(accountAdapter);
//            }
//            else
//            {
//                this.mRecyclerView.setVisibility(View.GONE);
//                this.empty_view.setVisibility(View.VISIBLE);
//            }
//
//        } catch (org.json.JSONException ex) {
//            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
//        }
//    }
    private class TextViewClickListner implements TextView.OnClickListener {
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {

            Intent intent = null;
            intent = new Intent(MyAccountsRecyclerView.this, CasesRecyclerView.class);
            intent.putExtra(MyAccountsRecyclerView.this.getResources().getString(R.string.queryWhere),"(cases.account_id='"+v.getTag(R.string.accountIdKey).toString()+"' and cases_cstm.consultationstatus_c='assigned')");
            intent.putExtra(MyAccountsRecyclerView.this.getResources().getString(R.string.searchType), com.example.hammadhanif.availabilityapplication.Utilities.Common.SearchType.MyConsults.name());
            startActivity(intent);
//            HashMap<String,String> changeValues = new HashMap<>();
//            changeValues.put("id",v.getTag(R.string.accountIdKey).toString());
//            changeValues.put("assigned_user_id",sessionInfo.getUserId());
//            changeValues.put("assigned_user_name",sessionInfo.getUserName());
//            changeValues.put("status_c","assigned");
//            Parameters parameters = new Parameters(session.getSessionId(), Common.ModuleName.Cases, Common.RequestType.SETENTRY.ENTRY,changeValues);
//            restClient.restRequest(parameters, new VolleyResponse() {
//                @Override
//                public void onSuccessResponse(String result) {
//                    Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_LONG).show();
//                }
//            }, MyAccountsRecyclerView.this);
        }
    }
}
