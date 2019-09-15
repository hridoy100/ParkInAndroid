package com.example.parkin.MyFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.parkin.R;
import com.example.parkin.RecyclerViewAdapters.RecyclerViewAdapterSingleSpace;
import com.example.parkin.RecyclerViewAdapters.RecyclerViewAdapterSpace;
import com.example.parkin.Stepper.MyStepperTest;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment implements RecyclerViewAdapterSingleSpace.OnItemClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //TextView..........
    TextView selectedLocationShow;
    TextView editLocationButton;
    TextView termsAndCo;

    //...........


    //RadioGroup........
    RadioGroup typeOfSpace;
    RadioGroup vehicleType;
    //......

    //RadioButton......
    RadioButton vehilceTypeRadio;
    RadioButton parkTypeRadio;
    //

    // Checkbox......
    CheckBox feature1;
    CheckBox feature2;
    CheckBox feature3;
    CheckBox feature4;
    CheckBox feature5;
    CheckBox feature6;
    CheckBox feature7;
    //

    // Button........
    Button plus;
    Button minus;

    //......

    //LinearLayout for checkboxes...
    LinearLayout accessInfoCheckbox;
    LinearLayout featureCheckBox;
    //.....

    //EditText.........
    AppCompatEditText spaceCount;
    AppCompatEditText aboutSpace;
    AppCompatEditText accessInstruction;
    AppCompatEditText mobileNo;
    //..........

    ImageView mapView;

    String featuresStr ="";

    ArrayList<String> spaceNo;

    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(String param1, String param2) {
        DetailsFragment fragment = new DetailsFragment();
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
        View v = inflater.inflate(R.layout.fragment_details, container, false);
/*
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.Theme_SwitchDateTime);

        // clone the inflater using the ContextThemeWrapper
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);

        // inflate the layout using the cloned inflater, not default inflater
        return localInflater.inflate(R.layout.fragment_details, container, false);

*/
        //plus.setOnClickListener(this);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TextView Initialization..
        selectedLocationShow = (TextView) view.findViewById(R.id.selectedLocation);
        editLocationButton = (TextView) view.findViewById(R.id.editLocation);
        //EditText init..
        spaceCount = (AppCompatEditText) view.findViewById(R.id.spaceCount);
        mobileNo = (AppCompatEditText) view.findViewById(R.id.mobileNo);

        mapView = (ImageView) view.findViewById(R.id.mapViewImage);

        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        mobileNo.setText(mySharedPreferences.getString(getString(R.string.mobileNo), ""));

        ////aboutSpace = (AppCompatEditText) view.findViewById(R.id.aboutSpace);
        ////accessInstruction = (AppCompatEditText) view.findViewById(R.id.accessInstruction);
        //RadioGroup
        ////typeOfSpace = (RadioGroup) view.findViewById(R.id.typeOfParking);
        //vehicleType = (RadioGroup) view.findViewById(R.id.vehicleType);

//        typeOfSpace.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                parkTypeRadio = (RadioButton) view.findViewById(checkedId);
//            }
//        });

//        vehicleType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                vehilceTypeRadio = (RadioButton) view.findViewById(checkedId);
//                Toast.makeText(getContext(), vehilceTypeRadio.getText().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });

        //Checkboxes..
        //accessInfoCheckbox = (LinearLayout) view.findViewById(R.id.accessInfoCheckBox);
        featureCheckBox = (LinearLayout) view.findViewById(R.id.featuresBox);

        plus = (Button) view.findViewById(R.id.plusButton);
        minus = (Button) view.findViewById(R.id.minusButton);

        spaceNo = new ArrayList<>();
        for(int i=0; i< Integer.parseInt(spaceCount.getText().toString()); i++)
            spaceNo.add("Space #"+(i+1));
        initRecyclerView(view);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(spaceCount.getText().toString());
                if(count<20)
                    count++;
                spaceCount.setText(Integer.toString(count));

                spaceNo = new ArrayList<>();
                for(int i=0; i< Integer.parseInt(spaceCount.getText().toString()); i++)
                    spaceNo.add("Space #"+(i+1));
                initRecyclerView(view);

            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(spaceCount.getText().toString());
                if(count>1)
                    count--;
                spaceCount.setText(Integer.toString(count));
                spaceNo = new ArrayList<>();
                for(int i=0; i< Integer.parseInt(spaceCount.getText().toString()); i++)
                    spaceNo.add("Space #"+(i+1));
                initRecyclerView(view);
            }
        });

        feature1 = (CheckBox) view.findViewById(R.id.feature1);
        feature2 = (CheckBox) view.findViewById(R.id.feature2);
        feature3 = (CheckBox) view.findViewById(R.id.feature3);
        feature4 = (CheckBox) view.findViewById(R.id.feature4);
        feature5 = (CheckBox) view.findViewById(R.id.feature5);
        feature6 = (CheckBox) view.findViewById(R.id.feature6);
        feature7 = (CheckBox) view.findViewById(R.id.feature7);

        feature1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    featuresStr +="Covered parking,";
                    Toast.makeText(getContext(), featuresStr, Toast.LENGTH_SHORT).show();
                }
                else {
                    if(featuresStr.contains("Covered parking,")){
                        featuresStr = featuresStr.replace("Covered parking,", "");
                        Toast.makeText(getContext(), featuresStr, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        feature2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    featuresStr +="CCTV,";
                    Toast.makeText(getContext(), featuresStr, Toast.LENGTH_SHORT).show();
                }
                else {
                    if(featuresStr.contains("CCTV,")){
                        featuresStr = featuresStr.replace("CCTV,", "");
                        Toast.makeText(getContext(), featuresStr, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        feature3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    featuresStr +="Securely gated,";
                    Toast.makeText(getContext(), featuresStr, Toast.LENGTH_SHORT).show();
                }
                else {
                    if(featuresStr.contains("Securely gated,")){
                        featuresStr = featuresStr.replace("Securely gated,", "");
                        Toast.makeText(getContext(), featuresStr, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        feature4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    featuresStr +="Disabled access,";
                    Toast.makeText(getContext(), featuresStr, Toast.LENGTH_SHORT).show();
                }
                else {
                    if(featuresStr.contains("Disabled access,")){
                        featuresStr = featuresStr.replace("Disabled access,", "");
                        Toast.makeText(getContext(), featuresStr, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        feature5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    featuresStr +="Electric vehicle charging,";
                    Toast.makeText(getContext(), featuresStr, Toast.LENGTH_SHORT).show();
                }
                else {
                    if(featuresStr.contains("Electric vehicle charging,")){
                        featuresStr = featuresStr.replace("Electric vehicle charging,", "");
                        Toast.makeText(getContext(), featuresStr, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        feature6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    featuresStr +="Lighting,";
                    Toast.makeText(getContext(), featuresStr, Toast.LENGTH_SHORT).show();
                }
                else {
                    if(featuresStr.contains("Lighting,")){
                        featuresStr = featuresStr.replace("Lighting,", "");
                        Toast.makeText(getContext(), featuresStr, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        feature7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    featuresStr +="Oil buying facility,";
                    Toast.makeText(getContext(), featuresStr, Toast.LENGTH_SHORT).show();
                }
                else {
                    if(featuresStr.contains("Oil buying facility,")){
                        featuresStr = featuresStr.replace("Oil buying facility,", "");
                        Toast.makeText(getContext(), featuresStr, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        termsAndCo = (TextView) view.findViewById(R.id.termsAndCo);
        String termsString = "By proceeding to add your space, you agree that this parking space listing is advertised in accordance with ParkIn's <font color=#3e9c64>Terms & Conditions</font>, "+
                "you have the legal right to list this parking location for rent, and that you agree with ParkIn <font color=#3e9c64>Privacy Policy</font>.";
        termsAndCo.setText(Html.fromHtml(termsString));

        MyStepperTest myStepperTest = (MyStepperTest) getActivity();
        selectedLocationShow.setText(myStepperTest.getAddressTitle());

        Glide.with(myStepperTest)
                .asBitmap()
                .load(myStepperTest.getPlace().getImglink())
                .into(mapView);

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
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(int i) {
        Toast.makeText(getActivity(),spaceNo.get(i),Toast.LENGTH_SHORT).show();
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

    public HashMap<String, String> getFinalizedData(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("space_count", spaceCount.getText().toString());
        hashMap.put("features", featuresStr);
        String mob = "0"+mobileNo.getText().toString();
        hashMap.put("mobileNo", mob);
        return hashMap;
    }
    void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycleView_space);
        RecyclerViewAdapterSingleSpace adapterSpace = new RecyclerViewAdapterSingleSpace(spaceNo, getActivity(), this);
        recyclerView.setAdapter(adapterSpace);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

}
