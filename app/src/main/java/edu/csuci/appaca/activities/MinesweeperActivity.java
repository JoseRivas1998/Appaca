package edu.csuci.appaca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridLayout;

import java.util.Random;

import edu.csuci.appaca.R;
import edu.csuci.appaca.graphics.minesweeper.MinesweeperTile;

public class MinesweeperActivity extends AppCompatActivity {
    private final int GRID_SIZE = 16;
    private final int MAX_BOMBS = 100;
    private MinesweeperTile[][] grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minesweeper);
        initMinesweeper();
        placeBombs();
        getSupportActionBar().hide();
    }

    private void initMinesweeper() {
        grid = new MinesweeperTile[GRID_SIZE][GRID_SIZE];
        GridLayout view = findViewById(R.id.minesweeper_grid);
        view.setColumnCount(GRID_SIZE);
        for(int i = 0; i < GRID_SIZE; i++) {
            for(int j = 0; j < GRID_SIZE; j++) {
                MinesweeperTile tile = MinesweeperTile.createTile(i+1, j+1, GRID_SIZE, this.getApplicationContext());
                grid[i][j] = tile;
                view.addView(tile.view);
            }
        }
    }

    private void placeBombs() {
        Random generator = new Random();
        for (int i = 0; i < this.MAX_BOMBS; i++)
        {
            int x = generator.nextInt(GRID_SIZE - 1) + 1;
            int y = generator.nextInt(GRID_SIZE - 1) + 1;
            grid[x][y].setBomb();
        }
    }

}

