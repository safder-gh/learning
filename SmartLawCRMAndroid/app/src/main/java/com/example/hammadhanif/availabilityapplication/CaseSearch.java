package com.example.hammadhanif.availabilityapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hammadhanif.availabilityapplication.Preference.PreferenceConnector;
import com.example.hammadhanif.availabilityapplication.Utilities.BaseActivity;
import com.kmincorp.smartlawcrm.Session;
import com.kmincorp.smartlawcrm.Account;
import com.kmincorp.smartlawcrm.Parameters;
import com.kmincorp.smartlawcrm.RestClient;
import com.kmincorp.smartlawcrm.Utilities.Common;
import com.kmincorp.smartlawcrm.VolleyResponse;
import com.kmincorp.smartlawcrm.VolleyResponseError;

import java.util.ArrayList;

public class CaseSearch extends BaseActivity {


    EditText etClientName;
    EditText etClientPhone;
    EditText etCaseName;
    EditText etCaseDocketNumber;
    String query = null;
    Session session;
    PreferenceConnector preferenceConnector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_search);
        super.setSubTitle("Case Search");
        etClientName = findViewById(R.id.etName);
        etClientPhone = findViewById(R.id.etPhoneNumber);
        etCaseName = findViewById(R.id.etCsName);
        etCaseDocketNumber = findViewById(R.id.etDocketNumber);
        Button btnSearchCase = findViewById(R.id.btnSearchCase);
        preferenceConnector = new PreferenceConnector(this);


        btnSearchCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(etClientName.getText())) {
                    etClientName.setText(etClientName.getText().toString().trim().replace(".",""));
                }
                if(!TextUtils.isEmpty(etClientPhone.getText())) {
                    etClientPhone.setText(etClientPhone.getText().toString().trim().replace(".",""));
                }
                if(!TextUtils.isEmpty(etCaseName.getText())) {
                    etCaseName.setText(etCaseName.getText().toString().trim().replace(".",""));
                }
                if(!TextUtils.isEmpty(etCaseDocketNumber.getText())) {
                    etCaseDocketNumber.setText(etCaseDocketNumber.getText().toString().trim().replace(".",""));
                }

                if(TextUtils.isEmpty(etClientName.getText()) && TextUtils.isEmpty(etClientPhone.getText()) && TextUtils.isEmpty(etCaseName.getText()) && TextUtils.isEmpty(etCaseDocketNumber.getText()))
                    Toast.makeText(getApplicationContext(), "Provide at least on search criteria.", Toast.LENGTH_LONG).show();
                else
                {

                    if(TextUtils.isEmpty(etClientName.getText()) && TextUtils.isEmpty(etClientPhone.getText()))
                    {
                        if(!TextUtils.isEmpty(etCaseName.getText()) && !TextUtils.isEmpty(etCaseDocketNumber.getText())) {
                            String[] splitString = etCaseName.getText().toString().split(" ");
                            for(String s:splitString) {
                                if(TextUtils.isEmpty(query))
                                    query = "(cases.name like '%" + s + "%')";
                                else
                                    query = " or (cases.name like '%" + s + "%')";
                            }
                            query += " or (docketnumber_c like '%"+etCaseDocketNumber.getText()+"%')";
                            query = "("+query+")";
                        }
                        else if(!TextUtils.isEmpty(etCaseName.getText())) {
                            String[] splitString = etCaseName.getText().toString().split(" ");
                            for(String s:splitString) {
                                if(TextUtils.isEmpty(query))
                                    query = "(cases.name like '%" + s + "%')";
                                else
                                    query = " or (cases.name like '%" + s + "%')";
                            }
                            query = "("+query+")";
                        }
                        else if(!TextUtils.isEmpty(etCaseDocketNumber.getText())) {
                            query = "docketnumber_c like '%"+etCaseDocketNumber.getText()+"%'";
                        }
                        Intent intent = null;
                        intent = new Intent(CaseSearch.this, CasesRecyclerView.class);
                        intent.putExtra(CaseSearch.this.getResources().getString(R.string.queryWhere),query);
                        intent.putExtra(CaseSearch.this.getResources().getString(R.string.searchType), com.example.hammadhanif.availabilityapplication.Utilities.Common.SearchType.UpdateConsults.name());
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        if(!TextUtils.isEmpty(etClientName.getText())){
                            String[] splitString = etClientName.getText().toString().split(" ");
                            for(String s :splitString) {
                                if(TextUtils.isEmpty(query))
                                    query = "(accounts.name like '%" + s + "%')";
                                else
                                    query += " or (accounts.name like '%" + s + "%')";
                                query += " or (cumiddlename_c like '%" + s + "%')";
                                query += " or (cuslastname_c like '%" + s + "%')";
                            }
                            query = "("+query+")";
                        }
                        if(!TextUtils.isEmpty(etClientPhone.getText())){
                            if(TextUtils.isEmpty(query)){
                                query = getClientPhoneQuery();
                            } else {
                                query = "("+query+" or "+getClientPhoneQuery()+")";
                            }
                        }
                        CaseSearch.this.session = preferenceConnector.getSession();
                        Parameters parameters = new Parameters(CaseSearch.this.session.getSessionId(), Common.ModuleName.Accounts, Common.RequestType.GETENTRYLIST, com.example.hammadhanif.availabilityapplication.Utilities.Common.accountsFields, query, "accounts.date_modified DESC");
                        RestClient restClient = new RestClient(CaseSearch.this.session.getUrl(), CaseSearch.this,CaseSearch.this.getProgressBar());
                        restClient.restRequest(parameters, new VolleyResponse() {
                            @Override
                            public void onSuccessResponse(String result,Session session) {
                                CaseSearch.this.session = session;
                                preferenceConnector.setSession(CaseSearch.this.session);
                                try {
                                    ArrayList<Account> accounts;
                                    if(com.example.hammadhanif.availabilityapplication.Utilities.Common.count(result) == 0)
                                        accounts = new ArrayList<Account>();
                                    else
                                        accounts = Account.jsonToList(result);
                                    StringBuilder sb = new StringBuilder();
                                    for(Account account:accounts){
                                        sb.append('\'');
                                        sb.append(account.getId());
                                        sb.append('\'');
                                        sb.append(',');
                                    }
                                    query ="";
                                    if(!TextUtils.isEmpty(etCaseName.getText()) && !TextUtils.isEmpty(etCaseDocketNumber.getText())) {
                                        query = "cases.name like '%" + etCaseName.getText() + "%' or docketnumber_c like '%"+etCaseDocketNumber.getText()+"%'";
                                    }
                                    else if(!TextUtils.isEmpty(etCaseName.getText())) {
                                        query = "cases.name like '%" + etCaseName.getText() + "%'";
                                    }
                                    else if(!TextUtils.isEmpty(etCaseDocketNumber.getText())) {
                                        query = "docketnumber_c like '%"+etCaseDocketNumber.getText()+"%'";
                                    }
                                    if(sb.length() > 0) {
                                        if (TextUtils.isEmpty(query))
                                            query = "(account_id in(" + sb.toString().substring(0, sb.toString().length() - 1) + "))";
                                        else
                                            query = "(" + "((account_id in(" + sb.toString().substring(0, sb.toString().length() - 1) + "))" + " and (" + query + ")))";
                                    }
                                    if(!TextUtils.isEmpty(query)) {
                                        Intent intent = null;
                                        intent = new Intent(CaseSearch.this, CasesRecyclerView.class);
                                        intent.putExtra(CaseSearch.this.getResources().getString(R.string.queryWhere), query);
                                        intent.putExtra(CaseSearch.this.getResources().getString(R.string.searchType), com.example.hammadhanif.availabilityapplication.Utilities.Common.SearchType.UpdateConsults.name());
                                        startActivity(intent);
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.item_not_found), Toast.LENGTH_LONG).show();
                                    }
                                }
                                catch (org.json.JSONException ex) {
                                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new VolleyResponseError() {
                            @Override
                            public void onResponseError(String result) {
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                            }
                        },CaseSearch.this.session);

                    }
                }
            }
        });
    }
    protected void refresh(){
        etClientName.setText("");
        etClientPhone.setText("");;
        etCaseName.setText("");;
        etCaseDocketNumber.setText("");;
    }
    private String getClientPhoneQuery()
    {
        String query = null;
        query = "(accounts.phone_fax like '%" + etClientPhone.getText() + "%')";
        query += " or (accounts.phone_office like '%" + etClientPhone.getText() + "%')";
        query += " or (accounts.phone_alternate like '%" + etClientPhone.getText() + "%')";
        query += " or (phone_work_c like '%" + etClientPhone.getText() + "%')";
        query += " or (payeemobilephone_c like '%" + etClientPhone.getText() + "%')";
        query += " or (familymemberphone_c like '%" + etClientPhone.getText() + "%')";
        query += " or (payeehomephone_c like '%" + etClientPhone.getText() + "%')";
        query += " or (payeeworkphone_c like '%" + etClientPhone.getText() + "%')";
        query += " or (workphone_c like '%" + etClientPhone.getText() + "%')";
        query += " or (accounts.phone_office like '%" + etClientPhone.getText() + "%')";
        query = "("+query+")";
        return query;
    }
}
