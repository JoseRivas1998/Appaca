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
import edu.csuci.appaca.utils.ListUtils;

public class HygieneNotification{

    public static void checkIfAnyAlpacasLowHygiene(final Context context) {

        AlpacaFarm.forEach(new ListUtils.Consumer<Alpaca>() {
            @Override
            public void accept(Alpaca alpaca) {
                if (alpaca.getHygieneStat() == Alpaca.MIN_STAT)
                {
                    sendNotification(context, alpaca.getName());
                }
            }
        });
    }

    private static void sendNotification(Context context, String alpacaName)
    {
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
