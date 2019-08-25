package com.example.parkin;

public class Notification {
    String id;
    String status;
    String notificationMessage;
    String date;
    String time;

    public Notification(String id, String status, String notificationMessage, String date, String time) {
        this.id = id;
        this.status = status;
        this.notificationMessage = notificationMessage;
        this.date = date;
        this.time = time;
    }

    public Notification() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
