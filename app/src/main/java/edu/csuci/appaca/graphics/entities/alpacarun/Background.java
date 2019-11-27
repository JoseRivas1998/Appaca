package edu.csuci.appaca.graphics.entities.alpacarun;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.graphics.entities.AbstractEntity;

import static edu.csuci.appaca.data.gameres.AlpacaRunResources.*;

public class Background extends AbstractEntity {

    private Texture texture;
    private float imageX;

    public Background() {
        super();
        texture = StaticContentManager.getTexture(StaticContentManager.Image.BACKGROUND_TILE);
        setSize(texture.getWidth(), texture.getHeight());
        imageX = 0;
        setPosition(0, 0);
        setVelocity(-speed() * parallaxMultiplier(), 0);
    }

    @Override
    public void update(float dt) {
        imageX += getVelocityX() * dt;
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        float x = imageX;
        while (x <= worldWidth()) {
            sb.draw(texture, x, 0, getWidth(), worldHeight());
            x += getWidth();
        }
    }
}
