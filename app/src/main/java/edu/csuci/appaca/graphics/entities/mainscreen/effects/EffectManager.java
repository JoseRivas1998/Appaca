package edu.csuci.appaca.graphics.entities.mainscreen.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.List;

import edu.csuci.appaca.data.Alpaca;
import edu.csuci.appaca.data.AlpacaFarm;
import edu.csuci.appaca.data.FoodDepletion;
import edu.csuci.appaca.data.HappinessCalc;
import edu.csuci.appaca.data.HygieneDepletion;
import edu.csuci.appaca.data.SavedTime;
import edu.csuci.appaca.graphics.entities.mainscreen.AlpacaEntity;
import edu.csuci.appaca.utils.MathFunctions;

public class EffectManager implements Disposable {

    private final float EFFECT_THRESHOLD = 0.1f;

    private final int viewportWidth;
    private final int viewportHeight;

    private enum EffectActive {
        RAINBOW,
        RAINY_DAY,
        DIRTY_BOI;
        boolean active;

        EffectActive() {
            this.active = false;
        }
    }

    private List<EffectActive> activeEffects;
    private RainbowEffectEntity rainbow;
    private RainyDay rainyDay;
    private DirtyBoi dirtyBoi;

    public EffectManager(int viewportWidth, int viewportHeight) {
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
        this.rainbow = new RainbowEffectEntity();
        this.rainbow.setCenter(viewportWidth * 0.5f, viewportHeight * 0.5f);
        this.activeEffects = new ArrayList<>();
        this.rainyDay = new RainyDay();
        this.dirtyBoi = new DirtyBoi();
    }

    public void update(float dt, AlpacaEntity alpaca) {
        updateEffectStatus();
        for (EffectActive effect : activeEffects) {
            updateEffect(effect, dt, alpaca);
        }
    }

    public void draw(float dt, AlpacaEntity alpaca, Viewport viewport, SpriteBatch sb, ShapeRenderer sr) {
        for (EffectActive effect : activeEffects) {
            drawEffect(effect, dt, alpaca, viewport, sb, sr);
        }
    }

    @Override
    public void dispose() {
        rainyDay.dispose();
        dirtyBoi.dispose();
    }

    private void updateEffectStatus() {
        Alpaca currentAlpaca = AlpacaFarm.getCurrentAlpaca();
        long previousTime = SavedTime.lastSavedTime();
        double currentFood = Math.max(Alpaca.MIN_STAT, FoodDepletion.foodDepletion(currentAlpaca, previousTime));
        double currentHappiness = Math.max(Alpaca.MIN_STAT, HappinessCalc.calcHappiness(currentAlpaca, previousTime));
        double currentHygiene = Math.max(Alpaca.MIN_STAT, HygieneDepletion.hygieneDepletion(currentAlpaca, previousTime));

        float foodPercent = (float) MathFunctions.map(currentFood, Alpaca.MIN_STAT, Alpaca.MAX_STAT, 0.0, 1.0);
        float happinessPercent = (float) MathFunctions.map(currentHappiness, Alpaca.MIN_STAT, Alpaca.MAX_STAT, 0.0, 1.0);
        float hygienePercent = (float) MathFunctions.map(currentHygiene, Alpaca.MIN_STAT, Alpaca.MAX_STAT, 0.0, 1.0);

        EffectActive.RAINBOW.active = Float.compare(1f - happinessPercent, EFFECT_THRESHOLD) <= 0;
        EffectActive.RAINY_DAY.active = Float.compare(happinessPercent, EFFECT_THRESHOLD) <= 0;

        EffectActive.DIRTY_BOI.active = Float.compare(hygienePercent, EFFECT_THRESHOLD) <= 0;

        setActiveEffectList();

    }

    private void updateEffect(EffectActive effect, float dt, AlpacaEntity alpaca) {
        switch (effect) {
            case RAINBOW:
                updateRainbow(dt, alpaca);
                break;
            case RAINY_DAY:
                rainyDay.update(dt, alpaca);
            case DIRTY_BOI:
                dirtyBoi.update(dt, alpaca);
                break;
        }
    }

    private void drawEffect(EffectActive effect, float dt, AlpacaEntity alpaca, Viewport viewport, SpriteBatch sb, ShapeRenderer sr) {
        switch (effect) {
            case RAINBOW:
                drawRainbow(dt, alpaca, viewport, sb, sr);
                break;
            case RAINY_DAY:
                rainyDay.draw(dt, alpaca, viewport, sb, sr);
                break;
            case DIRTY_BOI:
                dirtyBoi.draw(dt, alpaca, viewport, sb, sr);
                break;
        }
    }

    private void updateRainbow(float dt, AlpacaEntity alpaca) {
        rainbow.setCenter(viewportWidth * 0.5f, alpaca.getCenterY() + rainbow.getHeight() * 0.25f);
        rainbow.update(dt);
    }

    private void drawRainbow(float dt, AlpacaEntity alpaca, Viewport viewport, SpriteBatch sb, ShapeRenderer sr) {
        sb.begin();
        sb.setProjectionMatrix(viewport.getCamera().combined);
        rainbow.draw(dt, sb, sr);
        sb.end();
    }

    private void setActiveEffectList() {
        this.activeEffects.clear();
        for (EffectActive value : EffectActive.values()) {
            if(value.active) this.activeEffects.add(value);
        }
    }

}
