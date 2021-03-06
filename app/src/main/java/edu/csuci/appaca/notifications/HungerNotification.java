package edu.csuci.appaca.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.badlogic.gdx.math.MathUtils;

import java.util.HashMap;

import edu.csuci.appaca.R;
import edu.csuci.appaca.activities.MainActivity;
import edu.csuci.appaca.data.Alpaca;
import edu.csuci.appaca.data.AlpacaFarm;
import edu.csuci.appaca.data.FoodDepletion;
import edu.csuci.appaca.data.SavedTime;
import edu.csuci.appaca.utils.ListUtils;

public class HungerNotification {

    private static HashMap<Alpaca, Boolean> notificationSentMap;

    public static void checkForLowHunger(final Context context) {
        initMap();
        AlpacaFarm.forEach(new ListUtils.Consumer<Alpaca>() {
            @Override
            public void accept(Alpaca alpaca) {
                boolean sentNotification = ListUtils.getOrDefault(notificationSentMap, alpaca, false);
                long lastTime = SavedTime.lastSavedTime();
                double hungerStat = FoodDepletion.foodDepletion(alpaca, lastTime);
                hungerStat = MathUtils.clamp(hungerStat, Alpaca.MIN_STAT, Alpaca.MAX_STAT);
                if (Double.compare(hungerStat, Alpaca.MIN_STAT) == 0) {
                    if (!sentNotification) {
                        notificationSentMap.put(alpaca, true);
                        sendNotification(context, alpaca.getName());
                    }
                } else {
                    notificationSentMap.put(alpaca, false);
                }
            }
        });
    }

    private static void initMap() {
        if(notificationSentMap == null) {
            synchronized(HungerNotification.class) {
                if(notificationSentMap == null) {
                    notificationSentMap = new HashMap<>();
                }
            }
        }
    }

    private static void sendNotification(Context context, String alpacaName) {
        final String CHANNEL_ID = "hunger_id";
        final String GROUP_ID = "stat_group";
        Intent toMainScreen = new Intent(context, MainActivity.class);
        PendingIntent notificationIntent = PendingIntent.getActivity(context, 0, toMainScreen, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "hunger";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(channel);
        }

        builder.setContentTitle("Appaca");
        builder.setContentText(alpacaName + " is hungry!");

        builder.setGroup(GROUP_ID);
        builder.setSmallIcon(R.mipmap.appaca_game_icon);
        builder.setOnlyAlertOnce(true);
        builder.setContentIntent(notificationIntent);

        notificationManager.notify(NotificationId.HUNGER, builder.build());

    }

}
