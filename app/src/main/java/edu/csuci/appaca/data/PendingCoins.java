package edu.csuci.appaca.data;

public class PendingCoins {

    private static int coins = 0;

    public static boolean hasPendingCoins() {
        return coins > 0;
    }

    public static void addCoins(int coins) {
        PendingCoins.coins += coins;
    }

    public static int getCoinsAndEmpty() {
        int coins = PendingCoins.coins;
        PendingCoins.coins = 0;
        return coins;
    }

}
