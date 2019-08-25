package com.example.parkin.DB;

public class Constants {
    private static final String ROOT_URL = "http://formulabd.com/parkin/v1/";

    public static final String URL_REGISTER = ROOT_URL+"parkin.php/registerUser";
    public static final String URL_LOGIN = ROOT_URL+"parkin.php/verifyAdmin";
    public static final String URL_AllGarage = ROOT_URL+"parkin.php/getAllGarageDetails";
    public static final String URL_AllVehicle = ROOT_URL+"parkin.php/getVehicleDetails";
    public static final String URL_OneVehicle = ROOT_URL+"parkin.php/getVehicleDescription";
    public static final String URL_AddVehicle = ROOT_URL+"parkin.php/addVehicle";
    public static final String URL_EditVehicle = ROOT_URL+"parkin.php/updateVehicleInfo";
    public static final String URL_AVAILABLESPACES = ROOT_URL+"parkin.php/getAvailableSpace";
    public static final String URL_GARAGESPACE = ROOT_URL+"parkin.php/getGarageSpace";
    public static final String URL_BOOKSPACE = ROOT_URL+"parkin.php/bookGarageSpace";
    public static final String URL_GETMYGARAGES = ROOT_URL+"parkin.php/getMyGarages";
    public static final String URL_GETHISTORY = ROOT_URL+"parkin.php/getCustomerHistory";
    public static final String URL_GETNOTIFICATION = ROOT_URL+"parkin.php/getNotification";
}
