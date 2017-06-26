package com.example.harikakonagala.tourguide;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlacesFragment extends Fragment {


    public PlacesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.category_list, container, false);
        final ArrayList<tours> tour = new ArrayList<tours>();
        tour.add(new tours("Maryland Live Casino","Arundel Mills","open 24 hrs"));
        tour.add(new tours("Arundel Mills Mall","Hanover","Mon - Fri:10 AM - 9:30 PM \n Sat & Sun: 10 AM - 7 PM"));
        tour.add(new tours("Ripleys Believe it or Not","Baltimore","Mon - Fri:10 AM - 5 PM"));
        tour.add(new tours("Mall at Columbia","Columbia","Mon - Fri:10 AM - 9:30 PM \n Sat & Sun: 10 AM - 7 PM"));
        tour.add(new tours("Annapolis Harbor","Annapolis","open 24 hrs"));

        TourAdapter tourAdapter = new TourAdapter(getActivity(), tour);
        ListView listView = (ListView) rootView.findViewById(R.id.category);
        listView.setAdapter(tourAdapter);
        return rootView;
    }

}
