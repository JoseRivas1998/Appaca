package edu.csuci.appaca.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.AlpacaFarm;
import edu.csuci.appaca.data.CurrencyManager;
import edu.csuci.appaca.data.Inventory;
import edu.csuci.appaca.data.SaveDataUtils;
import edu.csuci.appaca.data.StaminaManager;
import edu.csuci.appaca.data.statics.AlpacaShopItem;
import edu.csuci.appaca.data.statics.StaticFoodItem;
import edu.csuci.appaca.utils.AssetsUtils;

import static edu.csuci.appaca.data.CurrencyManager.getCurrencyAlpaca;
import static edu.csuci.appaca.data.CurrencyManager.spendCurrencyAlpaca;

public class AlpacaConfirmationPage extends DialogFragment {

    private AlpacaShopItem alpacaItem;
    private TextView costView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alpaca_confirmation_page, container, false);
    }
    public static AlpacaConfirmationPage newInstance(AlpacaShopItem alpacaItem) {
        AlpacaConfirmationPage confirmationPage = new AlpacaConfirmationPage();
        confirmationPage.alpacaItem = alpacaItem;
        return confirmationPage;
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ImageView icon = view.findViewById(R.id.alpaca_confirmation_preview_image);
        final TextView label = view.findViewById(R.id.alpaca_confirmation_name);
        final TextView cost = view.findViewById(R.id.alpaca_confirmation_cost);
        final Button buy = view.findViewById(R.id.alpaca_confirmation_buy);
        final Button cancel = view.findViewById(R.id.alpaca_confirmation_cancel);
        final EditText nameInput = view.findViewById(R.id.alpaca_confirmation_name_field);


        cost.setText(String.valueOf(alpacaItem.cost));
        label.setText(alpacaItem.name);
        icon.setImageDrawable(AssetsUtils.drawableFromAsset(getContext(), alpacaItem.path));

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buy(view);
            }
        });

        nameInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_GO) {
                    buy(view);
                    return true;
                }
                return false;
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    private void buy(View view) {


        final EditText nameInput = view.findViewById(R.id.alpaca_confirmation_name_field);

        String name = nameInput.getText().toString().trim();
        if(name.isEmpty()) {
            cantToast("You need to give them a name!");
        }

        try {
            CurrencyManager.spendCurrencyAlpaca(alpacaItem.cost);
            AlpacaFarm.addAlpaca(alpacaItem.id, name);
            StaminaManager.increaseMaxStamina();
            StaminaManager.increaseCurrentStaminaToMax();
            SaveDataUtils.updateValuesAndSave(getContext());
            dismiss();
        } catch (CurrencyManager.CurrencyException e) {
            cantToast("You can't afford that!");
        }

    }

    private void cantToast(String text) {
        Context context = getContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}
