package com.example.hammadhanif.availabilityapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hammadhanif.availabilityapplication.Adapters.AccountAdapter;
import com.example.hammadhanif.availabilityapplication.Preference.PreferenceConnector;
import com.example.hammadhanif.availabilityapplication.Utilities.BaseActivity;
import com.kmincorp.smartlawcrm.Session;
import com.kmincorp.smartlawcrm.Account;
import com.kmincorp.smartlawcrm.CRMCase;
import com.kmincorp.smartlawcrm.Parameters;
import com.kmincorp.smartlawcrm.RestClient;
import com.kmincorp.smartlawcrm.Utilities.Common;
import com.kmincorp.smartlawcrm.VolleyResponse;
import com.kmincorp.smartlawcrm.VolleyResponseError;

import java.util.ArrayList;

public class PendingAccountsRecyclerView extends BaseActivity implements VolleyResponseError, VolleyResponse {

    private PreferenceConnector preferenceConnector;
    private RestClient restClient;
    private Session session;
    private RecyclerView mRecyclerView;
    private View empty_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts_recycler_view);
        super.setSubTitle("Pending Consult");
        preferenceConnector = new PreferenceConnector(getApplicationContext());
        session = preferenceConnector.getSession();
        restClient = new RestClient(session.getUrl(), PendingAccountsRecyclerView.this,super.getProgressBar());
        empty_view = findViewById(R.id.empty_view);
        getPendingCases();
        mRecyclerView = findViewById(R.id.my_recycler_view);
        // use a linear layout manager
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }
    protected void refresh(){
        getPendingCases();
    }
    @Override
    protected void onResume() {
        super.onResume();
        getPendingCases();
    }

    @Override
    public  void onResponseError(String result){

        Log.e("VolleyResponseError",result);
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
    }
    @Override
    public  void onSuccessResponse(String result,Session session) {
        this.session = session;
        this.preferenceConnector.setSession(this.session);
        try {
            ArrayList<Account> accounts;
            if(com.example.hammadhanif.availabilityapplication.Utilities.Common.count(result) == 0)
                accounts = new ArrayList<>();
            else
                accounts = Account.jsonToList(result);
            if(accounts.size() > 0) {
                AccountAdapter accountAdapter = new AccountAdapter(accounts, new TextViewClickListner());
                mRecyclerView.setAdapter(accountAdapter);
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
    private void getPendingCases(){
        ArrayList<CRMCase> arrayList = new ArrayList<CRMCase>();
        Parameters parameters = new Parameters(session.getSessionId(), Common.ModuleName.Cases, Common.RequestType.GETENTRYLIST, com.example.hammadhanif.availabilityapplication.Utilities.Common.caseFields,"(cases_cstm.consultationstatus_c='pending'  and cases_cstm.archive_c=0)","cases.date_modified DESC");
        restClient.restRequest(parameters, new VolleyResponse() {
            @Override
            public void onSuccessResponse(String result,Session session) {
                PendingAccountsRecyclerView.this.session = session;
                PendingAccountsRecyclerView.this.preferenceConnector.setSession(PendingAccountsRecyclerView.this.session);
                //Log.d("PENDINGCONSULT",result);
                StringBuilder sb = new StringBuilder();
                try {
                    ArrayList<CRMCase> cases;
                    if(com.example.hammadhanif.availabilityapplication.Utilities.Common.count(result) == 0)
                        cases = new ArrayList<>();
                    else
                        cases = CRMCase.jsonToList(result);
                    for(CRMCase crmCase:cases){

                        sb.append('\'');
                        sb.append(crmCase.getAccountId());
                        sb.append('\'');
                        sb.append(',');
                    }
                }
                catch (org.json.JSONException ex) {
                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
                if(sb.length() > 0 ) {
                    Parameters parameters = new Parameters(session.getSessionId(), Common.ModuleName.Accounts, Common.RequestType.GETENTRYLIST, com.example.hammadhanif.availabilityapplication.Utilities.Common.accountsFields, "accounts.id in(" + sb.toString().substring(0, sb.toString().length() - 1) + ")", "accounts.date_modified DESC");
                    restClient.restRequest(parameters, PendingAccountsRecyclerView.this, PendingAccountsRecyclerView.this,PendingAccountsRecyclerView.this.session);
                }
                else {
                    PendingAccountsRecyclerView.this.mRecyclerView.setVisibility(View.GONE);
                    PendingAccountsRecyclerView.this.empty_view.setVisibility(View.VISIBLE);
                }
            }
        }, PendingAccountsRecyclerView.this,PendingAccountsRecyclerView.this.session);
    }
    private class TextViewClickListner implements TextView.OnClickListener {
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {

            Intent intent = null;
            intent = new Intent(PendingAccountsRecyclerView.this, CasesRecyclerView.class);
            intent.putExtra(PendingAccountsRecyclerView.this.getResources().getString(R.string.queryWhere),"(account_id='"+v.getTag(R.string.accountIdKey).toString()+"' and (cases_cstm.consultationstatus_c='pending'  and cases_cstm.archive_c=0))");
            intent.putExtra(PendingAccountsRecyclerView.this.getResources().getString(R.string.searchType), com.example.hammadhanif.availabilityapplication.Utilities.Common.SearchType.PendingConsult.name());
            startActivity(intent);
        }
    }
}
