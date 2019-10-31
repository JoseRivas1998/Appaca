package edu.csuci.appaca.data;

import android.app.Activity;

import edu.csuci.appaca.R;
import edu.csuci.appaca.activities.AlpacaJumpActivity;

public enum MiniGames {
    ALPACA_JUMP(R.string.alpaca_jump, AlpacaJumpActivity.class, R.string.alpaca_jump_score_format, R.drawable.alpaca_icon, R.string.alpaca_jump_high_score_format);
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
}
