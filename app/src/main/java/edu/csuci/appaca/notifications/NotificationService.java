package edu.csuci.appaca.notifications;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class NotificationService extends Service {
    public NotificationService() {
        super();
    }

    private Binder localBinder = new Binder();

    @Override
    public IBinder onBind(Intent intent) {
        return this.localBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startID)
    {
        return START_STICKY;
    }
}
