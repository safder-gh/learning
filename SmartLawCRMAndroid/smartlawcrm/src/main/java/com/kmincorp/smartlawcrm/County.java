package com.kmincorp.smartlawcrm;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class County  implements Comparable<County>{
    private String id = null;
    private String name = null;
    private String description = null;

    @Override
    public int compareTo(County county)
    {
        return this.getName().compareTo( county.getName());
    }
    public County(String id, String name, String description) {
        this();
        this.id = id;
        this.name = name;
        this.description = description;
    }
    public County() {
        this.id = "";
        this.name = "";
        this.description = "";
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public  static ArrayList<County> jsonToList(String jsonString) throws org.json.JSONException{
        ArrayList<County> arrayList =null;
        JSONArray jsonArray = new JSONObject(jsonString).getJSONArray("entry_list");
        arrayList = new ArrayList<>();
        JSONObject jsonObject ;
        County county;
        try {
            for(int i=0;i<jsonArray.length();i++){
                county = new County();
                jsonObject = new JSONObject(jsonArray.getString(i));
                county.setId(jsonObject.getString("id"));
                county.setName(new JSONObject( new JSONObject(jsonObject.getString("name_value_list")).getString("name")).getString("value"));
                county.setDescription(new JSONObject( new JSONObject(jsonObject.getString("name_value_list")).getString("description")).getString("value"));
                arrayList.add(county);
            }
        }
        catch (org.json.JSONException ex) {
            throw ex;
        }
        return  arrayList;
    }
}
