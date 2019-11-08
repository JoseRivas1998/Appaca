package edu.csuci.appaca.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

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

    public static void sendNotification(Context context, String alpacaName)
    {
        final String CHANNEL_ID = "hygiene_id";
        final String GROUP_ID = "stat_group";
        final int NOTIFY_ID = 0;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = "hygiene";
            String description = "Hygiene channel";
            int importance= NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(channel);
        }

        builder.setContentTitle("Appaca");
        builder.setContentText(alpacaName + "is dirty!");
        builder.setGroup(GROUP_ID);

        notificationManager.notify(NOTIFY_ID, builder.build());
    }

}
