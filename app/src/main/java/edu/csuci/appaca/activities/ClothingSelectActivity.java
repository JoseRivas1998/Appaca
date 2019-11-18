package edu.csuci.appaca.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.AlpacaFarm;
import edu.csuci.appaca.data.Inventory;
import edu.csuci.appaca.data.SaveDataUtils;
import edu.csuci.appaca.data.statics.ShopData;
import edu.csuci.appaca.data.statics.StaticClothesItem;
import edu.csuci.appaca.fragments.IconBadgeFragment;


public class ClothingSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing_select);
        getSupportActionBar().hide();
        loadClothes();
    }

    private void loadClothes() {
        for (final StaticClothesItem clothesItem : ShopData.getAllClothes()) {
            int amount = Inventory.getClothesAmount(clothesItem.id);
            if(amount > 0) {
                IconBadgeFragment iconBadge = IconBadgeFragment.ofAsset(clothesItem.path, String.valueOf(amount));
                // TODO 144 load preview instead of doing this
                iconBadge.setOnIconClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmClothes(clothesItem.id);
                    }
                });
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.clothing_select_list, iconBadge)
                        .commit();
            }
        }
    }

    private void confirmClothes(int clothesId) {
        SaveDataUtils.updateValuesAndSave(ClothingSelectActivity.this);
        Inventory.useClothes(clothesId);
        AlpacaFarm.getCurrentAlpaca().setClothing(clothesId);
        SaveDataUtils.save(ClothingSelectActivity.this);
        finish();
    }

}


