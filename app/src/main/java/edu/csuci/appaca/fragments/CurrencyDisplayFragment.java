package edu.csuci.appaca.fragments;


import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import edu.csuci.appaca.R;

public class CurrencyDisplayFragment extends Fragment {

    private ImageView iconView;
    private TextView valueText;
    private int currencyValue;

    public CurrencyDisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_currency_display, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iconView = view.findViewById(R.id.currency_display_image);
        valueText = view.findViewById(R.id.currency_display_text);
        this.currencyValue = 0;
    }

    public void setIconResource(int resourceId) {
        iconView.setImageResource(resourceId);
    }

    public void setCurrencyValue(int currency) {
        this.currencyValue = currency;
        valueText.setText(Integer.toString(currency));
    }

    public int getCurrencyValue() {
        return this.currencyValue;
    }

}
