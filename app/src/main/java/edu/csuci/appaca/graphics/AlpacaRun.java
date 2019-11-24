package edu.csuci.appaca.graphics;

import android.app.Activity;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AlpacaRun extends ApplicationAdapter {

    private Texture texture;
    private SpriteBatch sb;

    private final Activity parent;

    public AlpacaRun(Activity parent) {
        this.parent = parent;
    }

    @Override
    public void create() {
        sb = new SpriteBatch();
        texture = new Texture("fatalpaca.png");
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.begin();
        sb.draw(texture, 0, 0);
        sb.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {
        texture.dispose();
        sb.dispose();
    }
}
