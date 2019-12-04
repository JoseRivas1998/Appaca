package edu.csuci.appaca.graphics.minesweeper;

import android.content.Context;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import edu.csuci.appaca.R;
import edu.csuci.appaca.activities.MinesweeperActivity;
import edu.csuci.appaca.utils.ScreenUtils;

public class MinesweeperTile {
    private boolean revealed;
    private boolean flagged;
    public int row;
    public int column;
    public boolean bomb;
    public TextView view;

    public  static MinesweeperTile createTile(int row, int column, int count, final Context context) {
        int size = (int) ScreenUtils.dpToPixels(context, (float)(2100/(count + 5 *(count - 1))));
        int margin = (int) ScreenUtils.dpToPixels(context, 1);

        MinesweeperTile ret = new MinesweeperTile();
        ret.view = new TextView(context);
        ret.view.setBackgroundColor(context.getColor(R.color.bluePastel));
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = size;
        params.height = size;
        params.setMargins(margin, margin, margin, margin);
        ret.view.setLayoutParams(params);
        ret.view.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        ret.row = row;
        ret.column = column;
        ret.flagged = false;
        ret.revealed = false;
        ret.bomb = false; //stub, use random chance to determine if bomb
        return ret;
    }

    public void reveal(Context context) {
        if (!revealed) {
            if (this.bomb) {
                this.view.setBackgroundColor(context.getColor(R.color.pinkPastel));
            } else {
                this.view.setBackgroundColor(context.getColor(R.color.greenPastel));
                MinesweeperActivity.tilesRevealed++;
                MinesweeperActivity.score++;
            }
            this.revealed = true;
        }
    }

    public void flag() {
        this.flagged = true;
    }

    public void unflag() {
        this.flagged = false;
    }

    public void setBomb() { this.bomb = true; }
}
