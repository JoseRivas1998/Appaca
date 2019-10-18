package edu.csuci.appaca.data.statics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public final class StaticClothesItem {

    public final int id;
    public final String name;
    public final String path;
    public final int cost;
    public final double value;

    public StaticClothesItem(int id, String name, String path, int cost, double value) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.cost = cost;
        this.value = value;
    }

    public static StaticClothesItem ofJSON(JSONObject json) throws JSONException {
        if(!json.has("id")) throw new JSONException("int value id missing");
        if(!json.has("name")) throw new JSONException("string name id missing");
        if(!json.has("path")) throw new JSONException("int path id missing");
        if(!json.has("cost")) throw new JSONException("int cost id missing");
        if(!json.has("value")) throw new JSONException("double value id missing");
        int id = json.getInt("id");
        String name = json.getString("name");
        String path = json.getString("path");
        int cost = json.getInt("cost");
        double value = json.getDouble("value");
        return new StaticClothesItem(id, name, path, cost, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StaticClothesItem that = (StaticClothesItem) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
