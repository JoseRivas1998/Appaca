package edu.csuci.appaca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.MiniGames;
import edu.csuci.appaca.utils.ScreenUtils;

public class MinigameSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minigame_select);
        GridLayout gridLayout = findViewById(R.id.game_select_grid);
        getSupportActionBar().hide();
        int size = (int) ScreenUtils.dpToPixels(this, 200);
        int margin = (int) ScreenUtils.dpToPixels(this, 30);

        for (final MiniGames miniGame : MiniGames.values()) {
            ImageView gameView = new ImageView(this);
            gameView.setImageResource(miniGame.iconId);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = size;
            params.height = size;
            params.setMargins(margin, margin, margin, margin);
            gameView.setLayoutParams(params);
            gameView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MinigameSelectActivity.this, miniGame.activityClass);
                    startActivity(intent);
                    finish();
                }
            });

            gridLayout.addView(gameView);
        }


    }


}
