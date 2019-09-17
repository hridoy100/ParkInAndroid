package com.example.parkin.DB;
public class AccountDetails {
    private String mobileNo;
    private String name;
    private String NID;
    private String email;
    private String address;
    private String birthdate;
    private String creationTime;


    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNID() {
        return NID;
    }

    public void setNID(String NID) {
        this.NID = NID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public AccountDetails() {
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public AccountDetails(String mobileNo, String name, String NID, String email, String address, String birthdate, String creationTime) {
        this.mobileNo = mobileNo;
        this.name = name;
        this.NID = NID;
        this.email = email;
        this.address = address;
        this.birthdate = birthdate;
        this.creationTime = creationTime;
    }

}
