package edu.csuci.appaca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.ImageView;

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.MiniGames;
import edu.csuci.appaca.utils.ScreenUtils;

public class MinigameSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GridLayout gridLayout = new GridLayout(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minigame_select);
        getSupportActionBar().hide();
        int size = (int) ScreenUtils.dpToPixels(this, 400);
        int margin = (int) ScreenUtils.dpToPixels(this, 30);


        for (MiniGames miniGame : MiniGames.values()) {
            int id = miniGame.iconId;
            ImageView gameView = new ImageView(this);
            gameView.setImageResource(miniGame.iconId);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = size;
            params.height = size;
            params.setMargins(margin, margin, margin, margin);
            gameView.setLayoutParams(params);

            gridLayout.addView(gameView);
        }


    }


}
