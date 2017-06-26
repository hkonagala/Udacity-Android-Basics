package com.example.harikakonagala.tourguide;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.resource;

/**
 * Created by Harika Konagala on 6/24/2017.
 */

public class TourAdapter extends ArrayAdapter<tours> {

    public TourAdapter(Context context, ArrayList<tours> tour) {
        super(context, 0, tour);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        RatingBar ratingBar;

        final SharedPreferences ratings;
        final SharedPreferences.Editor[] editor = new SharedPreferences.Editor[1];

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        tours CurrentTour = getItem(position);
        TextView title = (TextView) listItemView.findViewById(R.id.title_text_view);
        title.setText(CurrentTour.getTitle());
        TextView desc = (TextView) listItemView.findViewById(R.id.desc_text_view);
        desc.setText(CurrentTour.getDesc());
        TextView openHours = (TextView) listItemView.findViewById(R.id.open_text_view);
        openHours.setText(CurrentTour.getDate());

        ratingBar = (RatingBar) listItemView.findViewById(R.id.rate_me);
        ratings = PreferenceManager.getDefaultSharedPreferences(getContext());

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(ratingBar == null){
                    final int numStars = ratingBar.getNumStars();
                    editor[0] = ratings.edit();
                    editor[0].putInt("numstars", numStars);
                    editor[0].commit();
                }
            }
        });


        return listItemView;

    }
}
