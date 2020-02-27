package edu.csuci.appaca.data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import edu.csuci.appaca.utils.ListUtils;

public class AlpacaFarm {

    private enum FarmInstance {
        INSTANCE;
        int currentAlpaca;
        Semaphore semaphore;
        boolean loaded;
        List<Alpaca> alpacas;

        FarmInstance() {
            semaphore = new Semaphore(1, true);
            this.currentAlpaca = 0;
            this.loaded = false;
            this.alpacas = new ArrayList<>();
        }

    }

    public static boolean load(JSONObject jsonObject) throws JSONException {
        if (FarmInstance.INSTANCE.loaded) return true;
        if (!jsonObject.has("alpacas")) throw new JSONException("JSONArray alpacas missing,");

        JSONArray alpacas = jsonObject.getJSONArray("alpacas");
        for (int i = 0; i < alpacas.length(); i++) {
            FarmInstance.INSTANCE.alpacas.add(Alpaca.ofJSON(alpacas.getJSONObject(i)));
        }

        FarmInstance.INSTANCE.loaded = true;
        return FarmInstance.INSTANCE.loaded;
    }

    public static void clear() {
        FarmInstance.INSTANCE.currentAlpaca = 0;
        FarmInstance.INSTANCE.loaded = false;
        FarmInstance.INSTANCE.alpacas = new ArrayList<>();
    }

    public static void addAlpaca(int shopItemId, String name) {
        FarmInstance.INSTANCE.alpacas.add(Alpaca.newAlpaca(shopItemId, name));
    }

    public static JSONArray toJSONArray() throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (Alpaca alpaca : FarmInstance.INSTANCE.alpacas) {
            jsonArray.put(alpaca.toJSON());
        }
        return jsonArray;
    }

    public static int getMaxID() {
        if (FarmInstance.INSTANCE.alpacas.size() == 0) return 0;
        Alpaca maxAlpaca = ListUtils.getMax(FarmInstance.INSTANCE.alpacas, new ListUtils.MapToInt<Alpaca>() {
            @Override
            public int toInt(Alpaca value) {
                return value.getId();
            }
        });
        return maxAlpaca.getId();
    }

    public static synchronized void openFarm() {
        try {
            FarmInstance.INSTANCE.semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void closeFarm() {
        FarmInstance.INSTANCE.semaphore.release();
    }

    public static Alpaca getCurrentAlpaca() {
        openFarm();
        Alpaca alpaca = FarmInstance.INSTANCE.alpacas.get(FarmInstance.INSTANCE.currentAlpaca);
        closeFarm();
        return alpaca;
    }

    public static int numberOfAlpacas() {
        return FarmInstance.INSTANCE.alpacas.size();
    }

    public static void next() {
        FarmInstance.INSTANCE.currentAlpaca = (FarmInstance.INSTANCE.currentAlpaca + 1) % numberOfAlpacas();
    }

    public static void prev() {
        FarmInstance.INSTANCE.currentAlpaca = (FarmInstance.INSTANCE.currentAlpaca + (numberOfAlpacas() - 1)) % numberOfAlpacas();
    }

    public static void forEach(ListUtils.Consumer<Alpaca> alpacaConsumer) {
        for (Alpaca alpaca : FarmInstance.INSTANCE.alpacas) {
            alpacaConsumer.accept(alpaca);
        }
    }

}
