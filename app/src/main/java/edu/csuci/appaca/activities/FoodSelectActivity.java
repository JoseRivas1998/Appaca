package edu.csuci.appaca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.AlpacaFarm;
import edu.csuci.appaca.data.Inventory;
import edu.csuci.appaca.data.SaveDataUtils;
import edu.csuci.appaca.data.statics.ShopData;
import edu.csuci.appaca.data.statics.StaticFoodItem;
import edu.csuci.appaca.fragments.IconBadgeFragment;

public class FoodSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_select);
        getSupportActionBar().hide();
        for (final StaticFoodItem foodItem : ShopData.getAllFood()) {
            int amount = Inventory.getFoodAmount(foodItem.id);
            if(amount > 0) {
                IconBadgeFragment iconBadge = IconBadgeFragment.ofAsset(foodItem.path, String.valueOf(amount));
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.food_select_inventory, iconBadge)
                        .commit();
            }
        }
    }
}
