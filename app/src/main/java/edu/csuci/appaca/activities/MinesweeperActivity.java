package edu.csuci.appaca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.MiniGames;
import edu.csuci.appaca.data.SavedTime;
import edu.csuci.appaca.graphics.minesweeper.MinesweeperTile;
import edu.csuci.appaca.utils.AssetsUtils;
import edu.csuci.appaca.utils.TimeUtils;

public class MinesweeperActivity extends AppCompatActivity {
    private final int GRID_SIZE = 16;
    private final int MAX_BOMBS = 40;
    private MinesweeperTile[][] grid;
    private ArrayList<MinesweeperTile> bombs;
    private boolean flagToggle = false;

    public static long timeStarted = 0;
    public static long timePlayed = 0;
    public static int tilesRevealed = 0;
    public static int score;

    private String buffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minesweeper);
        initMinesweeper();
        placeBombs();
        getSupportActionBar().hide();
    }

    private void initMinesweeper() {
        timePlayed = 0;
        score = 0;
        tilesRevealed = 0;
        this.bombs = new ArrayList<>();
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
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                final MinesweeperTile tile = MinesweeperTile.createTile(i, j, GRID_SIZE, this.getApplicationContext());
                tile.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (tilesRevealed == 0) {
                            timeStarted = TimeUtils.getCurrentTime();
                        }

                        if (flagToggle) {
                            tile.flipFlag(context);
                        } else {
                            if (!tile.getFlag()) {
                                if (tile.bomb) {
                                    timePlayed = TimeUtils.getCurrentTime() - timeStarted;
                                    tile.reveal(context.getApplicationContext());
                                    revealAllBombs();
                                    MiniGames.endGame(MinesweeperActivity.this, MiniGames.MINESWEEPER, score, timePlayed);
                                } else {
                                    revealNeighboringTiles(tile.row, tile.column);
                                }
                            }
                        }
                        checkWin();
                        updateScore();
                    }
                });
                grid[i][j] = tile;
                view.addView(tile.view);
            }
        }
    }

    private void checkWin() {
        final int NUM_SAFE_TILES = (GRID_SIZE * GRID_SIZE) - MAX_BOMBS;
        if (tilesRevealed == NUM_SAFE_TILES) {
            this.timePlayed = TimeUtils.getCurrentTime() - timeStarted;
            MiniGames.winGame(MinesweeperActivity.this, MiniGames.MINESWEEPER, score, timePlayed);
        }
    }

    private void updateScore() {
        Context context = this.getApplicationContext();
        TextView scoreText = findViewById(R.id.minesweeper_score_text);
        String format = context.getText(MiniGames.MINESWEEPER.scoreFormatId).toString();
        String text = String.format(format, score);
        scoreText.setText(text);
    }

    private void placeBombs() {
        Random generator = new Random();
        int x, y;
        for (int i = 0; i < this.MAX_BOMBS; i++) {
            do {
                x = generator.nextInt(GRID_SIZE - 1);
                y = generator.nextInt(GRID_SIZE - 1);
            } while (grid[x][y].bomb);
            grid[x][y].setBomb();
            bombs.add(grid[x][y]);
        }
    }

    private void revealNeighboringTiles(int y, int x) {
        boolean xOutOfBounds = x < 0 || x > (GRID_SIZE - 1);
        boolean yOutOfBounds = y < 0 || y > (GRID_SIZE - 1);
        if (yOutOfBounds || xOutOfBounds || grid[y][x].revealed) {
            return;
        } else if (grid[y][x].bomb) {
            return;
        } else {
            int numOfBombs = doNeighborsHaveBombs(y,x);
            boolean bombExists = false;
            if (numOfBombs > 0) {
                bombExists = true;
            }
            boolean revealed = grid[y][x].reveal(this.getApplicationContext());
            if (revealed) {
                setNumOfNearMinesString(numOfBombs);
                grid[y][x].view.setText(buffer);
            }
            if (!bombExists) {
                for (int i = y - 1; i <= y + 1; i++) {
                    for (int j = x - 1; j <= x + 1; j++) {
                        revealNeighboringTiles(i, j);
                    }
                }
            }
        }
    }

    int doNeighborsHaveBombs(int y, int x) {
        boolean southExists = (y + 1) < GRID_SIZE - 1;
        boolean eastExists = (x + 1) < GRID_SIZE - 1;
        boolean northExists = (y - 1) >= 0;
        boolean westExists = (x - 1) >= 0;

        int bombsNear = 0;

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
            if (nBomb) {
                bombsNear++;
            }
        }
        if (eastExists) {
            eBomb = grid[y][x + 1].bomb;
            if (eBomb) {
                bombsNear++;
            }
        }
        if (southExists) {
            sBomb = grid[y + 1][x].bomb;
            if (sBomb) {
                bombsNear++;
            }
        }
        if (westExists) {
            wBomb = grid[y][x - 1].bomb;
            if (wBomb) {
                bombsNear++;
            }
        }

        if (northExists && eastExists) {
            neBomb = grid[y - 1][x + 1].bomb;
            if (neBomb) {
                bombsNear++;
            }
        }
        if (southExists && eastExists) {
            seBomb = grid[y + 1][x + 1].bomb;
            if (seBomb) {
                bombsNear++;
            }
        }
        if (northExists && westExists) {
            nwBomb = grid[y - 1][x - 1].bomb;
            if (nwBomb) {
                bombsNear++;
            }
        }
        if (southExists && westExists) {
            swBomb = grid[y + 1][x - 1].bomb;
            if (swBomb) {
                bombsNear++;
            }
        }

        boolean adj = nBomb || eBomb || sBomb || wBomb;
        boolean diag = neBomb || seBomb || nwBomb || swBomb;

        boolean bombExists = adj || diag;

        return bombsNear;
    }

    private void setNumOfNearMinesString(int number) {
        this.buffer = "";
        if (number > 0) {
            this.buffer += number;
        }
    }

    private void revealAllBombs(){
        Context context = this.getApplicationContext();
        for (int i = 0; i < bombs.size(); i++){
            bombs.get(i).reveal(context);
        }
    }

}

