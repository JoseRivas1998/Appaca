package edu.csuci.appaca.data;

import org.json.JSONException;
import org.json.JSONObject;

public interface JSONAble {

    JSONObject toJSON() throws JSONException;

}
