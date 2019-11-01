package edu.csuci.appaca.concurrency;

import android.util.Log;

import edu.csuci.appaca.R;
import edu.csuci.appaca.activities.GameOverActivity;
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

        void start(GameOverActivity activity) {
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

    public static void start(GameOverActivity activity) {
        ThreadInstance.INSTANCE.start(activity);
    }

    public static void stop() {
        ThreadInstance.INSTANCE.stop();
    }

    private static class BackgroundThread extends Thread {

        private static final long UPDATES_PER_SECOND = 60;

        private boolean running;
        private MinigameSelectActivity miniGameParent;
        private GameOverActivity gameOverParent;

        public BackgroundThread(MinigameSelectActivity parent) {
            super();
            this.running = false;
            this.miniGameParent = parent;
        }

        public BackgroundThread(GameOverActivity parent) {
            super();
            this.running = false;
            this.gameOverParent = parent;
        }

        @Override
        public void run() {
            this.running = true;
            if(AlpacaFarm.numberOfAlpacas() == 0) {
                return;
            }
            while(this.running) {
                if (StaminaManager.getFirstStaminaUsedTime() != 0)
                    if (miniGameParent != null)
                        updateStaminaFromMiniSelect();
                    else if (gameOverParent != null)
                        updateStaminaFromGameOver();
                try {
                    Thread.sleep(1000 / UPDATES_PER_SECOND);
                } catch (InterruptedException e) {
                    Log.e(getClass().getSimpleName() + ":" + getId(), e.getMessage(), e);
                }
            }
        }

        private void updateStaminaFromMiniSelect() {
            long currentTime = TimeUtils.getCurrentTime();
            double timeDifference = TimeUtils.secondsToMinutes(currentTime - StaminaManager.getFirstStaminaUsedTime());
            if (timeDifference >= miniGameParent.getResources().getInteger(R.integer.recovery_minutes)) {
                StaminaManager.increaseCurrentStaminaToMax();
                SaveDataUtils.updateValuesAndSave(miniGameParent);
            }
        }

        private void updateStaminaFromGameOver() {
            long currentTime = TimeUtils.getCurrentTime();
            double timeDifference = TimeUtils.secondsToMinutes(currentTime - StaminaManager.getFirstStaminaUsedTime());
            if (timeDifference >= gameOverParent.getResources().getInteger(R.integer.recovery_minutes)) {
                StaminaManager.increaseCurrentStaminaToMax();
                SaveDataUtils.updateValuesAndSave(gameOverParent);
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

