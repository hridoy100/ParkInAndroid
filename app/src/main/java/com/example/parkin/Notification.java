package com.example.parkin;

public class Notification {
    String id;
    String status;
    String notificationMessage;
    String rentno;
    String time;

    public Notification(String id, String status, String notificationMessage, String rentno, String time) {
        this.id = id;
        this.status = status;
        this.notificationMessage = notificationMessage;
        this.rentno = rentno;
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

    public String getRentno() {
        return rentno;
    }

    public void setRentno(String rentno) {
        this.rentno = rentno;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
