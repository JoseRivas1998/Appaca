package edu.csuci.appaca.data;

import org.json.JSONException;
import org.json.JSONObject;

public class CurrencyManager {

    private enum Currency {
        INSTANCE;
        int currencyAlpaca;
        int currencyOther;
        boolean loaded;
        Currency() {
            currencyAlpaca = 0;
            currencyOther = 0;
            loaded = false;
        }
    }

    public static void clear() {
        Currency.INSTANCE.currencyAlpaca = 0;
        Currency.INSTANCE.currencyOther = 0;
        Currency.INSTANCE.loaded = false;
    }

    public static void load(JSONObject jsonObject) {
        if(Currency.INSTANCE.loaded) return;
        try {
            if(!jsonObject.has("currencyAlpaca")) throw new JSONException("No Currency 1");
            if(!jsonObject.has("currencyOther")) throw new JSONException("No Currency 2");
            Currency.INSTANCE.currencyAlpaca = jsonObject.getInt("currencyAlpaca");
            Currency.INSTANCE.currencyOther = jsonObject.getInt("currencyOther");
        } catch (JSONException je) {
            CurrencyManager.init();
        }
        Currency.INSTANCE.loaded = true;
    }

    public static void init() {
        Currency.INSTANCE.currencyAlpaca = 0;
        Currency.INSTANCE.currencyOther = 0;
    }

    public static JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("currencyAlpaca", Currency.INSTANCE.currencyAlpaca);
        jsonObject.put("currencyOther", Currency.INSTANCE.currencyOther);
        return jsonObject;
    }

    public static int getCurrencyAlpaca() {
        return Currency.INSTANCE.currencyAlpaca;
    }

    public static int getCurrencyOther() {
        return Currency.INSTANCE.currencyOther;
    }

    public static void spendCurrencyAlpaca(int amount) throws CurrencyException {
        if(amount > Currency.INSTANCE.currencyAlpaca) {
            throw new CurrencyException("Not enough money!");
        }
        Currency.INSTANCE.currencyAlpaca -= amount;
    }

    public static void spendCurrencyOther(int amount) throws CurrencyException {
        if(amount > Currency.INSTANCE.currencyOther) {
            throw new CurrencyException("Not enough money!");
        }
        Currency.INSTANCE.currencyOther -= amount;
    }

    public static void gainCurrencyAlpaca(int amount) {
        Currency.INSTANCE.currencyAlpaca += amount;
    }

    public static void gainCurrencyOther(int amount) {
        Currency.INSTANCE.currencyOther += amount;
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
