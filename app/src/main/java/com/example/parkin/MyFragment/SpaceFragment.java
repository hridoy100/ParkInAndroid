package com.example.parkin.MyFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.parkin.R;
import com.example.parkin.RecyclerViewAdapters.RecyclerViewAdapterSingleSpace;
import com.example.parkin.RecyclerViewAdapters.RecyclerViewAdapterSpace;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SpaceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SpaceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpaceFragment extends Fragment implements RecyclerViewAdapterSingleSpace.OnItemClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<String> spaceNo;
    int spaceCount;

    private OnFragmentInteractionListener mListener;

    public SpaceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SpaceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SpaceFragment newInstance(String param1, String param2) {
        SpaceFragment fragment = new SpaceFragment();
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
        View v = inflater.inflate(R.layout.fragment_space, container, false);;
        RecyclerView recyclerView = v.findViewById(R.id.recycleView_space);
        if(spaceCount!=0) {
            spaceNo = new ArrayList<>();
            for(int i=0; i<spaceCount; i++)
                spaceNo.add("Space #"+(i+1));
        }
        RecyclerViewAdapterSingleSpace adapterSpace = new RecyclerViewAdapterSingleSpace(spaceNo, getActivity(), this);
        recyclerView.setAdapter(adapterSpace);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
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

}
