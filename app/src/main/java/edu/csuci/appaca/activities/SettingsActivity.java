package edu.csuci.appaca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.SaveDataUtils;
import edu.csuci.appaca.data.TCGAccount;
import edu.csuci.appaca.data.TCGDeviceId;
import edu.csuci.appaca.net.HttpCallback;
import edu.csuci.appaca.net.HttpRequestBuilder;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final Button loginButton = findViewById(R.id.login_btn);
        final EditText username = findViewById(R.id.username_input);
        final EditText password = findViewById(R.id.password_input);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setEnabled(false);
                final String user = username.getText().toString();
                final String pass = password.getText().toString();
                Map<String, String> loginParams = new HashMap<>();
                loginParams.put("username", user);
                loginParams.put("password", pass);
                HttpRequestBuilder.newPost(getString(R.string.webapi_base_url) + "/users/login", SettingsActivity.this)
                        .setBody(loginParams)
                        .setOnSuccess(new HttpCallback() {
                            @Override
                            public void callback(int responseCode, String data) {
                                try {
                                    JSONObject response = new JSONObject(data);
                                    TCGDeviceId.setDeviceId(response.getString("deviceId"));
                                    SaveDataUtils.updateValuesAndSave(SettingsActivity.this);
                                    HttpRequestBuilder.newPost(getString(R.string.webapi_base_url) + "/users/user", SettingsActivity.this)
                                            .setBodyJSON(response)
                                            .setOnSuccess(new HttpCallback() {
                                                @Override
                                                public void callback(int responseCode, String data) {
                                                    try {
                                                        JSONObject userData = new JSONObject(data);
                                                        TCGAccount.setAccountData(userData);
                                                        SettingsActivity.this.finish();
                                                    } catch (JSONException e) {
                                                        Log.e(getClass().getName(), e.getMessage(), e);
                                                    }
                                                }
                                            })
                                            .send();
                                } catch (JSONException e) {
                                    Log.e(getClass().getName(), e.getMessage(), e);
                                }
                            }
                        })
                        .setOnError(new HttpCallback() {
                            @Override
                            public void callback(int responseCode, String data) {
                                String error;
                                try {
                                    JSONObject response = new JSONObject(data);
                                    error = response.getString("message");
                                } catch (JSONException e) {
                                    error = e.getMessage();
                                }
                                Toast.makeText(SettingsActivity.this, error, Toast.LENGTH_LONG).show();
                                loginButton.setEnabled(true);
                            }
                        })
                        .send();
            }
        });
    }
}
