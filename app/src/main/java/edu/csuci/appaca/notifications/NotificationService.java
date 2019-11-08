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
}
