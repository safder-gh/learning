package com.kmincorp.smartlawcrm;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Charges implements Comparable<Charges>{
    private String id = null;
    private String name = null;
    private String charges = null;
    @Override
    public int compareTo(Charges o)
    {
        return this.getCharges().compareTo( o.getCharges());
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

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public Charges(String id, String name, String charges) {
        this();
        this.id = id;
        this.name = name;
        this.charges = charges;
    }
    public Charges() {
        this.id = "";
        this.name = "";
        this.charges = "";
    }
    public  static ArrayList<Charges> jsonToList(String jsonString) throws org.json.JSONException{
        ArrayList<Charges> arrayList =null;
        JSONArray jsonArray = new JSONObject(jsonString).getJSONArray("entry_list");
        arrayList = new ArrayList<>();
        JSONObject jsonObject ;
        Charges charges;
        try {
            for(int i=0;i<jsonArray.length();i++){
                charges = new Charges();
                jsonObject = new JSONObject(jsonArray.getString(i));
                charges.setId(jsonObject.getString("id"));
                charges.setName(new JSONObject( new JSONObject(jsonObject.getString("name_value_list")).getString("name")).getString("value"));
                charges.setCharges(new JSONObject( new JSONObject(jsonObject.getString("name_value_list")).getString("chargename_c")).getString("value"));
                arrayList.add(charges);
            }
        }
        catch (org.json.JSONException ex) {
            throw ex;
        }
        return arrayList;
    }
}
