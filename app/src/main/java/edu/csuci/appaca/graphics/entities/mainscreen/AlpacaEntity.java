package edu.csuci.appaca.graphics.entities.mainscreen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

import edu.csuci.appaca.data.Alpaca;
import edu.csuci.appaca.data.AlpacaFarm;
import edu.csuci.appaca.graphics.entities.AbstractSpriteEntity;

public class AlpacaEntity extends AbstractSpriteEntity implements Disposable {

    private Texture currentTexture;

    public AlpacaEntity(int worldWidth, int worldHeight) {
        super();
        updateCurrentTexture(worldWidth, worldHeight);
    }

    public void updateCurrentTexture(int worldWidth, int worldHeight) {
        if(currentTexture != null) currentTexture.dispose();
        Alpaca alpaca = AlpacaFarm.getCurrentAlpaca();
        currentTexture = new Texture(alpaca.getPath());
        setImage(currentTexture);
        setSize(imageWidth, imageHeight);
        setCenter(worldWidth * 0.5f, worldHeight * 0.5f);
    }

    @Override
    public void dispose() {
        currentTexture.dispose();
        currentTexture = null;
    }

    @Override
    public void update(float dt) {

    }
}
