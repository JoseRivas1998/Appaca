package edu.csuci.appaca.notifications;

import android.content.Context;
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
        final String NOTIFICATION_CHANNEL_ID = "hygiene_id";
        final String GROUP_ID = "stat_group";
        final int NOTIFY_ID = 0;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        builder.setContentTitle("Appaca");
        builder.setContentText(alpacaName + "is dirty!");
        builder.setGroup(GROUP_ID);

        notificationManager.notify(NOTIFY_ID, builder.build());
    }

}
