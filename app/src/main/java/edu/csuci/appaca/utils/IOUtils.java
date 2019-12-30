package edu.csuci.appaca.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class IOUtils {

    private IOUtils() {
    }

    public static String streamToSTring(InputStream inputStream) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder total = new StringBuilder();
        for (String line; (line = r.readLine()) != null; ) {
            total.append(line).append('\n');
        }
        return total.toString();
    }

    public static String jsonToUrlEncodedString(JSONObject jsonObject) throws JSONException, UnsupportedEncodingException {
        JSONArray names = jsonObject.names();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < names.length(); i++) {
            String name = names.getString(i);
            Object value = jsonObject.get(name);
            if (value == null || value instanceof JSONObject || value instanceof JSONArray) {
                continue; // skip for now
            }
            if (sb.length() != 0) sb.append('&');
            sb.append(encodeNameValuePair(name, value));
        }
        return sb.toString();
    }

    public static String nameValuePairsToUrlEncodedSTring(Map<String, Object> params) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (String name : params.keySet()) {
            Object value = params.get(name);
            if (sb.length() != 0) sb.append('&');
            sb.append(encodeNameValuePair(name, value));
        }
        return sb.toString();
    }

    private static String encodeNameValuePair(String name, Object value) throws UnsupportedEncodingException {
        return URLEncoder.encode(name, "UTF-8") + "=" + URLEncoder.encode(String.valueOf(value), "UTF-8");
    }

}
