package edu.csuci.appaca.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.csuci.appaca.data.statics.ShopData;
import edu.csuci.appaca.data.statics.StaticClothesItem;
import edu.csuci.appaca.data.statics.StaticFoodItem;
import edu.csuci.appaca.utils.ListUtils;

public class Inventory {

    private enum InventoryInstance {
        INSTANCE;
        private Map<StaticFoodItem, Integer> food;
        private Map<StaticClothesItem, Integer> clothes;
        private boolean loaded;

        InventoryInstance() {
            this.food = new HashMap<>();
            this.clothes = new HashMap<>();
            this.loaded = false;
        }
    }

    public static void init() {
        for (StaticFoodItem foodItem : ShopData.getAllFood()) {
            InventoryInstance.INSTANCE.food.put(foodItem, 0);
        }
        for (StaticClothesItem clothesItem : ShopData.getAllClothes()) {
            InventoryInstance.INSTANCE.clothes.put(clothesItem, 0);
        }
    }

    public static JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray food = new JSONArray();
        for (StaticFoodItem foodItem : ShopData.getAllFood()) {
            int amount = getFoodAmount(foodItem);
            JSONObject foodObject = new JSONObject();
            foodObject.put("id", foodItem.id);
            foodObject.put("amount", amount);
            food.put(foodObject);
        }
        JSONArray clothes = new JSONArray();
        for (StaticClothesItem clothesItem : ShopData.getAllClothes()) {
            int amount = getClothesAmount(clothesItem);
            JSONObject clothesObject = new JSONObject();
            clothesObject.put("id", clothesItem.id);
            clothesObject.put("amount", amount);
            clothes.put(clothesObject);
        }
        jsonObject.put("food", food);
        jsonObject.put("clothes", clothes);
        return jsonObject;
    }

    public static void load(JSONObject jsonObject) {
        if (InventoryInstance.INSTANCE.loaded) return;
        try {
            if (!jsonObject.has("food")) throw new JSONException("Food array missing");
            if (!jsonObject.has("clothes")) throw new JSONException("Clothes array missing");

            JSONArray food = jsonObject.getJSONArray("food");
            for (int i = 0; i < food.length(); i++) {
                JSONObject foodObject = food.getJSONObject(i);
                StaticFoodItem foodItem = ShopData.getFood(foodObject.getInt("id"));
                InventoryInstance.INSTANCE.food.put(foodItem, foodObject.getInt("amount"));
            }

            JSONArray clothes = jsonObject.getJSONArray("clothes");
            for (int i = 0; i < clothes.length(); i++) {
                JSONObject clothesObject = clothes.getJSONObject(i);
                StaticClothesItem clothesItem = ShopData.getClothes(clothesObject.getInt("id"));
                InventoryInstance.INSTANCE.clothes.put(clothesItem, clothesObject.getInt("amount"));
            }

        } catch (JSONException e) {
            init();
        }
        InventoryInstance.INSTANCE.loaded = true;
    }

    public static int getFoodAmount(int foodId) {
        StaticFoodItem foodItem = ShopData.getFood(foodId);
        return getFoodAmount(foodItem);
    }

    private static int getFoodAmount(StaticFoodItem foodItem) {
        return ListUtils.getOrDefault(InventoryInstance.INSTANCE.food, foodItem, 0);
    }

    public static void addFood(int foodId, int amount) {
        StaticFoodItem foodItem = ShopData.getFood(foodId);
        int newAmount = getFoodAmount(foodItem) + amount;
        InventoryInstance.INSTANCE.food.put(foodItem, newAmount);
    }

    public static void useFood(int foodId) {
        StaticFoodItem foodItem = ShopData.getFood(foodId);
        int newAmount = getFoodAmount(foodItem) - 1;
        InventoryInstance.INSTANCE.food.put(foodItem, Math.max(newAmount, 0));
    }

    public static int getClothesAmount(int clothesId) {
        StaticClothesItem clothesItem = ShopData.getClothes(clothesId);
        return getClothesAmount(clothesItem);
    }

    private static int getClothesAmount(StaticClothesItem clothesItem) {
        return ListUtils.getOrDefault(InventoryInstance.INSTANCE.clothes, clothesItem, 0);
    }

    public static void addClothes(int clothesId, int amount) {
        StaticClothesItem clothesItem = ShopData.getClothes(clothesId);
        int newAmount = getClothesAmount(clothesItem) + amount;
        InventoryInstance.INSTANCE.clothes.put(clothesItem, newAmount);
    }

    public static void useClothes(int clothesId) {
        StaticClothesItem clothesItem = ShopData.getClothes(clothesId);
        int newAmount = getClothesAmount(clothesItem) - 1;
        InventoryInstance.INSTANCE.clothes.put(clothesItem, Math.max(newAmount, 0));
    }

}
