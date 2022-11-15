package com.kmincorp.smartlawcrm;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Account {
    private String id = null;
    private String name = null;
    private String phoneWork = null;
    private String phoneOffice = null;
    private String phoneAlternat = null;
    private String email = null;

    public Account(String id,String name,String middleName,String lastName,String phoneHome,String phoneOffice,String phoneAlternat, String email){
        this();
        this.id = id;
        this.name = name;
        this.phoneWork = phoneHome;
        this.phoneOffice = phoneOffice;
        this.phoneAlternat = phoneAlternat;
        this.email = email;
    }
    public Account(){
        this.id = "";
        this.name = "";
        this.phoneWork = "";
        this.phoneOffice = "";
        this.phoneAlternat = "";
        this.email = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneWork() {
        return phoneWork;
    }

    public void setPhoneWork(String phoneHome) {
        this.phoneWork = phoneHome;
    }

    public String getPhoneOffice() {
        return phoneOffice;
    }

    public void setPhoneOffice(String phoneOffice) {
        this.phoneOffice = phoneOffice;
    }

    public String getPhoneAlternat() {
        return phoneAlternat;
    }

    public void setPhoneAlternat(String phoneAlternat) {
        this.phoneAlternat = phoneAlternat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public  static int count(String jsonString) throws org.json.JSONException{
        return new JSONObject(jsonString).getJSONArray("entry_list").length();
    }
    public  static ArrayList<Account> jsonToList(String jsonString) throws org.json.JSONException{
        ArrayList<Account> accounts =null;
        try{
            JSONArray jsonArray = new JSONObject(jsonString).getJSONArray("entry_list");
            accounts = new ArrayList<>();
            JSONObject jsonObject ;
            Account account;
            for(int i=0;i<jsonArray.length();i++){
                account = new Account();
                jsonObject = new JSONObject(jsonArray.getString(i));
                account.setId(jsonObject.getString("id"));
                account.setName(new JSONObject( new JSONObject(jsonObject.getString("name_value_list")).getString("name")).getString("value"));
                account.setPhoneWork(new JSONObject( new JSONObject(jsonObject.getString("name_value_list")).getString("phonework_c")).getString("value"));
                account.setPhoneOffice(new JSONObject( new JSONObject(jsonObject.getString("name_value_list")).getString("workphone_c")).getString("value"));
                account.setEmail(new JSONObject( new JSONObject(jsonObject.getString("name_value_list")).getString("email1")).getString("value"));
                accounts.add(account);
            }
        }
        catch (org.json.JSONException ex) {
            throw ex;
        }
        return  accounts;
    }
}
