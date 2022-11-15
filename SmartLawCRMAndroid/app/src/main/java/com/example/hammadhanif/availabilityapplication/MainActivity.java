package com.example.hammadhanif.availabilityapplication;

import android.content.Intent;
import android.content.SharedPreferences;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hammadhanif.availabilityapplication.Preference.PreferenceConnector;
import com.example.hammadhanif.availabilityapplication.Utilities.Common;
import com.kmincorp.smartlawcrm.Session;
import com.kmincorp.smartlawcrm.Parameters;
import com.kmincorp.smartlawcrm.RestClient;
import com.kmincorp.smartlawcrm.VolleyResponse;
import com.kmincorp.smartlawcrm.VolleyResponseError;


import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity implements VolleyResponseError, VolleyResponse {

    private TextView mTextViewResult;
    private EditText mUser, mPass , mUrl;
    private ViewGroup placeHolder;
    private String email, Pass;
    private JSONObject Objects;
    private String url;
    private String userID, name, type, statusL, image_url;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private PreferenceConnector preferenceConnector;
    private Session session;
    private final VolleyResponseError volleyResponseError = this;
    private final VolleyResponse volleyResponse = this;
    private View root;
    Parameters parameters;

    private RestClient restClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        root = (View) findViewById(R.id.root);

        preferenceConnector = new PreferenceConnector(getApplicationContext());
        MainActivity.this.session = preferenceConnector.getSession();
        //RequestQueue requestQueue = Volley.newRequestQueue(this);

        mTextViewResult = findViewById ( R.id.inValid );
        mTextViewResult.setText("");

        mUser = findViewById ( R.id.editText_username );
        mPass = findViewById ( R.id.editText_password );
        mUrl = findViewById ( R.id.txtUrl);

        Button mLogin = findViewById(R.id.button_login);
        placeHolder = findViewById(R.id.placeHolder);
        Common.checkPlayServices(this);
        //android.os.Debug.waitForDebugger();
//        Intent intent = getIntent();
//        if (intent != null && intent.getExtras() != null) {
//
//            Bundle extras = intent.getExtras();
//
//            for (String key : extras.keySet()) {
//                Log.d("FCM", key + " : " + extras.get(key));
//            }
//        }

        if(MainActivity.this.session != null) {
            if (MainActivity.this.session.getActive()) {
                restClient = new RestClient(MainActivity.this.session.getUrl(), MainActivity.this, placeHolder);
                parameters = new Parameters(MainActivity.this.session.getSessionId(), com.kmincorp.smartlawcrm.Utilities.Common.RequestType.GETUSERID);
                restClient.loginRequest(parameters, new VolleyResponse() {
                    @Override
                    public void onSuccessResponse(String result,Session session) {
                        MainActivity.this.session = session;
                        if (MainActivity.this.session.getUserId().equalsIgnoreCase(result.replaceAll("^\"|\"$", "")))
                            goToNextActivity();
                        else {
                            Parameters parameters = new Parameters(MainActivity.this.session.getUserName(), MainActivity.this.session.getPassword());
                            restClient.loginRequest(parameters, new VolleyResponse() {
                                @Override
                                public void onSuccessResponse(String result,Session session) {
                                    MainActivity.this.session = session;
                                    preferenceConnector.setSession(MainActivity.this.session);
                                    try {
                                        JSONObject jsonObj = new JSONObject(result);
                                        if(jsonObj.has("number")) {
                                            mUser.setText(MainActivity.this.session.getUserName());
                                            mUrl.setText(MainActivity.this.session.getUrl());
                                            mTextViewResult.setText(jsonObj.getString("description"));
                                        }
                                        else {
                                            MainActivity.this.session.fillInstance(jsonObj);
                                            preferenceConnector.setSession(MainActivity.this.session);
                                            Intent intent;
                                            intent = new Intent(MainActivity.this, HomeActivity.class);
                                            startActivity(intent);
                                            MainActivity.this.finish();
                                        }
                                    }
                                    catch (org.json.JSONException ex)
                                    {
                                        mUser.setText(MainActivity.this.session.getUserName());
                                        mUrl.setText(MainActivity.this.session.getUrl());
                                        Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }, new VolleyResponseError() {
                                @Override
                                public void onResponseError(String result) {
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                }
                            },MainActivity.this.session);
                        }
                    }
                }, volleyResponseError,MainActivity.this.session);
            }
            else
            {
                mUser.setText(MainActivity.this.session.getUserName());
                mUrl.setText(MainActivity.this.session.getUrl());
            }

        }
//        else{
//            mUrl.setText("https://stage.smartlawcrm.com/");
//            //mPass.setText("admin)(*&");
//        }

        mLogin.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (mUser.getText ().toString ().equals("") || mPass.getText ().toString ().equals("") || mUrl.getText().toString().equals("")  ) {
                    Toast.makeText(getApplicationContext(), "One of the field is empty!", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(!URLUtil.isValidUrl(mUrl.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "URL is not well-formed.", Toast.LENGTH_LONG).show();
                    return;
                }
                MainActivity.this.session = new Session();
                MainActivity.this.session.setUserName(mUser.getText().toString());
                MainActivity.this.session.setUrl(mUrl.getText().toString());
                restClient = new RestClient(MainActivity.this.session.getUrl(), MainActivity.this,placeHolder);
                try {
                    MainActivity.this.session.setPassword(mPass.getText().toString());
                    Parameters parameters = new Parameters(mUser.getText().toString(), Common.md5(mPass.getText().toString()));
                    restClient.loginRequest(parameters, volleyResponse, volleyResponseError,MainActivity.this.session);
                }
                catch (NoSuchAlgorithmException ex){
                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        /*FirebaseMessaging.getInstance().subscribeToTopic("weather")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subcription Successful";
                        if (!task.isSuccessful()) {
                            msg = "Subcription UnSuccessful";
                        }
                        Log.d("FIREBASE", msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });*/
    }
    /*private void showSnakeBar(String message,int colorId)
    {
        Snackbar snackbar = Snackbar.make( root,message,Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(colorId));
        snackbar.show();
    }*/
    @Override
    public  void onResponseError(String result){
        mUser.setText(MainActivity.this.session.getUserName());
        mUrl.setText(MainActivity.this.session.getUrl());
        mTextViewResult.setText(result);
    }
    @Override
    public  void onSuccessResponse(String result,Session session){
        MainActivity.this.session = session;
        try {
            JSONObject jsonObj = new JSONObject(result);
            if(jsonObj.has("number")) {
                mTextViewResult.setText(jsonObj.getString("description"));
                return;
            }

            MainActivity.this.session.fillInstance(jsonObj);
            this.preferenceConnector.setSession(this.session);
            this.goToNextActivity();
        }
        catch (org.json.JSONException ex)
        {
            mTextViewResult.setText(ex.getMessage());
        }
    }
    private void goToNextActivity(){
        Intent intent;
        if(getIntent().getStringExtra("caseId")!=null){
            intent = new Intent(this, CasesRecyclerView.class);
            Bundle bundle = new Bundle();
            bundle.putString(this.getResources().getString(R.string.queryWhere),"(cases.id='"+getIntent().getStringExtra("caseId")+"' and cases_cstm.archive_c=0)");
            bundle.putString(this.getResources().getString(R.string.searchType), com.example.hammadhanif.availabilityapplication.Utilities.Common.SearchType.PendingConsult.name());
            intent.putExtras(bundle);

        }else{
            intent = new Intent(this, HomeActivity.class);
        }

            startActivity(intent);
            this.finish();
    }
}
