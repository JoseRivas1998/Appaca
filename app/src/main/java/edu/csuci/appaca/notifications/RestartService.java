package edu.csuci.appaca.notifications;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RestartService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Log.i(getClass().getName(), "RECEIVED RESTART");
            Intent startService = new Intent(context, NotificationService.class);
            context.startService(startService);
        } catch (IllegalStateException ise) {
            Log.e(getClass().getName(), "UNABLE TO START SERVICE", ise);
        }
    }
}
