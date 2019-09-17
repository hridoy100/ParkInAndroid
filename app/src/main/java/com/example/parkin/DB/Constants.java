package com.example.parkin.DB;

public class Constants {
    public static final String ROOT_URL = "http://formulabd.com/parkin/v1/";

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
    public static final String URL_GETRENTINFO = ROOT_URL+"parkin.php/getRentInfo";
    public static final String URL_UPDATENOTIF = ROOT_URL+"parkin.php/updateNotification";
    public static final String URL_DELVEHICLE = ROOT_URL+"parkin.php/deleteVehicle";
    public static final String URL_GETEMAIL = ROOT_URL+"parkin.php/getEmail";
    public static final String URL_UPDATEUSERPASSADDRESS = ROOT_URL+"parkin.php/updateUserPassAddress";
    public static final String URL_GETCUSTOMERDETAILS = ROOT_URL+"parkin.php/getCustomerDetails";
    public static final String URL_ADDGARAGE = ROOT_URL+"parkin.php/addGarage";
    public static final String URL_ADDSPACE = ROOT_URL+"parkin.php/addSpace";
    //public static final String URL_TOGGLEAVAILABILITY = ROOT_URL+"parkin.php/toggleSpaceAvailability";
    public static final String URL_TOGGLEAVAILABILITY = ROOT_URL+"parkin.php/updateSpaceAvailability";
    public static final String URL_GETSINGLESPACE = ROOT_URL+"parkin.php/getSpaceInfo";
    public static final String URL_UPDATESPACE = ROOT_URL+"parkin.php/updateSpaceInfo";
    public static final String URL_GETFACILITY = ROOT_URL+"parkin.php/getFacility";
    public static final String URL_GETRENTERMOB = ROOT_URL+"parkin.php/getRenterMobNoUsingGarageId";
    public static int Motor_Bike=10;
    public static int Small_Car=15;
    public static int Medium_Car=25;
    public static int Large_Car=35;
    public static int Mini_Van=40;
    public static int Large_Van=50;
    public static long per_hour_cost=5;

    public static String booked = "booked";
    public static String starting_maybe="ongoing:online?";
    public static String started="ongoing:online";
    public static String stopping_maybe="ongoing:offline?";
    public static String stopped="ongoing:offline";
    public static String finishing="ongoing:completed?";
    public static String completed="completed";
    public static int covered_parking_index=0;
    public static int cctv_index=1;
    public static int securely_gated_index=2;
    public static int disabled_access_index=3;
    public static int electric_vehicle_charging_index=4;
    public static int lighting_index=5;
    public static int oil_buying_index=6;
    public static long covered_parking_cost=5;
    public static long cctv_cost=5;
    public static long securely_gated_cost=5;
    public static long disabled_access_cost=5;
    public static long electric_vehicle_cost=10;
    public static long lighting_cost=5;
    public static long oil_buying_cost=10;

}
