package edu.csuci.appaca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.csuci.appaca.R;

public class MinesweeperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minesweeper);
    }

    public void initButton()
    {
        final Button exitButton = findViewById(R.id.poosweeper_close);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MinesweeperActivity.this, MinigameSelectActivity.class);
                startActivity(intent);
            }
        });

    }
}

