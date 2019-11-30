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
                final MinesweeperTile tile = MinesweeperTile.createTile(i, j, GRID_SIZE, this.getApplicationContext());
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

    private void revealNeighboringTiles(int y, int x) {
        boolean southExists;
        boolean eastExists;
        boolean northExists;
        boolean westExists;

        for (int i = 0; i < GRID_SIZE; i++) {
            southExists = (y + i) < GRID_SIZE - 1;
            eastExists = (x + i) < GRID_SIZE - 1;
            northExists = (y - i) > 0;
            westExists = (x - i) > 0;
            
            if (northExists && !grid[y-1][x].bomb) {
                checkNeighboringTiles(y-1, x);
            }
            if (eastExists && !grid[y][x+1].bomb) {
                checkNeighboringTiles( y, x+1);
            }
            if (southExists && !grid[y+1][x].bomb) {
                checkNeighboringTiles(y+1, x);
            }
            if (westExists && !grid[y][x-1].bomb) {
                checkNeighboringTiles(y, x-1);
            }

            //diagonals
            if (northExists && eastExists && !grid[y-1][x+1].bomb) {
                checkNeighboringTiles(y-1,x+1);
            }
            if (southExists && eastExists && !grid[y+1][x+1].bomb) {
                checkNeighboringTiles(y+1,x+1);
            }
            if (northExists && westExists && !grid[y-1][x-1].bomb) {
                checkNeighboringTiles(y-1,x-1);
            }
            if (southExists && westExists && !grid[y+1][x-1].bomb) {
                checkNeighboringTiles(y+1,x-1);
            }
        }
    }

    private void checkNeighboringTiles(int y, int x){
        boolean southExists = y < GRID_SIZE - 1;
        boolean eastExists = x < GRID_SIZE - 1;
        boolean northExists = y > 0;
        boolean westExists = x > 0;

        boolean nBomb = false;
        boolean eBomb = false;
        boolean sBomb = false;
        boolean wBomb = false;

        boolean neBomb = false;
        boolean seBomb = false;
        boolean nwBomb = false;
        boolean swBomb = false;
        
        if (northExists) {
            nBomb = grid[y - 1][x].bomb;
        } if (eastExists) {
             eBomb = grid[y][x + 1].bomb;
        } if (southExists) {
             sBomb = grid[y + 1][x].bomb;
        } if (westExists) {
             wBomb = grid[y][x - 1].bomb;
        }
        
        if (northExists && eastExists) {
             neBomb = grid[y - 1][x + 1].bomb;
        } if (southExists && eastExists) {
             seBomb = grid[y + 1][x + 1].bomb;
        } if (northExists && westExists) {
            nwBomb = grid[y - 1][x - 1].bomb;
        } if (southExists && westExists) {
            swBomb = grid[y + 1][x - 1].bomb;
        }
        
        boolean adj = nBomb || eBomb || sBomb || wBomb;
        boolean diag = neBomb || seBomb || nwBomb || swBomb;
        
        boolean bombExists = adj || diag;
        
        Context context = this.getApplicationContext();
        
        grid[y][x].reveal(context);
        if (!bombExists){
            if (northExists) {
                grid[y-1][x].reveal(context);
            }
            if (eastExists) {
                grid[y][x+1].reveal(context);
            }
            if (southExists) {
                grid[y+1][x].reveal(context);
            }
            if (westExists) {
                grid[y][x-1].reveal(context);
            }

            //diagonals
            if (northExists && eastExists) {
                grid[y-1][x+1].reveal(context);
            }
            if (southExists && eastExists) {
                grid[y+1][x+1].reveal(context);
            }
            if (northExists && westExists) {
                grid[y-1][x-1].reveal(context);
            }
            if (southExists && westExists) {
                grid[y+1][x-1].reveal(context);
            }
        }
    }
}

