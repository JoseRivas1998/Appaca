package edu.csuci.appaca.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.statics.ShopData;
import edu.csuci.appaca.data.statics.StaticFoodItem;
import edu.csuci.appaca.utils.AssetsUtils;
import edu.csuci.appaca.utils.ScreenUtils;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final GridLayout gridLayout = view.findViewById(R.id.food_shop_grid);
        int size = (int) ScreenUtils.dpToPixels(view.getContext(), 100);
        int margin = (int) ScreenUtils.dpToPixels(view.getContext(), 20);
        ShopData.load(getContext());
        for (final StaticFoodItem foodItem : ShopData.getAllFood()) {
            ImageView foodView = new ImageView(this.getContext());
            foodView.setImageDrawable(AssetsUtils.drawableFromAsset(getContext(), foodItem.path));
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = size;
            params.height = size;
            params.setMargins(margin, margin, margin, margin);
            foodView.setLayoutParams(params);
            foodView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FoodConfirmationPage foodConfirmationPage = FoodConfirmationPage.newInstance(foodItem);
                    foodConfirmationPage.show(fm, "fragment_food_conf");
                }
            });
            gridLayout.addView(foodView);
        }
    }
}
