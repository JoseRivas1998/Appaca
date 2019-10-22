package edu.csuci.appaca.data;

import edu.csuci.appaca.R;

public enum Stat {
    HUNGER(R.drawable.food_stat, R.string.hunger),
    HAPPINESS(R.drawable.happiness_stat, R.string.happiness),
    HYGIENE(R.drawable.hygeine_stat, R.string.hygiene);
    public final int iconId;
    public final int labelId;

    Stat(int iconId, int labelId) {
        this.iconId = iconId;
        this.labelId = labelId;
    }
}
