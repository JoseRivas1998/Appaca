package edu.csuci.appaca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.AlpacaFarm;
import edu.csuci.appaca.data.SaveDataUtils;
import edu.csuci.appaca.data.StaminaManager;
import edu.csuci.appaca.data.statics.AlpacaShopItem;
import edu.csuci.appaca.data.statics.ShopData;
import edu.csuci.appaca.utils.AssetsUtils;

public class FirstAlpacaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_alpaca);
        loadImage();
        initButtons();
    }

    private void initButtons() {
        final Button button = findViewById(R.id.first_alpaca_confirmation_button);
        final EditText editText = findViewById(R.id.first_alpaca_name_input);
        final int defaultSkin = getResources().getInteger(R.integer.default_alpaca_skin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm(editText, defaultSkin);
            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_GO) {
                    confirm(editText, defaultSkin);
                    return true;
                }
                return false;
            }
        });
    }

    private void confirm(EditText editText, int defaultSkin) {
        String name = editText.getText().toString().trim();
        if(name.isEmpty()) {
            return;
        }
        AlpacaFarm.addAlpaca(defaultSkin, name);
        StaminaManager.increaseCurrentStaminaToMax();
        SaveDataUtils.save(FirstAlpacaActivity.this);
        Intent intent = new Intent(FirstAlpacaActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void loadImage() {
        final ImageView image = findViewById(R.id.first_alpaca_sprite);
        final int defaultSkin = getResources().getInteger(R.integer.default_alpaca_skin);
        final AlpacaShopItem defaultShopItem = ShopData.getAlpaca(defaultSkin);
        Drawable drawable = AssetsUtils.drawableFromAsset(this, defaultShopItem.path);
        image.setImageDrawable(drawable);
    }

}
