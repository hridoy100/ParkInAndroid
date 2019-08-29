package com.example.parkin.Add;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.parkin.R;
import com.example.parkin.RecyclerViewAdapters.RecyclerViewAdapterSpace;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddGarage extends FragmentActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener, RecyclerViewAdapterSpace.OnItemClickListener {

    private static final String TAG = "AddGarage";
    Spinner totalSlot;
    RadioGroup facilities;

    RecyclerView viewSpace;

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    String address;
    String city;
    String state;
    String country;
    String postalCode;
    String knownName;
    View mapView;
    LatLng latLngCur;
    private Marker current_loc;
    private Marker search_loc;
    public LatLng latlng;
    int init;
    private GoogleMap.OnCameraIdleListener onCameraIdleListener;
    private GoogleMap.OnCameraMoveListener onCameraMoveListener;

    boolean cctvSelection;
    boolean indoorSelection;
    boolean guardSelection;

    //RecyclerView Items
    ArrayList<String> spaceNo;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                }
            }
        }
    }
    public void initVariables(){
//        hrCost = (Spinner) findViewById(R.id.hourlyCost);
        totalSlot = (Spinner) findViewById(R.id.spaceCount);
//        city = (Spinner) findViewById(R.id.cityCode);
//        locality = (Spinner) findViewById(R.id.localityCode);
//        openTime = (Button) findViewById(R.id.openTime);
//        closeTime = (Button) findViewById(R.id.closeTime);

        ArrayAdapter arrayAdapter;// = new ArrayAdapter(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.hourCost));
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        hrCost.setAdapter(arrayAdapter);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.totalSlot));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        totalSlot.setAdapter(arrayAdapter);

        viewSpace = (RecyclerView) findViewById(R.id.recycleView_space);
        //facilities = (RadioGroup) findViewById(R.id.radioGroup);
        /*position = (Spinner) findViewById(R.id.position);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.position));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        position.setAdapter(arrayAdapter);
        */

//        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city));
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        city.setAdapter(arrayAdapter);

//        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.localityDhaka));
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        locality.setAdapter(arrayAdapter);
        init = 0;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.garage_location_layout);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map3);
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();
        configureCameraIdle();
        configureCameraMove();
        init=0;
        //initVariables();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

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
                LatLng hall=null;
                if(init==1)
                    current_loc.remove();
                if(init!=2) {
                    hall = new LatLng(location.getLatitude(), location.getLongitude());
                    current_loc = mMap.addMarker(new MarkerOptions().position(hall).title("My Location"));
                }
                mMap.setOnCameraIdleListener(onCameraIdleListener);
                mMap.setOnCameraMoveListener(onCameraMoveListener);
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
                if(init!=2)
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
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }

    }


    public void onCheckboxClicked(View view){
        //Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        //check which checkbox was clicked
        switch (view.getId()){
            case R.id.cctv :
                if(checked){
                    cctvSelection = true;
                    //cctv true;
                }
                else {
                    cctvSelection = false;
                    //cctv false;
                }
                break;
            case R.id.guard :
                if(checked){
                    //cctv true;
                    guardSelection = true;
                }
                else {
                    //cctv false;
                    guardSelection = false;
                }
                break;
            case R.id.indoor :
                if(checked){
                    //cctv true;
                    indoorSelection = true;
                }
                else {
                    //cctv false;
                    indoorSelection = false;
                }
                break;
        }
    }

    public void nextPage(View view){
        setContentView(R.layout.garage_add_layout);
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map2);
//        mapFragment.getMapAsync(this);
        latlng=current_loc.getPosition();
        Log.i("Lat",Double.toString(latlng.latitude));
        Log.i("Lng",Double.toString(latlng.longitude));
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latLngCur.latitude, latLngCur.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
            country = addresses.get(0).getCountryName();
            postalCode = addresses.get(0).getPostalCode();
            String admin = addresses.get(0).getAdminArea();
            knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
            Log.i("address",address);
            Log.i("admin area",admin);
            Log.i("city",city);
            Log.i("state",state);
            Log.i("country",country);
            Log.i("postalCode",postalCode);
            Log.i("knownName",knownName);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        initVariables();
        totalSlot.setOnItemSelectedListener(this);
    }

    public void backToPRevIntent(View view){
        finish();
    }
    public void backToPRevLayout(View view){
        finish();
        startActivity(getIntent());
    }

    private void configureCameraIdle() {
        onCameraIdleListener = new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                init=2;
                current_loc.remove();
                latLngCur = mMap.getCameraPosition().target;
                mMap.addMarker(new MarkerOptions().position(latLngCur).title("Marker"));
            }
        };
    }
    private void configureCameraMove() {
        onCameraMoveListener = new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                mMap.clear();
            }
        };
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position!=0) {
            spaceNo = new ArrayList<>();
            for(int i=0; i<position; i++)
                spaceNo.add("Space "+(i+1));
            initRecyclerView();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycleView_space);
        RecyclerViewAdapterSpace adapterSpace = new RecyclerViewAdapterSpace(spaceNo, this, this);
        recyclerView.setAdapter(adapterSpace);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemClick(int i) {
        Toast.makeText(this,spaceNo.get(i),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    public void submit(View view){
        RadioButton radioSelected = (RadioButton) findViewById(facilities.getCheckedRadioButtonId());

        Log.i("selected",radioSelected.getText().toString());
        String slot = totalSlot.getSelectedItem().toString();
        Log.i("slot",slot);
    }
}
