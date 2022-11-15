package com.example.hammadhanif.availabilityapplication.Preference;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.kmincorp.smartlawcrm.Session;
import com.google.gson.Gson;


public class PreferenceConnector {
    private final Context context;
    private static final String PREF_NAME = "com.example.hammadhanif";
    public  enum PreferenceKey {
        SESSION_INFO,
    }

    public PreferenceConnector(Context context) {
        this.context = context;
    }

    public void setSession(Session session)
    {
        Gson gson = new Gson();
        getEditor().putString(PreferenceKey.SESSION_INFO.toString(), gson.toJson(session)).commit();
    }
    public Session getSession()
    {
        Session session;
        Gson gson = new Gson();
        String res= getPreferences().getString(PreferenceKey.SESSION_INFO.toString(),"");
        if(res.isEmpty())
            session = null;
        else
            session = gson.fromJson(res,Session.class);
        return  session;
    }
    public void removeSession()
    {
        getEditor().remove(PreferenceKey.SESSION_INFO.toString()).commit();
    }

    private SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(this.context);
        //return context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
    }
    private SharedPreferences.Editor getEditor() {
        return getPreferences().edit();
    }
}
