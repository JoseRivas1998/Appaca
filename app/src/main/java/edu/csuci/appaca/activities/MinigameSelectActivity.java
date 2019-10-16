package edu.csuci.appaca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
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
                if (selectedClass == MatchingActivity.class) {
                    matchingButton.setBackgroundResource(R.drawable.unselect_button);
                    selectedClass = null;
                } else {
                    if (selectedClass != null) {
                        unselectIcon(selectedClass);
                    }
                    selectedClass = MatchingActivity.class;
                    matchingButton.setBackgroundResource(R.drawable.select_button);
                }
            }
        });

        poopsweepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedClass == MinesweeperActivity.class) {
                    poopsweepButton.setBackgroundResource(R.drawable.unselect_button);
                    selectedClass = null;
                } else {
                    if (selectedClass != null) {
                        unselectIcon(selectedClass);
                    }
                    selectedClass = MinesweeperActivity.class;
                    poopsweepButton.setBackgroundResource(R.drawable.select_button);
                }
            }
        });

        fruitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedClass == FruitCatchActivity.class) {
                    fruitButton.setBackgroundResource(R.drawable.unselect_button);
                    selectedClass = null;
                } else {
                    if (selectedClass != null) {
                        unselectIcon(selectedClass);
                    }
                    selectedClass = FruitCatchActivity.class;
                    fruitButton.setBackgroundResource(R.drawable.select_button);
                }
            }
        });

        jumpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedClass == AlpacaJumpActivity.class) {
                    jumpButton.setBackgroundResource(R.drawable.unselect_button);
                    selectedClass = null;
                } else {
                    if (selectedClass != null) {
                        unselectIcon(selectedClass);
                    }
                    selectedClass = AlpacaJumpActivity.class;
                    jumpButton.setBackgroundResource(R.drawable.select_button);
                }
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

    private void unselectIcon(Class unselectClass) {
        System.out.println("unselectClass is " + unselectClass.getName());
        switch (unselectClass.getName()) {
            case "edu.csuci.appaca.activities.AlpacaJumpActivity":
                jumpButton.setBackgroundResource(R.drawable.unselect_button);
                break;
            case "edu.csuci.appaca.activities.MatchingActivity":
                matchingButton.setBackgroundResource(R.drawable.unselect_button);
                break;
            case "edu.csuci.appaca.activities.MinesweeperActivity":
                poopsweepButton.setBackgroundResource(R.drawable.unselect_button);
                break;
            case "edu.csuci.appaca.activities.FruitCatchActivity":
                fruitButton.setBackgroundResource(R.drawable.unselect_button);
                break;
            default:
                break;
        }
    }

}
