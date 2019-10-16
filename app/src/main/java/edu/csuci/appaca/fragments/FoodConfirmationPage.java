package edu.csuci.appaca.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import edu.csuci.appaca.R;

public class FoodConfirmationPage extends DialogFragment {

    private int amount = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirmation_page_with_amount, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ImageView icon = view.findViewById(R.id.shop_conf_amount_item_icon);
        final TextView label = view.findViewById(R.id.shop_conf_amount_item_label);
        final Button minus = view.findViewById(R.id.shop_conf_amount_minus_button);
        final Button plus = view.findViewById(R.id.shop_conf_amount_plus_button);
        final Button buy = view.findViewById(R.id.shop_conf_amount_buy);
        final Button cancel = view.findViewById(R.id.shop_conf_amount_cancel);
        icon.setImageResource(R.drawable.apple);
        label.setText("Apple");

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = Math.max(amount - 1, 0);
                updateAmountText(view);
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount++;
                updateAmountText(view);
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    private void updateAmountText(View view) {
        final TextView amountText = view.findViewById(R.id.shop_conf_amount_amount);
        amountText.setText(String.format("%d", amount));
    }

}
