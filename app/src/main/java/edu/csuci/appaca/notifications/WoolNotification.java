package edu.csuci.appaca.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.util.HashMap;

import edu.csuci.appaca.R;
import edu.csuci.appaca.activities.MainActivity;
import edu.csuci.appaca.data.Alpaca;
import edu.csuci.appaca.data.AlpacaFarm;
import edu.csuci.appaca.utils.ListUtils;
import edu.csuci.appaca.utils.ShearUtils;

public class WoolNotification {

    private static HashMap<Alpaca, Boolean> notificationSent;

    public static void checkIfAnyAlpacasMaxWool(final Context context) {
        initMap();
        AlpacaFarm.forEach(new ListUtils.Consumer<Alpaca>() {
            @Override
            public void accept(Alpaca alpaca) {
                final int MAX_MONEY = context.getResources().getInteger(R.integer.money_for_full_shear);
                int money = ShearUtils.getShearValue(alpaca, context);
                boolean wasNotificationSent = ListUtils.getOrDefault(notificationSent, alpaca, false);
                boolean maxMoney = money == MAX_MONEY;
                if (maxMoney) {
                    if (!wasNotificationSent) {
                        sendNotification(context, alpaca.getName());
                        notificationSent.put(alpaca, true);
                    }
                } else {
                    notificationSent.put(alpaca, false);
                }
            }
        });
    }

    private static void initMap() {
        if (notificationSent == null) {
            synchronized (WoolNotification.class) {
                if (notificationSent == null) {
                    notificationSent = new HashMap<>();
                }
            }
        }
    }

    private static void sendNotification(Context context, String alpacaName) {
        //send notification saying that alpaca is ready to shear
        final String CHANNEL_ID = "wool_id";
        final String GROUP_ID = "stat_group";
        Intent toMainScreen = new Intent(context, MainActivity.class);
        PendingIntent notificationIntent = PendingIntent.getActivity(context, 0, toMainScreen, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        NotificationManager manager = context.getSystemService(NotificationManager.class);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "wool";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            manager.createNotificationChannel(channel);
        }

        builder.setContentTitle("Appaca");
        builder.setContentText(alpacaName + " is ready to be sheared!");
        builder.setOnlyAlertOnce(true);
        builder.setGroup(GROUP_ID);
        builder.setSmallIcon(R.mipmap.appaca_game_icon);
        builder.setContentIntent(notificationIntent);

        manager.notify(NotificationId.WOOL, builder.build());
    }
}
