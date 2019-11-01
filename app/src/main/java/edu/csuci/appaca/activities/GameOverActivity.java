package edu.csuci.appaca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.badlogic.gdx.Game;

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.CurrencyManager;
import edu.csuci.appaca.data.HighScore;
import edu.csuci.appaca.data.MiniGames;
import edu.csuci.appaca.data.PendingCoins;
import edu.csuci.appaca.data.SaveDataUtils;

public class GameOverActivity extends AppCompatActivity {

    private MiniGames returnTo;
    private int score;
    private int highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        loadIntentData();
        updateSaveData();
        getSupportActionBar().hide();
        initButton();
    }

    private void updateSaveData() {
        updateHighScore();
        updateCurrencyAlpacas();
        updateHappiness();
        SaveDataUtils.updateValuesAndSave(this);
    }

    private void updateCurrencyAlpacas() {
        int coinsToGet = returnTo.coinsForScore(this.score);
        CurrencyManager.gainCurrencyAlpaca(coinsToGet);
        PendingCoins.addCoins(coinsToGet);
    }

    private void updateHappiness() {
        // TODO this is a stub
    }

    private void updateHighScore() {
        this.highScore = HighScore.getHighScore(returnTo);
        if(HighScore.putHighScore(returnTo, this.score)) {
            this.highScore = this.score;
        }
    }

    private void loadIntentData() {
        Intent intent = getIntent();
        this.score = intent.getIntExtra("score", 0);
        int ordinal = intent.getIntExtra("return", 0);
        this.returnTo = MiniGames.values()[ordinal];
    }

    public void initButton() {
        final Button exitButton = findViewById(R.id.exitButton);
        final Button playAgainButton = findViewById(R.id.playAgain);
        final TextView scoreText = findViewById(R.id.scoreText);
        final TextView highScoreText = findViewById(R.id.highScoreText);

        scoreText.setText(String.format(getString(returnTo.scoreFormatId), this.score));

        highScoreText.setText(String.format(getString(returnTo.highScoreFormatId), this.highScore));

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this, returnTo.activityClass);
                startActivity(intent);
                finish();
            }
        });
    }
}
