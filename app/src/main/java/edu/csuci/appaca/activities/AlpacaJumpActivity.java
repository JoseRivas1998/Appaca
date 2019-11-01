package edu.csuci.appaca.activities;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;

import edu.csuci.appaca.graphics.AlpacaJump;

public class AlpacaJumpActivity extends AndroidApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(new AlpacaJump(this));
    }

}
