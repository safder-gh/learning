package com.kmincorp.smartlawcrm;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.sql.Timestamp;
import java.util.HashMap;

public class RestClient  {
    private final String url;
    private final String restUrl = "/service/v4_1/rest.php";
    private final Context context;
    ProgressBar progressBar;
    //private ProgressDialog progressDialog;

    public RestClient(String url,Context context) {
        this.url = url;
        this.context = context;
   }
    public RestClient(String url,Context context,ViewGroup viewGroup) {
        this.url = url+this.restUrl;
        this.context = context;
        progressBar = new ProgressBar(context,null,android.R.attr.progressBarStyleLarge);
        viewGroup.addView(progressBar);
    }
    public RestClient(String url,Context context,ProgressBar progressBar) {
        this.url = url+this.restUrl;
        this.context = context;
        this.progressBar = progressBar;
    }
    public void dropDownListRequest(String listName,final VolleyResponse volleyResponse, final  VolleyResponseError  volleyResponseError){
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            HashMap<String, String> params = new HashMap<String, String>();
        params.put("list_name",listName);

            VolleyStringRequest jsonObjectRequest = new VolleyStringRequest(Request.Method.POST, url,
                    new Response.Listener<String>(){

                        @Override
                        public void onResponse(String  response) {
                            volleyResponse.onSuccessResponse(response,null);
                        }
                    },new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {
                    stopProgress();
                    volleyResponseError.onResponseError(processError(error));
                }
            },params);
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    1000*5,
                    2,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            startProgress();
            requestQueue.getCache().clear();
            requestQueue.add(jsonObjectRequest);
        }
    public void  loginRequest(IPostRequestable iPostRequestable,final VolleyResponse volleyResponse, final  VolleyResponseError  volleyResponseError,final Session session){
        session.setTimestamp(new Timestamp(System.currentTimeMillis()));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        VolleyStringRequest jsonObjectRequest = new VolleyStringRequest(Request.Method.POST, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String  response) {
                        stopProgress();
                        volleyResponse.onSuccessResponse(response,session);
                    }
                },new Response.ErrorListener(){
                @Override
                    public void onErrorResponse(VolleyError error) {
                    stopProgress();
                    volleyResponseError.onResponseError(processError(error));
                }
        },iPostRequestable.getParams());
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                1000*5,
                2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        startProgress();
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
    }
    public void  logoffRequest(IPostRequestable iPostRequestable,final VolleyResponse volleyResponse, final  VolleyResponseError  volleyResponseError,final Session session){
        session.setTimestamp(new Timestamp(System.currentTimeMillis()));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        VolleyStringRequest jsonObjectRequest = new VolleyStringRequest(Request.Method.POST, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String  response) {
                        stopProgress();
                        volleyResponse.onSuccessResponse(response,session);
                    }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                stopProgress();
                volleyResponseError.onResponseError(processError(error));
            }
        },iPostRequestable.getParams());
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                1000*5,
                2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        startProgress();
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
    }
    public void  restRequest(final Parameters parameters,final VolleyResponse volleyResponse, final  VolleyResponseError  volleyResponseError ,final Session session){
        session.setTimestamp(new Timestamp(System.currentTimeMillis()));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        VolleyStringRequest jsonObjectRequest = new VolleyStringRequest(Request.Method.POST, url,
                new Response.Listener<String>(){

                    @Override
                    public void onResponse(String  response) {
                        //Log.d("RESPONSE",response);
                        JSONObject jsonObject = null;
                        //{"name":"Invalid Session ID","number":11,"description":"The session ID is invalid"}
                        try {
                            jsonObject = new JSONObject(response);
                            if(jsonObject.has("number")){
                                if(jsonObject.getInt("number") == 11){
                                   Parameters loginParameters = new Parameters(session.getUserName(), session.getPassword());
                                   restRequest(loginParameters,new VolleyResponse(){
                                       @Override
                                       public void onSuccessResponse(String result,Session session){
                                           try{
                                               session = processLogin(result,session);
                                               parameters.setSessionId(session.getSessionId());
                                               restRequest(parameters, new VolleyResponse() {
                                                   @Override
                                                   public void onSuccessResponse(String result, Session session) {
                                                       stopProgress();
                                                       volleyResponse.onSuccessResponse(result,session);
                                                   }
                                               }, new VolleyResponseError() {
                                                   @Override
                                                   public void onResponseError(String result) {
                                                       stopProgress();
                                                       volleyResponseError.onResponseError(result);
                                                   }
                                               },session);
                                           }catch (JSONException ex){
                                               stopProgress();
                                               volleyResponseError.onResponseError(ex.getMessage());
                                           }catch (Exception ex){
                                               stopProgress();
                                               volleyResponseError.onResponseError(ex.getMessage());
                                           }
                                       }
                                   },new VolleyResponseError(){
                                       @Override
                                       public  void onResponseError(String result){
                                           stopProgress();
                                           volleyResponseError.onResponseError(result);
                                       }
                                   },session);
                                }
                                else if(jsonObject.getInt("number") == 40){
                                    stopProgress();
                                    volleyResponse.onSuccessResponse(null,session);
                                }
                                else{
                                    stopProgress();
                                    volleyResponseError.onResponseError(response);
                                }
                            }else{
                                stopProgress();
                                volleyResponse.onSuccessResponse(response,session);
                            }

                        }
                        catch (JSONException ex){
                            stopProgress();
                            volleyResponseError.onResponseError(ex.getMessage());
                        }
                    }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                stopProgress();
                volleyResponseError.onResponseError(processError(error));
            }
        },parameters.getParams());
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                1000*5,
                2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        startProgress();
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
    }
    private Session processLogin(String result,Session session) throws JSONException,Exception {
        JSONObject jsonObject = new JSONObject(result);
        if(jsonObject.has("number")){
            throw new Exception(jsonObject.getString("description"));
        }
        else{
            session.fillInstance(jsonObject);
        }
        return session;
    }
    private String processError(VolleyError error){
        String errStr = null;
        try {
            if(error.networkResponse != null)
            {
                if(error.networkResponse.statusCode == 302)
                {
                    errStr = "Requires  secure connection .Change http:// to https://.";
                }
                else
                {
                    errStr = "Response code is "+ String.valueOf(error.networkResponse.statusCode);
                }
            }
            else if (error instanceof NoConnectionError) {
                /*NoConnectionError noConnectionError = (NoConnectionError)error;
                ConnectivityManager cm = (ConnectivityManager) this.context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = null;
                if (cm != null) {
                    activeNetwork = cm.getActiveNetworkInfo();
                }*/
                errStr = "Can't resolve host \""+this.url.replace("/service/v4_1/rest.php","")+"\"";
            } else if (error instanceof TimeoutError) {
                if (error.getCause() != null)
                    errStr = error.getCause().getMessage();
                else
                    errStr = "Timeout error";
            } else if (error instanceof NetworkError) {
                errStr = "instanceof NetworkError";
            } else if (error.getCause() instanceof ConnectException) {
                errStr = "instanceof ConnectException";
            } else if (error.getCause() != null){
                if(error.getCause().getMessage().contains("connection")) {
                    errStr = "Your device is not connected to internet.";
                }
            } else if (error.getCause() instanceof MalformedURLException) {
                errStr = "Bad Request.";
            } else if (error instanceof ParseError || error.getCause() instanceof IllegalStateException
                    || error.getCause() instanceof JSONException
                    || error.getCause() instanceof XmlPullParserException) {
                errStr = "Parse Error (because of invalid json or xml).";
            } else if (error.getCause() instanceof OutOfMemoryError) {
                errStr = "Out Of Memory Error.";
            } else if (error instanceof AuthFailureError) {
                errStr = "server couldn't find the authenticated request.";
            } else if (error instanceof ServerError || error.getCause() instanceof ServerError) {
                errStr = "Server is not responding.";
            } else if (error instanceof TimeoutError || error.getCause() instanceof SocketTimeoutException

                    || error.getCause() instanceof SocketException
                    || (error.getCause().getMessage() != null
                    && error.getCause().getMessage().contains("Connection timed out"))) {
                errStr = "Connection timeout error";
            } else {
                errStr = "An unknown error occurred.";
            }
        }
        catch (NullPointerException ex){
            errStr = ex.getMessage();
        }
        catch (Exception ex)
        {
            errStr = ex.getMessage();
        }
        return errStr;
    }
    private void startProgress(){
        if(progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }
    private  void stopProgress(){
        if(progressBar != null)
            progressBar.setVisibility(View.GONE);
    }

}
