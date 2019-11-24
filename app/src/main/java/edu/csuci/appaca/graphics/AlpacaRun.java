package edu.csuci.appaca.graphics;

import android.app.Activity;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.data.gameres.AlpacaRunResources;
import edu.csuci.appaca.graphics.entities.LabelEntity;

import static edu.csuci.appaca.data.gameres.AlpacaRunResources.*;

public class AlpacaRun extends ApplicationAdapter {

    private final Activity parent;

    private SpriteBatch sb;
    private ShapeRenderer sr;
    private Viewport viewport;

    private LabelEntity tapToStart;

    private boolean started;

    public AlpacaRun(Activity parent) {
        this.parent = parent;
        AlpacaRunResources.load(parent);
    }

    @Override
    public void create() {
        sb = new SpriteBatch();
        sr = new ShapeRenderer();
        viewport = new FitViewport(worldWidth(), worldHeight());

        tapToStart = new LabelEntity();
        tapToStart.setText("Tap to Start!");
        tapToStart.setPosition(worldWidth() * 0.5f, worldHeight() * 0.5f);
        tapToStart.setFont(StaticContentManager.Font.ALPACA_JUMP_START);
        tapToStart.setAlign(LabelEntity.MIDDLE_CENTER);

        started = false;

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(bgColor().r, bgColor().g, bgColor().b, bgColor().a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float dt = Gdx.graphics.getDeltaTime();

        if(started) {

        } else {
            updateStartingState(dt);
            drawStartingState(dt);
        }

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        sb.dispose();
        sr.dispose();
    }

    private void drawStartingState(float dt) {
        sb.begin();
        sb.setProjectionMatrix(viewport.getCamera().combined);
        tapToStart.draw(dt, sb, sr);
        sb.end();
    }

    private void updateStartingState(float dt) {
        if(Gdx.input.justTouched()) started = true;
        tapToStart.update(dt);
    }

}
