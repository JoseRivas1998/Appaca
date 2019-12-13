package edu.csuci.appaca.activities;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;

import edu.csuci.appaca.graphics.AlpacaRun;

public class AlpacaRunActivity extends AndroidApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(new AlpacaRun(this));
    }
}




