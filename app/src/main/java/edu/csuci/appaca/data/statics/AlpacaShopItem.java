package edu.csuci.appaca.data.statics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public final class AlpacaShopItem {

    public final int id;
    public final String name;
    public final String path;
    public final String spritesheet;
    public final int cost;

    private AlpacaShopItem(int id, String name, String path, String spritesheet, int cost) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.spritesheet = spritesheet;
        this.cost = cost;
    }

    public static AlpacaShopItem ofJSON(JSONObject json) throws JSONException {
        if(!json.has("id")) throw new JSONException("int value id missing");
        if(!json.has("name")) throw new JSONException("string name id missing");
        if(!json.has("path")) throw new JSONException("int path id missing");
        if(!json.has("spritesheet")) throw new JSONException("string spritesheet is missing");
        if(!json.has("cost")) throw new JSONException("int cost id missing");
        int id = json.getInt("id");
        String name =  json.getString("name");
        String path = json.getString("path");
        String spritesheet = json.getString("spritesheet");
        int cost = json.getInt("cost");
        return new AlpacaShopItem(id, name, path, spritesheet, cost);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlpacaShopItem that = (AlpacaShopItem) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
