package edu.csuci.appaca.graphics.entities.alpacajump;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import edu.csuci.appaca.data.b2d.PhysicsLayers;
import edu.csuci.appaca.data.b2d.UserData;
import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.graphics.AlpacaJump;
import edu.csuci.appaca.utils.b2d.Collidable;

public class Spring extends AbstractB2DSpriteEntity implements Collidable {

    public Spring(World world, Platform platform) {
        super();
        float platformTop = (platform.getY() + platform.getHeight()) * AlpacaJump.metersPerPixel();
        setImage(StaticContentManager.getTexture(StaticContentManager.Image.SPRING_DOWN));
        setSize(imageWidth, imageHeight);
        float spawnY = platformTop + (imageHeight * AlpacaJump.metersPerPixel() * 0.5f);
        float spawnX = ((platform.getX() + MathUtils.random(platform.getWidth() - imageWidth)) + imageWidth * 0.5f) * AlpacaJump.metersPerPixel();
        initB2DBody(world, new Vector2(spawnX, spawnY));
        super.update(0);
    }

    @Override
    protected void initB2DBody(World world, Vector2 spawnPoint) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(spawnPoint);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        body.setUserData(this);

        float hWidth = imageWidth * 0.5f * AlpacaJump.metersPerPixel();
        float hheight = imageHeight * 0.5f * AlpacaJump.metersPerPixel();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(hWidth, hheight);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = false;
        fixtureDef.filter.categoryBits = PhysicsLayers.SPRING;
        fixtureDef.filter.maskBits = PhysicsLayers.ALPACA_FOOT;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(UserData.SPRING);

        shape.dispose();

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void beginContact(Contact contact, Fixture thisFixture, Fixture other) {

    }

    @Override
    public void endContact(Contact contact, Fixture thisFixture, Fixture other) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold, Fixture thisFixture, Fixture other) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse, Fixture thisFixture, Fixture other) {

    }
}
