package edu.csuci.appaca.notifications;

import android.app.NotificationManager;
import android.content.Context;

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
    }
}
