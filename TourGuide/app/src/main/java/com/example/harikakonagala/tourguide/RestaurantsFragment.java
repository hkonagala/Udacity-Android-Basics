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
public class RestaurantsFragment extends Fragment {


    public RestaurantsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.category_list, container, false);
        final ArrayList<tours> tour = new ArrayList<tours>();
        tour.add(new tours("Royal Taj","Ellicot City","Mon - Sun:10 AM - 12 AM"));
        tour.add(new tours("Cheesecake Factory","Baltimore","Mon - Sun:10 AM - 12 AM"));
        tour.add(new tours("Uno Grill","Baltimore","Mon - Sun:10 AM - 12 AM"));
        tour.add(new tours("PF Changs","Baltimore","Mon - Sun:10 AM - 12 AM"));
        tour.add(new tours("Nando's","Baltimore","Mon - Sun:10 AM - 12 AM"));

        TourAdapter tourAdapter = new TourAdapter(getActivity(), tour);
        ListView listView = (ListView) rootView.findViewById(R.id.category);
        listView.setAdapter(tourAdapter);
        return rootView;
    }

}
