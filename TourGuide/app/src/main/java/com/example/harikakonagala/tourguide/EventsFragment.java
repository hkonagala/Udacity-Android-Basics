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
public class EventsFragment extends Fragment {


    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.category_list, container, false);

        final ArrayList<tours> tour = new ArrayList<tours>();

        tour.add(new tours("Adventure's Club","Baltimore Sound Stage","Sat, Jun 24 : 12 PM"));
        tour.add(new tours("Dragon Boat Challenge","Baltimore","Fri, Jun 26: 7 PM"));
        tour.add(new tours("Farmer's Market & Bazaar","Baltimore","Sat, Jun 24: 10 AM - 7 PM"));
        tour.add(new tours("City Lights Cruise","Baltimore Harbor","Sat, Jul 7: 7 PM - 12 AM"));
        tour.add(new tours("Crabs for Grads","Philips Seafood","Mon, Jun 26 - Fri, Jul 6:11 AM - 3 PM"));

        TourAdapter tourAdapter = new TourAdapter(getActivity(), tour);
        ListView listView = (ListView) rootView.findViewById(R.id.category);
        listView.setAdapter(tourAdapter);
        return rootView;

    }

}
