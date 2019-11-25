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
import edu.csuci.appaca.data.CurrencyManager;
import edu.csuci.appaca.data.Inventory;
import edu.csuci.appaca.data.SaveDataUtils;
import edu.csuci.appaca.data.statics.AlpacaShopItem;
import edu.csuci.appaca.data.statics.StaticFoodItem;
import edu.csuci.appaca.utils.AssetsUtils;

public class AlpacaConfirmationPage extends DialogFragment {

    private AlpacaShopItem alpacaItem;


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
        final Button buy = view.findViewById(R.id.alpaca_confirmation_buy);
        final Button cancel = view.findViewById(R.id.alpaca_confirmation_cancel);
        final EditText nameInput = view.findViewById(R.id.alpaca_confirmation_name_field);

        label.setText(alpacaItem.name);
        icon.setImageDrawable(AssetsUtils.drawableFromAsset(getContext(), alpacaItem.path));

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buy();
            }
        });

        nameInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_GO) {
                    buy();
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

    private void buy() {
        // TODO
    }

    private void cantAffordThatMuchToast() {
        Context context = getContext();
        CharSequence text = "You cannot afford that!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}
