package edu.csuci.appaca.data;

import org.json.JSONException;
import org.json.JSONObject;

public class CurrencyManager {

    private enum Currency {
        INSTANCE;
        int currency1;
        int currency2;
        boolean loaded;
        Currency() {
            currency1 = 0;
            currency2 = 0;
            loaded = false;
        }
    }

    public static void load(JSONObject jsonObject) {
        if(Currency.INSTANCE.loaded) return;
        try {
            if(!jsonObject.has("currency1")) throw new JSONException("No Currency 1");
            if(!jsonObject.has("currency2")) throw new JSONException("No Currency 2");
            Currency.INSTANCE.currency1 = jsonObject.getInt("currency1");
            Currency.INSTANCE.currency2 = jsonObject.getInt("currency2");
        } catch (JSONException je) {
            CurrencyManager.init();
        }
        Currency.INSTANCE.loaded = true;
    }

    public static void init() {
        Currency.INSTANCE.currency1 = 0;
        Currency.INSTANCE.currency2 = 0;
    }

    public static JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("currency1", Currency.INSTANCE.currency1);
        jsonObject.put("currency2", Currency.INSTANCE.currency2);
        return jsonObject;
    }

    public static int getCurrency1() {
        return Currency.INSTANCE.currency1;
    }

    public static int getCurrency2() {
        return Currency.INSTANCE.currency2;
    }

    public static void spendCurrency1(int amount) throws CurrencyException {
        if(amount > Currency.INSTANCE.currency1) {
            throw new CurrencyException("Not enough money!");
        }
        Currency.INSTANCE.currency1 -= amount;
    }

    public static void spendCurrency2(int amount) throws CurrencyException {
        if(amount > Currency.INSTANCE.currency2) {
            throw new CurrencyException("Not enough money!");
        }
        Currency.INSTANCE.currency2 -= amount;
    }

    public static void gainCurrency1(int amount) {
        Currency.INSTANCE.currency1 += amount;
    }

    public static void gainCurrency2(int amount) {
        Currency.INSTANCE.currency2 += amount;
    }

    public static class CurrencyException extends Exception {
        public CurrencyException() {
            super();
        }

        public CurrencyException(String message) {
            super(message);
        }
    }

}
