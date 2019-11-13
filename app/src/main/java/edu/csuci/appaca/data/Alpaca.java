package edu.csuci.appaca.data;

import org.json.JSONException;
import org.json.JSONObject;

import edu.csuci.appaca.data.statics.ShopData;
import edu.csuci.appaca.utils.MathFunctions;
import edu.csuci.appaca.utils.TimeUtils;

public class Alpaca implements JSONAble {

    public static final double MAX_STAT = 1.0;
    public static final double MIN_STAT = 0.0;

    private static final long INITIAL_SECONDS_SINCE_SHEAR = 12 * 60 * 60;

    public static final int NO_CLOTHING = -1;

    private int id;
    private String name;
    private String path;
    private double foodStat;
    private double hygieneStat;
    private double happinessStat;
    private long lastShearTime;
    private int clothing;

    private Alpaca(int id, String name, String path, double foodStat, double hygieneStat, double happinessStat, long lastShearTime, int clothing) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.foodStat = foodStat;
        this.hygieneStat = hygieneStat;
        this.happinessStat = happinessStat;
        this.lastShearTime = lastShearTime;
        this.clothing = clothing;
    }

    public static Alpaca newAlpaca(int shopItemID, String name) {
        int id = AlpacaFarm.getMaxID() + 1;
        String path;
        if(ShopData.getAlpaca(shopItemID) == null) {
            path = "";
        } else {
            path = ShopData.getAlpaca(shopItemID).path;
        }
        long lastShearTime = TimeUtils.getCurrentTime() - INITIAL_SECONDS_SINCE_SHEAR;
        return new Alpaca(id, name, path, MAX_STAT, MAX_STAT, MAX_STAT, lastShearTime, NO_CLOTHING);
    }

    public static Alpaca ofJSON(JSONObject json) throws JSONException {
        if(!json.has("id")) throw new JSONException("int value id missing");
        if(!json.has("name")) throw new JSONException("int string name missing");
        if(!json.has("path")) throw new JSONException("int string path missing");
        if(!json.has("foodStat")) throw new JSONException("double value foodStat missing");
        if(!json.has("hygieneStat")) throw new JSONException("double value hygieneStat missing");
        if(!json.has("happinessStat")) throw new JSONException("double value happinessStat missing");
        if(!json.has("clothing")) throw new JSONException("int value clothing missing");
        int id = json.getInt("id");
        String name = json.getString("name");
        String path = json.getString("path");
        double foodStat = json.getDouble("foodStat");
        double hygieneStat = json.getDouble("hygieneStat");
        double happinessStat = json.getDouble("happinessStat");
        long lastShearTime;
        if(json.has("lastShearTime")) {
            lastShearTime = json.getLong("lastShearTime");
        } else {
            lastShearTime = TimeUtils.getCurrentTime() - INITIAL_SECONDS_SINCE_SHEAR;
        }
        int clothing = json.getInt("clothing");

        return new Alpaca(id, name, path, foodStat, hygieneStat, happinessStat, lastShearTime, clothing);
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

    public long getLastShearTime() {
        return lastShearTime;
    }

    public int getClothing() {
        return clothing;
    }

    public void setLastShearTimeToNow() {
        this.lastShearTime = TimeUtils.getCurrentTime();
    }

    public void setClothing(int clothingID) {
        if (this.clothing != -1) {
            Inventory.addClothes(this.clothing, 1);
        }
        this.clothing = clothingID;
    }

    public void updateValuesBasedOnTime() {
        long previousTime = SavedTime.lastSavedTime();
        double currentFood = FoodDepletion.foodDepletion(this, previousTime);
        double currentHappiness = HappinessCalc.calcHappiness(this, previousTime);
        double currentHygiene = HygieneDepletion.hygieneDepletion(this, previousTime);
        this.foodStat = MathFunctions.clamp(currentFood, Alpaca.MIN_STAT, Alpaca.MAX_STAT);
        this.happinessStat = MathFunctions.clamp(currentHappiness, Alpaca.MIN_STAT, Alpaca.MAX_STAT);
        this.hygieneStat = MathFunctions.clamp(currentHygiene, Alpaca.MIN_STAT, Alpaca.MAX_STAT);
    }

    public void incrementHappinessStat(double increment) {
        this.happinessStat = MathFunctions.clamp(this.happinessStat + increment, Alpaca.MIN_STAT, Alpaca.MAX_STAT);
    }

    public void incrementHungerStat(double increment) {
        this.foodStat = MathFunctions.clamp(this.foodStat + increment, Alpaca.MIN_STAT, Alpaca.MAX_STAT);
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
        jsonObject.put("lastShearTime", this.lastShearTime);
        jsonObject.put("clothing", this.clothing);
        return jsonObject;
    }

    @Override
    public boolean equals(Object object) {
        boolean result;
        if(object == null || object.getClass() != this.getClass()) {
            result = false;
        } else if(object == this) {
            result = true;
        } else {
            int id = ((Alpaca) object).id;
            result = this.id == id;
        }
        return result;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

}
