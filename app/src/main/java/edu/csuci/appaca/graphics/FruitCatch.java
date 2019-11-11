package edu.csuci.appaca.graphics;

import android.app.Activity;
import android.content.Context;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.csuci.appaca.data.HighScore;
import edu.csuci.appaca.data.MiniGames;
import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.data.gameres.FruitCatchResources;
import edu.csuci.appaca.graphics.entities.LabelEntity;
import edu.csuci.appaca.graphics.entities.fruitcatch.FCHUD;
import edu.csuci.appaca.graphics.entities.fruitcatch.FruitEntity;
import edu.csuci.appaca.utils.ActionTimer;

import static edu.csuci.appaca.data.gameres.FruitCatchResources.maxMisses;

public class FruitCatch extends ApplicationAdapter {

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private Viewport viewport;

    private boolean started;
    private LabelEntity tapToStart;

    private ActionTimer fruitSpawnTimer;

    private List<FruitEntity> fruitEntities;

    private int score;
    private int highScore;
    private FCHUD hud;

    private int misses;
    private Activity parent;

    public FruitCatch(Activity parent) {
        super();
        FruitCatchResources.load(parent);
        this.parent = parent;
    }

    @Override
    public void create() {
        StaticContentManager.load();
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        viewport = new FitViewport(FruitCatchResources.worldWidth(), FruitCatchResources.worldHeight());

        started = false;

        tapToStart = new LabelEntity();
        tapToStart.setFont(StaticContentManager.Font.ALPACA_JUMP_START);
        tapToStart.setText("Tap to Start!");
        tapToStart.setAlign(LabelEntity.MIDDLE_CENTER);

        fruitSpawnTimer = new ActionTimer(nextFruitSpawnTime(), ActionTimer.ActionTimerMode.RUN_CONTINUOUSLY);
        fruitSpawnTimer.setActionTimerEvent(new ActionTimer.ActionTimerEvent() {
            @Override
            public void action() {
                spawnFruit();
            }
        });
        fruitEntities = new ArrayList<>();

        score = 0;
        highScore = HighScore.getHighScore(MiniGames.FRUIT_CATCH);
        hud = new FCHUD();

    }

    private float nextFruitSpawnTime() {
        return MathUtils.random(FruitCatchResources.minSpawnTime(), FruitCatchResources.maxSpawnTime());
    }

    private void spawnFruit() {
        fruitSpawnTimer.setTimer(nextFruitSpawnTime());
        fruitEntities.add(new FruitEntity());
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(
                FruitCatchResources.bgColor().r,
                FruitCatchResources.bgColor().g,
                FruitCatchResources.bgColor().b,
                FruitCatchResources.bgColor().a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float dt = Gdx.graphics.getDeltaTime();

        if (started) {
            updatePlaying(dt);
        } else {
            updateStartingState(dt);
        }

        viewport.apply(true);

        draw(dt);

    }

    private void updatePlaying(float dt) {
        fruitSpawnTimer.update(dt);
        updateFruit(dt);
        hud.update(dt, score, highScore, misses);
    }

    private void updateFruit(float dt) {
        Iterator<FruitEntity> fruitIter = fruitEntities.iterator();
        while(fruitIter.hasNext()) {
            FruitEntity fruit = fruitIter.next();
            fruit.update(dt);
            if(fruit.isTouched(viewport)) {
                fruit.dispose();
                fruitIter.remove();
                score++;
            } else if(fruit.getY() + fruit.getHeight() < 0 && fruit.getVelocityY() < 0) {
                fruit.dispose();
                fruitIter.remove();
                if(++misses >= maxMisses()) {
                    MiniGames.endGame(parent, MiniGames.FRUIT_CATCH, score);
                }
            }
        }
    }

    private void updateStartingState(float dt) {
        if (Gdx.input.justTouched()) {
            started = true;
        }
        tapToStart.setPosition(FruitCatchResources.worldWidth() * 0.5f, FruitCatchResources.worldHeight() * 0.5f);
        tapToStart.update(dt);
    }

    private void draw(float dt) {
        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        if (!started) {
            tapToStart.draw(dt, spriteBatch, shapeRenderer);
        }

        for (FruitEntity fruit : fruitEntities) {
            fruit.draw(dt, spriteBatch, shapeRenderer);
        }

        if(started) {
            hud.draw(dt, spriteBatch, shapeRenderer);
        }

        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        StaticContentManager.dispose();
        spriteBatch.dispose();
        shapeRenderer.dispose();
        disposeAllFruitEntities();
    }

    private void disposeAllFruitEntities() {
        Iterator<FruitEntity> fruitIter = fruitEntities.iterator();
        while (fruitIter.hasNext()) {
            FruitEntity fruit = fruitIter.next();
            fruit.dispose();
            fruitIter.remove();
        }
    }
}
