package com.example.parkin;

public class Notification {
    String id;
    String status;
    String notificationMessage;
    String rentno;
    String time;
    String date;
    String mobileNo;

    public Notification(String id, String status, String notificationMessage, String rentno, String time, String date, String mobileNo) {
        this.id = id;
        this.status = status;
        this.notificationMessage = notificationMessage;
        this.rentno = rentno;
        this.time = time;
        this.date = date;
        this.mobileNo = mobileNo;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
