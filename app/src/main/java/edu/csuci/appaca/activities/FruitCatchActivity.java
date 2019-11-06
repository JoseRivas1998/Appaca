package edu.csuci.appaca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.badlogic.gdx.backends.android.AndroidApplication;

import edu.csuci.appaca.R;
import edu.csuci.appaca.graphics.FruitCatch;

public class FruitCatchActivity extends AndroidApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FruitCatch fruitCatch = new FruitCatch(this);
        initialize(fruitCatch);
    }
}
