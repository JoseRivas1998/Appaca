package edu.csuci.appaca.adapters;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import edu.csuci.appaca.fragments.AlpacaShopFragment;
import edu.csuci.appaca.fragments.ClothingShopFragment;
import edu.csuci.appaca.fragments.FoodShopFragment;

public class ShopPagerAdapter extends FragmentStatePagerAdapter {

    public enum Page {
        FOOD("Food"),
        CLOTHES("Clothes"),
        ALPACAS("Alpacas");
        public final String title;

        Page(String title) {
            this.title = title;
        }
    }

    public ShopPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Page page = Page.values()[position];
        Fragment fragment;
        switch (page) {
            case FOOD:
                fragment = new FoodShopFragment();
                break;
            case CLOTHES:
                fragment = new ClothingShopFragment();
                break;
            case ALPACAS:
                fragment = new AlpacaShopFragment();
                break;
            default:
                fragment = null;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return Page.values().length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return Page.values()[position].title;
    }
}
