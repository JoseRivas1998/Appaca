package edu.csuci.appaca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.badlogic.gdx.backends.android.AndroidApplication;

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.Alpaca;
import edu.csuci.appaca.data.AlpacaFarm;
import edu.csuci.appaca.data.Inventory;
import edu.csuci.appaca.data.SaveDataUtils;
import edu.csuci.appaca.data.gameres.ClothingPreviewResources;
import edu.csuci.appaca.data.statics.ShopData;
import edu.csuci.appaca.data.statics.StaticClothesItem;
import edu.csuci.appaca.fragments.IconBadgeFragment;
import edu.csuci.appaca.graphics.ClothingPreview;


public class ClothingSelectActivity extends AndroidApplication {

    private ClothingPreview preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing_select);
        loadClothes();
        initPreview();
    }

    private void initPreview() {
        final FrameLayout frameLayout = findViewById(R.id.clothing_select_preview);
        ClothingPreviewResources.load(this);
        preview = new ClothingPreview();
        frameLayout.addView(initializeForView(preview));
    }

    private void loadClothes() {
        IconBadgeFragment remove = IconBadgeFragment.ofResource(R.drawable.x_2, "Remove");
        // TODO 144 load preview instead of doing this
        remove.setOnIconClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmClothes(Alpaca.NO_CLOTHING);
            }
        });
        getFragmentManager()
                .beginTransaction()
                .add(R.id.clothing_select_list, remove)
                .commit();

        for (final StaticClothesItem clothesItem : ShopData.getAllClothes()) {
            int amount = Inventory.getClothesAmount(clothesItem.id);
            if (amount > 0 || AlpacaFarm.getCurrentAlpaca().getClothing() == clothesItem.id) {
                IconBadgeFragment iconBadge = IconBadgeFragment.ofAsset(clothesItem.path, String.valueOf(amount));
                iconBadge.setOnIconClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmClothes(clothesItem.id);
                    }
                });
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.clothing_select_list, iconBadge)
                        .commit();
            }
        }
    }

    private void confirmClothes(int clothesId) {
        if (AlpacaFarm.getCurrentAlpaca().getClothing() != clothesId) {
            SaveDataUtils.updateValuesAndSave(ClothingSelectActivity.this);
            Inventory.useClothes(clothesId);
            AlpacaFarm.getCurrentAlpaca().setClothing(clothesId);
            SaveDataUtils.save(ClothingSelectActivity.this);
            //finish();
        }
    }

}


