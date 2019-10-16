package edu.csuci.appaca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import edu.csuci.appaca.R;

public class MatchingActivity extends AppCompatActivity {

    private ImageButton returnToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);
        getSupportActionBar().setTitle("Matching Game");

        returnToMain = (ImageButton) findViewById(R.id.exitButton);

        returnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final ImageView imageView22 = findViewById(R.id.imageView22);
        imageView22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatchingActivity.this, GameOverActivity.class);
                startActivity(intent);
            }
        });

    }
}



