package edu.csuci.appaca.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import edu.csuci.appaca.R;
import edu.csuci.appaca.concurrency.StaminaRecovery;
import edu.csuci.appaca.data.MiniGames;
import edu.csuci.appaca.data.SaveDataUtils;
import edu.csuci.appaca.data.StaminaManager;
import edu.csuci.appaca.utils.ScreenUtils;
import edu.csuci.appaca.fragments.EmptyStaminaFragment;

public class MinigameSelectActivity extends AppCompatActivity {

    TextView staminaCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minigame_select);
        GridLayout gridLayout = findViewById(R.id.game_select_grid);
        getSupportActionBar().hide();
        StaminaRecovery.start(this);

        initStaminaCount(gridLayout);

        loadMinigames(gridLayout);


    }

    private void initStaminaCount(GridLayout gridLayout) {
        staminaCount = findViewById(R.id.minigame_select_stamina_text);
        String staminaStart = "Stamina: " + StaminaManager.getCurrentStamina();
        setStaminaMessage(staminaStart);
    }

    private void loadMinigames(GridLayout gridLayout) {
        int size = (int) ScreenUtils.dpToPixels(this, 150);
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
                    playGame(miniGame);
                }
            });

            gridLayout.addView(gameView);
        }
    }

    private void playGame(MiniGames miniGame) {
        if (StaminaManager.getCurrentStamina() > 0) {
            Intent intent = new Intent(MinigameSelectActivity.this, miniGame.activityClass);
            StaminaManager.decreaseCurrentStamina();
            SaveDataUtils.updateValuesAndSave(MinigameSelectActivity.this);
            startActivity(intent);
            finish();
        } else {
            FragmentManager fm = getSupportFragmentManager();
            EmptyStaminaFragment emptyStamina = new EmptyStaminaFragment();
            emptyStamina.show(fm, "no_remaining_stamina");
        }
    }

    public void setStaminaMessage(String message) {
        staminaCount.setText(message);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StaminaRecovery.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StaminaRecovery.start(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        StaminaRecovery.stop();
    }

}
