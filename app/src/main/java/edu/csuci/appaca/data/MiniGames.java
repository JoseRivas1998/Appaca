package edu.csuci.appaca.data;

import android.app.Activity;
import android.content.Intent;

import edu.csuci.appaca.R;
import edu.csuci.appaca.activities.AlpacaJumpActivity;
import edu.csuci.appaca.activities.FruitCatchActivity;
import edu.csuci.appaca.activities.GameOverActivity;
import edu.csuci.appaca.activities.AlpacaRunActivity;
import edu.csuci.appaca.activities.MinesweeperActivity;
import edu.csuci.appaca.activities.MinesweeperWinActivity;

public enum MiniGames {
    ALPACA_JUMP(R.string.alpaca_jump, AlpacaJumpActivity.class, R.string.alpaca_jump_score_format, R.drawable.alpacajump_icon, R.string.alpaca_jump_high_score_format) {
        @Override
        public int coinsForScore(int score) {
            return score / 100;
        }
    },
    FRUIT_CATCH(R.string.fruit_catch, FruitCatchActivity.class, R.string.fruit_catch_score_format, R.drawable.fruitcatch_icon, R.string.fruit_catch_high_score_format) {
        @Override
        public int coinsForScore(int score) {
            return score / 10;
        }
    },
    ALPACA_RUN(R.string.alpaca_run, AlpacaRunActivity.class, R.string.alpaca_run_score_format, R.drawable.alpaca_run_icon, R.string.alpaca_run_high_score_format) {
        @Override
        public int coinsForScore(int score) {
            return score / 10;
        }
    },
    MINESWEEPER(R.string.minesweeper, MinesweeperActivity.class, R.string.minesweeper_score_format, R.drawable.poosweeper_grid, R.string.minesweeper_high_score_format) {
        @Override
        public int coinsForScore(int score) {
            return score / 100;
        }
    };
    public final int nameId;
    public final Class<? extends Activity> activityClass;
    public final int scoreFormatId;
    public final int iconId;
    public final int highScoreFormatId;

    MiniGames(int nameId, Class<? extends Activity> activityClass, int scoreFormatId, int iconId, int highScoreFormatId) {
        this.nameId = nameId;
        this.activityClass = activityClass;
        this.scoreFormatId = scoreFormatId;
        this.iconId = iconId;
        this.highScoreFormatId = highScoreFormatId;
    }

    public abstract int coinsForScore(int score);

    public static void endGame(Activity parent, MiniGames miniGame, int score, long timePlayed) {
        Intent intent = new Intent(parent, GameOverActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("return", miniGame.ordinal());
        intent.putExtra("timePlayed", (int) timePlayed);
        parent.startActivity(intent);
        parent.finish();
    }

    public static void winGame(Activity parent, MiniGames minigame, int score, long timePlayed) {
        Intent intent = new Intent(parent, MinesweeperWinActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("return", minigame.ordinal());
        intent.putExtra("timePlayed", (int) timePlayed);
        parent.startActivity(intent);
        parent.finish();
    }

}
