package edu.csuci.appaca.net;

import android.app.Activity;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import edu.csuci.appaca.utils.IOUtils;

public class HttpRequestBuilder {

    private enum RequestType {
        POST("POST"), GET("GET");
        final String requestTypeString;

        RequestType(String requestTypeString) {
            this.requestTypeString = requestTypeString;
        }
    }

    private enum ContentType {
        JSON, NAME_VALUE_MAP
    }

    private final String url;
    private final Activity parent;

    private boolean hasContentType;
    private ContentType contentType;

    private JSONObject jsonBody;
    private Map<String, ?> nameValueMap;

    private boolean sent;

    private final RequestType requestType;

    private boolean hasErrorCallback;
    private HttpCallback errorCallback;

    private boolean hasExceptionHandler;
    private ExceptionHandler exceptionHandler;

    private boolean hasSuccessCallback;
    private HttpCallback successCallBack;

    private HttpRequestBuilder(String url, Activity parent, RequestType requestType) {
        this.url = url;
        this.parent = parent;
        this.hasContentType = false;
        this.contentType = null;
        this.jsonBody = null;
        this.sent = false;
        this.requestType = requestType;
        this.hasErrorCallback = false;
        this.errorCallback = null;
        this.hasExceptionHandler = false;
        this.exceptionHandler = null;
        this.hasSuccessCallback = false;
        this.successCallBack = null;
    }

    public static HttpRequestBuilder newPost(String url, Activity parent) {
        return new HttpRequestBuilder(url, parent, RequestType.POST);
    }

    public static HttpRequestBuilder newGet(String url, Activity parent) {
        return new HttpRequestBuilder(url, parent, RequestType.GET);
    }

    public HttpRequestBuilder setBodyJSON(JSONObject json) {
        verifyState();
        this.setContentType(ContentType.JSON);
        this.jsonBody = json;
        return this;
    }

    public HttpRequestBuilder setBody(Map<String, ?> nameValueMap) {
        verifyState();
        this.setContentType(ContentType.NAME_VALUE_MAP);
        this.nameValueMap = nameValueMap;
        return this;
    }

    public HttpRequestBuilder setOnError(HttpCallback errorCallback) {
        verifyState();
        this.hasErrorCallback = true;
        this.errorCallback = errorCallback;
        return this;
    }

    public HttpRequestBuilder setOnException(ExceptionHandler exceptionHandler) {
        verifyState();
        this.hasExceptionHandler = true;
        this.exceptionHandler = exceptionHandler;
        return this;
    }

    public HttpRequestBuilder setOnSuccess(HttpCallback successCallBack) {
        verifyState();
        this.hasSuccessCallback = true;
        this.successCallBack = successCallBack;
        return this;
    }

    public void send() {
        verifyState();
        this.sent = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendSynchronous();
            }
        }).start();
    }

    private void setContentType(ContentType contentType) {
        this.contentType = contentType;
        this.hasContentType = true;
    }

    private void sendSynchronous() {
        try {
            URL url = new URL(this.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(this.requestType.requestTypeString);
            if (this.hasContentType) {
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                String urlEncodedData;
                switch (this.contentType) {
                    case JSON:
                        urlEncodedData = IOUtils.jsonToUrlEncodedString(this.jsonBody);
                        break;
                    case NAME_VALUE_MAP:
                        urlEncodedData = IOUtils.nameValuePairsToUrlEncodedSTring(this.nameValueMap);
                        break;
                    default:
                        urlEncodedData = "";
                        break;
                }
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                os.writeBytes(urlEncodedData);
                os.flush();
                os.close();
            }
            final int responseCode = conn.getResponseCode();
            if (responseCode < 400) {
                final String successData = IOUtils.streamToSTring(conn.getInputStream());
                if(this.hasSuccessCallback) {
                    this.parent.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            HttpRequestBuilder.this.successCallBack.callback(responseCode, successData);
                        }
                    });
                }
            } else {
                final String errorData = IOUtils.streamToSTring(conn.getErrorStream());
                if (hasErrorCallback) {
                    this.parent.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            HttpRequestBuilder.this.errorCallback.callback(responseCode, errorData);
                        }
                    });
                } else {
                    Log.e(getClass().getName(), errorData);
                }
            }
        } catch (final Exception e) {
            if (this.hasExceptionHandler) {
                this.parent.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        exceptionHandler.catchException(e);
                    }
                });
            } else {
                Log.e(getClass().getName(), e.getMessage(), e);
            }
        }
    }

    private void verifyState() {
        if (sent) throw new IllegalStateException("This post request wsa already sent.");
    }

}
