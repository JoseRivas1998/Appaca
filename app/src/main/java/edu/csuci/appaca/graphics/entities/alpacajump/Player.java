package edu.csuci.appaca.graphics.entities.alpacajump;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
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

public class Player extends AbstractB2DSpriteEntity implements Collidable {

    private boolean accelerometer;
    private final int accelerometerFrames;
    private float[] accelerometerRecentValues;
    private int accelerometerIndex;

    public Player(World world) {
        super();
        accelerometer = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
        if (accelerometer) {
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
        accelerometerFrames = AlpacaJump.getInt(R.integer.accelerometer_frames);
        accelerometerRecentValues = new float[accelerometerFrames];
        accelerometerIndex = 0;
        this.update(0);
    }

    @Override
    protected void initB2DBody(World world, Vector2 spawnPoint) {
        // In Box2D, a body holds position data, velocity data (linear and angular), and forces
        BodyDef bodyDef = new BodyDef();
        // A dynamic body responds to forces such as gravity
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(spawnPoint);
        body = world.createBody(bodyDef);
        // We want a circular dependency, if we have the body we can get the entity and visa versa
        body.setUserData(this);

        float halfWidth = imageWidth * AlpacaJump.metersPerPixel() * 0.5f;
        float halfHeight = imageHeight * AlpacaJump.metersPerPixel() * 0.5f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(halfWidth, halfHeight);

        /* In Box2D, a fixture holds data about the object itself, such as it's shape, friction,
         * bounciness as well as its filter data, which is a bit flag and bit mask to determine
         * what the fixture can collide with.
         */
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = PhysicsLayers.ALPACA;
        fixtureDef.filter.maskBits = PhysicsLayers.PLATFORM_BREAKABLE;

        Fixture fixture = body.createFixture(fixtureDef);
        // User data will be used to identify fixtures
        fixture.setUserData(UserData.PLAYER);

        shape.dispose();

        float halfFootHeight = AlpacaJump.getFloat(R.dimen.player_food_height_px) * AlpacaJump.metersPerPixel() * 0.5f;
        shape = new PolygonShape();
        // By default the polygon shape is centered around the position of the body, we can change this
        shape.setAsBox(halfWidth, halfFootHeight,
                new Vector2(0, -halfHeight), 0);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = PhysicsLayers.ALPACA_FOOT;
        fixtureDef.filter.maskBits = PhysicsLayers.PLATFORM | PhysicsLayers.SPRING;
        // A sensor detects collision, but does not stop motion. Think of a ghost
        fixtureDef.isSensor = true;

        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(UserData.PLAYER_FOOT);

    }

    @Override
    public void update(float dt) {
        super.update(dt);
        float right = AlpacaJump.worldWidth();
        float y = body.getTransform().getPosition().y;
        float angle = body.getAngle();
        if (getX() + getWidth() < 0) {
            body.setTransform((right + (getWidth() * 0.5f)) * AlpacaJump.metersPerPixel(), y, angle);
        }
        if (getX() > right) {
            body.setTransform(-(getWidth() * 0.5f * AlpacaJump.metersPerPixel()), y, angle);
        }

    }

    /*
     * Insert the current accelerometer value into the recent values array, circulating so that it
     * always replaces the oldest value.
     * Return the average of the most recent values.
     * This will reduce the noise of the accelerometer, so shaky hands wont be an issue
     */
    private float addAccelerometerValueAndGetAverage() {
        accelerometerRecentValues[(accelerometerIndex++) % accelerometerFrames] = Gdx.input.getAccelerometerX();
        float total = 0;
        for (int i = 0; i < accelerometerFrames; i++) {
            total += accelerometerRecentValues[i];
        }
        return total / accelerometerFrames;
    }

    public void handleInput(float dt) {
        float accelX = 0;
        float velY = body.getLinearVelocity().y;
        if (accelerometer) {
            accelX = -addAccelerometerValueAndGetAverage() / 10f;
        }
        float velX = accelX * AlpacaJump.getFloat(R.dimen.player_x_velocity);
        body.setLinearVelocity(velX, velY);
        if (Float.compare(body.getLinearVelocity().x, AlpacaJump.getFloat(R.dimen.player_turn_threshold)) > 0) {
            scaleX = 1;
        }
        if (Float.compare(body.getLinearVelocity().x, AlpacaJump.getFloat(R.dimen.player_turn_threshold)) < 0) {
            scaleX = -1;
        }
    }

    private void jump(float multiplier) {
        float xVel = body.getLinearVelocity().x;
        body.setLinearVelocity(xVel, 0f);
        body.applyForceToCenter(0, AlpacaJump.getFloat(R.dimen.player_jump_force) * multiplier, true);
    }

    public void jump() {
        jump(1.0f);
    }

    @Override
    public void beginContact(Contact contact, Fixture thisFixture, Fixture other) {
        if (UserData.areBothFixtureData(thisFixture, UserData.PLAYER_FOOT, other, UserData.PLATFORM)) {
            // Only jump if we are falling
            if (Float.compare(body.getLinearVelocity().y, 0) < 0) {
                StaticContentManager.playSound(StaticContentManager.SoundEffect.NORMAL_BOUNCE);
                jump();
            }
        }
        if (UserData.areBothFixtureData(thisFixture, UserData.PLAYER_FOOT, other, UserData.SPRING)) {
            // Only jump if we are falling
            if (Float.compare(body.getLinearVelocity().y, 0) < 0) {
                jump(AlpacaJump.getFloat(R.dimen.spring_multiplier));
                ((Spring) other.getBody().getUserData()).extend();
            }
        }
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
