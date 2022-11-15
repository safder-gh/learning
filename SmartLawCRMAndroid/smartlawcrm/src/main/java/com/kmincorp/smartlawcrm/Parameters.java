package com.kmincorp.smartlawcrm;

import android.text.TextUtils;

import com.kmincorp.smartlawcrm.Utilities.Common;

import java.util.HashMap;

public class Parameters implements IPostRequestable{
    private  Common.RequestType requestType;
    private  String userName;
    private  String password;
    private  String sessionId;
    private  Common.ModuleName moduleName;
    private  String[] fields;
    private  String[] relatedFields;
    private  Common.ModuleName relatedModule;
    private  String queryWhere;
    private  String ascendingDescending;
    HashMap<String,String> entryParams ;
    private Note note;

    public Parameters(String userName, String password)
    {
        this.requestType = Common.RequestType.LOGIN;
        this.userName = userName;
        this.password = password;
    }
    public Parameters(String sessionId, Common.RequestType requestType)
    {
        this.requestType = requestType;
        this.sessionId = sessionId;
    }
    public Parameters(String sessionId, Common.RequestType requestType, Note note)
    {
        this.requestType = requestType;
        this.sessionId = sessionId;
        this.note = note;
    }
    /*public Parameters(String sessionId)
    {
        this.requestType = Common.RequestType.CURRENTUSER;
        this.sessionId = sessionId;
    }*/
    public Parameters(String sessionId, Common.ModuleName moduleName, Common.RequestType requestType)
    {
    this.sessionId=sessionId;
    this.moduleName = moduleName;
    this.requestType=requestType;
    }
    public Parameters(String sessionId, Common.ModuleName moduleName, Common.RequestType requestType, String[] fields)
    {
        this.sessionId=sessionId;
        this.moduleName = moduleName;
        this.requestType=requestType;
        this.fields = fields;
    }
    public Parameters(String sessionId, Common.ModuleName moduleName, Common.RequestType requestType, String[] fields,Common.ModuleName relatedModule,String[] relatedFields)
    {
        this.sessionId = sessionId;
        this.moduleName = moduleName;
        this.requestType=requestType;
        this.fields = fields;
        this.relatedModule = relatedModule;
        this.relatedFields = relatedFields;
    }
    public Parameters(String sessionId, Common.ModuleName moduleName, Common.RequestType requestType, HashMap<String,String> entryParams)
    {
        this.sessionId=sessionId;
        this.moduleName = moduleName;
        this.requestType=requestType;
        this.entryParams = entryParams;
    }
    public Parameters(String sessionId, Common.ModuleName moduleName, Common.RequestType requestType, String[] fields, String queryWhere)
    {
        this.sessionId=sessionId;
        this.moduleName = moduleName;
        this.requestType=requestType;
        this.fields = fields;
        this.queryWhere = queryWhere;
    }
    public Parameters(String sessionId, Common.ModuleName moduleName, Common.RequestType requestType, String[] fields, String queryWhere, String ascendingDescending)
    {
        this.sessionId=sessionId;
        this.moduleName = moduleName;
        this.requestType=requestType;
        this.fields = fields;
        this.queryWhere = queryWhere;
        this.ascendingDescending  = ascendingDescending;
    }
    public HashMap<String, String> getParams()  {
        HashMap<String,String> params = new HashMap<String, String>();
        switch (requestType) {
            case LOGIN: {
                params.put("method", "login");
                params.put("input_type", "JSON");
                params.put("response_type", "JSON");
                params.put("rest_data", "{\"user_auth\":{\"user_name\":\"" + this.userName + "\",\"password\":\"" + this.password + "\"}}");
                break;
            }
            case GETUSERID:{
                params.put("method","get_user_id");
                params.put("input_type","JSON");
                params.put("response_type","JSON");
                params.put("rest_data","{\"session\":\""+this.sessionId+"\"}");
                break;
            }
            case GETENTRYLIST:{
                params.put("method","get_entry_list");
                params.put("input_type","JSON");
                params.put("response_type","JSON");
                if(this.relatedModule == null)
                    params.put("rest_data","{\"session\":\""+this.sessionId+"\",\"module_name\":\""+this.moduleName+"\",\"query\":\""+(TextUtils.isEmpty(this.queryWhere)  ? "":this.queryWhere)+"\",\"order_by\":\""+(TextUtils.isEmpty(this.ascendingDescending)  ? "":this.ascendingDescending)+"\",\"offset\":0,\"select_fields\":["+ Common.getFields(this.fields)+"],\"link_name_to_fields_array\":[],\"max_results\":300,\"deleted\":0}");
                else
                    params.put("rest_data","{\"session\":\""+this.sessionId+"\",\"module_name\":\""+this.moduleName+"\",\"query\":\""+(TextUtils.isEmpty(this.queryWhere)  ? "":this.queryWhere)+"\",\"order_by\":\""+(TextUtils.isEmpty(this.ascendingDescending)  ? "":this.ascendingDescending)+"\",\"offset\":0,\"select_fields\":["+ Common.getFields(this.fields)+"],\"link_name_to_fields_array\":["+this.getRelationData()+"],\"max_results\":100,\"deleted\":0}");
                break;
            }
            case SETENTRY:{
                params.put("method","set_entry");
                params.put("input_type","JSON");
                params.put("response_type","JSON");
                params.put("rest_data","{\"session\":\""+this.sessionId+"\",\"module_name\":\""+this.moduleName+"\",\"name_value_list\":"+Common.getJsonArrayString(this.entryParams)+"}");
                break;
            }
            case SETNOTEATTACHMENT:{
                params.put("method","set_note_attachment");
                params.put("input_type","JSON");
                params.put("response_type","JSON");
                params.put("rest_data","{\"session\":\""+this.sessionId+"\",\"note\":{\"id\":\""+this.note.getId()+"\",\"filename\":\""+this.note.getFileName()+"\",\"file\":\""+this.note.getFile()+"\"}}");
                break;
            }
            case LOGOUT:{
                params.put("method","logout");
                params.put("input_type","JSON");
                params.put("response_type","JSON");
                params.put("rest_data","{\"session\":\""+this.sessionId+"\"}");
                break;
            }
        }
        return params;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    private String getRelationData(){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"");
        sb.append("name");
        sb.append("\"");
        sb.append(":");
        sb.append("\"");
        sb.append(this.relatedModule.name());
        sb.append("\"");
        sb.append(",");
        sb.append("\"");
        sb.append("value");
        sb.append("\"");
        sb.append(":");
        sb.append("[");
        sb.append(Common.getFields(this.relatedFields));
        sb.append("]");
        sb.append("}");
        return sb.toString();
    }
}
