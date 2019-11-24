package edu.csuci.appaca.data.gameres;

import android.content.Context;

import com.badlogic.gdx.graphics.Color;

import edu.csuci.appaca.R;

import static edu.csuci.appaca.utils.ResourceUtils.getInt;
import static edu.csuci.appaca.utils.ResourceUtils.libGDXColor;

public class AlpacaRunResources {

    private enum AlpacaRunRes {
        INSTANCE;
        private int worldWidth;
        private int worldHeight;
        private Color background;
        private boolean loaded;

        AlpacaRunRes() {
            loaded = false;
        }
    }

    public static void load(Context context) {
        if (AlpacaRunRes.INSTANCE.loaded) return;

        AlpacaRunRes.INSTANCE.worldWidth = getInt(context, R.integer.clothing_preview_width);
        AlpacaRunRes.INSTANCE.worldHeight = getInt(context, R.integer.clothing_preview_height);
        AlpacaRunRes.INSTANCE.background = libGDXColor(context, R.color.bluePastel);

        AlpacaRunRes.INSTANCE.loaded = true;
    }

    private static void verify() {
        if(!AlpacaRunRes.INSTANCE.loaded) throw new GameResourcesNotLoadedException("Resources for Alpaca Run have not been loaded");
    }

    public static int worldWidth() {
        verify();
        return AlpacaRunRes.INSTANCE.worldWidth;
    }

    public static int worldHeight() {
        verify();
        return AlpacaRunRes.INSTANCE.worldHeight;
    }

    public static Color bgColor() {
        verify();
        return AlpacaRunRes.INSTANCE.background;
    }

}
