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
import edu.csuci.appaca.data.SavedTime;
import edu.csuci.appaca.utils.ListUtils;

import static androidx.core.content.ContextCompat.getSystemService;

public class HappinessNotification {

    public static void checkForLowHappiness(final Context context) {
        AlpacaFarm.forEach(new ListUtils.Consumer<Alpaca>() {
            @Override
            public void accept(Alpaca alpaca) {
                double happinessStat = alpaca.getHappinessStat();
                final double DELTA = 0.001;
                if (happinessStat - Alpaca.MIN_STAT < DELTA) {
                    sendNotification(context, alpaca.getName());
                }
            }
        });
    }

    private static void sendNotification(Context context, String alpacaName) {
        //send notification saying that the alpaca is dirty
        final String CHANNEL_ID = "happiness_id";
        final String GROUP_ID = "stat_group";
        final int NOTIFY_ID = 0;
        Intent toMainScreen = new Intent(context, MainActivity.class);
        PendingIntent notificationIntent = PendingIntent.getActivity(context, 0, toMainScreen, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "happiness";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(channel);
        }

        builder.setContentTitle("Appaca");
        builder.setContentText(alpacaName + " is no longer happy!");

        builder.setGroup(GROUP_ID);
        builder.setSmallIcon(R.drawable.alpaca_icon); //placeholder
        builder.setOnlyAlertOnce(true);
        builder.setContentIntent(notificationIntent);

        notificationManager.notify(NOTIFY_ID, builder.build());
    }
}