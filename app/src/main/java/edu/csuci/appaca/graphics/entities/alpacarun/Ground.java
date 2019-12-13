package edu.csuci.appaca.graphics.entities.alpacarun;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.graphics.entities.AbstractEntity;

import static edu.csuci.appaca.data.gameres.AlpacaRunResources.speed;
import static edu.csuci.appaca.data.gameres.AlpacaRunResources.worldWidth;

public class Ground extends AbstractEntity {

    private float imageX;
    private float imageWidth;
    private Texture groundTile;

    public Ground() {
        super();
        imageX = 0;
        groundTile = StaticContentManager.getTexture(StaticContentManager.Image.GROUND_TILE);
        imageWidth = groundTile.getWidth();
        setSize(worldWidth(), groundTile.getHeight() * 0.65f);
        setVelocity(-speed(), 0);
    }

    @Override
    public void update(float dt) {
        imageX += getVelocityX();
        if(imageX < -imageWidth) {
            imageX += imageWidth;
        }
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        float x = imageX;
        while(x < worldWidth()) {
            sb.draw(groundTile, x, 0);
            x += imageWidth;
        }
    }
}
