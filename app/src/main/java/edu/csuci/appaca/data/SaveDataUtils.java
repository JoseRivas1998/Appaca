package edu.csuci.appaca.data;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class SaveDataUtils {

    private static final String FILENAME = "appacaSaveData.json";

    public static void load(Context context) {
        try (FileInputStream fis = context.openFileInput(FILENAME);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(isr)) {
            StringBuilder sb = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
            JSONObject jsonObject = new JSONObject(sb.toString());
            AlpacaFarm.load(jsonObject);
        } catch (FileNotFoundException e) {
            // TODO
            Log.i(SaveDataUtils.class.getName(), "No save data yet!");
            Log.e(SaveDataUtils.class.getName(), e.getMessage(), e);
            initialize();
        } catch (IOException | JSONException e) {
            Log.e(SaveDataUtils.class.getName(), e.getMessage(), e);
        }
    }

    private static void initialize() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("alpacas", new JSONArray());
            AlpacaFarm.load(jsonObject);
        } catch (JSONException e) {
            Log.e(SaveDataUtils.class.getName(), e.getMessage());
        }
    }

    public static void save(Context context) {
        JSONObject saveData = new JSONObject();
        try {
            JSONArray alpacas = AlpacaFarm.toJSONArray();
            saveData.put("alpacas", alpacas);
        } catch (JSONException e) {
            Log.e(SaveDataUtils.class.getName(), e.getMessage(), e);
        }
        try (FileOutputStream fo = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
             OutputStreamWriter osw = new OutputStreamWriter(fo)) {
            osw.write(saveData.toString());
        } catch (IOException e) {
            Log.e(SaveDataUtils.class.getName(), e.getMessage(), e);
        }
    }

}
