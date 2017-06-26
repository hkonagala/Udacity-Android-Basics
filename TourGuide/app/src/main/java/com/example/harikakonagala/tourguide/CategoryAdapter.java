package com.example.harikakonagala.tourguide;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Harika Konagala on 6/24/2017.
 */

public class CategoryAdapter extends FragmentPagerAdapter {

    private Context context;

    public CategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new AttractionsFragment();
        }else if(position == 1){
            return new PlacesFragment();
        }else if(position == 2){
            return new RestaurantsFragment();
        }else {
            return new EventsFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "Attractions";
        }else if(position == 1){
            return "Public Places";
        }else if(position == 2 ){
            return "Restaurants";
        }else{
            return "Events";
        }

    }
}
