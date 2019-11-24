package edu.csuci.appaca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.badlogic.gdx.backends.android.AndroidApplication;

import edu.csuci.appaca.R;
import edu.csuci.appaca.graphics.AlpacaRun;

public class AlpacaRunActivity extends AndroidApplication {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(new AlpacaRun(this));
    }
}




