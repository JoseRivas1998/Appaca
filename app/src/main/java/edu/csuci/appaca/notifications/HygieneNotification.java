package edu.csuci.appaca.notifications;

import edu.csuci.appaca.data.Alpaca;
import edu.csuci.appaca.data.AlpacaFarm;
import edu.csuci.appaca.utils.ListUtils;

public class HygieneNotification {
    public static boolean checkIfAnyAlpacasLowHygiene() {
        AlpacaFarm.forEach(new ListUtils.Consumer<Alpaca>() {
            @Override
            public void accept(Alpaca alpaca) {

            }
        });
        return false;
    }

    public static void sendNotification() {

    }

}
