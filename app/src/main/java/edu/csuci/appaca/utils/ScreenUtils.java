package edu.csuci.appaca.utils;

import android.content.Context;

public class ScreenUtils {

    public static float dpToPixels(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

}
