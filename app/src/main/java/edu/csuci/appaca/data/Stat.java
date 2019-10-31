package edu.csuci.appaca.data;

import edu.csuci.appaca.R;

public enum Stat {
    HUNGER(R.drawable.hunger_icon, R.string.hunger),
    HAPPINESS(R.drawable.happiness_icon, R.string.happiness),
    HYGIENE(R.drawable.hygeine_icon, R.string.hygiene);
    public final int iconId;
    public final int labelId;

    Stat(int iconId, int labelId) {
        this.iconId = iconId;
        this.labelId = labelId;
    }
}
