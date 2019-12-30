package edu.csuci.appaca.fragments.settings;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import edu.csuci.appaca.R;
import edu.csuci.appaca.activities.SettingsActivity;
import edu.csuci.appaca.data.SaveDataUtils;
import edu.csuci.appaca.data.TCGAccount;
import edu.csuci.appaca.data.TCGDeviceId;
import edu.csuci.appaca.utils.AssetsUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class TCGAccountDetailsFragment extends Fragment {


    public TCGAccountDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tcgaccount_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        populateUserData(view);
        initButtons(view);
    }

    private void initButtons(@NonNull View view) {
        final Button manageAccount = view.findViewById(R.id.tcg_details_manage_account_btn);
        final Button logoutButton = view.findViewById(R.id.tcg_details_log_out_btn);
        manageAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.tcg_account_manage_page)));
                startActivity(i);
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TCGDeviceId.setDeviceId("");
                TCGAccount.clear();
                SaveDataUtils.updateValuesAndSave(getContext());
                Activity activity = getActivity();
                if(activity instanceof SettingsActivity) {
                    SettingsActivity settingsActivity = (SettingsActivity) activity;
                    settingsActivity.replaceAccountDetailsWithLogin();
                }
            }
        });
    }

    private void populateUserData(@NonNull View view) {
        final View detailsView = view.findViewById(R.id.tcg_details_account);
        final View loadingView = view.findViewById(R.id.tcg_details_loading);
        detailsView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);

        final String tcgUsername = TCGAccount.getUsername();
        final String tcgProfileImage = TCGAccount.getProfileImage();
        final TextView usernameView = view.findViewById(R.id.tcg_details_username);
        final ImageView profileImageView = view.findViewById(R.id.tcg_details_profile_img);
        usernameView.setText(tcgUsername);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(tcgProfileImage);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    final InputStream response = connection.getInputStream();
                    final Bitmap profileImage = BitmapFactory.decodeStream(response);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            profileImageView.setImageBitmap(profileImage);
                            loadingView.setVisibility(View.GONE);
                            detailsView.setVisibility(View.VISIBLE);
                        }
                    });
                } catch (Exception e) {
                    Log.e(getClass().getName(), e.getMessage(), e);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "Unable to download profile image!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();
    }
}
