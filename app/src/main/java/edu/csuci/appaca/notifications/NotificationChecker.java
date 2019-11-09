package edu.csuci.appaca.notifications;

import android.content.Context;

public class NotificationChecker implements Runnable
{
    private Context context;

    public NotificationChecker(Context context)
    {
        super();
        this.context = context;
    }

    @Override
    public void run() {
        while(true) {
            HygieneNotification.checkIfAnyAlpacasLowHygiene(context);
        }
    }
}
