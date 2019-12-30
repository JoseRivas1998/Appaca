package edu.csuci.appaca.net;

import org.json.JSONObject;

public interface HttpCallback {

    void callback(final int responseCode, final String data);

}
