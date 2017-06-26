package com.example.harikakonagala.tourguide;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttractionsFragment extends Fragment {


    public AttractionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.category_list, container, false);


        final ArrayList<tours> tour = new ArrayList<tours>();
        tour.add(new tours("National Aquarium","Baltimore","Mon - Fri:10 AM - 5 PM"));
        tour.add(new tours("Inner Harbor","Baltimore","open 24 hrs"));
        tour.add(new tours("Maryland Zoo","Baltimore","Mon - Fri:10 AM - 5 PM"));
        tour.add(new tours("Mount Vernon","Vernon","Mon - Fri:10 AM - 5 PM"));
        tour.add(new tours("Museum of Art","Baltimore","Mon - Fri:10 AM - 5 PM"));

        TourAdapter tourAdapter = new TourAdapter(getActivity(), tour);
        ListView listView = (ListView) rootView.findViewById(R.id.category);
        listView.setAdapter(tourAdapter);


        return rootView;


    }
    }



