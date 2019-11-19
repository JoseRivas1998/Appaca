package edu.csuci.appaca.graphics;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.csuci.appaca.data.gameres.ClothingPreviewResources;

public class ClothingPreview extends ApplicationAdapter {

    private SpriteBatch spriteBatch;
    private Texture texture;

    private Viewport viewport;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        texture = new Texture("fatalpaca.png");
        viewport = new FitViewport(ClothingPreviewResources.worldWidth(), ClothingPreviewResources.worldHeight());
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(
                ClothingPreviewResources.bgColor().r,
                ClothingPreviewResources.bgColor().g,
                ClothingPreviewResources.bgColor().b,
                ClothingPreviewResources.bgColor().a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply(true);

        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.draw(texture, 0, 0);
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
