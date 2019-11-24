package edu.csuci.appaca.data.gameres;

import android.content.Context;

import com.badlogic.gdx.graphics.Color;

import edu.csuci.appaca.R;
import edu.csuci.appaca.graphics.AlpacaRun;

import static edu.csuci.appaca.utils.ResourceUtils.getFloat;
import static edu.csuci.appaca.utils.ResourceUtils.getInt;
import static edu.csuci.appaca.utils.ResourceUtils.libGDXColor;

public class AlpacaRunResources {

    private enum AlpacaRunRes {
        INSTANCE;
        private int worldWidth;
        private int worldHeight;
        private Color background;

        private float speed;
        private float minSpawnTime;
        private float maxSpawnTime;

        private boolean loaded;

        AlpacaRunRes() {
            loaded = false;
        }
    }

    public static void load(Context context) {
        if (AlpacaRunRes.INSTANCE.loaded) return;

        AlpacaRunRes.INSTANCE.worldWidth = getInt(context, R.integer.libgdx_fullscreen_width);
        AlpacaRunRes.INSTANCE.worldHeight = getInt(context, R.integer.libgdx_fullscreen_height);
        AlpacaRunRes.INSTANCE.background = libGDXColor(context, R.color.bluePastel);

        AlpacaRunRes.INSTANCE.speed = getFloat(context, R.dimen.alpaca_run_speed);
        AlpacaRunRes.INSTANCE.minSpawnTime = getFloat(context, R.dimen.alpaca_run_min_spawn_time);
        AlpacaRunRes.INSTANCE.maxSpawnTime = getFloat(context, R.dimen.alpaca_run_max_spawn_time);

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

    public static float speed() {
        verify();
        return AlpacaRunRes.INSTANCE.speed;
    }

    public static float minSpawnTime() {
        verify();
        return AlpacaRunRes.INSTANCE.minSpawnTime;
    }

    public static float maxSpawnTime() {
        verify();
        return AlpacaRunRes.INSTANCE.maxSpawnTime;
    }

}
