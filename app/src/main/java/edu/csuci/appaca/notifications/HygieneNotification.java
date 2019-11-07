package edu.csuci.appaca.notifications;

import android.app.NotificationManager;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import edu.csuci.appaca.data.Alpaca;
import edu.csuci.appaca.data.AlpacaFarm;
import edu.csuci.appaca.utils.ListUtils;

abstract public class HygieneNotification extends AppCompatActivity {

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
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

}
