package edu.csuci.appaca.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import edu.csuci.appaca.R;

public class AssetsUtils {

    public static String assetFileString(Context context, String path) {
        StringBuilder sb = new StringBuilder();
        try (InputStream is = context.getAssets().open(path);
             InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static Drawable drawableFromAsset(Context context, String path) {
        try {
            return Drawable.createFromStream(context.getAssets().open(path), null);
        } catch (IOException e) {
            Log.e(AssetsUtils.class.getName(), e.getMessage(), e);
            return context.getDrawable(R.drawable.sparkle_poo);
        }
    }

}
