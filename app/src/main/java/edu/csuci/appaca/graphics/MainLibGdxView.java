package edu.csuci.appaca.graphics;

import android.content.Context;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.csuci.appaca.R;
import edu.csuci.appaca.graphics.entities.mainscreen.AlpacaEntity;

public class MainLibGdxView extends ApplicationAdapter {

    private final Context parent;

    private final int VIEWPORT_WIDTH;
    private final int VIEW_HEIGHT;

    private SpriteBatch spriteBatch;

    private Viewport viewport;

    private AlpacaEntity alpaca;

    public MainLibGdxView(Context parent) {
        this.parent = parent;
        VIEWPORT_WIDTH = parent.getResources().getInteger(R.integer.main_view_libgdx_width);
        VIEW_HEIGHT = parent.getResources().getInteger(R.integer.main_view_libgdx_height);
    }

    @Override
    public void create() {
        viewport = new StretchViewport(VIEWPORT_WIDTH, VIEW_HEIGHT);
        spriteBatch = new SpriteBatch();
        alpaca = new AlpacaEntity(VIEWPORT_WIDTH, VIEW_HEIGHT);
    }

    @Override
    public void render() {
        //2196F3
        Gdx.gl.glClearColor(0x21 / 255f, 0x96 / 255f, 0xf3 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float dt = Gdx.graphics.getDeltaTime();

        viewport.apply(true);
        draw(dt);

    }

    private void draw(float dt) {
        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        alpaca.draw(dt, spriteBatch, null);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("MainLibGdxView", String.format("%d, %d", width, height));
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }
}
