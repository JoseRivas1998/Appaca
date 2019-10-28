package edu.csuci.appaca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View;
import android.content.Intent;

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.MiniGames;

public class MinigameSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minigame_select);
        getSupportActionBar().hide();

        for (MiniGames miniGame : MiniGames.values())
        {

        }
    }



}

//    terate through minigames enum in mingamesjava.
//
//        fo each minigame, create an image view where i set the drawale to the value's icon id.
//        set the onclick to witch to cactivity clas