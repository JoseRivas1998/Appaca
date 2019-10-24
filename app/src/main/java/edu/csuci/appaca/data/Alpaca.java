package edu.csuci.appaca.data;

import org.json.JSONException;
import org.json.JSONObject;

import edu.csuci.appaca.data.statics.ShopData;

public class Alpaca implements JSONAble {

    public static final double MAX_STAT = 1.0;
    public static final double MIN_STAT = 0.0;

    private int id;
    private String name;
    private String path;
    private double foodStat;
    private double hygieneStat;
    private double happinessStat;

    private Alpaca(int id, String name, String path, double foodStat, double hygieneStat, double happinessStat) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.foodStat = foodStat;
        this.hygieneStat = hygieneStat;
        this.happinessStat = happinessStat;
    }

    public static Alpaca newAlpaca(int shopItemID, String name) {
        int id = AlpacaFarm.getMaxID() + 1;
        String path = ShopData.getAlpaca(shopItemID).path;
        return new Alpaca(id, name, path, MAX_STAT, MAX_STAT, MAX_STAT);
    }

    public static Alpaca ofJSON(JSONObject json) throws JSONException {
        if(!json.has("id")) throw new JSONException("int value id missing");
        if(!json.has("name")) throw new JSONException("int string name missing");
        if(!json.has("path")) throw new JSONException("int string path missing");
        if(!json.has("foodStat")) throw new JSONException("double value foodStat missing");
        if(!json.has("hygieneStat")) throw new JSONException("double value hygieneStat missing");
        if(!json.has("happinessStat")) throw new JSONException("double value happinessStat missing");
        int id = json.getInt("id");
        String name = json.getString("name");
        String path = json.getString("path");
        double foodStat = json.getDouble("foodStat");
        double hygieneStat = json.getDouble("hygieneStat");
        double happinessStat = json.getDouble("happinessStat");
        return new Alpaca(id, name, path, foodStat, hygieneStat, happinessStat);
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getPath() {
        return path;
    }

    public double getFoodStat() {
        return foodStat;
    }

    public double getHygieneStat() {
        return hygieneStat;
    }

    public double getHappinessStat() {
        return happinessStat;
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", this.id);
        jsonObject.put("name", this.name);
        jsonObject.put("path", this.path);
        jsonObject.put("foodStat", this.foodStat);
        jsonObject.put("hygieneStat", this.hygieneStat);
        jsonObject.put("happinessStat", this.happinessStat);
        return jsonObject;
    }
}
