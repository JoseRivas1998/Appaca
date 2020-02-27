package edu.csuci.appaca.fragments.settings;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONException;

import java.util.Objects;

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.SaveDataUtils;
import edu.csuci.appaca.data.SavedTime;
import edu.csuci.appaca.data.TCGAccount;
import edu.csuci.appaca.data.TCGDeviceId;
import edu.csuci.appaca.net.DataSync;
import edu.csuci.appaca.net.DataUploadQueue;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataManagementFragment extends Fragment {


    public DataManagementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_management, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        update(view);
        final Activity parent = getActivity();
        final Button upload = view.findViewById(R.id.data_management_upload);
        final Button download = view.findViewById(R.id.data_management_download);
        final Button keep = view.findViewById(R.id.data_management_keep);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveDataUtils.updateValuesAndSave(parent, true);
            }
        });
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSync.forceDownload(SavedTime.lastSavedTime(), parent);
            }
        });
        keep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSync.syncData(SavedTime.lastSavedTime(), parent);
            }
        });
    }

    public void update() {
        final View view = getView();
        if (view != null) {
            update(view);
        }
    }

    public void update(View view) {
        final View please = view.findViewById(R.id.data_management_please);
        final View buttons = view.findViewById(R.id.data_management_buttons);
        if (TCGDeviceId.getDeviceId().length() == 0 || !TCGAccount.isLoggedIn()) {
            please.setVisibility(View.VISIBLE);
            buttons.setVisibility(View.GONE);
        } else {
            please.setVisibility(View.GONE);
            buttons.setVisibility(View.VISIBLE);
        }
    }

}
