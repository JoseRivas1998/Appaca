package edu.csuci.appaca.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import edu.csuci.appaca.R;
import edu.csuci.appaca.utils.ScreenUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClothingShopFragment extends Fragment {


    public ClothingShopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clothing_shop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final GridLayout gridLayout = view.findViewById(R.id.clothing_shop_grid);
        int size = (int) ScreenUtils.dpToPixels(view.getContext(), 100);
        int margin = (int) ScreenUtils.dpToPixels(view.getContext(), 20);
        for (int i = 0; i < 9; i++) {
            ImageView appleView = new ImageView(this.getContext());
            appleView.setImageResource(R.drawable.armor_icon);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = size;
            params.height = size;
            params.setMargins(margin, margin, margin, margin);
            appleView.setLayoutParams(params);
            gridLayout.addView(appleView);
        }
    }
}
