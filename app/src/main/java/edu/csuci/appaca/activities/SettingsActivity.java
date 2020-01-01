package edu.csuci.appaca.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.SaveDataUtils;
import edu.csuci.appaca.data.TCGAccount;
import edu.csuci.appaca.data.TCGDeviceId;
import edu.csuci.appaca.fragments.settings.DataManagementFragment;
import edu.csuci.appaca.fragments.settings.TCGAccountDetailsFragment;
import edu.csuci.appaca.fragments.settings.TCGAccountLoginFormFragment;
import edu.csuci.appaca.fragments.settings.TCGAccountLoginSignupFragment;
import edu.csuci.appaca.net.HttpCallback;
import edu.csuci.appaca.net.HttpRequestBuilder;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();
        setupTCGAccounts();

    }

    public void replaceAccountDetailsWithLogin() {
        Fragment fragment = new TCGAccountLoginSignupFragment();
        replaceTCGAccount(fragment);
        updateDataManagement();
    }

    public void replaceLoginButtonsWithForm() {
        Fragment fragment = new TCGAccountLoginFormFragment();
        replaceTCGAccount(fragment);
        updateDataManagement();
    }

    public void replaceLoginFormWithAccountDetails() {
        Fragment fragment = new TCGAccountDetailsFragment();
        replaceTCGAccount(fragment);
        updateDataManagement();
    }

    public void updateDataManagement() {
        final DataManagementFragment fragment = (DataManagementFragment) getSupportFragmentManager().findFragmentById(R.id.settings_data_management);
        fragment.update();
    }

    private void replaceTCGAccount(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.tcg_accounts_fragment_container, fragment)
                .commit();
    }

    private void setupTCGAccounts() {
        Fragment fragment;
        if (TCGAccount.isLoggedIn()) {
            fragment = new TCGAccountDetailsFragment();
        } else {
            fragment = new TCGAccountLoginSignupFragment();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.tcg_accounts_fragment_container, fragment)
                .commit();
    }
}
