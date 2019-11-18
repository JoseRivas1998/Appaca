package edu.csuci.appaca.graphics.entities.mainscreen;

import com.badlogic.gdx.math.MathUtils;

import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.graphics.entities.AbstractSpriteEntity;

public class WaterDrop extends AbstractSpriteEntity {

    private static final float GRAVITY = -980f;
    private boolean hasCounted;

    public WaterDrop(HoseHead hoseHead) {
        super();
        setImage(StaticContentManager.getTexture(StaticContentManager.Image.WATER_DROP));
        setSize(imageWidth, imageHeight);
        setY(hoseHead.getY() - MathUtils.random(getHeight()));
        setX(MathUtils.random(hoseHead.getX(), hoseHead.getX() + hoseHead.getWidth() - getWidth()));
        hasCounted = false;
    }

    @Override
    public void update(float dt) {
        setImage(StaticContentManager.getTexture(StaticContentManager.Image.WATER_DROP));
        setVelocityY(getVelocityY() + GRAVITY * dt);
        applyVelocity(dt);
    }

    public void count() {
        hasCounted = true;
    }

    public boolean isHasCounted() {
        return hasCounted;
    }
}
