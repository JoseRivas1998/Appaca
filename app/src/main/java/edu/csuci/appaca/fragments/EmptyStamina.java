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
import edu.csuci.appaca.data.StaminaManager;
import edu.csuci.appaca.utils.TimeUtils;

public class EmptyStamina extends DialogFragment {

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
        final Button close = view.findViewById(R.id.close_button);

        timeLeft.setText(timeLeftMessage());

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    private String timeLeftMessage() {
        long currentTime = TimeUtils.getCurrentTime();
        double timeUntilRecovery = 30 - TimeUtils.secondsToMinutes(currentTime - StaminaManager.getFirstStaminaUsedTime());
        int minutes = (int)Math.floor(timeUntilRecovery);
        int seconds = (int)Math.floor((timeUntilRecovery - minutes) * 60);
        return "Time left: " + minutes + ":" + String.format("%02d", seconds);
    }

}
