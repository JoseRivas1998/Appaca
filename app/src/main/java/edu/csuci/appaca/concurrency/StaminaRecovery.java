package edu.csuci.appaca.concurrency;

import android.util.Log;

import edu.csuci.appaca.R;
import edu.csuci.appaca.activities.MinigameSelectActivity;
import edu.csuci.appaca.data.AlpacaFarm;
import edu.csuci.appaca.data.SaveDataUtils;
import edu.csuci.appaca.data.StaminaManager;
import edu.csuci.appaca.utils.TimeUtils;

public class StaminaRecovery {

    private enum ThreadInstance {
        INSTANCE;
        BackgroundThread thread;

        void start(MinigameSelectActivity activity) {
            if(thread != null && thread.isRunning()) {
                thread.stopRunning();
            }
            thread = new BackgroundThread(activity);
            thread.start();
        }

        void stop() {
            if(thread != null && thread.isRunning()) {
                thread.stopRunning();
                thread = null;
            }
        }
    }

    public static void start(MinigameSelectActivity activity) {
        ThreadInstance.INSTANCE.start(activity);
    }

    public static void stop() {
        ThreadInstance.INSTANCE.stop();
    }

    private static class BackgroundThread extends Thread {

        private static final long UPDATES_PER_SECOND = 60;

        boolean countDown = false;

        private boolean running;
        private MinigameSelectActivity parent;

        public BackgroundThread(MinigameSelectActivity parent) {
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
                if (StaminaManager.getFirstStaminaUsedTime() != 0) {
                    updateStamina();
                    countDown = true;
                }
                if (countDown)
                    updateTime();
                try {
                    Thread.sleep(1000 / UPDATES_PER_SECOND);
                } catch (InterruptedException e) {
                    Log.e(getClass().getSimpleName() + ":" + getId(), e.getMessage(), e);
                }
            }
        }

        private void updateStamina() {
            long currentTime = TimeUtils.getCurrentTime();
            double timeDifference = TimeUtils.secondsToMinutes(currentTime - StaminaManager.getFirstStaminaUsedTime());
            if (timeDifference >= parent.getResources().getInteger(R.integer.recovery_minutes)) {
                StaminaManager.increaseCurrentStaminaToMax();
                SaveDataUtils.updateValuesAndSave(parent);
                setMessage(StaminaManager.getCurrentStamina(),  "");
                countDown = false;
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

            if (StaminaManager.getFirstStaminaUsedTime() == 0)
                countDown = false;

            final String message = countDown ? "Time Until Refill: " + String.format("%02d",minutes) + ":" + String.format("%02d", seconds) : "";

            setMessage(StaminaManager.getCurrentStamina(), message);

        }

        private void setMessage(int stamina, String timeUntilRefill) {
            final String message = "Stamina: " + stamina + "\n" + timeUntilRefill;

            parent.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    parent.setStaminaMessage(message);
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

