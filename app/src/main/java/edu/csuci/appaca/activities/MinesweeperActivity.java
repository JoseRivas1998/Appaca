package edu.csuci.appaca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;

import java.util.Random;

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.MiniGames;
import edu.csuci.appaca.graphics.minesweeper.MinesweeperTile;

public class MinesweeperActivity extends AppCompatActivity {
    private final int GRID_SIZE = 16;
    private final int MAX_BOMBS = 40;
    private MinesweeperTile[][] grid;
    private int score = 0;
    private long timePlayed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minesweeper);
        initMinesweeper();
        placeBombs();
        getSupportActionBar().hide();
    }

    private void initMinesweeper() {
        final Context context = this.getApplicationContext();
        grid = new MinesweeperTile[GRID_SIZE][GRID_SIZE];
        GridLayout view = findViewById(R.id.minesweeper_grid);
        view.setColumnCount(GRID_SIZE);
        for(int i = 0; i < GRID_SIZE; i++) {
            for(int j = 0; j < GRID_SIZE; j++) {
                final MinesweeperTile tile = MinesweeperTile.createTile(i+1, j+1, GRID_SIZE, this.getApplicationContext());
                tile.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tile.reveal(context);
                        if (tile.bomb)
                        {
                            MiniGames.endGame(MinesweeperActivity.this, MiniGames.MINESWEEPER, score, timePlayed);
                        }
                        else
                        {
                            revealNeighboringTiles(tile.row, tile.column);
                        }
                    }
                });
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

    private void revealNeighboringTiles(int x, int y) {
        boolean northExists = y < GRID_SIZE - 1;
        boolean eastExists = x < GRID_SIZE - 1;
        boolean southExists = y > 0;
        boolean westExists = x > 0;

        for (int i = 0; i < GRID_SIZE; i++) {
            if (northExists) {

            }
            if (eastExists) {
            }
            if (southExists) {
            }
            if (westExists) {
            }

            //diagonals
            if (northExists && eastExists) {
            }
            if (southExists && eastExists) {
            }
            if (northExists && westExists) {
            }
            if (southExists && westExists) {
            }

            northExists = (y + i) < GRID_SIZE - 1;
            eastExists = (x + i) < GRID_SIZE - 1;
            southExists = (y - i) > 0;
            westExists = (x - i) > 0;
        }
    }
}

