package edu.csuci.appaca.concurrency;

import android.util.Log;

import edu.csuci.appaca.R;
import edu.csuci.appaca.fragments.EmptyStaminaFragment;
import edu.csuci.appaca.data.AlpacaFarm;
import edu.csuci.appaca.data.StaminaManager;
import edu.csuci.appaca.utils.TimeUtils;

public class NoStaminaBackground {

    private enum ThreadInstance {
        INSTANCE;
        BackgroundThread thread;

        void start(EmptyStaminaFragment fragment) {
            if(thread != null && thread.isRunning()) {
                thread.stopRunning();
            }
            thread = new BackgroundThread(fragment);
            thread.start();
        }

        void stop() {
            if(thread != null && thread.isRunning()) {
                thread.stopRunning();
                thread = null;
            }
        }
    }

    public static void start(EmptyStaminaFragment fragment) {
        ThreadInstance.INSTANCE.start(fragment);
    }

    public static void stop() {
        ThreadInstance.INSTANCE.stop();
    }

    private static class BackgroundThread extends Thread {

        private static final long UPDATES_PER_SECOND = 60;

        private boolean running;
        private EmptyStaminaFragment parent;

        boolean stopCountDown = false;

        public BackgroundThread(EmptyStaminaFragment parent) {
            super();
            this.running = false;
            this.parent = parent;
        }

        @Override
        public void run() {
            this.running = true;
            if(AlpacaFarm.numberOfAlpacas() == 0) {
                return;
            }
            while(this.running) {
                if (StaminaManager.getFirstStaminaUsedTime() != 0)
                    updateTime();
                if (stopCountDown) {
                    setTimeToZero();
                }
                try {
                    Thread.sleep(1000 / UPDATES_PER_SECOND);
                } catch (InterruptedException e) {
                    Log.e(getClass().getSimpleName() + ":" + getId(), e.getMessage(), e);
                }
            }
        }

        private void updateTime() {
            long currentTime = TimeUtils.getCurrentTime();
            long timeDiff = currentTime - StaminaManager.getFirstStaminaUsedTime();
            double timeUntilRecovery = parent.getResources().getInteger(R.integer.recovery_minutes) - TimeUtils.secondsToMinutes(timeDiff);
            int minutes = (int)Math.floor(timeUntilRecovery);
            int seconds = (int)(60 - timeDiff % 60);

            if (seconds == 60)
                seconds = 0;

            if (minutes == 0 && seconds == 0)
                stopCountDown = true;

            final String message = "Time left: " + String.format("%02d",minutes) + ":" + String.format("%02d", seconds);


            parent.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    parent.updateTimeLeft(message);
                }
            });
        }

        private void setTimeToZero() {

            final String message = "Time left: 0:00";

            parent.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    parent.updateTimeLeft(message);
                }
            });
        }

        public void stopRunning() {
            this.running = false;
        }

        public boolean isRunning() {
            return running;
        }
    }


}
