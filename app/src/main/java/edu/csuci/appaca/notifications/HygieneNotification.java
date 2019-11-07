package edu.csuci.appaca.notifications;

import edu.csuci.appaca.data.Alpaca;
import edu.csuci.appaca.data.AlpacaFarm;
import edu.csuci.appaca.utils.ListUtils;

public class HygieneNotification {
    public static void checkIfAnyAlpacasLowHygiene() {

        AlpacaFarm.forEach(new ListUtils.Consumer<Alpaca>() {
            private boolean oneAlpacaFound = false;
            @Override
            public void accept(Alpaca alpaca) {
                if (alpaca.getHygieneStat() == Alpaca.MIN_STAT && !oneAlpacaFound)
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
