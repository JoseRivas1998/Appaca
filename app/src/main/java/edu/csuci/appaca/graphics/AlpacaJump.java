package edu.csuci.appaca.graphics;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AlpacaJump extends ApplicationAdapter {

    private SpriteBatch spriteBatch;
    private Texture texture;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        texture = new Texture("fatalpaca.png");
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.draw(texture, 0, 0);
        spriteBatch.end();

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        texture.dispose();
    }
}
