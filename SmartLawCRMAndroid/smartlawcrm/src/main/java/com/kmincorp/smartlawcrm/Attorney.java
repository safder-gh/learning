package com.kmincorp.smartlawcrm;

import android.widget.Toast;

import com.kmincorp.smartlawcrm.Utilities.Common;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Attorney implements Comparable<Attorney>{
    private String id=null;
    private String userId=null;
    private String name=null;
    private Boolean available = false;
    private Boolean availableInOffice = false;


    @Override
    public int compareTo(Attorney attorney)
            {
            return attorney.getName().compareTo( attorney.getName());
            }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getAvailableInOffice() {
        return availableInOffice;
    }

    public void setAvailableInOffice(Boolean availableInOffice) {
        this.availableInOffice = availableInOffice;
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

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    private  Attorney(String id,String userId, String name, Boolean available) {
        this();
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.available = available;
    }

    private Attorney() {
        this.id = "";
        this.userId = "";
        this.name = "";
        this.available = false;
    }

    public  static ArrayList<Attorney> jsonToList(String jsonString) throws org.json.JSONException{
        ArrayList<Attorney> attorneys =null;
        try {
            //="{\"result_count\":2,\"total_count\":\"2\",\"next_offset\":2,\"entry_list\":[{\"id\":\"a01be83f-dfee-0682-67a6-5c4c1384f886\",\"module_name\":\"Attor_AttorneyAvailability\",\"name_value_list\":{\"id\":{\"name\":\"id\",\"value\":\"a01be83f-dfee-0682-67a6-5c4c1384f886\"},\"available\":{\"name\":\"available\",\"value\":\"1\"}}},{\"id\":\"b508935a-7901-465e-9c39-5c4c1399bfb0\",\"module_name\":\"Attor_AttorneyAvailability\",\"name_value_list\":{\"id\":{\"name\":\"id\",\"value\":\"b508935a-7901-465e-9c39-5c4c1399bfb0\"},\"available\":{\"name\":\"available\",\"value\":\"0\"}}}],\"relationship_list\":[{\"link_list\":[{\"name\":\"attor_attorneyavailability_users\",\"records\":[{\"link_value\":{\"first_name\":{\"name\":\"first_name\",\"value\":\"Muntaha\"},\"last_name\":{\"name\":\"last_name\",\"value\":\"Ghauri\"}}}]}]},{\"link_list\":[{\"name\":\"attor_attorneyavailability_users\",\"records\":[{\"link_value\":{\"first_name\":{\"name\":\"first_name\",\"value\":\"\"},\"last_name\":{\"name\":\"last_name\",\"value\":\"Administrator\"}}}]}]}]}";

            JSONArray jsonArray = new JSONObject(jsonString).getJSONArray("entry_list");
            JSONArray relationShipArray = new JSONObject(jsonString).getJSONArray("relationship_list");
            attorneys = new ArrayList<>();
            Attorney attorney;
            JSONObject jsonObject ;
            String firstName;
            String lastName;
            String available;
            String availableInOffice;
            for(int i=0;i<jsonArray.length();i++){
                jsonObject = new JSONObject(jsonArray.getString(i));
                attorney = new Attorney();
                attorney.setId(jsonObject.getString("id"));

                available = new JSONObject( new JSONObject(jsonObject.getString("name_value_list")).getString("available")).getString("value");
                attorney.setAvailable((available.equalsIgnoreCase("") || available.equalsIgnoreCase("0")) ? false:true );

                availableInOffice = new JSONObject( new JSONObject(jsonObject.getString("name_value_list")).getString("availableinoffice_c")).getString("value");
                attorney.setAvailableInOffice((availableInOffice.equalsIgnoreCase("") || availableInOffice.equalsIgnoreCase("0")) ? false:true );

                attorney.setUserId(new JSONObject(new JSONObject(new JSONObject(new JSONObject(new JSONObject(relationShipArray.getString(i)).getJSONArray("link_list").getString(0)).getJSONArray("records").getString(0)).getString("link_value")).getString("id")).getString("value"));

                firstName =  new JSONObject(new JSONObject(new JSONObject(new JSONObject(new JSONObject(relationShipArray.getString(i)).getJSONArray("link_list").getString(0)).getJSONArray("records").getString(0)).getString("link_value")).getString("first_name")).getString("value");
                lastName =  new JSONObject(new JSONObject(new JSONObject(new JSONObject(new JSONObject(relationShipArray.getString(i)).getJSONArray("link_list").getString(0)).getJSONArray("records").getString(0)).getString("link_value")).getString("last_name")).getString("value");
                if(!firstName.equalsIgnoreCase(""))
                    attorney.setName(Common.toInitCap(firstName));
                if(!lastName.equalsIgnoreCase(""))
                {
                    if(attorney.getName().equalsIgnoreCase(""))
                        attorney.setName(Common.toInitCap(lastName));
                    else
                        attorney.setName(attorney.getName()+" "+Common.toInitCap(lastName));
                }
                attorneys.add(attorney);
            }

        } catch (org.json.JSONException ex) {
            throw ex;
        }
        return attorneys;
    }
}
