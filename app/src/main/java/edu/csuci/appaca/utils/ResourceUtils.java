package edu.csuci.appaca.utils;

import android.content.Context;
import android.util.TypedValue;

import com.badlogic.gdx.graphics.Color;

public class ResourceUtils {
    public static int getInt(Context context, int id) {
        return context.getResources().getInteger(id);
    }

    public static float getDimension(Context context, int id) {
        return context.getResources().getDimension(id);
    }

    public static String getString(Context context, int id) {
        return context.getResources().getString(id);
    }

    public static float getFloat(Context context, int id) {
        TypedValue outValue = new TypedValue();
        context.getResources().getValue(id, outValue, true);
        return outValue.getFloat();
    }

    public static Color libGDXColor(Context context, int id) {
        int color = context.getResources().getColor(id, context.getTheme());
        /*
         * Android colors are in AARRGGBBAA formats, so getting each value can be done with the
         * following bit operations
         * (AARRGGBB >> 24) &0xFF = (AAAAAAAA) & 0xFF = AA
         * (AARRGGBB >> 16) &0xFF = (AAAAAARR) & 0xFF = RR
         * (AARRGGBB >> 8)  &0xFF = (AAAARRGG) & 0xFF = GG
         * (AARRGGBB)       &0xFF = (AARRGGBB) & 0xFF = BB
         */
        int alpha = (color >> 24) & 0xFF;
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;
        return new Color((float) red / 0xFF, (float) green / 0xFF, (float) blue / 0xFF, (float) alpha / 0xFF);
    }

}
