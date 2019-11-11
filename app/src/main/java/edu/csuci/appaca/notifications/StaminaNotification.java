package edu.csuci.appaca.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import edu.csuci.appaca.data.StaminaManager;

public class StaminaNotification {

    public static void checkIfStaminaIsFull(Context context){
        int currentStamina = StaminaManager.getCurrentStamina();
        if (currentStamina == StaminaManager.MAX_STAMINA)
        {
            sendNotification(context);
        }
    }

    private static void sendNotification(Context context){
        final String CHANNEL_ID = "stamina_id";
        final String GROUP_ID = "stat_group";
        final int NOTIFY_ID = 0;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = "stamina";
            int importance= NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
