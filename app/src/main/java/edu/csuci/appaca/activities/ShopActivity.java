package edu.csuci.appaca.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import edu.csuci.appaca.R;
import edu.csuci.appaca.adapters.ShopPagerAdapter;
import edu.csuci.appaca.utils.ArrayUtils;

public class ShopActivity extends AppCompatActivity {

    private ShopPagerAdapter shopPagerAdapter;
    private ViewPager viewPager;
    private ShopPagerAdapter.Page currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        setupNavigation();
        getSupportActionBar().hide();
    }

    private void setupNavigation() {
        shopPagerAdapter = new ShopPagerAdapter(getSupportFragmentManager());
        currentPage = ShopPagerAdapter.Page.FOOD;
        int[] currentIds = getTabId(currentPage);
        ImageView newDrawable = findViewById(currentIds[1]);
        TextView newTab = findViewById(currentIds[2]);
        newTab.setTextColor(getResources().getColor(R.color.colorAccent, getTheme()));
        newDrawable.setColorFilter(getResources().getColor(R.color.colorAccent, getTheme()));
        viewPager = findViewById(R.id.shop_view_pager);
        viewPager.setAdapter(shopPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                ShopPagerAdapter.Page nextPage = ShopPagerAdapter.Page.values()[position];

                int[] oldIds = getTabId(currentPage);
                int[] newIds = getTabId(nextPage);

                ImageView oldDrawable = findViewById(oldIds[1]);
                TextView oldTab = findViewById(oldIds[2]);
                oldDrawable.setColorFilter(null);
                oldTab.setTextColor(getResources().getColor(android.R.color.tab_indicator_text, getTheme()));

                ImageView newDrawable = findViewById(newIds[1]);
                TextView newTab = findViewById(newIds[2]);
                newTab.setTextColor(getResources().getColor(R.color.colorAccent, getTheme()));
                newDrawable.setColorFilter(getResources().getColor(R.color.colorAccent, getTheme()));

                currentPage = nextPage;
            }
        });
        setupTabs();
    }

    private void setupTabs() {
        final View foodTab = findViewById(R.id.shop_food_tab);
        foodTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToPage(ShopPagerAdapter.Page.FOOD);
            }
        });
        final View clothingTab = findViewById(R.id.shop_clothing_tab);
        clothingTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToPage(ShopPagerAdapter.Page.CLOTHES);
            }
        });
        final View alpacasTab = findViewById(R.id.shop_alpacas_tab);
        alpacasTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToPage(ShopPagerAdapter.Page.ALPACAS);
            }
        });
    }

    private int[] getTabId(ShopPagerAdapter.Page page) {
        int[] tabs = {-1, -1, -1};
        switch (page) {
            case FOOD:
                tabs[0] = R.id.shop_food_tab;
                tabs[1] = R.id.shop_food_tab_img;
                tabs[2] = R.id.shop_food_tab_text;
                break;
            case CLOTHES:
                tabs[0] = R.id.shop_clothing_tab;
                tabs[1] = R.id.shop_clothing_tab_img;
                tabs[2] = R.id.shop_clothing_tab_text;
                break;
            case ALPACAS:
                tabs[0] = R.id.shop_alpacas_tab;
                tabs[1] = R.id.shop_alpacas_tab_img;
                tabs[2] = R.id.shop_alpacas_tab_text;
                break;
        }
        return tabs;
    }

    private void switchToPage(ShopPagerAdapter.Page page) {
        viewPager.setCurrentItem(page.ordinal());
    }
}
