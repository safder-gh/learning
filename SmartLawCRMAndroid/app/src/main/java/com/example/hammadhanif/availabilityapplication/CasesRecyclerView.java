package com.example.hammadhanif.availabilityapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hammadhanif.availabilityapplication.Adapters.CaseAdapter;
import com.example.hammadhanif.availabilityapplication.Adapters.ChargesAdapter;
import com.example.hammadhanif.availabilityapplication.Adapters.CountyAdapter;
import com.example.hammadhanif.availabilityapplication.Adapters.MapAdapter;
import com.example.hammadhanif.availabilityapplication.Preference.PreferenceConnector;
import com.example.hammadhanif.availabilityapplication.Utilities.BaseActivity;
import com.example.hammadhanif.availabilityapplication.Utilities.Charges;
import com.example.hammadhanif.availabilityapplication.Utilities.County;
import com.example.hammadhanif.availabilityapplication.Utilities.FileChooser;
import com.google.gson.JsonObject;
import com.kmincorp.smartlawcrm.Session;
import com.kmincorp.smartlawcrm.Account;
import com.kmincorp.smartlawcrm.CRMCase;
import com.kmincorp.smartlawcrm.Note;
import com.kmincorp.smartlawcrm.Parameters;
import com.kmincorp.smartlawcrm.RestClient;
import com.kmincorp.smartlawcrm.Utilities.Common;
import com.kmincorp.smartlawcrm.VolleyResponse;
import com.kmincorp.smartlawcrm.VolleyResponseError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CasesRecyclerView extends BaseActivity implements VolleyResponseError, VolleyResponse , Button.OnClickListener {

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */


    private RecyclerView mRecyclerView;
    private View empty_view;
    private RestClient restClient;
    private Session session;
    ArrayList<CRMCase> cases;
    View currentView;
    String buttonCaption="";
    File file;
    PreferenceConnector preferenceConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cases_recycler_view);
        super.setSubTitle("Cases");
        if(getIntent().hasExtra(this.getResources().getString(R.string.searchType))) {
            if (getIntent().getStringExtra(this.getResources().getString(R.string.searchType)).equalsIgnoreCase(com.example.hammadhanif.availabilityapplication.Utilities.Common.SearchType.PendingConsult.name())) {
                buttonCaption = this.getResources().getString(R.string.up_cnotes);
            }else{
                buttonCaption = "Edit";
            }
        }else{
            buttonCaption = "Edit";
        }

        //Toast.makeText(getApplicationContext(), getIntent().getStringExtra(this.getResources().getString(R.string.accountIdKey)), Toast.LENGTH_LONG).show();
        preferenceConnector = new PreferenceConnector(getApplicationContext());
        CasesRecyclerView.this.session = preferenceConnector.getSession();
        restClient = new RestClient(CasesRecyclerView.this.session.getUrl(), CasesRecyclerView.this,super.getProgressBar());
        Parameters parameters;
        if(getIntent().getStringExtra(getResources().getString(R.string.notification_case_id))!=null)
            parameters = new Parameters(CasesRecyclerView.this.session.getSessionId(), Common.ModuleName.Cases, Common.RequestType.GETENTRYLIST, com.example.hammadhanif.availabilityapplication.Utilities.Common.caseFields,"cases.id = '"+getIntent().getStringExtra(getResources().getString(R.string.notification_case_id))+"'","cases.date_modified DESC");
        else
            parameters = new Parameters(CasesRecyclerView.this.session.getSessionId(), Common.ModuleName.Cases, Common.RequestType.GETENTRYLIST, com.example.hammadhanif.availabilityapplication.Utilities.Common.caseFields,getIntent().getStringExtra(this.getResources().getString(R.string.queryWhere)),"cases.date_modified DESC");
        restClient.restRequest(parameters, this,this,CasesRecyclerView.this.session);
        RecyclerView.LayoutManager mLayoutManager;
        mRecyclerView = findViewById(R.id.my_recycler_view);
        empty_view = findViewById(R.id.empty_view);
        // use a linear layout manager
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        if (com.example.hammadhanif.availabilityapplication.Utilities.Common.caseConsultationStatus == null) {
            RestClient ddlClient = new RestClient(CasesRecyclerView.this.session.getUrl() + "/index.php?entryPoint=GetDropDownList", this);
            ddlClient.dropDownListRequest("client_status_list",new VolleyResponse() {
                @Override
                public void onSuccessResponse(String result, Session session) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        com.example.hammadhanif.availabilityapplication.Utilities.Common.caseConsultationStatus = com.example.hammadhanif.availabilityapplication.Utilities.Common.parseJSONObjectToMap(jsonObject);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }, CasesRecyclerView.this);
        }
    }
    protected void refresh(){
        Parameters parameters = new Parameters(session.getSessionId(), Common.ModuleName.Cases, Common.RequestType.GETENTRYLIST, com.example.hammadhanif.availabilityapplication.Utilities.Common.caseFields,getIntent().getStringExtra(this.getResources().getString(R.string.queryWhere)),"cases.date_modified DESC");
        restClient.restRequest(parameters, this,this,CasesRecyclerView.this.session);
    }
    @Override
    public  void onResponseError(String result){

        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
    }



    @Override
    public  void onSuccessResponse(String result,Session session) {
        CasesRecyclerView.this.session = session;
        preferenceConnector.setSession(CasesRecyclerView.this.session);
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
                Parameters parameters = new Parameters(CasesRecyclerView.this.session.getSessionId(), Common.ModuleName.Accounts, Common.RequestType.GETENTRYLIST, com.example.hammadhanif.availabilityapplication.Utilities.Common.accountsFields, "accounts.id in(" + sb.toString().substring(0, sb.toString().length() - 1) + ")", "accounts.date_modified DESC");
                restClient.restRequest(parameters, new VolleyResponse() {
                    @Override
                    public void onSuccessResponse(String result,Session session) {
                        CasesRecyclerView.this.session = session;
                        preferenceConnector.setSession(CasesRecyclerView.this.session);
                        try {
                            ArrayList<Account> accounts;
                            if(com.example.hammadhanif.availabilityapplication.Utilities.Common.count(result) == 0)
                                accounts = new ArrayList<>();
                            else
                                accounts = Account.jsonToList(result);
                            for(CRMCase crmCase:cases){
                                crmCase.setAccount(getAccount(crmCase.getAccountId(),accounts));
                            }
                        }
                        catch (org.json.JSONException ex) {
                            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        CaseAdapter mAdapter = new CaseAdapter(cases,CasesRecyclerView.this,buttonCaption);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                }, CasesRecyclerView.this,CasesRecyclerView.this.session);
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
    @Override
    public void onClick(View v) {
        this.currentView = v;

        if(County.getArrayList() == null){
            Parameters parameters = new Parameters(CasesRecyclerView.this.session.getSessionId(), Common.ModuleName.ct_county, Common.RequestType.GETENTRYLIST, com.example.hammadhanif.availabilityapplication.Utilities.Common.countyFields,"","");
            restClient.restRequest(parameters, new VolleyResponse() {
                @Override
                public void onSuccessResponse(String result,Session session) {
                    CasesRecyclerView.this.session = session;
                    preferenceConnector.setSession(CasesRecyclerView.this.session);
                    try
                    {
                        County.setArrayList(com.kmincorp.smartlawcrm.County.jsonToList(result));
                        County.getArrayList().add( new com.kmincorp.smartlawcrm.County("-1","None","None"));
                        CustomAlertDialog customAlertDialog = new CustomAlertDialog(CasesRecyclerView.this, getCase( CasesRecyclerView.this.currentView.getTag(R.string.caseIdKey).toString()));
                        customAlertDialog.show();
                    }
                    catch (org.json.JSONException ex){
                        Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }, CasesRecyclerView.this,CasesRecyclerView.this.session);
        }
        else{
            CustomAlertDialog customAlertDialog = new CustomAlertDialog(CasesRecyclerView.this, getCase( CasesRecyclerView.this.currentView.getTag(R.string.caseIdKey).toString()));
            customAlertDialog.show();
        }
 /*       if(Charges.getChargesList() == null){
            Parameters parameters = new Parameters(CasesRecyclerView.this.session.getSessionId(), Common.ModuleName.CH12_charges, Common.RequestType.GETENTRYLIST, com.example.hammadhanif.availabilityapplication.Utilities.Common.chargesFields,"","");
            restClient.restRequest(parameters, new VolleyResponse() {
                @Override
                public void onSuccessResponse(String result,Session session) {
                    CasesRecyclerView.this.session = session;
                    preferenceConnector.setSession(CasesRecyclerView.this.session);
                    try {
                        Charges.setChargesList(com.kmincorp.smartlawcrm.Charges.jsonToList(result));
                        Charges.getChargesList().add( new com.kmincorp.smartlawcrm.Charges("-1","None","None"));
                        if(County.getArrayList() == null){
                            Parameters parameters = new Parameters(CasesRecyclerView.this.session.getSessionId(), Common.ModuleName.ct_county, Common.RequestType.GETENTRYLIST, com.example.hammadhanif.availabilityapplication.Utilities.Common.countyFields,"","");
                            restClient.restRequest(parameters, new VolleyResponse() {
                                @Override
                                public void onSuccessResponse(String result,Session session) {
                                    CasesRecyclerView.this.session = session;
                                    preferenceConnector.setSession(CasesRecyclerView.this.session);
                                try
                                {
                                    County.setArrayList(com.kmincorp.smartlawcrm.County.jsonToList(result));
                                    County.getArrayList().add( new com.kmincorp.smartlawcrm.County("-1","None","None"));
                                    CustomAlertDialog customAlertDialog = new CustomAlertDialog(CasesRecyclerView.this, getCase( CasesRecyclerView.this.currentView.getTag(R.string.caseIdKey).toString()));
                                    customAlertDialog.show();
                                }
                                catch (org.json.JSONException ex){
                                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }, CasesRecyclerView.this,CasesRecyclerView.this.session);
                        }
                        else{
                            CustomAlertDialog customAlertDialog = new CustomAlertDialog(CasesRecyclerView.this, getCase( CasesRecyclerView.this.currentView.getTag(R.string.caseIdKey).toString()));
                            customAlertDialog.show();
                        }
                    }catch (org.json.JSONException ex) {
                        Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }, this,CasesRecyclerView.this.session);
        }
        else {
            CustomAlertDialog customAlertDialog = new CustomAlertDialog(CasesRecyclerView.this, getCase(  v.getTag(R.string.caseIdKey).toString()));
            customAlertDialog.show();
        }*/
    }
    private Account getAccount(String accountId,ArrayList<Account> accounts){
        Account account = null;
        for(Account innerAccount:accounts){
         if(innerAccount.getId().equalsIgnoreCase(accountId))   {
             account = innerAccount;
             break;
         }
        }
        return  account;
    }
    private CRMCase getCase(String caseId){
        CRMCase crmCase = null;
        for(CRMCase innerCrmCase:this.cases){
            if(innerCrmCase.getId().equalsIgnoreCase(caseId) ){
                crmCase = innerCrmCase;
                break;
            }
        }
        return crmCase;
    }
//    private void SetConsultAttorneyStatus(String caseId){
//        HashMap<String,String> changeValues = new HashMap<>();
//            changeValues.put("id",caseId);
//            changeValues.put("user_id_c",CasesRecyclerView.this.session.getUserId());
//            changeValues.put("consult_c",CasesRecyclerView.this.session.getUserName());
//            changeValues.put("consultationstatus_c","assigned");
//            Parameters parameters = new Parameters(CasesRecyclerView.this.session.getSessionId(), Common.ModuleName.Cases, Common.RequestType.SETENTRY.ENTRY,changeValues);
//
//            restClient.restRequest(parameters, new VolleyResponse() {
//                @Override
//                public void onSuccessResponse(String result,Session session) {
//                    CasesRecyclerView.this.session = session;
//                    preferenceConnector.setSession(CasesRecyclerView.this.session);
//                    Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_LONG).show();
//                }
//            }, CasesRecyclerView.this,CasesRecyclerView.this.session);
//    }

    private class CustomAlertDialog extends AlertDialog {
        CRMCase crmCase;
        public CustomAlertDialog(@NonNull Context context,CRMCase crmCase) {
            super(context);
            this.crmCase = crmCase;
            View view = getLayoutInflater().inflate(R.layout.case_detail_popup,null);
            this.setView(view);
            final TextView tvClientName = view.findViewById(R.id.tvClientName);
            tvClientName.setText(com.example.hammadhanif.availabilityapplication.Utilities.Common.fromHtml(crmCase.getAccount().getName()));
            final EditText etPhoneNumber = view.findViewById(R.id.etPhoneNumber);
            etPhoneNumber.setText(crmCase.getAccount().getPhoneWork());
            final EditText etConsultationNotes = view.findViewById(R.id.etConsultationNotes);
            etConsultationNotes.setText(com.example.hammadhanif.availabilityapplication.Utilities.Common.fromHtml(crmCase.getConsultationNotes()));
            final EditText etCaseSynopsis = view.findViewById(R.id.etCaseSynopsis);
            etCaseSynopsis.setText(com.example.hammadhanif.availabilityapplication.Utilities.Common.fromHtml(crmCase.getCaseSynopsis()));
            final EditText etCharges = view.findViewById(R.id.etCharges);
            etCharges.setText(com.example.hammadhanif.availabilityapplication.Utilities.Common.fromHtml(crmCase.getCharges()));
            final TextView tvFname =  view.findViewById(R.id.tvFname);
            final CheckBox cbFile = view.findViewById(R.id.cbFile);

            Button btnCancle = view.findViewById(R.id.btnCancel);
            btnCancle.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            Button btnTextNote = view.findViewById(R.id.btnTextNote);


            /*final Spinner chargesSpinner = view.findViewById(R.id.spCharges);
            ChargesAdapter chargesAdapter = new ChargesAdapter(CasesRecyclerView.this,R.layout.spinner_charges_item,R.id.tvCharges,Charges.getChargesList());
            chargesSpinner.setAdapter(chargesAdapter);*/
            /*if(!crmCase.getChargesId().equalsIgnoreCase(""))
                chargesSpinner.setSelection(Common.getChrgesPositionById(Charges.getChargesList(),crmCase.getChargesId()));
            else
                chargesSpinner.setSelection(Common.getChrgesPositionById(Charges.getChargesList(),"-1"));*/
            /*chargesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    com.kmincorp.smartlawcrm.Charges charges = (com.kmincorp.smartlawcrm.Charges)parent.getSelectedItem();
                    chargesSpinner.setTag(charges.getName());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });*/

            final Spinner countySpinner = view.findViewById(R.id.spCounty);
            CountyAdapter countyAdapter = new CountyAdapter(CasesRecyclerView.this,R.layout.spinner_county_item,R.id.tvCounty,County.getArrayList());
            countySpinner.setAdapter(countyAdapter);

            if(!crmCase.getCountyId().equalsIgnoreCase(""))
                countySpinner.setSelection(Common.getCountyPositionById(County.getArrayList(),crmCase.getCountyId()));
            else
                countySpinner.setSelection(Common.getCountyPositionById(County.getArrayList(),"-1"));

            final Spinner caseConsultationStatusSpinner = view.findViewById(R.id.spStatus);
            MapAdapter caseConsultationStatusAdapter = new MapAdapter(com.example.hammadhanif.availabilityapplication.Utilities.Common.caseConsultationStatus);
            caseConsultationStatusSpinner.setAdapter(caseConsultationStatusAdapter);
            caseConsultationStatusSpinner.setSelection(com.example.hammadhanif.availabilityapplication.Utilities.Common.getIndexByKey(com.example.hammadhanif.availabilityapplication.Utilities.Common.caseConsultationStatus,crmCase.getConsultationStatus()));

            btnTextNote.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    updateCase(((com.kmincorp.smartlawcrm.County)countySpinner.getSelectedItem()).getName(),((com.kmincorp.smartlawcrm.County)countySpinner.getSelectedItem()).getId(),etCharges.getText().toString(),etCaseSynopsis.getText().toString(),etConsultationNotes.getText().toString(),((Map.Entry<String, String>)caseConsultationStatusSpinner.getSelectedItem()).getKey(),CustomAlertDialog.this.crmCase,cbFile.isChecked());
                    dismiss();
                }
            });
            Button btnVoiceNote = view.findViewById(R.id.btnAddVoiceNote);
            btnVoiceNote.setTag(R.string.caseIdKey,crmCase.getId());
            btnVoiceNote.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(final View v) {
                    FileChooser fileChooser = new FileChooser(CasesRecyclerView.this);
                    fileChooser.setFileListener(new FileChooser.FileSelectedListener() {
                        @Override
                        public void fileSelected(File file) {
                            CasesRecyclerView.this.file = file;
                            tvFname.setText(CasesRecyclerView.this.file.getName());
                            cbFile.setChecked(true);
                        }
                    });
                    fileChooser.showDialog();
                }
            });
        }
        private void updateCase(String county,String countyId,String charges,String caseSynopsis,String consultationNotes,String consultationStatus, final CRMCase crmCase ,final Boolean includeFile){
            HashMap<String,String> changeValues = new HashMap<>();
            changeValues.put("id",crmCase.getId());
            if(countyId.equalsIgnoreCase("-1")) {
                changeValues.put("ct_county_id_c", "");
            }
            else {
                changeValues.put("ct_county_id_c", countyId);
            }
            /*if(chargesId.equalsIgnoreCase("-1")){
                changeValues.put("ch12_charges_id_c","");
                //changeValues.put("casesynopsis_c","");
            }
            else {
                changeValues.put("ch12_charges_id_c",chargesId);
                //changeValues.put("casesynopsis_c",caseSynopsis);
            }*/
            //changeValues.put("txt_charges_c",charges);
            
            changeValues.put("casesynopsis_c",caseSynopsis.replaceAll("\n", "").replace("\"","\\\""));
            changeValues.put("notes_c",consultationNotes.replaceAll("\n", "").replace("\"","\\\""));

            if (getIntent().getStringExtra(CasesRecyclerView.this.getResources().getString(R.string.searchType)).equalsIgnoreCase(com.example.hammadhanif.availabilityapplication.Utilities.Common.SearchType.PendingConsult.name())) {
                if(consultationStatus.equalsIgnoreCase("assigned")) {
                    changeValues.put("user_id_c", CasesRecyclerView.this.session.getUserId());
                    changeValues.put("assigned_user_id", CasesRecyclerView.this.session.getUserId());
                    changeValues.put("consultationstatus_c", "assigned");
                }else{
                    changeValues.put("consultationstatus_c",consultationStatus);
                }
            }else{
                changeValues.put("consultationstatus_c",consultationStatus);
            }

            Parameters parameters = new Parameters(CasesRecyclerView.this.session.getSessionId(), Common.ModuleName.Cases, Common.RequestType.SETENTRY,changeValues);

            restClient.restRequest(parameters, new VolleyResponse() {
                @Override
                public void onSuccessResponse(String result,Session session) {
                    CasesRecyclerView.this.session = session;
                    preferenceConnector.setSession(CasesRecyclerView.this.session);
                    if(CasesRecyclerView.this.file != null && includeFile ){
                        HashMap<String,String> entryParams = new HashMap<>();
                        entryParams.put("name",crmCase.getAccount().getName());
                        entryParams.put("parent_type",Common.ModuleName.Cases.name());
                        entryParams.put("parent_id",crmCase.getId());
                        Parameters parameters = new Parameters(preferenceConnector.getSession().getSessionId(),Common.ModuleName.Notes, Common.RequestType.SETENTRY,entryParams);
                        restClient.restRequest(parameters, new VolleyResponse() {
                            @Override
                            public void onSuccessResponse(String result,Session session) {
                                CasesRecyclerView.this.session = session;
                                preferenceConnector.setSession(CasesRecyclerView.this.session);
                                Note note = new Note();
                                try {
                                    note.fillInstance(new JSONObject(result));
                                    note.setFileName(CasesRecyclerView.this.file.getName());
                                    try {
                                        note.setFile(com.example.hammadhanif.availabilityapplication.Utilities.Common.encodeFileToBase64Binary(CasesRecyclerView.this.file));
                                    }
                                    catch (IOException ex){
                                        Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                    Parameters noteParameters = new Parameters(preferenceConnector.getSession().getSessionId(),Common.RequestType.SETNOTEATTACHMENT,note);
                                    restClient.restRequest(noteParameters, new VolleyResponse() {
                                        @Override
                                        public void onSuccessResponse(String result,Session session) {
                                            CasesRecyclerView.this.session = session;
                                            preferenceConnector.setSession(CasesRecyclerView.this.session);
                                        }
                                    }, new VolleyResponseError() {
                                        @Override
                                        public void onResponseError(String result) {
                                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                        }
                                    },CasesRecyclerView.this.session);
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
                        },CasesRecyclerView.this.session);
                    }
                   Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                }
            }, CasesRecyclerView.this,CasesRecyclerView.this.session);
        }
        /*public CustomAlertDialog(@NonNull Context context, int themeResId) {
            super(context, themeResId);
        }

        public CustomAlertDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
        }*/
    }
}
