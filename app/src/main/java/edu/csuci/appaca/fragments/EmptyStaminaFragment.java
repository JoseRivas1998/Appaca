package edu.csuci.appaca.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import edu.csuci.appaca.R;
import edu.csuci.appaca.concurrency.NoStaminaBackground;

public class EmptyStaminaFragment extends DialogFragment {
    private TextView timeLeftRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_empty_stamina, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ImageView icon = view.findViewById(R.id.paca_image);
        final TextView label = view.findViewById(R.id.no_stamina_message);
        final TextView timeLeft = view.findViewById(R.id.time_until_refill);

        timeLeftRef = timeLeft;

        NoStaminaBackground.start(this);


    }

    public void updateTimeLeft(String message) {
        timeLeftRef.setText(message);
    }

    @Override
    public void onPause() {
        super.onPause();
        NoStaminaBackground.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        NoStaminaBackground.start(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NoStaminaBackground.stop();
    }
}
