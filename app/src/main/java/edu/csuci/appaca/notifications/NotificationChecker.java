package edu.csuci.appaca.notifications;

import android.content.Context;

public class NotificationChecker extends Thread
{

    public static void checkNotifications(Context context) {
        HygieneNotification.checkIfAnyAlpacasLowHygiene(context);
        WoolNotification.checkIfAnyAlpacasMaxWool(context);
        HappinessNotification.checkForLowHappiness(context);
        StaminaNotification.checkIfStaminaIsFull(context);
        HungerNotification.checkForLowHunger(context);
    }

}
