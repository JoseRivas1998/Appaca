package edu.csuci.appaca.graphics.entities.mainscreen;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.graphics.entities.AbstractSpriteEntity;

public class Heart extends AbstractSpriteEntity {

    private Vector2 spawnCenter;
    float scale;
    private static final float FLOAT_SPEED = 200f;

    private static final float MAX_SCALE = 1.75f;

    public Heart(Vector2 spawnPoint) {
        super();
        setImage(StaticContentManager.getTexture(StaticContentManager.Image.HEART));
        setSize(imageWidth, imageHeight);
        spawnCenter = new Vector2(spawnPoint);
        setPosition(spawnCenter);
        setVelocityY(FLOAT_SPEED);
        do {
            this.scale = MathUtils.random(-MAX_SCALE, MAX_SCALE);
        } while (Float.compare(scale, 0) == 0);
    }

    @Override
    public void update(float dt) {
        this.applyVelocity(dt);
        float diffY = this.getY() - spawnCenter.y;
        float halfWidth = getWidth() * 0.5f;
        this.setCenterX(spawnCenter.x + (this.scale * halfWidth * MathUtils.sin(diffY / halfWidth)));

    }
}
