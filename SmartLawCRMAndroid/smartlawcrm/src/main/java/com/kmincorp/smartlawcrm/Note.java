package com.kmincorp.smartlawcrm;

import com.kmincorp.smartlawcrm.Utilities.Common;

import org.json.JSONObject;

public class Note {
    private String id = null;
    private String name = null;
    private String parentId;
    private Common.ModuleName parentModule;
    private String fileName;
    private String file;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Common.ModuleName getParentModule() {
        return parentModule;
    }

    public void setParentModule(Common.ModuleName parentModule) {
        this.parentModule = parentModule;
    }

    public Note(String id, String name, String parentId, Common.ModuleName parentModule) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.parentModule = parentModule;
    }
    public Note() {
        this.id = "";
        this.name = "";
        this.parentId = "";
        this.parentModule = null;
    }
    public void fillInstance(JSONObject jsonObj) throws org.json.JSONException
    {
        this.setId(jsonObj.getString("id"));
        JSONObject parentJson = jsonObj.getJSONObject("entry_list");
        this.setName(parentJson.optJSONObject("name").get("value").toString());
        this.setParentId(parentJson.optJSONObject("parent_id").get("value").toString());
        this.setParentModule(Common.ModuleName.valueOf(   parentJson.optJSONObject("parent_type").get("value").toString()));

    }



}
