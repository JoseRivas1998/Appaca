package edu.csuci.appaca.data.gameres;

import android.content.Context;

import com.badlogic.gdx.graphics.Color;

import edu.csuci.appaca.R;

import static edu.csuci.appaca.utils.ResourceUtils.*;

public class ClothingPreviewResources {

    private enum ClothingPreviewRes {
        INSTANCE;
        private int worldWidth;
        private int worldHeight;
        private Color background;
        private boolean loaded;

        ClothingPreviewRes() {
            loaded = false;
        }
    }

    public static void load(Context context) {
        if (ClothingPreviewRes.INSTANCE.loaded) return;

        ClothingPreviewRes.INSTANCE.worldWidth = getInt(context, R.integer.clothing_preview_width);
        ClothingPreviewRes.INSTANCE.worldHeight = getInt(context, R.integer.clothing_preview_height);
        ClothingPreviewRes.INSTANCE.background = libGDXColor(context, R.color.bluePastel);

        ClothingPreviewRes.INSTANCE.loaded = true;
    }

    private static void verify() {
        if(!ClothingPreviewRes.INSTANCE.loaded) throw new GameResourcesNotLoadedException("Resources for Fruit Catch have not been loaded");
    }

    public static int worldWidth() {
        verify();
        return ClothingPreviewRes.INSTANCE.worldWidth;
    }

    public static int worldHeight() {
        verify();
        return ClothingPreviewRes.INSTANCE.worldHeight;
    }

    public static Color bgColor() {
        verify();
        return ClothingPreviewRes.INSTANCE.background;
    }

}
