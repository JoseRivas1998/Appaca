package edu.csuci.appaca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

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
        initLibGDX();
        initButtons();
    }

    private void initLibGDX() {
        final FrameLayout layout = findViewById(R.id.main_libGDX_view);
        layout.addView(initializeForView(new MainLibGdxView(this)));
    }

    private void initButtons() {
        final ImageView shopBtn = findViewById(R.id.shopBtn);
        final ImageView playBtn = findViewById(R.id.playBtn);
        final ImageView feedBtn = findViewById(R.id.feedBtn);
        final ImageView clothesBtn = findViewById(R.id.clothesBtn);

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

        feedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FoodSelectActivity.class);
                startActivity(intent);
            }
        });

        clothesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ClothingSelectActivity.class);
                startActivity(intent);
            }
        });



    }

}
