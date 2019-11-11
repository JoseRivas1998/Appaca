package edu.csuci.appaca.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import edu.csuci.appaca.R;

import static androidx.core.content.ContextCompat.getSystemService;

public class HappinessNotification {

    private static final String CHANNEL_ID = "happines_notification_channel";
    public HappinessNotification(Context context)

    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);

    }

    private void createNotificationChannel(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }
}
