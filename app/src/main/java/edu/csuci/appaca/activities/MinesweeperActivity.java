package edu.csuci.appaca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridLayout;

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.MinesweeperTile;

public class MinesweeperActivity extends AppCompatActivity {
    private final int GRID_SIZE = 5*5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMinesweeper();
        setContentView(R.layout.activity_minesweeper);
    }

    private void initMinesweeper() {
        GridLayout grid = findViewById(R.id.minesweeper_grid);
        for (int tileNum = 1; tileNum <= GRID_SIZE; tileNum++) {
            MinesweeperTile tile = MinesweeperTile.createTile(tileNum, this.getApplicationContext());
        }
    }

}

