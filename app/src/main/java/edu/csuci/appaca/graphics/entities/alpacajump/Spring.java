package edu.csuci.appaca.graphics.entities.alpacajump;

import com.badlogic.gdx.Gdx;
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

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.b2d.PhysicsLayers;
import edu.csuci.appaca.data.b2d.UserData;
import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.graphics.AlpacaJump;
import edu.csuci.appaca.utils.b2d.Collidable;

public class Spring extends AbstractB2DSpriteEntity {

    private boolean isExtended;
    private float extendedTime;

    public Spring(World world, Platform platform) {
        super();
        float platformTop = (platform.getY() + platform.getHeight()) * AlpacaJump.metersPerPixel();
        setImage(StaticContentManager.getTexture(StaticContentManager.Image.SPRING_DOWN));
        setSize(imageWidth, imageHeight);
        float spawnY = platformTop + (imageHeight * AlpacaJump.metersPerPixel() * 0.5f);
        float spawnX = ((platform.getX() + MathUtils.random(platform.getWidth() - imageWidth)) + imageWidth * 0.5f) * AlpacaJump.metersPerPixel();
        initB2DBody(world, new Vector2(spawnX, spawnY));
        isExtended = false;
        extendedTime = 0;
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
        if (isExtended) {
            setImage(StaticContentManager.getTexture(StaticContentManager.Image.SPRING_EXTENDED));
            extendedTime += dt;
            if (extendedTime >= AlpacaJump.getFloat(R.dimen.spring_extended_timer)) {
                extendedTime = 0;
                isExtended = false;
            }
        } else {
            setImage(StaticContentManager.getTexture(StaticContentManager.Image.SPRING_DOWN));
        }
        setSize(imageWidth, imageHeight);
    }

    public void extend() {
        extendedTime = 0;
        isExtended = true;
    }
}
