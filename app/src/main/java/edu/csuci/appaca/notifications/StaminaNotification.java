package edu.csuci.appaca.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.util.Map;

import edu.csuci.appaca.R;
import edu.csuci.appaca.activities.MainActivity;
import edu.csuci.appaca.data.Alpaca;
import edu.csuci.appaca.data.StaminaManager;

public class StaminaNotification {
    private static boolean notificationSent = false;

    public static void checkIfStaminaIsFull(Context context) {
        int currentStamina = StaminaManager.getCurrentStamina();
        boolean isStaminaAtMax = currentStamina == StaminaManager.MAX_STAMINA;
        if (isStaminaAtMax) {
            if (!notificationSent) {
                sendNotification(context);
                notificationSent = true;
            }
        } else {
            notificationSent = false;
        }

    }

    private static void sendNotification(Context context) {
        final String CHANNEL_ID = "stamina_id";
        final String GROUP_ID = "stat_group";
        Intent toMainScreen = new Intent(context, MainActivity.class);
        PendingIntent notificationIntent = PendingIntent.getActivity(context, 0, toMainScreen, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "stamina";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(channel);
        }

        builder.setContentTitle("Appaca");
        builder.setContentText("Stamina is recharged!");
        builder.setGroup(GROUP_ID);
        builder.setSmallIcon(R.drawable.alpaca_icon); //placeholder
        builder.setOnlyAlertOnce(true);
        builder.setContentIntent(notificationIntent);

        notificationManager.notify(NotificationId.STAMINA, builder.build());
    }
}
