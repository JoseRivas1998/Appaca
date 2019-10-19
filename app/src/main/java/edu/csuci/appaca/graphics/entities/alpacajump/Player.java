package edu.csuci.appaca.graphics.entities.alpacajump;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.Alpaca;
import edu.csuci.appaca.data.b2d.PhysicsLayers;
import edu.csuci.appaca.data.b2d.UserData;
import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.graphics.AlpacaJump;
import edu.csuci.appaca.utils.b2d.Collidable;

public class Player extends AbstractB2DSpriteEntity implements Collidable {

    private boolean accelerometer;

    public Player(World world) {
        super();
        accelerometer = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
        if(accelerometer) {
            Gdx.app.log(getClass().getName(), "Accelerometer available!");
        } else {
            Gdx.app.log(getClass().getName(), "Accelerometer NOT available!");
        }
        setImage(StaticContentManager.getTexture(StaticContentManager.Image.ALPACA_JUMP_PLAYER));
        setSize(imageWidth, imageHeight);
        centerOrigin();
        float halfWidth = imageWidth * 0.5f;
        float halfHeight = imageHeight * 0.5f;
        float x = MathUtils.random(halfWidth, AlpacaJump.worldWidth() - halfWidth) * AlpacaJump.metersPerPixel();
        float y = halfHeight * AlpacaJump.metersPerPixel();
        initB2DBody(world, new Vector2(x, y));
        this.update(0);
    }

    @Override
    protected void initB2DBody(World world, Vector2 spawnPoint) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // TODO Change this to dynamic
        bodyDef.position.set(spawnPoint);
        body = world.createBody(bodyDef);
        body.setUserData(this);

        float halfWidth = imageWidth * AlpacaJump.metersPerPixel() * 0.5f;
        float halfHeight = imageHeight * AlpacaJump.metersPerPixel() * 0.5f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(halfWidth, halfHeight);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = PhysicsLayers.ALPACA;
        fixtureDef.filter.maskBits = PhysicsLayers.PLATFORM;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(UserData.PLAYER);

        shape.dispose();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        float right = AlpacaJump.worldWidth();
        float y = body.getTransform().getPosition().y;
        float angle = body.getAngle();
        if(getX() + getWidth() < 0) {
            body.setTransform((right + (getWidth() * 0.5f)) * AlpacaJump.metersPerPixel(), y, angle);
        }
        if(getX() > right) {
            body.setTransform(-(getWidth() * 0.5f * AlpacaJump.metersPerPixel()), y, angle);
        }

    }

    public void handleInput() {
        float accelX = 0;
        float velY = body.getLinearVelocity().y;
        if(accelerometer) {
            accelX = -Gdx.input.getAccelerometerX() / 10f;
        }
        body.setLinearVelocity(accelX * AlpacaJump.getFloat(R.dimen.player_x_velocity), velY);
        scaleX = Float.compare(body.getLinearVelocity().x, 0) >= 0 ? 1 : -1;
    }

    public void jump() {
        float xVel = body.getLinearVelocity().x;
        body.setLinearVelocity(xVel, 0f);
        body.applyForceToCenter(0, AlpacaJump.getFloat(R.dimen.player_jump_force), true);
    }

    @Override
    public void beginContact(Contact contact, Fixture thisFixture, Fixture other) {

    }

    @Override
    public void endContact(Contact contact, Fixture thisFixture, Fixture other) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold, Fixture thisFixture, Fixture other) {
        if(other.getUserData().equals(UserData.PLATFORM)) {
            float yVel = body.getLinearVelocity().y;
            if(Float.compare(yVel, 0) >= 0) {
                contact.setEnabled(false);
            } else {
                jump();
            }
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse, Fixture thisFixture, Fixture other) {

    }
}
