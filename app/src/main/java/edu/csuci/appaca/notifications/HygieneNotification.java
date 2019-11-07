package edu.csuci.appaca.notifications;

import android.app.NotificationManager;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import edu.csuci.appaca.data.Alpaca;
import edu.csuci.appaca.data.AlpacaFarm;
import edu.csuci.appaca.utils.ListUtils;

abstract public class HygieneNotification extends AppCompatActivity {
    private final String NOTIFICATION_CHANNEL_ID = "hygiene_id";
    private NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
    private NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

    public static void checkIfAnyAlpacasLowHygiene() {

        AlpacaFarm.forEach(new ListUtils.Consumer<Alpaca>() {
            @Override
            public void accept(Alpaca alpaca) {
                if (alpaca.getHygieneStat() == Alpaca.MIN_STAT)
                {
                    sendNotification(alpaca.getName());
                }
            }
        });
    }

    public static void sendNotification(String alpacaName) {

    }

}
