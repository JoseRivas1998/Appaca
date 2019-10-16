package edu.csuci.appaca.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.csuci.appaca.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodShopFragment extends Fragment {


    public FoodShopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_shop, container, false);
    }

}
