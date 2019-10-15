package edu.csuci.appaca.graphics;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainLibGdxView extends ApplicationAdapter {

    SpriteBatch spriteBatch;
    Texture texture;

    @Override
    public void create() {
        Gdx.app.log("MainLibGdxView", "create");
        spriteBatch = new SpriteBatch();
        texture = new Texture("badlogic.jpg");
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.draw(texture, 0, 0);
        spriteBatch.end();

    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("MainLibGdxView", "resize " + String.format("(%d, %d)", width, height));
    }

    @Override
    public void pause() {
        Gdx.app.log("MainLibGdxView", "pause");
    }

    @Override
    public void resume() {
        Gdx.app.log("MainLibGdxView", "resume");
    }

    @Override
    public void dispose() {
        Gdx.app.log("MainLibGdxView", "dispose");
    }
}
