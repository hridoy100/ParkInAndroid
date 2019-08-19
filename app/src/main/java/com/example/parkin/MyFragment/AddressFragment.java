package com.example.parkin.MyFragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.parkin.R;
import com.example.parkin.Stepper.MyStepperTest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;

import barikoi.barikoilocation.BarikoiAPI;
import barikoi.barikoilocation.PlaceModels.GeoCodePlace;
import barikoi.barikoilocation.SearchAutoComplete.SearchAutocompleteFragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddressFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddressFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddressFragment.
     */
    // TODO: Rename and change types and number of parameters

    AppCompatSpinner citySpinner;
    Double latitude,longitude;
    String addressTitle;

    GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    private Marker current_loc;
    int init;

    LatLng latLngCur;
    private GoogleMap.OnCameraIdleListener onCameraIdleListener;
    private GoogleMap.OnCameraMoveListener onCameraMoveListener;
    View mapView;

    public static AddressFragment newInstance(String param1, String param2) {
        AddressFragment fragment = new AddressFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_address, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        citySpinner = (AppCompatSpinner) view.findViewById(R.id.sp_city);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(arrayAdapter);

//        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.googleMapBarikoi);
//        mapFragment.getMapAsync(this);
//        mapView = mapFragment.getView();
//        init=0;
//        configureCameraIdle();
//        configureCameraMove();

        if (!Places.isInitialized()) {
            Places.initialize(getContext(), "AIzaSyAK9AJgEjjqpjbQ-dd5NjNT9Q9CTKrkvbQ");
        }

        //barikoi..

        BarikoiAPI.getINSTANCE(getContext(), "MTQ0NjpWUTg2SldKWTRL");


        SearchAutocompleteFragment searchAutocompleteFragment;
        searchAutocompleteFragment=(SearchAutocompleteFragment)this.getChildFragmentManager().findFragmentById(R.id.barikoiSearchAutocompleteFragment);
        searchAutocompleteFragment.setPlaceSelectionListener(new SearchAutocompleteFragment.PlaceSelectionListener() {

            @Override
            public void onPlaceSelected(GeoCodePlace place) {
                Toast.makeText(getContext(), "Place Selected: "+place.getAddress(), Toast.LENGTH_SHORT).show();
                latitude=Double.parseDouble(place.getLatitude());
                longitude=Double.parseDouble(place.getLongitude());
                addressTitle = place.getAddress();
                MyStepperTest myStepperTest = (MyStepperTest) getActivity();
                myStepperTest.setLatLngFromAddressFragment(latitude,longitude,addressTitle);
                //mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(place.getAddress()));

            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(getContext(), "Error Message"+error, Toast.LENGTH_SHORT).show();
            }

        });



    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) getActivity();
        } else {
            throw new RuntimeException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    /*@Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
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
            mapView.setVisibility(View.INVISIBLE);
        }

        /*locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
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
                    //hall = new LatLng(latitude, longitude);
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
        */
    /*}


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
    */


}
