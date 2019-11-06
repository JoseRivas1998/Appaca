package edu.csuci.appaca.graphics;

import android.content.Context;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.data.gameres.FruitCatchResources;
import edu.csuci.appaca.graphics.entities.LabelEntity;

public class FruitCatch extends ApplicationAdapter {

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private Viewport viewport;

    private boolean started;
    private LabelEntity tapToStart;

    public FruitCatch(Context parent) {
        super();
        FruitCatchResources.load(parent);
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

        if(started) {
            updatePlaying(dt);
        } else {
            updateStartingState(dt);
        }

        viewport.apply(true);

        draw(dt);

    }

    private void updatePlaying(float dt) {
        // TODO this is a stub
    }

    private void updateStartingState(float dt) {
        if(Gdx.input.justTouched()) {
            started = true;
        }
        tapToStart.setPosition(FruitCatchResources.worldWidth() * 0.5f, FruitCatchResources.worldHeight() * 0.5f);
        tapToStart.update(dt);
    }

    private void draw(float dt) {
        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        if(!started) {
            tapToStart.draw(dt, spriteBatch, shapeRenderer);
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
    }
}
