package edu.csuci.appaca.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;

import java.util.EnumMap;
import java.util.Map;

import edu.csuci.appaca.R;
import edu.csuci.appaca.concurrency.MainScreenBackground;
import edu.csuci.appaca.data.AlpacaFarm;
import edu.csuci.appaca.data.Stat;
import edu.csuci.appaca.fragments.StatBarFragment;
import edu.csuci.appaca.graphics.MainLibGdxView;
import edu.csuci.appaca.utils.ListUtils;

public class MainActivity extends AndroidApplication {

    private Map<Stat, StatBarFragment> statBars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(AlpacaFarm.numberOfAlpacas() == 0) {
            Intent intent = new Intent(this, FirstAlpacaActivity.class);
            startActivity(intent);
            finish();
        }
        initLibGDX();
        initButtons();
        initStatBars();
        MainScreenBackground.start(this);
    }

    private void initStatBars() {
        final FrameLayout stats = findViewById(R.id.stat_bars);
        statBars = new EnumMap<Stat, StatBarFragment>(Stat.class);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setId(View.generateViewId());
        for (Stat stat : Stat.values()) {
            StatBarFragment fragment = StatBarFragment.buildStatBar(stat);
            statBars.put(stat, fragment);
            getFragmentManager()
                    .beginTransaction()
                    .add(ll.getId(), fragment)
                    .commit();
        }
        stats.addView(ll);
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

    public void forEachStatBar(ListUtils.DuelConsumer<Stat, StatBarFragment> consumer) {
        for (Map.Entry<Stat, StatBarFragment> statBarEntry : statBars.entrySet()) {
            consumer.accept(statBarEntry.getKey(), statBarEntry.getValue());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainScreenBackground.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainScreenBackground.start(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainScreenBackground.stop();
    }
}
