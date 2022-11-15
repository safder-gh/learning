package com.kmincorp.smartlawcrm;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

class VolleyStringRequest  extends StringRequest {
    private final HashMap<String,String> params;
    public VolleyStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener ,HashMap<String,String> params) {
        super( method,url, listener, errorListener);
        this.params = params;
        this.setShouldCache(false);
    }
    @Override
    public HashMap<String, String> getParams()  {
        return this.params;
    }
}
