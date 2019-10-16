package edu.csuci.appaca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View;
import android.content.Intent;

import edu.csuci.appaca.R;

public class MinigameSelectActivity extends AppCompatActivity {

    private ImageButton returnToMain, matchingButton, poopsweepButton, fruitButton, jumpButton;
    private Button playButton;
    private Class selectedClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minigame_select);

        returnToMain = (ImageButton) findViewById(R.id.exitButton);

        matchingButton = (ImageButton) findViewById(R.id.matchingIcon);
        poopsweepButton = (ImageButton) findViewById(R.id.poopsweeperIcon);
        fruitButton = (ImageButton) findViewById(R.id.fruitCatchIcon);
        jumpButton = (ImageButton) findViewById(R.id.alpacaJumpIcon);
        playButton = (Button) findViewById(R.id.selectButton);

        returnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        matchingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedClass = MatchingActivity.class;
            }
        });

        poopsweepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedClass = MinesweeperActivity.class;
            }
        });

        fruitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedClass = FruitCatchActivity.class;
            }
        });

        jumpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedClass = AlpacaJumpActivity.class;
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedClass != null) {
                    launchActivity(selectedClass);
                    finish();
                }
            }
        });

    }

    private void launchActivity(Class gotoClass) {

        Intent intent = new Intent(this, gotoClass);
        startActivity(intent);
    }

    private void selectIcon(Class gotoClass) {

        Intent intent = new Intent(this, gotoClass);
        startActivity(intent);
    }

}
