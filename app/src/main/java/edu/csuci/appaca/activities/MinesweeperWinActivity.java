package edu.csuci.appaca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.csuci.appaca.R;

public class MinesweeperWinActivity extends GameOverActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minesweeper_win);
    }

    @Override
    public void initButton() {
        super.initButton();
    }
}
