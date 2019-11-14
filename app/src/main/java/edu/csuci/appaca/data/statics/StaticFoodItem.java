package edu.csuci.appaca.data.statics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public final class StaticFoodItem {

    public final int id;
    public final String name;
    public final String path;
    public final int cost;
    public final double value;
    public final String anim;
    public final int frames;
    public final float frameDuration;

    public StaticFoodItem(int id, String name, String path, int cost, double value, String anim, int frames, float frameDuration) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.cost = cost;
        this.value = value;
        this.anim = anim;
        this.frames = frames;
        this.frameDuration = frameDuration;
    }

    public static StaticFoodItem ofJSON(JSONObject json) throws JSONException {
        if(!json.has("id")) throw new JSONException("int value id missing");
        if(!json.has("name")) throw new JSONException("string name id missing");
        if(!json.has("path")) throw new JSONException("int path id missing");
        if(!json.has("cost")) throw new JSONException("int cost id missing");
        if(!json.has("value")) throw new JSONException("double value id missing");
        if(!json.has("anim")) throw new JSONException("String value anim missing");
        if(!json.has("frames")) throw new JSONException("int value frames missing");
        if(!json.has("frame_dur")) throw new JSONException("double value frame_dur missing");
        int id = json.getInt("id");
        String name = json.getString("name");
        String path = json.getString("path");
        int cost = json.getInt("cost");
        double value = json.getDouble("value");
        String anim = json.getString("anim");
        int frames = json.getInt("frames");
        float frameDuration = (float) json.getDouble("frame_dur");
        return new StaticFoodItem(id, name, path, cost, value, anim, frames, frameDuration);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StaticFoodItem that = (StaticFoodItem) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
