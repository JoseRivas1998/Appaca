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
                            if (tile.bomb){
                                tile.reveal(context.getApplicationContext());
                                MiniGames.endGame(MinesweeperActivity.this, MiniGames.MINESWEEPER, score, timePlayed);
                            } else {
                                revealNeighboringTiles(tile.row, tile.column);
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
        boolean xOutOfBounds = x < 0 || x > (GRID_SIZE - 1);
        boolean yOutOfBounds = y < 0 || y > (GRID_SIZE - 1);
        if (yOutOfBounds || xOutOfBounds || grid[y][x].revealed){
            return;
        } else if (grid[y][x].bomb) {
            return;
        }
        else {
            grid[y][x].reveal(this.getApplicationContext());
            for (int i = y-1; i <= y+1; i++){
                for(int j = x-1; j <= x+1; j++) {
                    revealNeighboringTiles(i, j);
                }
            }
        }
    }

    boolean doNeighborsHaveBombs(){
        return false;
    }


}

