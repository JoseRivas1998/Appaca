package edu.csuci.appaca.concurrency;

import android.util.Log;

import edu.csuci.appaca.activities.MinigameSelectActivity;
import edu.csuci.appaca.data.AlpacaFarm;
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

    private static int recoveryMinutes = 30;

    public static void start(MinigameSelectActivity activity) {
        ThreadInstance.INSTANCE.start(activity);
    }

    public static void stop() {
        ThreadInstance.INSTANCE.stop();
    }

    private static class BackgroundThread extends Thread {

        private static final long UPDATES_PER_SECOND = 60;

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
                updateStamina();
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
            if ((StaminaManager.getFirstStaminaUsedTime() != 0) && (timeDifference >= recoveryMinutes)) {
                double staminaToRecover = timeDifference/recoveryMinutes;
                double missingStamina = (double)StaminaManager.MAX_STAMINA - StaminaManager.getCurrentStamina();
                if (staminaToRecover > missingStamina) {
                    staminaToRecover = missingStamina;
                }
                for (int i = 0; i < staminaToRecover; i++) {
                    StaminaManager.increaseCurrentStamina();
                }

            }
        }

        public void stopRunning() {
            this.running = false;
        }

        public boolean isRunning() {
            return running;
        }
    }

}

