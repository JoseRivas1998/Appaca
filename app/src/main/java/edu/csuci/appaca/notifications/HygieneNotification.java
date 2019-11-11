package edu.csuci.appaca.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import edu.csuci.appaca.R;
import edu.csuci.appaca.activities.MainActivity;
import edu.csuci.appaca.data.Alpaca;
import edu.csuci.appaca.data.AlpacaFarm;
import edu.csuci.appaca.data.HygieneDepletion;
import edu.csuci.appaca.data.SavedTime;
import edu.csuci.appaca.utils.ListUtils;

public class HygieneNotification{
    public static void checkIfAnyAlpacasLowHygiene(final Context context) {
        //use foreach to check hygiene for each alpaca, send notification if it is fully depleted

        AlpacaFarm.forEach(new ListUtils.Consumer<Alpaca>() {
            @Override
            public void accept(Alpaca alpaca) {
                long lastTime = SavedTime.lastSavedTime();
                double predictedHygieneLoss = HygieneDepletion.hygieneDepletion(alpaca, lastTime);
                final double DELTA = 0.001;
                if (predictedHygieneLoss - Alpaca.MIN_STAT < DELTA)
                {
                    sendNotification(context, alpaca.getName());
                }
            }
        });
    }

    private static void sendNotification(Context context, String alpacaName)
    {
        //send notification saying that the alpaca is dirty
        final String CHANNEL_ID = "hygiene_id";
        final String GROUP_ID = "stat_group";
        final int NOTIFY_ID = 0;
        Intent toMainScreen = new Intent(context, MainActivity.class);
        PendingIntent notificationIntent = PendingIntent.getActivity(context, 0, toMainScreen, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = "hygiene";
            int importance= NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(channel);
        }

        builder.setContentTitle("Appaca");
        builder.setContentText(alpacaName + " is dirty!");
        builder.setGroup(GROUP_ID);
        builder.setSmallIcon(R.drawable.alpaca_icon); //placeholder
        builder.setOnlyAlertOnce(true);
        builder.setContentIntent(notificationIntent);

        notificationManager.notify(NOTIFY_ID, builder.build());
    }

}
