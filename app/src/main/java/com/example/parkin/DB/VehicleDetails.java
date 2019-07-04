package com.example.parkin.DB;

public class VehicleDetails {
    private String licenseId, licenseNo, areaCode, vehicleCode, registrationNo, customerMobNo, type, company, fitnessCertificate, taxToken;

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

    public void setType(String type) {
        this.type = type;
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
