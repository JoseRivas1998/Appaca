package edu.csuci.appaca.concurrency;

import android.app.Activity;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import edu.csuci.appaca.activities.MainActivity;
import edu.csuci.appaca.data.Alpaca;
import edu.csuci.appaca.data.AlpacaFarm;
import edu.csuci.appaca.data.CurrencyManager;
import edu.csuci.appaca.data.FoodDepletion;
import edu.csuci.appaca.data.HappinessCalc;
import edu.csuci.appaca.data.HygieneDepletion;
import edu.csuci.appaca.data.SavedTime;
import edu.csuci.appaca.data.Stat;
import edu.csuci.appaca.fragments.StatBarFragment;
import edu.csuci.appaca.utils.ListUtils;
import edu.csuci.appaca.utils.TimeUtils;

public class MainScreenBackground {

    private enum ThreadInstance {
        INSTANCE;
        BackgroundThread thread;

        void start(MainActivity activity) {
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

    public static void start(MainActivity activity) {
        ThreadInstance.INSTANCE.start(activity);
    }

    public static void stop() {
        ThreadInstance.INSTANCE.stop();
    }

    private static class BackgroundThread extends Thread {

        private static final long UPDATES_PER_SECOND = 60;

        private boolean running;
        private MainActivity parent;

        public BackgroundThread(MainActivity parent) {
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
            final Map<Stat, Double> currentValues = new HashMap<>();
            for (Stat stat : Stat.values()) {
                currentValues.put(stat, Alpaca.MAX_STAT);
            }
            while(this.running) {
                Alpaca current = AlpacaFarm.getCurrentAlpaca();
                updateStats(currentValues, current);
                updateCurrencyDisplays();
                try {
                    Thread.sleep(1000 / UPDATES_PER_SECOND);
                } catch (InterruptedException e) {
                    Log.e(getClass().getSimpleName() + ":" + getId(), e.getMessage(), e);
                }
            }
        }

        private void updateCurrencyDisplays() {
            int currentRealCurrencyAlpaca = CurrencyManager.getCurrencyAlpaca();
            int currentDisplayedCurrencyAlpaca = parent.getDisplayedCurrencyAlpaca();
            if(currentRealCurrencyAlpaca != currentDisplayedCurrencyAlpaca) {
                if(currentDisplayedCurrencyAlpaca < currentRealCurrencyAlpaca) {
                    currentDisplayedCurrencyAlpaca++;
                } else {
                    currentDisplayedCurrencyAlpaca--;
                }

            }
            int currentRealCurrencyOther = CurrencyManager.getCurrencyOther();
            int currentDisplayedCurrencyOther = parent.getDisplayedCurrencyOther();
            if(currentRealCurrencyOther != currentDisplayedCurrencyOther) {
                if(currentDisplayedCurrencyOther < currentRealCurrencyOther) {
                    currentDisplayedCurrencyOther++;
                } else {
                    currentDisplayedCurrencyOther--;
                }
            }
            parent.updateCurrencyValues(currentDisplayedCurrencyAlpaca, currentDisplayedCurrencyOther);
        }

        private void updateStats(final Map<Stat, Double> currentValues, Alpaca current) {
            long previousTime = SavedTime.lastSavedTime();

            double currentFood = Math.max(Alpaca.MIN_STAT, FoodDepletion.foodDepletion(current, previousTime));
            currentValues.put(Stat.HUNGER, currentFood);

            double currentHappiness = Math.max(Alpaca.MIN_STAT, HappinessCalc.calcHappiness(null, current, previousTime));
            currentValues.put(Stat.HAPPINESS, currentHappiness);

            double currentHygiene = Math.max(Alpaca.MIN_STAT, HygieneDepletion.hygieneDepletion(current, previousTime));
            currentValues.put(Stat.HYGIENE, currentHygiene);

            parent.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    parent.forEachStatBar(new ListUtils.DuelConsumer<Stat, StatBarFragment>() {
                        @Override
                        public void accept(Stat stat, StatBarFragment statBarFragment) {
                            double value = ListUtils.getOrDefault(currentValues, stat, Alpaca.MAX_STAT);
                            statBarFragment.setBarFillLevel(value, Alpaca.MIN_STAT, Alpaca.MAX_STAT);
                        }
                    });
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
