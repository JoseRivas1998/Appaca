package edu.csuci.appaca.data.gameres;

import android.content.Context;

import com.badlogic.gdx.graphics.Color;

import edu.csuci.appaca.R;
import edu.csuci.appaca.graphics.FruitCatch;

import static edu.csuci.appaca.utils.ResourceUtils.*;

public class FruitCatchResources {

    private enum FruitCatchRes {
        INSTANCE;
        private float gravity;
        private int worldWidth;
        private int worldHeight;
        private String scoreFormat;
        private String highScoreFormat;
        private Color background;
        private float minSpawnTime;
        private float maxSpawnTime;
        private float hudSpacing;
        private boolean loaded;

        FruitCatchRes() {
            loaded = false;
        }
    }

    public static void load(Context context) {
        if (FruitCatchRes.INSTANCE.loaded) return;

        FruitCatchRes.INSTANCE.worldWidth = getInt(context, R.integer.libgdx_fullscreen_width);
        FruitCatchRes.INSTANCE.worldHeight = getInt(context, R.integer.libgdx_fullscreen_height);
        FruitCatchRes.INSTANCE.scoreFormat = getString(context, R.string.fruit_catch_score_format);
        FruitCatchRes.INSTANCE.highScoreFormat = getString(context, R.string.fruit_catch_high_score_format);
        FruitCatchRes.INSTANCE.gravity = getFloat(context, R.dimen.fruit_catch_gravity);
        FruitCatchRes.INSTANCE.background = libGDXColor(context, R.color.bluePastel);
        FruitCatchRes.INSTANCE.minSpawnTime = getFloat(context, R.dimen.fruit_catch_min_spawn_time);
        FruitCatchRes.INSTANCE.maxSpawnTime = getFloat(context, R.dimen.fruit_catch_max_spawn_time);
        FruitCatchRes.INSTANCE.hudSpacing = getDimension(context, R.dimen.hud_padding);

        FruitCatchRes.INSTANCE.loaded = true;
    }

    private static void verify() {
        if(!FruitCatchRes.INSTANCE.loaded) throw new GameResourcesNotLoadedException("Resources for Fruit Catch have not been loaded");
    }

    public static int worldWidth() {
        verify();
        return FruitCatchRes.INSTANCE.worldWidth;
    }

    public static int worldHeight() {
        verify();
        return FruitCatchRes.INSTANCE.worldHeight;
    }

    public static Color bgColor() {
        verify();
        return FruitCatchRes.INSTANCE.background;
    }

    public static String scoreFormat() {
        verify();
        return FruitCatchRes.INSTANCE.scoreFormat;
    }

    public static String highScoreFormat() {
        verify();
        return FruitCatchRes.INSTANCE.highScoreFormat;
    }

    public static float gravity() {
        verify();
        return FruitCatchRes.INSTANCE.gravity;
    }

    public static float minSpawnTime() {
        verify();
        return FruitCatchRes.INSTANCE.minSpawnTime;
    }

    public static float maxSpawnTime() {
        verify();
        return FruitCatchRes.INSTANCE.maxSpawnTime;
    }

    public static float hudSpacing() {
        verify();
        return FruitCatchRes.INSTANCE.hudSpacing;
    }

}
