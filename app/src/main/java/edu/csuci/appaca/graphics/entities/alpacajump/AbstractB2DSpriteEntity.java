package edu.csuci.appaca.graphics.entities.alpacajump;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import edu.csuci.appaca.graphics.AlpacaJump;
import edu.csuci.appaca.graphics.entities.AbstractSpriteEntity;

public abstract class AbstractB2DSpriteEntity extends AbstractSpriteEntity {

    protected Body body;

    public Body getBody() {
        return body;
    }

    @Override
    public void update(float dt) {
        setPosition(
                (body.getPosition().x * AlpacaJump.pixelsPerMeter()) - (imageWidth * 0.5f),
                (body.getPosition().y * AlpacaJump.pixelsPerMeter()) - (imageHeight * 0.5f)
        );
    }

    protected abstract void initB2DBody(World world, Vector2 spawnPoint);

}
