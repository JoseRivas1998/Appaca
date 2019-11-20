package edu.csuci.appaca.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.CurrencyManager;
import edu.csuci.appaca.data.Inventory;
import edu.csuci.appaca.data.SaveDataUtils;
import edu.csuci.appaca.data.statics.StaticClothesItem;
import edu.csuci.appaca.utils.AssetsUtils;

public class ClothingConfirmationPage extends DialogFragment {

    private int amount = 0;
    private StaticClothesItem clothingItem;
    private TextView costView;

    public static ClothingConfirmationPage newInstance(StaticClothesItem clothingItem) {
        ClothingConfirmationPage confirmationPage = new ClothingConfirmationPage();
        confirmationPage.clothingItem = clothingItem;
        return confirmationPage;
    }

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
        costView = view.findViewById(R.id.shop_conf_amount_cost);

        label.setText(clothingItem.name);
        icon.setImageDrawable(AssetsUtils.drawableFromAsset(getContext(), clothingItem.path));


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
                int maxAfford = CurrencyManager.getCurrencyOther() / clothingItem.cost;
                if(amount + 1 > maxAfford) {
                    cantAffordThatMuchToast();
                }
                amount = Math.min(amount + 1, maxAfford);
                updateAmountText(view);
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amount == 0) return;
                int cost = amount * clothingItem.cost;
                try {
                    CurrencyManager.spendCurrencyOther(cost);
                    Inventory.addClothes(clothingItem.id, amount);
                    SaveDataUtils.updateValuesAndSave(getContext());
                    dismiss();
                } catch (CurrencyManager.CurrencyException e) {
                    cantAffordThatMuchToast();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        updateAmountText(view);

    }

    private void updateAmountText(View view) {
        final TextView amountText = view.findViewById(R.id.shop_conf_amount_amount);
        amountText.setText(String.format("%d", amount));
        costView.setText(String.format("%d", amount * clothingItem.cost));
    }

    private void cantAffordThatMuchToast() {
        Context context = getContext();
        CharSequence text = "You cannot afford that!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}
