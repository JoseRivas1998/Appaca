package edu.csuci.appaca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.MiniGames;
import edu.csuci.appaca.graphics.minesweeper.MinesweeperTile;
import edu.csuci.appaca.utils.AssetsUtils;

public class MinesweeperActivity extends AppCompatActivity {
    private final int GRID_SIZE = 16;
    private final int MAX_BOMBS = 40;
    private MinesweeperTile[][] grid;
    private long timePlayed = 0;
    private boolean flagToggle = false;

    public static int tilesRevealed = 0;
    public static int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minesweeper);
        initMinesweeper();
        placeBombs();
        getSupportActionBar().hide();
    }

    private void initMinesweeper() {
        this.score = 0;
        final Context context = this.getApplicationContext();
        grid = new MinesweeperTile[GRID_SIZE][GRID_SIZE];
        TextView scoreText = findViewById(R.id.minesweeper_score_text);
        String format = context.getText(MiniGames.MINESWEEPER.scoreFormatId).toString();
        String text = String.format(format, score);
        scoreText.setText(text);
        GridLayout view = findViewById(R.id.minesweeper_grid);
        final ImageButton flagButton = findViewById(R.id.flag_toggle);
        flagButton.setImageDrawable(AssetsUtils.drawableFromAsset(this, "minesweeper/orange_flag.png"));
        flagButton.setBackgroundColor(Color.TRANSPARENT);

        flagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flagToggle) {
                    flagButton.setBackgroundColor(Color.TRANSPARENT);
                    flagToggle = false;
                } else {
                    flagButton.setBackgroundColor(getColor(R.color.yellowPastel));
                    flagToggle = true;
                }

            }
        });

        view.setColumnCount(GRID_SIZE);
        for(int i = 0; i < GRID_SIZE; i++) {
            for(int j = 0; j < GRID_SIZE; j++) {
                final MinesweeperTile tile = MinesweeperTile.createTile(i, j, GRID_SIZE, this.getApplicationContext());
                tile.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (flagToggle) {
                            tile.flipFlag(context);
                        } else {
                            boolean revealed = tile.reveal(context);
                            if (revealed) {
                                if (tile.bomb) {
                                    MiniGames.endGame(MinesweeperActivity.this, MiniGames.MINESWEEPER, score, timePlayed);
                                } else {
                                    revealNeighboringTiles(tile.row, tile.column);
                                }
                            }
                        }
                        updateScore();
                    }
                });
                grid[i][j] = tile;
                view.addView(tile.view);
            }
        }
    }

    private void checkWin(){
        final int NUM_SAFE_TILES = (GRID_SIZE * GRID_SIZE) - MAX_BOMBS;
        if(tilesRevealed == NUM_SAFE_TILES) {
            MiniGames.winGame(MinesweeperActivity.this, MiniGames.MINESWEEPER, score, timePlayed);
        }
    }

    private void updateScore(){
        Context context = this.getApplicationContext();
        TextView scoreText = findViewById(R.id.minesweeper_score_text);
        String format = context.getText(MiniGames.MINESWEEPER.scoreFormatId).toString();
        String text = String.format(format, score);
        scoreText.setText(text);
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

        boolean southContinue = true;
        boolean eastContinue = true;
        boolean northContinue = true;
        boolean westContinue = true;

        boolean NEContinue = true;
        boolean SEContinue = true;
        boolean NWContinue = true;
        boolean SWContinue = true;
        for (int i = 0; i < GRID_SIZE; i++) {
            southExists = (y + i) < GRID_SIZE - 1;
            eastExists = (x + i) < GRID_SIZE - 1;
            northExists = (y - i) > 0;
            westExists = (x - i) > 0;
            
            if (northExists && !grid[y-i][x].bomb && northContinue) {
                checkNeighboringTiles(y-i, x);
            } else {
                northContinue = false;
            }
            if (eastExists && !grid[y][x+i].bomb && eastContinue) {
                checkNeighboringTiles( y, x+i);
            } else {
                eastContinue = false;
            }
            if (southExists && !grid[y+i][x].bomb && southContinue) {
                checkNeighboringTiles(y+i, x);
            } else {
                southContinue = false;
            }
            if (westExists && !grid[y][x-i].bomb && westContinue) {
                checkNeighboringTiles(y, x-i);
            } else {
                westContinue = false;
            }

            //diagonals

            if (northExists && eastExists && !grid[y-i][x+i].bomb && NEContinue) {
                checkNeighboringTiles(y-i,x+i);
            } else {
                NEContinue = false;
            }
            if (southExists && eastExists && !grid[y+i][x+i].bomb && SEContinue) {
                checkNeighboringTiles(y+i,x+i);
            } else {
                SEContinue = false;
            }
            if (northExists && westExists && !grid[y-i][x-i].bomb && NWContinue) {
                checkNeighboringTiles(y-i,x-i);
            } else {
                NWContinue = false;
            }
            if (southExists && westExists && !grid[y+i][x-i].bomb && SWContinue) {
                checkNeighboringTiles(y+i,x-i);
            } else {
                SWContinue = false;
            }
        }
        checkWin();
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

