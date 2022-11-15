package com.kmincorp.smartlawcrm;

public class FCM {
    private String id = null;
    private String fcmToken = null;
    private String os = null;
    private String description = null;
    private String uuid = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUuid() {
        return uuid;
    }

    public FCM() {
        this.fcmToken = "";
        this.os = "";
        this.description = "";
        this.uuid = "";
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }



}
