package edu.csuci.appaca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import edu.csuci.appaca.R;

public class MinesweeperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minesweeper);
        initButton();
    }

    public void initButton()
    {
        final ImageView exitButton = findViewById(R.id.poosweeper_close);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MinesweeperActivity.this, MinigameSelectActivity.class);
                startActivity(intent);
            }
        });

        final ImageView poop = findViewById(R.id.imageView2);
        poop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MinesweeperActivity.this, GameOverActivity.class);
                startActivity(intent);
            }
        });

    }
}

