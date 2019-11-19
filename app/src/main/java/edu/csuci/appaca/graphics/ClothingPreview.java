package edu.csuci.appaca.graphics;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.csuci.appaca.data.AlpacaFarm;
import edu.csuci.appaca.data.gameres.ClothingPreviewResources;
import edu.csuci.appaca.graphics.entities.mainscreen.ClothingEntity;

import edu.csuci.appaca.graphics.entities.mainscreen.AlpacaEntity;
import edu.csuci.appaca.graphics.entities.mainscreen.PetDetector;

public class ClothingPreview extends ApplicationAdapter {

    private SpriteBatch spriteBatch;
    private Texture texture;

    private Viewport viewport;

    private ClothingEntity clothingEntity;
    private AlpacaEntity alpacaEntity;
    private PetDetector petDetector;

    private ShapeRenderer shapeRenderer;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        texture = new Texture(AlpacaFarm.getCurrentAlpaca().getPath());
        viewport = new FitViewport(ClothingPreviewResources.worldWidth(), ClothingPreviewResources.worldHeight());

        shapeRenderer = new ShapeRenderer();

        clothingEntity = new ClothingEntity();
        alpacaEntity = new AlpacaEntity(ClothingPreviewResources.worldWidth(), ClothingPreviewResources.worldHeight());

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


        float dt = Gdx.graphics.getDeltaTime();

        updateClothingEntity(dt);

        draw(dt);

    }

    private void draw(float dt) {
        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        //spriteBatch.draw(texture, 0, 0);
        alpacaEntity.draw(dt, spriteBatch, shapeRenderer);
        clothingEntity.draw(dt, spriteBatch, shapeRenderer);
        spriteBatch.end();
    }

    private void updateClothingEntity(float dt) {
        clothingEntity.update(dt);
        if (clothingEntity.shouldChangeTexture()) {
            clothingEntity.updateClothesTexture(alpacaEntity);
        }

        viewport.apply(true);
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
