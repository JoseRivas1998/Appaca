package edu.csuci.appaca.notifications;

import android.content.Context;

public class NotificationChecker extends Thread
{
    private Context context;
    private static final NotificationChecker instance = new NotificationChecker();

    private NotificationChecker()
    {

    }

    public static NotificationChecker getInstance(Context context)
    {
        instance.context = context;
        return instance;
    }

    @Override
    public void run() {
        while(true) {
            HygieneNotification.checkIfAnyAlpacasLowHygiene(context);
            WoolNotification.checkIfAnyAlpacasMaxWool(context);
        }
    }
}
