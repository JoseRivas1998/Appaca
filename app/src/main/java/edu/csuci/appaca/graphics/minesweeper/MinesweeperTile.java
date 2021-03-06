package edu.csuci.appaca.graphics.minesweeper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.utils.AssetsUtils;
import edu.csuci.appaca.activities.MinesweeperActivity;
import edu.csuci.appaca.utils.ScreenUtils;

public class MinesweeperTile {
    private boolean flagged;
    public int row;
    public int column;
    public boolean bomb;

    public TextView view;
    public boolean revealed;


    public  static MinesweeperTile createTile(int row, int column, int count, final Context context) {
        int size = (int) ScreenUtils.dpToPixels(context, (float)(2100/(count + 5 *(count - 1))));
        int margin = (int) ScreenUtils.dpToPixels(context, 1);

        MinesweeperTile ret = new MinesweeperTile();
        ret.view = new TextView(context);
        ret.view.setBackgroundResource(R.drawable.covered_tile);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = size;
        params.height = size;
        params.setMargins(margin, margin, margin, margin);
        ret.view.setLayoutParams(params);
        ret.view.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        ret.view.setText("");

        ret.row = row;
        ret.column = column;
        ret.flagged = false;
        ret.revealed = false;
        ret.bomb = false; //stub, use random chance to determine if bomb
        return ret;
    }

    public boolean reveal() {
        if(!flagged) {
            if (!revealed) {
                if (this.bomb) {
                    this.view.setBackgroundResource(R.drawable.poop);
                } else {
                    this.view.setBackgroundResource(R.drawable.uncovered_tile);
                    MinesweeperActivity.tilesRevealed++;
                    MinesweeperActivity.score++;
                }
                this.revealed = true;
            }
        }
        return this.revealed;
    }

    private void flag() {
        if (!revealed) {
            this.flagged = true;
            this.view.setBackgroundResource(R.drawable.flag_tile);
        }
    }

    private void unflag() {
        if (!revealed) {
            this.flagged = false;
            this.view.setBackgroundResource(R.drawable.covered_tile);
        }
    }

    public void flipFlag() {
        if (this.flagged) {
            unflag();
        } else {
            flag();
        }
    }

    public boolean getFlag() {
        return this.flagged;
    }

    public void setBomb() { this.bomb = true; }
}
