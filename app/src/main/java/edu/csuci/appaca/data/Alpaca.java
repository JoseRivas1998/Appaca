package edu.csuci.appaca.data;

import org.json.JSONObject;

public class Alpaca implements JSONAble {

    private int id;

    public int id() {
        return id;
    }

    @Override
    public JSONObject toJSON() {
        return null;
    }
}
