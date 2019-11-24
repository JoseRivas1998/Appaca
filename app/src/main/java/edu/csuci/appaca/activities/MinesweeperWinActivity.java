package edu.csuci.appaca.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.Alpaca;
import edu.csuci.appaca.data.AlpacaFarm;
import edu.csuci.appaca.data.CurrencyManager;
import edu.csuci.appaca.data.HighScore;
import edu.csuci.appaca.data.MiniGames;
import edu.csuci.appaca.data.PendingCoins;
import edu.csuci.appaca.data.SaveDataUtils;
import edu.csuci.appaca.data.StaminaManager;
import edu.csuci.appaca.utils.TimeUtils;

public class MinesweeperWinActivity extends AppCompatActivity {

    private MiniGames returnTo;
    private int score;
    private int highScore;
    private int timePlayed;
    private double happinessToGain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minesweeper_win);
    }

    private void updateSaveData() {
        updateHighScore();
        updateCurrencyAlpacas();
        calculateHappinessToGain();
        SaveDataUtils.updateValuesAndSave(this);
        AlpacaFarm.getCurrentAlpaca().incrementHappinessStat(happinessToGain);
        SaveDataUtils.save(this);
    }

    private void updateCurrencyAlpacas() {
        int coinsToGet = returnTo.coinsForScore(this.score);
        CurrencyManager.gainCurrencyAlpaca(coinsToGet);
        PendingCoins.addCoins(coinsToGet);
    }

    private void calculateHappinessToGain() {
        final double HAPPINESS_PER_SECOND = Alpaca.MAX_STAT / 60.0f;
        happinessToGain = timePlayed * HAPPINESS_PER_SECOND;
    }

    private void updateHighScore() {
        this.highScore = HighScore.getHighScore(returnTo);
        if (HighScore.putHighScore(returnTo, this.score)) {
            this.highScore = this.score;
        }
    }

    private void loadIntentData() {
        Intent intent = getIntent();
        this.score = intent.getIntExtra("score", 0);
        int ordinal = intent.getIntExtra("return", 0);
        this.returnTo = MiniGames.values()[ordinal];
        this.timePlayed = intent.getIntExtra("timePlayed", 0);
    }

    private void checkStaminaAndUpdate() {
        long currentTime = TimeUtils.getCurrentTime();
        double timeDifference = TimeUtils.secondsToMinutes(currentTime - StaminaManager.getFirstStaminaUsedTime());
        if (timeDifference >= this.getResources().getInteger(R.integer.recovery_minutes)) {
            StaminaManager.increaseCurrentStaminaToMax();
            SaveDataUtils.updateValuesAndSave(this);
        }
    }
}
