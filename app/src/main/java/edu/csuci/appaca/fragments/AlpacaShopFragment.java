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
import edu.csuci.appaca.data.statics.AlpacaShopItem;
import edu.csuci.appaca.data.statics.ShopData;
import edu.csuci.appaca.data.statics.StaticFoodItem;
import edu.csuci.appaca.utils.AssetsUtils;
import edu.csuci.appaca.utils.ScreenUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlpacaShopFragment extends Fragment {


    public AlpacaShopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alpaca_shop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final GridLayout gridLayout = view.findViewById(R.id.alpaca_shop_grid);
        int size = (int) ScreenUtils.dpToPixels(view.getContext(), 100);
        int margin = (int) ScreenUtils.dpToPixels(view.getContext(), 20);
        ShopData.load(getContext());
        for (final AlpacaShopItem alpaca : ShopData.getAllAlpacas()) {
            ImageView alpacaView = new ImageView(this.getContext());
            alpacaView.setImageDrawable(AssetsUtils.drawableFromAsset(getContext(), alpaca.path));
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = size;
            params.height = size;
            params.setMargins(margin, margin, margin, margin);
            alpacaView.setLayoutParams(params);
            alpacaView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    AlpacaConfirmationPage alpacaConfirmationPage = AlpacaConfirmationPage.newInstance(alpaca);
                    alpacaConfirmationPage.show(fm, "fragment_food_conf");
                }
            });
            gridLayout.addView(alpacaView);
        }
    }
}


