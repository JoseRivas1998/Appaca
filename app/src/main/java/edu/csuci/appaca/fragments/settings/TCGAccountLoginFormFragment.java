package edu.csuci.appaca.fragments.settings;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.csuci.appaca.R;
import edu.csuci.appaca.activities.SettingsActivity;
import edu.csuci.appaca.data.SaveDataUtils;
import edu.csuci.appaca.data.TCGAccount;
import edu.csuci.appaca.data.TCGDeviceId;
import edu.csuci.appaca.net.DataUploadQueue;
import edu.csuci.appaca.net.HttpCallback;
import edu.csuci.appaca.net.HttpRequestBuilder;

public class TCGAccountLoginFormFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tcgaccount_login_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initMainFormControls(view);
        final Button signup = view.findViewById(R.id.tcg_login_form_signup_btn);
        final Button reset = view.findViewById(R.id.tcg_login_form_reset_pass_btn);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.tcg_account_signup_page)));
                startActivity(i);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.tcg_account_reset_page)));
                startActivity(i);
            }
        });
    }

    private void initMainFormControls(@NonNull View view) {
        final Button loginButton = view.findViewById(R.id.tcg_login_form_login_btn);
        final EditText username = view.findViewById(R.id.tcg_login_form_username);
        final EditText password = view.findViewById(R.id.tcg_login_form_password);
        final Activity activity = getActivity();
        final View loggingIn = view.findViewById(R.id.tcg_login_form_logging_in);
        final View errorView = view.findViewById(R.id.tcg_login_form_error_view);
        final TextView errorText = view.findViewById(R.id.tcg_login_form_error_text);
        final CheckBox uploadCheckbox = view.findViewById(R.id.tcg_login_form_upload_checkbox);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = username.getText().toString().trim().toLowerCase();
                if (user.isEmpty()) {
                    errorText.setText(getString(R.string.enter_a_username));
                    errorView.setVisibility(View.VISIBLE);
                    return;
                }
                final String pass = password.getText().toString();
                if (pass.isEmpty()) {
                    errorText.setText(getString(R.string.enter_a_password));
                    errorView.setVisibility(View.VISIBLE);
                    return;
                }
                final boolean shouldUpload = uploadCheckbox.isChecked();
                loginButton.setVisibility(View.GONE);
                errorView.setVisibility(View.GONE);
                loggingIn.setVisibility(View.VISIBLE);
                Map<String, String> loginParams = new HashMap<>();
                loginParams.put("username", user);
                loginParams.put("password", pass);
                HttpRequestBuilder.newPost(getString(R.string.webapi_base_url) + "/users/login", activity)
                        .setBody(loginParams)
                        .setOnSuccess(new HttpCallback() {
                            @Override
                            public void callback(int responseCode, String data) {
                                try {
                                    JSONObject response = new JSONObject(data);
                                    TCGDeviceId.setDeviceId(response.getString("deviceId"));
                                    SaveDataUtils.updateValuesAndSave(activity, false);
                                    HttpRequestBuilder.newPost(getString(R.string.webapi_base_url) + "/users/user", activity)
                                            .setBodyJSON(response)
                                            .setOnSuccess(new HttpCallback() {
                                                @Override
                                                public void callback(int responseCode, String data) {
                                                    try {
                                                        JSONObject userData = new JSONObject(data);
                                                        TCGAccount.setAccountData(userData);
                                                        if (shouldUpload) {
                                                            DataUploadQueue.addUpload(activity);
                                                        }
                                                        if (activity instanceof SettingsActivity) {
                                                            ((SettingsActivity) activity).replaceLoginFormWithAccountDetails();
                                                        }
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
                                errorText.setText(error);
                                loggingIn.setVisibility(View.GONE);
                                loginButton.setVisibility(View.VISIBLE);
                                errorView.setVisibility(View.VISIBLE);
                            }
                        })
                        .send();
            }
        });
    }
}
