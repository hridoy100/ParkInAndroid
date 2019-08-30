package com.example.parkin.DB;

public class VehicleDetails {
    private String licenseId, licenseNo, areaCode, vehicleCode, registrationNo, customerMobNo, type, company, fitnessCertificate, taxToken;

    private int space;

    public int getSpaceSize() {
        return space;
    }

    public void setSpaceSize(int space) {
        this.space = space;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public String getCustomerMobNo() {
        return customerMobNo;
    }

    public void setCustomerMobNo(String customerMobNo) {
        this.customerMobNo = customerMobNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
        if(type.equals("Car"))
            setSpaceSize(25);
        if(type.equals("Motor Bike") || type.equals("Bike"))
            setSpaceSize(10);
        if(type.equals("Bus"))
            setSpaceSize(45);
        if(type.equals("Truck"))
            setSpaceSize(50);
        if(type.equals("CNG") ||type.equals("Auto Rickshaw"))
            setSpaceSize(15);
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getVehicleCode() {
        return vehicleCode;
    }

    public void setVehicleCode(String vehicleCode) {
        this.vehicleCode = vehicleCode;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getFitnessCertificate() {
        return fitnessCertificate;
    }

    public void setFitnessCertificate(String fitnessCertificate) {
        this.fitnessCertificate = fitnessCertificate;
    }

    public String getTaxToken() {
        return taxToken;
    }

    public void setTaxToken(String taxToken) {
        this.taxToken = taxToken;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
