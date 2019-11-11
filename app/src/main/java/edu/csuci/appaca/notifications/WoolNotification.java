package edu.csuci.appaca.notifications;

import android.app.NotificationManager;
import android.content.Context;

import androidx.core.app.NotificationCompat;

import edu.csuci.appaca.data.Alpaca;
import edu.csuci.appaca.data.AlpacaFarm;
import edu.csuci.appaca.utils.ListUtils;
import edu.csuci.appaca.utils.ShearUtils;

public class WoolNotification {

    public static void checkIfAnyAlpacasMaxWool(final Context context){
        AlpacaFarm.forEach(new ListUtils.Consumer<Alpaca>() {
            @Override
            public void accept(Alpaca alpaca) {
                final int MAX_MONEY = 50;
                int money = ShearUtils.getShearValue(alpaca, context);
                if (money == MAX_MONEY)
                {
                    sendNotification(context, alpaca.getName());
                }
            }
        });
    }

    private static void sendNotification(Context context, String alpacaName){
        //send notification saying that alpaca is ready to shear
        final String CHANNEL_ID = "wool_id";
        final String GROUP_ID = "stat_group";
        final int NOTIFY_ID = 1;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        NotificationManager manager = context.getSystemService(NotificationManager.class);
    }
}
