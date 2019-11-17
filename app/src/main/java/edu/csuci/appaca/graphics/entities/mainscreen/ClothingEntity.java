package edu.csuci.appaca.graphics.entities.mainscreen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;

import edu.csuci.appaca.data.Alpaca;
import edu.csuci.appaca.data.AlpacaFarm;
import edu.csuci.appaca.data.statics.ShopData;
import edu.csuci.appaca.data.statics.StaticClothesItem;
import edu.csuci.appaca.graphics.entities.AbstractSpriteEntity;

public class ClothingEntity extends AbstractSpriteEntity implements Disposable {

    private int clothingId = Alpaca.NO_CLOTHING;
    private int previousClothingId = Alpaca.NO_CLOTHING;
    private Texture currentTexture;

    @Override
    public void update(float dt) {
        clothingId = AlpacaFarm.getCurrentAlpaca().getClothing();
    }

    public void updateClothesTexture(AlpacaEntity alpaca) {
        if(currentTexture != null) currentTexture.dispose();
        StaticClothesItem clothesItem = ShopData.getClothes(clothingId);
        currentTexture = new Texture(clothesItem.path);
        setImage(currentTexture, false);
        imageWidth = currentTexture.getWidth() * clothesItem.scale;
        imageHeight = currentTexture.getHeight() * clothesItem.scale;
        setSize(imageWidth, imageHeight);
        setCenter(alpaca.getX() + clothesItem.x, alpaca.getY() + clothesItem.y);
        this.previousClothingId = clothingId;
    }

    public boolean shouldChangeTexture() {
        return this.clothingId != this.previousClothingId;
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        if(clothingId != Alpaca.NO_CLOTHING) super.draw(dt, sb, sr);
    }

    @Override
    public void dispose() {
        if(currentTexture != null) {
            currentTexture.dispose();
            currentTexture = null;
        }
    }

}
