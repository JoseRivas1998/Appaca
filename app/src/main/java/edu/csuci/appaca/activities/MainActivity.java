package edu.csuci.appaca.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.badlogic.gdx.backends.android.AndroidApplication;

import java.util.EnumMap;
import java.util.Map;

import edu.csuci.appaca.R;
import edu.csuci.appaca.concurrency.MainScreenBackground;
import edu.csuci.appaca.data.AlpacaFarm;
import edu.csuci.appaca.data.CurrencyManager;
import edu.csuci.appaca.data.Stat;
import edu.csuci.appaca.fragments.CurrencyDisplayFragment;
import edu.csuci.appaca.fragments.StatBarFragment;
import edu.csuci.appaca.graphics.MainLibGdxView;
import edu.csuci.appaca.notifications.NotificationChecker;
import edu.csuci.appaca.utils.ListUtils;

public class MainActivity extends AndroidApplication {

    private Map<Stat, StatBarFragment> statBars;
    private CurrencyDisplayFragment currencyAlpacaDisplay;
    private CurrencyDisplayFragment currencyOtherDisplay;
    private MainLibGdxView libGDXView;
    private ImageView shopBtn;
    private ImageView playBtn;
    private ImageView feedBtn;
    private ImageView clothesBtn;
    private ImageView shearBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(AlpacaFarm.numberOfAlpacas() == 0) {
            Intent intent = new Intent(this, FirstAlpacaActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        initLibGDX();
        initButtons();
        initStatBars();
        initCurrencyDisplays();
        updateName();
        MainScreenBackground.start(this);
        NotificationChecker checker = NotificationChecker.getInstance(this.getApplicationContext());
        checker.start();
    }

    private void initCurrencyDisplays() {
        currencyAlpacaDisplay = (CurrencyDisplayFragment) getFragmentManager().findFragmentById(R.id.main_view_currency_alpacas);
        currencyAlpacaDisplay.setIconResource(R.drawable.currency_alpacas);
        currencyOtherDisplay = (CurrencyDisplayFragment) getFragmentManager().findFragmentById(R.id.main_view_currency_other);
        currencyOtherDisplay.setIconResource(R.drawable.currency_other);
        updateCurrencyValues(CurrencyManager.getCurrencyAlpaca(), CurrencyManager.getCurrencyOther());
    }

    public void updateCurrencyValues(int currencyAlpaca, int currencyOther) {
        currencyAlpacaDisplay.setCurrencyValue(currencyAlpaca);
        currencyOtherDisplay.setCurrencyValue(currencyOther);
    }

    public int getDisplayedCurrencyAlpaca() {
        return currencyAlpacaDisplay.getCurrencyValue();
    }

    public int getDisplayedCurrencyOther() {
        return currencyOtherDisplay.getCurrencyValue();
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
        libGDXView = new MainLibGdxView(this);
        layout.addView(initializeForView(libGDXView));
    }

    private void initButtons() {
        shopBtn = findViewById(R.id.shopBtn);
        playBtn = findViewById(R.id.playBtn);
        feedBtn = findViewById(R.id.feedBtn);
        clothesBtn = findViewById(R.id.clothesBtn);
        shearBtn = findViewById(R.id.shearBtn);

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

        shearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                libGDXView.shear();
            }
        });


    }

    public void forEachStatBar(ListUtils.DuelConsumer<Stat, StatBarFragment> consumer) {
        for (Map.Entry<Stat, StatBarFragment> statBarEntry : statBars.entrySet()) {
            consumer.accept(statBarEntry.getKey(), statBarEntry.getValue());
        }
    }

    private void updateName() {
        String name = AlpacaFarm.getCurrentAlpaca().getName();
        TextView view = findViewById(R.id.main_alpaca_name_view);
        view.setText(name);
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
