package edu.csuci.appaca.notifications;

import android.content.Context;

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

    public static void sendNotification(Context context, String alpacaName){}
}
