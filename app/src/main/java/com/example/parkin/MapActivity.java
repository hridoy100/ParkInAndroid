package com.example.parkin;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.parkin.DB.CommunicateWithPhp;
import com.example.parkin.DB.GarageDetails;
import com.example.parkin.DB.SpaceDetails;
import com.example.parkin.Stepper.MyStepperTest;
import com.example.parkin.util.MyClusterManagerRenderer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.algo.GridBasedAlgorithm;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import barikoi.barikoilocation.BarikoiAPI;
import barikoi.barikoilocation.PlaceModels.GeoCodePlace;
import barikoi.barikoilocation.SearchAutoComplete.SearchAutocompleteFragment;

import static com.example.parkin.DB.Constants.Large_Car;
import static com.example.parkin.DB.Constants.Large_Van;
import static com.example.parkin.DB.Constants.Mini_Van;
import static com.example.parkin.DB.Constants.Motor_Bike;
import static com.example.parkin.DB.Constants.Small_Car;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String TAG = "MapActivity";
    private GoogleMap mMap;
    private Spinner vehicle_spinner;
    private Button show_button;
    ArrayList<Integer>marker_flag=new ArrayList<>();
    LocationManager locationManager;
    LocationListener locationListener;
    private ClusterManager<ClusterMarker> mClusterManager;
    private MyClusterManagerRenderer mClusterManagerRenderer;
    private ArrayList<ClusterMarker> mClusterMarkers = new ArrayList<>();
    private ArrayList<GarageObject> garages = new ArrayList<>();
    private Marker current_loc;
    private Marker search_loc;
    int init;
    private int vehicle_size;
    private String vehicle_type=null;
    private int show_button_flag=0;
    Button arrivalTime;
    Button depatureTime;
    private Button current;
    CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
    Calendar arrivaltime;
    Calendar departuretime;
    View mapView;

    Double latitude,longitude;
    String addressTitle;
    String postalCode;
    GeoCodePlace selectedPlace;

    private SwitchDateTimeDialogFragment dateTimeFragment;
    private SwitchDateTimeDialogFragment dateTimeFragment2;
    private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        vehicle_size=0;
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);
        arrivalTime = findViewById(R.id.Arrival_button);
        depatureTime = findViewById(R.id.Departure_button);
        show_button=findViewById(R.id.show_button);
        vehicle_spinner=(Spinner)findViewById(R.id.vehicle_spinner);
        List<String>spinneritem=new ArrayList<String>();
        spinneritem.add("Any Vehicle");
        spinneritem.add("Medium Car");
        spinneritem.add("Motor Bike");
        spinneritem.add("Small Car");
        spinneritem.add("Large Car");
        spinneritem.add("Mini Van");
        spinneritem.add("Large Van");


        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyAK9AJgEjjqpjbQ-dd5NjNT9Q9CTKrkvbQ");
        }

        //barikoi..

        BarikoiAPI.getINSTANCE(getApplicationContext(), "MTQ0NjpWUTg2SldKWTRL");


        SearchAutocompleteFragment searchAutocompleteFragment;
        searchAutocompleteFragment=(SearchAutocompleteFragment)getSupportFragmentManager().findFragmentById(R.id.barikoiSearchAutocompleteFragment);
        searchAutocompleteFragment.setPlaceSelectionListener(new SearchAutocompleteFragment.PlaceSelectionListener() {

            @Override
            public void onPlaceSelected(GeoCodePlace place) {
                Toast.makeText(getApplicationContext(), "Place Selected: "+place.getAddress(), Toast.LENGTH_SHORT).show();
                latitude=Double.parseDouble(place.getLatitude());
                longitude=Double.parseDouble(place.getLongitude());
                addressTitle = place.getAddress();
                postalCode = place.getPostalcode();
                selectedPlace = place;
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(getApplicationContext(), "Error Message"+error, Toast.LENGTH_SHORT).show();
            }

        });


        vehicle_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch(position){
                    case 0:
                        vehicle_size=0;
                        break;
                    case 1:
                        vehicle_size=25;
                        vehicle_type="Medium Car";
                        break;
                    case 2:
                        vehicle_size=Motor_Bike;
                        vehicle_type="Motor_Bike";
                        break;
                    case 3:
                        vehicle_size=Small_Car;
                        vehicle_type="Small Car";
                        break;
                    case 4:
                        vehicle_size=Large_Car;
                        vehicle_type="Large Car";
                        break;
                    case 5:
                        vehicle_size=Mini_Van;
                        vehicle_type="Mini Van";
                        break;
                    case 6:
                        vehicle_size=Large_Van;
                        vehicle_type="Large Van";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinneritem);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicle_spinner.setAdapter(adapter);
        init = 0;
        ArrayList<GarageDetails> garageDetailsArrayList = communicateWithPhp.getAllGarageDetailsDB();
        for (int i=0; i<garageDetailsArrayList.size(); i++){
            System.out.println("From up:"+garageDetailsArrayList.get(i).getGarageId());
            garages.add(new GarageObject(i+1, new LatLng(Double.parseDouble(garageDetailsArrayList.get(i).getLatitude()),
                    Double.parseDouble(garageDetailsArrayList.get(i).getLongitude())),garageDetailsArrayList.get(i)));
        }
        if (dateTimeFragment == null) {
            dateTimeFragment = SwitchDateTimeDialogFragment.newInstance(
                    getString(R.string.label_datetime_dialog),
                    getString(android.R.string.ok),
                    getString(android.R.string.cancel)
                    // Optional
            );
        }
        if (dateTimeFragment2 == null) {
            dateTimeFragment2 = SwitchDateTimeDialogFragment.newInstance(
                    getString(R.string.label_datetime_dialog),
                    getString(android.R.string.ok),
                    getString(android.R.string.cancel)
                    // Optional
            );
        }
        // Optionally define a timezone
        dateTimeFragment.setTimeZone(TimeZone.getDefault());
        dateTimeFragment2.setTimeZone(TimeZone.getDefault());
        // Init format
        final SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
        // Assign unmodifiable values
        //arrivaltime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
        //departuretime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
        arrivaltime=Calendar.getInstance();
        departuretime=Calendar.getInstance();
        departuretime.add(Calendar.MINUTE,30);
        Calendar calendar = Calendar.getInstance();
        dateTimeFragment.set24HoursMode(false);
        dateTimeFragment.setHighlightAMPMSelection(false);
        dateTimeFragment.setMinimumDateTime(calendar.getTime());
        dateTimeFragment.setMaximumDateTime(new GregorianCalendar(2025, Calendar.DECEMBER, 31).getTime());
        dateTimeFragment2.set24HoursMode(false);
        dateTimeFragment2.setHighlightAMPMSelection(false);
        dateTimeFragment2.setMinimumDateTime(departuretime.getTime());
        dateTimeFragment2.setMaximumDateTime(new GregorianCalendar(2025, Calendar.DECEMBER, 31).getTime());
        dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                arrivaltime.setTime(date);
                current.setText(myDateFormat.format(date));


                //dateTimeFragment2.setMinimumDateTime(cal.getTime());
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Do nothing
            }

            @Override
            public void onNeutralButtonClick(Date date) {
                // Optional if neutral button does'nt exists
            }
        });
        dateTimeFragment2.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                departuretime.setTime(date);
                current.setText(myDateFormat.format(date));
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Do nothing
            }

            @Override
            public void onNeutralButtonClick(Date date) {
                // Optional if neutral button does'nt exists
            }
        });
    }

    public void onMapSearch(View view) {
//        EditText locationSearch = (EditText) findViewById(R.id.editText);
//        String location = locationSearch.getText().toString();
        String location = addressTitle;
        List<Address> addressList = null;
        if (search_loc != null)
            search_loc.remove();
        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            //Address address = addressList.get(0);
            LatLng latLng = new LatLng(latitude, longitude);
            search_loc = mMap.addMarker(new MarkerOptions().position(latLng).title(addressTitle).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(latitude, longitude))      // Sets the center of the map to Mountain View
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //return;
        }
        mMap.setMyLocationEnabled(true);
        */
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
        }
        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 30);
        }
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("Location", location.toString());
                Log.v(TAG, "IN ON LOCATION CHANGE, lat=" + location.getLatitude() + ", lon=" + location
                        .getLongitude());
                //mMap.clear();
                if(init==1)
                    current_loc.remove();
                LatLng hall = new LatLng(location.getLatitude(), location.getLongitude());
                current_loc=mMap.addMarker(new MarkerOptions().position(hall).title("My Location"));
                if(init==0) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hall, 15));
                    // Zoom in, animating the camera.
                    mMap.animateCamera(CameraUpdateFactory.zoomIn());

                    // Zoom out to zoom level 10, animating with a duration of 2 seconds.
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

                    // Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to Mountain View
                            .zoom(17)                   // Sets the zoom
                            .bearing(90)                // Sets the orientation of the camera to east
                            .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                            .build();                   // Creates a CameraPosition from the builder
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
                init=1;

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng( 23.777176, 90.399452), 15));
        addMapMarkers();
        mMap.setOnMarkerClickListener(mClusterManager);
        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<ClusterMarker>() {
            @Override
            public boolean onClusterItemClick(ClusterMarker ClusterItem) {
                //Log.d("custeritemcheck","hoitese");
                Toast.makeText(getApplicationContext(),"Opening SpaceList",Toast.LENGTH_SHORT).show();
                SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
                Log.d("arrivaltime",myDateFormat.format(arrivaltime.getTime()));
                Log.d("departuretime",myDateFormat.format(departuretime.getTime()));
                Log.d("GarageLocation",ClusterItem.getGarage().getAddressName());
                Log.d("GarageId",ClusterItem.getGarage().getGarageId());
                Intent spaceintent=new Intent(getApplicationContext(),SpaceDetailsView.class);
                String send_vehicle_type=null;
                if(show_button_flag==1)
                {
                    send_vehicle_type=vehicle_type;
                }
                System.out.println("Onclick garageid: "+ClusterItem.getGarage().getGarageId());
                spaceintent.putExtra("arrivaltime",arrivaltime.getTimeInMillis());
                spaceintent.putExtra("departuretime",departuretime.getTimeInMillis());
                spaceintent.putExtra("garagelocation",ClusterItem.getGarage().getAddressName());
                spaceintent.putExtra("garageid",ClusterItem.getGarage().getGarageId());
                System.out.println("From MapActivity Garage Id:"+ClusterItem.getGarage().getGarageId());
                spaceintent.putExtra("vehicleType",send_vehicle_type);
                String facility=new CommunicateWithPhp().getFacility(ClusterItem.getGarage().getGarageId());
                System.out.println(facility);
                spaceintent.putExtra("facility",facility);
                startActivity(spaceintent);
                return true;
            }
        });
    }
    public void setDepatureTime(View view)
    {
        current=depatureTime;
        Calendar calendar=Calendar.getInstance();
        dateTimeFragment2.startAtCalendarView();
        dateTimeFragment2.setDefaultDateTime(departuretime.getTime());
        dateTimeFragment2.show(getSupportFragmentManager(), TAG_DATETIME_FRAGMENT);
    }
    public void setArrivalTime(View view)
    {
        current=arrivalTime;
        Calendar calendar=Calendar.getInstance();
        dateTimeFragment.startAtCalendarView();
        dateTimeFragment.setDefaultDateTime(arrivaltime.getTime());
        dateTimeFragment.show(getSupportFragmentManager(), TAG_DATETIME_FRAGMENT);
    }
    public void customiseMarkers(View view)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String mobNo = sharedPreferences.getString("com.example.parkin.mobileNo", "");
        show_button_flag=1;
        CommunicateWithPhp com=new CommunicateWithPhp();
        for(int i=0;i<garages.size();i++)
        {
            ArrayList<SpaceDetails>spaceDetails=com.getAvailableSpaces(Integer.parseInt(garages.get(i).getGarage().
                            getGarageId()),
                    arrivaltime,departuretime);
            System.out.println("Iterating garagaes: "+garages.get(i).getGarage_id());
            boolean flag=is_eligible(spaceDetails);
            System.out.println(flag);
            String renter_mob=new CommunicateWithPhp().getRenterMobNo(garages.get(i).getGarage().getGarageId());
            if(flag==true && !renter_mob.equals(mobNo))
            {
                if(marker_flag.get(i)==0)
                {
                    mClusterManager.addItem(mClusterMarkers.get(i));
                    marker_flag.set(i,1);
                }
            }
            if(flag==false)
            {
                if(marker_flag.get(i)==1) {
                    mClusterManager.removeItem(mClusterMarkers.get(i));
                    marker_flag.set(i,0);
                }
            }
        }
        mClusterManager.cluster();
    }
    public boolean is_eligible(ArrayList<SpaceDetails>spaceDetails)
    {
        for(int i=0;i<spaceDetails.size();i++)
        {
            System.out.println(spaceDetails.get(i).getAvailability());
            if(spaceDetails.get(i).getSpacesize()>=vehicle_size)
                return true;
        }
        return false;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
//    public void setDepatureTime(View view){
//        Calendar mcurrentTime = Calendar.getInstance();
//        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//        int minute = mcurrentTime.get(Calendar.MINUTE);
//        TimePickerDialog mTimePicker;
//        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                Log.i("hr", String.valueOf(selectedHour));
//                Log.i("min", String.valueOf(selectedMinute));
//                if(selectedMinute<10)
//                    depatureTime.setText( selectedHour + ":0" + selectedMinute);
//                else
//                    depatureTime.setText( selectedHour + ":" + selectedMinute);
//            }
//        }, hour, minute, false);//No 24 hour time
//        mTimePicker.setTitle("Select Time");
//        mTimePicker.show();
//    }
//
//    public void setArrivalTime(View view){
//        Calendar mcurrentTime = Calendar.getInstance();
//        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//        int minute = mcurrentTime.get(Calendar.MINUTE);
//        TimePickerDialog mTimePicker;
//        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                Log.i("hr", String.valueOf(selectedHour));
//                Log.i("min", String.valueOf(selectedMinute));
//                if(selectedMinute<10)
//                    arrivalTime.setText( selectedHour + ":0" + selectedMinute);
//                else
//                    arrivalTime.setText( selectedHour + ":" + selectedMinute);
//            }
//        }, hour, minute, false);//No 24 hour time
//        mTimePicker.setTitle("Select Time");
//        mTimePicker.show();
//    }
    private void addMapMarkers(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String mobNo = sharedPreferences.getString("com.example.parkin.mobileNo", "");
        System.out.println("mobNos:"+mobNo);
        if(mMap != null){

            if(mClusterManager == null){
                mClusterManager = new ClusterManager<ClusterMarker>(this, mMap);
            }
            if(mClusterManagerRenderer == null){
                mClusterManagerRenderer = new MyClusterManagerRenderer(
                        this,
                        mMap,
                        mClusterManager
                );
                mClusterManager.setRenderer(mClusterManagerRenderer);
                mClusterManager.setAlgorithm(new GridBasedAlgorithm<ClusterMarker>());
            }

            for(GarageObject garage: garages){
                try{
                    String renter_mob=new CommunicateWithPhp().getRenterMobNo(garage.getGarage().getGarageId());
                    System.out.println(renter_mob);
                    System.out.println("equality:"+renter_mob.equals(mobNo));
                    String snippet = "This is garage no : " + garage.getGarage().getGarageId();


                    int avatar = R.drawable.garage_for_map_small; // set the default avatar
                    ClusterMarker newClusterMarker = new ClusterMarker(
                            garage.getPos(),
                            "Default title",
                            snippet,
                            avatar,
                            garage.getGarage()
                    );
                    System.out.println("Garage id:"+garage.getGarage().getGarageId());
                    ArrayList<SpaceDetails>spaceDetails=new CommunicateWithPhp().getAvailableSpaces(Integer.parseInt(garage.getGarage().
                                    getGarageId()),
                            arrivaltime,departuretime);
                    System.out.println("Arraylist size:"+spaceDetails.size());

                    if(spaceDetails!=null) {
                        if (is_eligible(spaceDetails) && !(renter_mob.equals(mobNo))) {
                            mClusterManager.addItem(newClusterMarker);
                            marker_flag.add(1);
                            System.out.println("Dhuke");
                        }
                        else
                            marker_flag.add(0);
                    }
                    else
                        marker_flag.add(0);
                    //mClusterManager.addItem(newClusterMarker);
                    //marker_flag.add(1);
                    //mClusterManager.removeItem(newClusterMarker);
                    mClusterMarkers.add(newClusterMarker);


                }catch (NullPointerException e){
                    Log.e(TAG, "addMapMarkers: NullPointerException: " + e.getMessage() );
                }

            }
            mClusterManager.cluster();

        }
    }
    // Add a marker in Sydney and move the camera @23.7254245,90.3875091
}
