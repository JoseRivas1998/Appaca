package edu.csuci.appaca.data.statics;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.csuci.appaca.utils.AssetsUtils;

public class ShopData {

    private static final String SHOP_FILE = "data/shop.json";

    private enum ShopInstance {
        INSTANCE;
        private boolean loaded;
        Map<Integer, StaticFoodItem> food;
        Map<Integer, StaticClothesItem> clothes;
        Map<Integer, AlpacaShopItem> alpacas;

        ShopInstance() {
            this.food = new HashMap<>();
            this.clothes = new HashMap<>();
            this.alpacas = new HashMap<>();
            this.loaded = false;
        }
    }

    /**
     * Loads the data found in <code>/app/src/main/assets/data/shop.json</code>. If the data has
     * already been loaded, does not load again.
     *
     * @param context - The current activity
     * @return Whether the items are loaded
     */
    public static boolean load(Context context) {
        if (ShopInstance.INSTANCE.loaded) return true;

        String dataFile = AssetsUtils.assetFileString(context, SHOP_FILE);
        try {
            JSONObject dataJSON = new JSONObject(dataFile);

            JSONArray food = dataJSON.getJSONArray("food");
            for (int i = 0; i < food.length(); i++) {
                StaticFoodItem foodItem = StaticFoodItem.ofJSON(food.getJSONObject(i));
                ShopInstance.INSTANCE.food.put(foodItem.id, foodItem);
            }

            JSONArray clothes = dataJSON.getJSONArray("clothes");
            for (int i = 0; i < clothes.length(); i++) {
                StaticClothesItem clothesItem = StaticClothesItem.ofJSON(clothes.getJSONObject(i));
                ShopInstance.INSTANCE.clothes.put(clothesItem.id, clothesItem);
            }

            JSONArray alpacas = dataJSON.getJSONArray("alpacas");
            for (int i = 0; i < alpacas.length(); i++) {
                AlpacaShopItem alpaca = AlpacaShopItem.ofJSON(alpacas.getJSONObject(i));
                ShopInstance.INSTANCE.alpacas.put(alpaca.id, alpaca);
            }

            ShopInstance.INSTANCE.loaded = true;
        } catch (JSONException je) {
            Log.e(ShopData.class.getName(), je.getMessage(), je);
        }
        return ShopInstance.INSTANCE.loaded;
    }

    public static StaticFoodItem getFood(int id) {
        return ShopInstance.INSTANCE.food.get(id);
    }

    public static List<StaticFoodItem> getAllFood() {
        return new ArrayList<>(ShopInstance.INSTANCE.food.values());
    }

    public static StaticClothesItem getClothes(int id) {
        return ShopInstance.INSTANCE.clothes.get(id);
    }

    public static List<StaticClothesItem> getAllClothes() {
        return new ArrayList<>(ShopInstance.INSTANCE.clothes.values());
    }

    public static AlpacaShopItem getAlpaca(int id) {
        return ShopInstance.INSTANCE.alpacas.get(id);
    }

    public static List<AlpacaShopItem> getAllAlpacas() {
        return new ArrayList<>(ShopInstance.INSTANCE.alpacas.values());
    }

}
