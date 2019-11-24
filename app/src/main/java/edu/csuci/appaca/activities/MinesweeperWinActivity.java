package edu.csuci.appaca.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.MiniGames;

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
}
