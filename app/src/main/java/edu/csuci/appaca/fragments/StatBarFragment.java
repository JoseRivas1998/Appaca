package edu.csuci.appaca.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.Stat;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatBarFragment extends Fragment {

    private Stat stat;

    public StatBarFragment() {
        // Required empty public constructor
    }

    public static StatBarFragment buildStatBar(Stat stat) {
        StatBarFragment fragment = new StatBarFragment();
        fragment.stat = stat;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stat_bar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ImageView icon = view.findViewById(R.id.stat_bar_icon);
        final TextView label = view.findViewById(R.id.stat_bar_label);

        icon.setImageResource(stat.iconId);
        label.setText(stat.labelId);

    }
}
