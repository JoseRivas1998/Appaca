package edu.csuci.appaca.graphics.entities.mainscreen.effects;

import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.graphics.entities.AbstractSpriteEntity;

public class RainbowEffectEntity extends AbstractSpriteEntity {

    public RainbowEffectEntity() {
        super();
        setImage(StaticContentManager.getTexture(StaticContentManager.Image.RAINBOW));
        setSize(imageWidth, imageHeight);
    }

    @Override
    public void update(float dt) {
        setImage(StaticContentManager.getTexture(StaticContentManager.Image.RAINBOW));
    }
}
