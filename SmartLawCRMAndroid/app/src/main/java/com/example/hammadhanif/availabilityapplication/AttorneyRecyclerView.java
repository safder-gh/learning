package com.example.hammadhanif.availabilityapplication;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.hammadhanif.availabilityapplication.Adapters.AttorneyAdapter;
import com.example.hammadhanif.availabilityapplication.Preference.PreferenceConnector;
import com.example.hammadhanif.availabilityapplication.Utilities.BaseActivity;
import com.kmincorp.smartlawcrm.Session;
import com.kmincorp.smartlawcrm.Attorney;
import com.kmincorp.smartlawcrm.Parameters;
import com.kmincorp.smartlawcrm.RestClient;
import com.kmincorp.smartlawcrm.Utilities.Common;
import com.kmincorp.smartlawcrm.VolleyResponse;
import com.kmincorp.smartlawcrm.VolleyResponseError;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class AttorneyRecyclerView extends BaseActivity implements VolleyResponseError, VolleyResponse {

    private RecyclerView mRecyclerView;
    //private final Handler handler = new Handler();
    private RestClient restClient;
    private Session session;
    private View empty_view;
    PreferenceConnector preferenceConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attorney_recycler_view);
        preferenceConnector = new PreferenceConnector(getApplicationContext());
        AttorneyRecyclerView.this.session = preferenceConnector.getSession();
        restClient = new RestClient(AttorneyRecyclerView.this.session.getUrl(), AttorneyRecyclerView.this,super.getProgressBar());
        Parameters parameters = new Parameters(AttorneyRecyclerView.this.session.getSessionId(),Common.ModuleName.km_AttorneyAvailability,Common.RequestType.GETENTRYLIST, com.example.hammadhanif.availabilityapplication.Utilities.Common.availabilityFields,Common.ModuleName.km_attorneyavailability_users, com.example.hammadhanif.availabilityapplication.Utilities.Common.km_attorneyavailability_usersFields);
        restClient.restRequest(parameters, this,this,AttorneyRecyclerView.this.session);
        RecyclerView.LayoutManager mLayoutManager;
        mRecyclerView = findViewById(R.id.my_recycler_view);
        // use a linear layout manager
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        empty_view = findViewById(R.id.empty_view);
        //onFinishGetRequest(URL);

        //doTheAutoRefresh();
    }

    protected void refresh(){
        Parameters parameters = new Parameters(AttorneyRecyclerView.this.session.getSessionId(),Common.ModuleName.km_AttorneyAvailability,Common.RequestType.GETENTRYLIST, com.example.hammadhanif.availabilityapplication.Utilities.Common.availabilityFields,Common.ModuleName.km_attorneyavailability_users, com.example.hammadhanif.availabilityapplication.Utilities.Common.km_attorneyavailability_usersFields);
        restClient.restRequest(parameters, this,this,AttorneyRecyclerView.this.session);
    }
    @Override
    public  void onResponseError(String result){

        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
    }
    @Override
    public  void onSuccessResponse(String result,Session session) {
        AttorneyRecyclerView.this.session = session;
        AttorneyRecyclerView.this.preferenceConnector.setSession(AttorneyRecyclerView.this.session);
        try {
            ArrayList<Attorney> attorneys;
            if(com.example.hammadhanif.availabilityapplication.Utilities.Common.count(result) == 0)
               attorneys = new ArrayList<>();
            else
                attorneys = Attorney.jsonToList(result);
            if(attorneys.size() > 0) {
                AttorneyAdapter mAdapter = new AttorneyAdapter(attorneys, AttorneyRecyclerView.this.session, new AvailabilitySwitch(), new AvailabilityInOfficeSwitch());
                mRecyclerView.setAdapter(mAdapter);
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

    private class AvailabilitySwitch implements  CompoundButton.OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            HashMap<String,String> changeValues = new HashMap<>();
            changeValues.put("id",(String)compoundButton.getTag(R.string.attorneyIdKey));
            changeValues.put("available",String.valueOf(b));
            Parameters parameters = new Parameters(AttorneyRecyclerView.this.session.getSessionId(), Common.ModuleName.km_AttorneyAvailability, Common.RequestType.SETENTRY,changeValues);
            restClient.restRequest(parameters, new VolleyResponse() {
                @Override
                public void onSuccessResponse(String result,Session session) {
                    AttorneyRecyclerView.this.session = session;
                    preferenceConnector.setSession(AttorneyRecyclerView.this.session);
                    Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_LONG).show();
                }
            }, AttorneyRecyclerView.this,AttorneyRecyclerView.this.session);
        }
    }


    private class AvailabilityInOfficeSwitch implements  CompoundButton.OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            HashMap<String,String> changeValues = new HashMap<>();
            changeValues.put("id",(String)compoundButton.getTag(R.string.attorneyIdKey));
            changeValues.put("availableinoffice_c",String.valueOf(b));
            Parameters parameters = new Parameters(AttorneyRecyclerView.this.session.getSessionId(), Common.ModuleName.km_AttorneyAvailability, Common.RequestType.SETENTRY,changeValues);
            restClient.restRequest(parameters, new VolleyResponse() {
                @Override
                public void onSuccessResponse(String result,Session session) {
                    AttorneyRecyclerView.this.session = session;
                    preferenceConnector.setSession(AttorneyRecyclerView.this.session);
                    Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_LONG).show();
                }
            }, AttorneyRecyclerView.this,AttorneyRecyclerView.this.session);
        }
    }
}
