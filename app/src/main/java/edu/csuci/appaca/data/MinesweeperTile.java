package edu.csuci.appaca.data;

import android.content.Context;
import android.view.View;

import edu.csuci.appaca.R;

public class MinesweeperTile {
    private boolean revealed;
    private boolean flagged;
    private boolean bomb;
    private int tileID;
    private View tile;

    public  static MinesweeperTile createTile(int tileID, Context context)
    {
        MinesweeperTile ret = new MinesweeperTile();
        ret.tile = new View(context);
        ret.tile.setBackgroundColor(context.getResources().getColor(R.color.bluePastel));
        ret.tileID = tileID;
        ret.flagged = false;
        ret.revealed = false;
        ret.bomb = false;
        return ret;
    }
}
