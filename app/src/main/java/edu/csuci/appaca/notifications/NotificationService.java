package edu.csuci.appaca.notifications;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.UUID;

import edu.csuci.appaca.utils.TimeUtils;

public class NotificationService extends Service {

    private static UUID id;
    private static NotificationThread notificationThread;

    private static String getUUID() {
        if(id == null) {
            synchronized (NotificationService.class) {
                if(id == null) {
                    id = UUID.randomUUID();
                }
            }
        }
        return id.toString();
    }

    private static void startNotificationThread(Context context) {
        if(notificationThread != null) {
            if(notificationThread.isRunning()) {
                notificationThread.stopRunning();
            }
            notificationThread = null;
        }
        notificationThread = new NotificationThread(context);
        notificationThread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.i(getClass().getName(), "ON TASK START " + getUUID());
        startNotificationThread(this);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(getClass().getName(), "ON BIND " + getUUID());
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(getClass().getName(), "ON DESTROY " + getUUID());
        Intent restartService = new Intent(this, RestartService.class);
        sendBroadcast(restartService);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.i(getClass().getName(), "ON TASK REMOVED " + getUUID());
        Intent restartService = new Intent(this, RestartService.class);
        sendBroadcast(restartService);
    }

    private static class NotificationThread extends Thread {

        private long prevTime;
        private boolean isRunning;
        private Context context;

        NotificationThread(Context context) {
            prevTime = 0;
            this.isRunning = true;
            this.context = context;
        }

        @Override
        public void run() {
            long currentTime;
            Log.i(getClass().getName(), "STARTING NOTIFICATION THREAD.");
            while (isRunning) {
                currentTime = TimeUtils.getCurrentTime();
                if(currentTime - prevTime >= 30) {
                    Log.i(getClass().getName(), "CHECKING NOTIFICATIONS.");
                    synchronized (this) {
                        NotificationChecker.checkNotifications(context);
                    }
                    prevTime = currentTime;
                }
            }
            Log.i(getClass().getName(), "STOPPING NOTIFICATION THREAD.");
        }

        boolean isRunning() {
            return isRunning;
        }

        void stopRunning() {
            if(isRunning) {
                synchronized (this) {
                    if(isRunning) {
                        this.isRunning = false;
                    }
                }
            }
        }

    }

}
