package edu.csuci.appaca.graphics.entities.alpacarun;

import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.graphics.entities.AbstractSpriteEntity;
import edu.csuci.appaca.utils.ListUtils;

import static edu.csuci.appaca.data.gameres.AlpacaRunResources.speed;
import static edu.csuci.appaca.data.gameres.AlpacaRunResources.worldWidth;

public class Obstacle extends AbstractSpriteEntity {

    private boolean gotPoint;

    public Obstacle(Ground ground) {
        super();
        setImage(StaticContentManager.getTexture(ListUtils.choose(
                StaticContentManager.Image.OBSTACLE_SMALL,
                StaticContentManager.Image.OBSTACLE_MEDIUM,
                StaticContentManager.Image.OBSTACLE_LARGE
        )));
        setSize(imageWidth, imageHeight);
        setPosition(worldWidth(), ground.getHeight());
        setVelocity(-speed(), 0);
    }

    @Override
    public void update(float dt) {
        applyVelocity(dt);
    }

    public boolean hasGotPoint() {
        return gotPoint;
    }

    public void earnPoint() {
        gotPoint = true;
    }

}
