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

public class MainLibGdxView extends ApplicationAdapter {

    private final Context parent;

    private final int VIEWPORT_WIDTH;
    private final int VIEW_HEIGHT;

    private SpriteBatch spriteBatch;
    private Texture texture;
    private Rectangle alpacaBounds;
    private Vector2 alpacaVel;

    private Viewport viewport;

    public MainLibGdxView(Context parent) {
        this.parent = parent;
        VIEWPORT_WIDTH = parent.getResources().getInteger(R.integer.main_view_libgdx_width);
        VIEW_HEIGHT = parent.getResources().getInteger(R.integer.main_view_libgdx_height);
    }

    @Override
    public void create() {
        viewport = new StretchViewport(VIEWPORT_WIDTH, VIEW_HEIGHT);
        spriteBatch = new SpriteBatch();
        texture = new Texture("fatalpaca.png");
        alpacaBounds = new Rectangle();
        alpacaBounds.setSize(texture.getWidth(), texture.getHeight());
        alpacaBounds.setCenter(VIEWPORT_WIDTH * 0.5f, VIEW_HEIGHT * 0.5f);
        float speed = 750;
        float angle = MathUtils.random(MathUtils.PI2);
        alpacaVel = new Vector2(speed * MathUtils.cos(angle), speed * MathUtils.sin(angle));
    }

    @Override
    public void render() {
        //2196F3
        Gdx.gl.glClearColor(0x21 / 255f, 0x96 / 255f, 0xf3 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float dt = Gdx.graphics.getDeltaTime();

        alpacaBounds.x += alpacaVel.x * dt;
        alpacaBounds.y += alpacaVel.y * dt;

        if(alpacaBounds.x < 0) {
            alpacaBounds.x = 0;
            alpacaVel.x *= -1;
        }
        if(alpacaBounds.y < 0) {
            alpacaBounds.y = 0;
            alpacaVel.y *= -1;
        }
        if(alpacaBounds.x + alpacaBounds.width > VIEWPORT_WIDTH) {
            alpacaBounds.x = VIEWPORT_WIDTH - alpacaBounds.width;
            alpacaVel.x *= -1;
        }
        if(alpacaBounds.y + alpacaBounds.height > VIEW_HEIGHT) {
            alpacaBounds.y = VIEW_HEIGHT - alpacaBounds.height;
            alpacaVel.y *= -1;
        }

        viewport.apply(true);
        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.draw(texture, alpacaBounds.x, alpacaBounds.y);
        spriteBatch.end();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        texture.dispose();
    }
}
