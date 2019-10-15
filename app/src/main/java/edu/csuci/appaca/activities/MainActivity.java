package edu.csuci.appaca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.graphics.GL20;

import edu.csuci.appaca.R;
import edu.csuci.appaca.graphics.MainLibGdxView;

public class MainActivity extends AndroidApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FrameLayout layout = findViewById(R.id.main_libGDX_view);
        layout.addView(initializeForView(new MainLibGdxView()));
        initButtons();
    }

    private void initButtons() {
        final Button shopBtn = findViewById(R.id.shopBtn);
        final Button playBtn = findViewById(R.id.playBtn);

        shopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShopActivity.class);
                startActivity(intent);
            }
        });

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MinigameSelectActivity.class);
                startActivity(intent);
            }
        });

    }

}
