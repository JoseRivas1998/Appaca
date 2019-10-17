package edu.csuci.appaca.data;

import java.util.ArrayList;
import java.util.List;

import edu.csuci.appaca.utils.ListUtils;

public class AlpacaFarm {

    private enum FarmInstance {
        INSTANCE;
        int currentAlpaca;
        List<Alpaca> alpacas;

        FarmInstance() {
            this.currentAlpaca = 0;
            alpacas = new ArrayList<>();
        }

    }

    public static int getMaxID() {
        Alpaca maxAlpaca = ListUtils.getMax(FarmInstance.INSTANCE.alpacas, new ListUtils.MapToInt<Alpaca>() {
            @Override
            public int toInt(Alpaca value) {
                return value.id();
            }
        });
        return maxAlpaca.id();
    }

}
