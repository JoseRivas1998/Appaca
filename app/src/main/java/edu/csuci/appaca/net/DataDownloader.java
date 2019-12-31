package edu.csuci.appaca.net;

import android.app.Activity;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.TCGDeviceId;

public class DataDownloader {

    private static JSONObject data = null;
    private static boolean downloading = false;

    private static synchronized void setData(JSONObject data) {
        DataDownloader.data = data;
    }

    private static synchronized JSONObject getData() {
        return DataDownloader.data;
    }

    private static synchronized void setDownloading(boolean downloading) {
        DataDownloader.downloading = downloading;
    }

    private static synchronized boolean isDownloading() {
        return DataDownloader.downloading;
    }

    public static JSONObject downloadDataBlocking(Activity parent) {
        // Wait until not busy
        while (DataDownloader.isDownloading());
        DataDownloader.setDownloading(true);
        final String deviceId = TCGDeviceId.getDeviceId();
        final Map<String, String> params = new HashMap<>();
        params.put("deviceId", deviceId);
        HttpRequestBuilder.newPost(parent.getString(R.string.webapi_base_url) + "/appaca/download/userdata", parent)
                .setBody(params)
                .setOnSuccess(new HttpCallback() {
                    @Override
                    public void callback(int responseCode, String data) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(data);
                        } catch (JSONException e) {
                            Log.e(getClass().getName(), e.getMessage(), e);
                        }
                        DataDownloader.setData(jsonObject);
                        DataDownloader.setDownloading(false);
                    }
                })
                .setOnError(new HttpCallback() {
                    @Override
                    public void callback(int responseCode, String data) {
                        setData(null);
                        DataDownloader.setDownloading(false);
                    }
                })
                .setOnException(new ExceptionHandler() {
                    @Override
                    public void catchException(Exception e) {
                        setData(null);
                        DataDownloader.setDownloading(false);
                    }
                })
                .send();
        // Wait until done
        while (DataDownloader.isDownloading());
        return DataDownloader.getData();
    }

}
