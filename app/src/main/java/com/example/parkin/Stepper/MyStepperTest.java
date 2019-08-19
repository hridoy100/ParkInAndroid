package com.example.parkin.Stepper;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkin.MainActivity;
import com.example.parkin.MyFragment.AddressFragment;
import com.example.parkin.MyFragment.DetailsFragment;
import com.example.parkin.MyFragment.LocationFragment;
import com.example.parkin.R;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.ArrayList;
import java.util.Arrays;

import barikoi.barikoilocation.BarikoiAPI;
import barikoi.barikoilocation.NearbyPlace.NearbyPlaceAPI;
import barikoi.barikoilocation.NearbyPlace.NearbyPlaceListener;
import barikoi.barikoilocation.PlaceModels.GeoCodePlace;
import barikoi.barikoilocation.PlaceModels.NearbyPlace;
import barikoi.barikoilocation.SearchAutoComplete.SearchAutocompleteFragment;


public class MyStepperTest extends AppCompatActivity implements AddressFragment.OnFragmentInteractionListener,
        LocationFragment.OnFragmentInteractionListener, DetailsFragment.OnFragmentInteractionListener{

    private static final String TAG = "MyStepperTest";

    ImageView backButton;
//    AppCompatSpinner citySpinner;
    TextView step1roundedView;
    TextView step2roundedView;
    TextView step3roundedView;
    TextView step1text;
    TextView step2text;
    TextView step3text;

    AppCompatEditText addressFetch;

    TextView goBack;
    Button continueNext;
    int currentStep = 1;

    Double latitude,longitude;
    String addressTitle;
//
//    GoogleMap mMap;
//    LocationManager locationManager;
//    LocationListener locationListener;
//    private Marker current_loc;
//    int init;
//
//    LatLng latLngCur;
//    private GoogleMap.OnCameraIdleListener onCameraIdleListener;
//    private GoogleMap.OnCameraMoveListener onCameraMoveListener;
//    View mapView;


    // Fragments..
    AddressFragment addressFragment;
    LocationFragment locationFragment;
    DetailsFragment detailsFragment;

    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_stepper_test);
        backButton = (ImageView) findViewById(R.id.backImage);
//        citySpinner = (AppCompatSpinner) findViewById(R.id.sp_city);

        step1roundedView = (TextView) findViewById(R.id.step1roundText);
        step2roundedView = (TextView) findViewById(R.id.step2roundText);
        step3roundedView = (TextView) findViewById(R.id.step3roundText);

        step1text = (TextView) findViewById(R.id.step1Text);
        step2text = (TextView) findViewById(R.id.step2Text);
        step3text = (TextView) findViewById(R.id.step3Text);

        goBack = (TextView) findViewById(R.id.goBack);
        continueNext = (Button) findViewById(R.id.continueToNext);

        //addressFetch = (AppCompatEditText) findViewById(R.id.addressFetch);

//        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city));
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        citySpinner.setAdapter(arrayAdapter);

        step1text.setTextColor(Color.parseColor("#000000"));
        step1roundedView.setTextColor(Color.parseColor("#FFFFFF"));
        step1roundedView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
        goBack.setEnabled(false);
        goBack.setVisibility(View.INVISIBLE);

        test = findViewById(R.id.test);

        //google map initialization..
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.googleMapBarikoi);
//        mapFragment.getMapAsync(this);
//        mapView = mapFragment.getView();
//        init=0;
//        configureCameraIdle();
//        configureCameraMove();

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentStep--;
                if(currentStep<1)
                    currentStep=1;
                onNextOrBackPressed();
            }
        });

        continueNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentStep++;
                if(currentStep>3)
                    currentStep=3;
                onNextOrBackPressed();

                /*mapView.setVisibility(View.VISIBLE);
                current_loc = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(addressTitle));
                mMap.setOnCameraIdleListener(onCameraIdleListener);
                mMap.setOnCameraMoveListener(onCameraMoveListener);
                LatLng hall= new LatLng(latitude,longitude);
                if(init==0) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hall, 15));
                    // Zoom in, animating the camera.
                    mMap.animateCamera(CameraUpdateFactory.zoomIn());

                    // Zoom out to zoom level 10, animating with a duration of 2 seconds.
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

                    // Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(hall)      // Sets the center of the map to Mountain View
                            .zoom(17)                   // Sets the zoom
                            .bearing(90)                // Sets the orientation of the camera to east
                            .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                            .build();                   // Creates a CameraPosition from the builder
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
                */

            }
        });


        if(savedInstanceState == null) {
            addressFragment = AddressFragment.newInstance("address","add");
            locationFragment = LocationFragment.newInstance("location","loc");
            detailsFragment = DetailsFragment.newInstance("details","det");
            //fragment = new AddressFragment();
        }

        displayAddressFragment();


        /*addressFetch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                // we test it here (assuming your main Activity is called "MainActivity"
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
                // you can do whatever with the value here directly (like call "do_search"),
                // or launch a background Thread to do it from here
            }
        });*/

//        if (!Places.isInitialized()) {
//            Places.initialize(getApplicationContext(), "AIzaSyAK9AJgEjjqpjbQ-dd5NjNT9Q9CTKrkvbQ");
//        }

        /*PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);*/

        /*AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        autocompleteFragment.setTypeFilter(TypeFilter.ADDRESS);
        //autocompleteFragment.setCountry("BD");

        /*AutocompleteFilter filter = new AutocompleteFilter.Builder()
                .setCountry("IN")
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();
        //autocompleteFragment.(filter);*/
        /*
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Toast.makeText(getApplicationContext(), place.getName(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(Status status) {
                Toast.makeText(getApplicationContext(), status.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        */

//        //barikoi..
//
//        BarikoiAPI.getINSTANCE(getApplicationContext(), "MTQ0NjpWUTg2SldKWTRL");
//
//
//        SearchAutocompleteFragment searchAutocompleteFragment;
//        searchAutocompleteFragment=(SearchAutocompleteFragment)getSupportFragmentManager().findFragmentById(R.id.barikoiSearchAutocompleteFragment);
//        searchAutocompleteFragment.setPlaceSelectionListener(new SearchAutocompleteFragment.PlaceSelectionListener() {
//
//            @Override
//            public void onPlaceSelected(GeoCodePlace place) {
//                Toast.makeText(getApplicationContext(), "Place Selected: "+place.getAddress(), Toast.LENGTH_SHORT).show();
//                latitude=Double.parseDouble(place.getLatitude());
//                longitude=Double.parseDouble(place.getLongitude());
//                addressTitle = place.getAddress();
//                //mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(place.getAddress()));
//
//            }
//
//            @Override
//            public void onFailure(String error) {
//                Toast.makeText(getApplicationContext(), "Error Message"+error, Toast.LENGTH_SHORT).show();
//            }
//
//        });
//

        //mapbox
        /*
        Mapbox.getInstance(this, "pk.eyJ1IjoiaHJpZG95MTAwIiwiYSI6ImNqemczdTM3ODBnNmYzaHA1bnZ3MGcyZ2EifQ.hZGjS6lzRuBw3vSoUiCtgg");
        //setContentView(R.layout.activity_main);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

// Map is set up and the style has loaded. Now you can add data or make other map adjustments


                    }
                });
            }
        });
        */

        //mapView.setVisibility(View.INVISIBLE);
    }

    public void returnBack(View view) {
        finish();
    }

    public void onNextOrBackPressed() {
        switch (currentStep) {
            case 1 :
                step1text.setTextColor(Color.parseColor("#000000"));
                step1roundedView.setTextColor(Color.parseColor("#FFFFFF"));
                step1roundedView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                goBack.setEnabled(false);
                goBack.setVisibility(View.INVISIBLE);

                step2text.setTextColor(Color.parseColor("#9daba6"));
                step2roundedView.setTextColor(Color.parseColor("#9daba6"));
                step2roundedView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                displayAddressFragment();

                break;

            case 2 :
                step2text.setTextColor(Color.parseColor("#000000"));
                step2roundedView.setTextColor(Color.parseColor("#FFFFFF"));
                step2roundedView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                goBack.setEnabled(true);
                goBack.setVisibility(View.VISIBLE);

                step3text.setTextColor(Color.parseColor("#9daba6"));
                step3roundedView.setTextColor(Color.parseColor("#9daba6"));
                step3roundedView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));

                displayLocationFragment();
                break;
            case 3 :
                step3text.setTextColor(Color.parseColor("#000000"));
                step3roundedView.setTextColor(Color.parseColor("#FFFFFF"));
                step3roundedView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                displayDetailsFragment();
                break;
            default:
                throw new IllegalStateException();
        }
    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//
//        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
//                PackageManager.PERMISSION_GRANTED &&
//                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
//                        PackageManager.PERMISSION_GRANTED) {
//            googleMap.setMyLocationEnabled(true);
//            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
//        } else {
//            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
//        }
//        if (mapView != null &&
//                mapView.findViewById(Integer.parseInt("1")) != null) {
//            // Get the button view
//            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
//            // and next place it, on bottom right (as Google Maps app)
//            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
//                    locationButton.getLayoutParams();
//            // position on right bottom
//            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
//            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
//            layoutParams.setMargins(0, 0, 30, 30);
//            mapView.setVisibility(View.INVISIBLE);
//        }
//
//        /*locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                Log.i("Location", location.toString());
//                Log.v(TAG, "IN ON LOCATION CHANGE, lat=" + location.getLatitude() + ", lon=" + location
//                        .getLongitude());
//                //mMap.clear();
//                LatLng hall=null;
//                if(init==1)
//                    current_loc.remove();
//                if(init!=2) {
//                    //hall = new LatLng(latitude, longitude);
//                    hall = new LatLng(location.getLatitude(), location.getLongitude());
//                    current_loc = mMap.addMarker(new MarkerOptions().position(hall).title("My Location"));
//                }
//                mMap.setOnCameraIdleListener(onCameraIdleListener);
//                mMap.setOnCameraMoveListener(onCameraMoveListener);
//                if(init==0) {
//                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hall, 15));
//                    // Zoom in, animating the camera.
//                    mMap.animateCamera(CameraUpdateFactory.zoomIn());
//
//                    // Zoom out to zoom level 10, animating with a duration of 2 seconds.
//                    mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
//
//                    // Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
//                    CameraPosition cameraPosition = new CameraPosition.Builder()
//                            .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to Mountain View
//                            .zoom(17)                   // Sets the zoom
//                            .bearing(90)                // Sets the orientation of the camera to east
//                            .tilt(30)                   // Sets the tilt of the camera to 30 degrees
//                            .build();                   // Creates a CameraPosition from the builder
//                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//
//                }
//                if(init!=2)
//                    init=1;
//
//            }
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//
//            }
//        };
//        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//        }
//        else {
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
//        }
//        */
//    }
    /*
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    */
//    private void configureCameraIdle() {
//        onCameraIdleListener = new GoogleMap.OnCameraIdleListener() {
//            @Override
//            public void onCameraIdle() {
//                init=2;
//                current_loc.remove();
//                latLngCur = mMap.getCameraPosition().target;
//                mMap.addMarker(new MarkerOptions().position(latLngCur).title("Marker"));
//            }
//        };
//    }
//    private void configureCameraMove() {
//        onCameraMoveListener = new GoogleMap.OnCameraMoveListener() {
//            @Override
//            public void onCameraMove() {
//                mMap.clear();
//            }
//        };
//    }

    void displayAddressFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(addressFragment.isAdded()){
            ft.show(addressFragment);
        } else {
            ft.add(R.id.fragContainer, addressFragment, "AddressFragment");
        }

        //hide other fragments..
        if(locationFragment.isAdded()) {
            ft.hide(locationFragment);
        }

        ft.commit();
    }

    void displayLocationFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(locationFragment.isAdded()){
            ft.show(locationFragment);
        } else {
            ft.add(R.id.fragContainer, locationFragment, "LocationFragment");
        }

        //hide other fragments..
        if(addressFragment.isAdded()) {
            ft.hide(addressFragment);
        }
        if(detailsFragment.isAdded()) {
            ft.hide(detailsFragment);
        }

        ft.commit();
    }

    void displayDetailsFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(detailsFragment.isAdded()){
            ft.show(detailsFragment);
        } else {
            ft.add(R.id.fragContainer, detailsFragment, "DetailsFragment");
        }

        //hide other fragments..
        if(addressFragment.isAdded()) {
            ft.hide(addressFragment);
        }
        if(locationFragment.isAdded()) {
            ft.hide(locationFragment);
        }

        ft.commit();
    }


    public void setLatLngFromAddressFragment(Double lat, Double lon, String addressTitle){
        latitude = lat;
        longitude = lon;
        this.addressTitle = addressTitle;
        test.setText(Double.toString(latitude)+ " " +Double.toString(longitude)+" "+addressTitle);
    }

    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }
}
