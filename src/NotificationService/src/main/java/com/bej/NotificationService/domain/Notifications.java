package com.bej.NotificationService.domain;


import org.json.simple.JSONObject;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
public class Notifications {

    @MongoId
    private String emailId;
    private String notificationMessage;
    private JSONObject registerDetails;


    public Notifications() {
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public JSONObject getRegisterDetails() {
        return registerDetails;
    }

    public void setRegisterDetails(JSONObject registerDetails) {
        this.registerDetails = registerDetails;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "email='" + emailId + '\'' +
                ", notificationMessage='" + notificationMessage + '\'' +
                ", movieNames=" + registerDetails +
                '}';
    }
}





