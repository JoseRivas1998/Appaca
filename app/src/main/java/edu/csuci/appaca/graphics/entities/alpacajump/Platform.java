package edu.csuci.appaca.graphics.entities.alpacajump;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import edu.csuci.appaca.data.b2d.PhysicsLayers;
import edu.csuci.appaca.data.b2d.UserData;
import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.graphics.AlpacaJump;

public class Platform extends AbstractB2DSpriteEntity {

    public Platform(World world, float yPosition) {
        super();
        setImage(StaticContentManager.getTexture(StaticContentManager.Image.PLATFORM));
        setSize(imageWidth, imageHeight);
        centerOrigin();
        float halfWidth = imageWidth * 0.5f;
        float x = MathUtils.random(halfWidth, AlpacaJump.worldWidth() - halfWidth) * AlpacaJump.metersPerPixel();
        initB2DBody(world, new Vector2(x, yPosition));
        super.update(0);
    }

    @Override
    protected void initB2DBody(World world, Vector2 spawnPoint) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(spawnPoint);

        body = world.createBody(bodyDef);
        body.setUserData(this);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((imageWidth * 0.5f) * AlpacaJump.metersPerPixel(),
                (imageHeight * 0.5f) * AlpacaJump.metersPerPixel());

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;
        fixtureDef.filter.categoryBits = PhysicsLayers.PLATFORM;
        fixtureDef.filter.maskBits = PhysicsLayers.ALPACA;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(UserData.PLATFORM);

        shape.dispose();

    }
}
