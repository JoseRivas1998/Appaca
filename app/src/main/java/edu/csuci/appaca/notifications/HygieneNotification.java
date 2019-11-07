package edu.csuci.appaca.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.badlogic.gdx.backends.android.AndroidApplication;

import edu.csuci.appaca.data.Alpaca;
import edu.csuci.appaca.data.AlpacaFarm;
import edu.csuci.appaca.utils.ListUtils;

abstract public class HygieneNotification extends AppCompatActivity {
    private final String NOTIFICATION_CHANNEL_ID = "hygiene_id";
    private NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
    private NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

    public static void checkIfAnyAlpacasLowHygiene() {

        AlpacaFarm.forEach(new ListUtils.Consumer<Alpaca>() {
            private boolean oneAlpacaFound = false;
            @Override
            public void accept(Alpaca alpaca) {
                boolean hygieneDepleted = alpaca.getHygieneStat() == Alpaca.MIN_STAT;
                if (hygieneDepleted && !oneAlpacaFound)
                {
                    oneAlpacaFound = true;
                    sendNotification();
                }
            }
        });
    }

    public static void sendNotification() {

    }

}
