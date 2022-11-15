package com.kmincorp.smartlawcrm;

import org.json.JSONObject;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.UUID;

public class Session {
    private String userName;
    private String password;
    private String url;
    private String sessionId = null;
    private Timestamp timestamp;

    public String getFcmToken() {
        return fcmToken==null ? "" :fcmToken;
    }

    public void setFcmToken(String fcmToken) {

        this.fcmToken = fcmToken;
    }

    private String fcmToken;

    public String getSystemId() {
        return systemId==null ? "":systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    private String systemId;





    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) throws NoSuchAlgorithmException {
        this.password = md5(password);
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    private Boolean isAdmin = false;
    public Boolean getActive() {
        return isActive;
    }
    public void setActive(Boolean active) {
        isActive = active;
    }
    private Boolean isActive = false;
    private String userId = null;
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public Boolean getAdmin() {
        return isAdmin;
    }
    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
    public void setSessionId(String sessionId){
        this.sessionId = sessionId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getSessionId(){
        return this.sessionId;
    }
    public void fillInstance(JSONObject jsonObj) throws org.json.JSONException
    {
        JSONObject userObj = new JSONObject(jsonObj.getString("name_value_list"));
        this.setSessionId(jsonObj.getString("id"));
        this.setUserId(new JSONObject(userObj.getString("user_id")).getString("value"));
        this.setUserName(new JSONObject(userObj.getString("user_name")).getString("value"));
        this.setAdmin(new JSONObject(userObj.getString("user_is_admin")).getBoolean("value"));
        this.setActive(true);
    }
    private String md5(String s) throws NoSuchAlgorithmException {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(s.getBytes(StandardCharsets.UTF_8));
            BigInteger hash = new BigInteger(1, md.digest());
            result = hash.toString(16);
            if ((result.length() % 2) != 0) {
                result = "0" + result;
            }
        }
        catch(NoSuchAlgorithmException ex){
            throw ex;
        }
        return result;
    }
}
