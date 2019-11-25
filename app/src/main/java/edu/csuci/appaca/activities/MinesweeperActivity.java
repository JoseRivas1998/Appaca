package edu.csuci.appaca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.csuci.appaca.R;

public class MinesweeperActivity extends AppCompatActivity {
    private final int GRID_SIZE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMinesweeper();
        setContentView(R.layout.activity_minesweeper);
    }

    private void initMinesweeper() {

    }

}

