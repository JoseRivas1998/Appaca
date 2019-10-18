package edu.csuci.appaca.graphics;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.csuci.appaca.R;

public class AlpacaJump extends ApplicationAdapter {

    private static Context parent;

    private SpriteBatch spriteBatch;
    private Texture texture;

    private Viewport mainView;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Viewport b2dView;
    private float accumulator;

    public AlpacaJump(Context parent) {
        AlpacaJump.parent = parent;
    }

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        texture = new Texture("fatalpaca.png");
        mainView = new FitViewport(worldWidth(), worldHeight());
        initPhys();
    }

    private void initPhys() {
        this.accumulator = 0;
        this.world = new World(new Vector2(0, gravity()), true);
        debugRenderer = new Box2DDebugRenderer();
        b2dView = new FitViewport(worldWidth() * metersPerPixel(), worldHeight() * metersPerPixel());
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(worldWidth() * 0.5f * metersPerPixel(), 5);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(worldWidth() * 0.5f * metersPerPixel(), 5);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        shape.dispose();

        bodyDef.position.set(worldWidth() * 0.5f * metersPerPixel(), worldHeight() * 0.5f * metersPerPixel());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        shape = new PolygonShape();
        shape.setAsBox(2, 2);
        fixtureDef.shape = shape;
        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        shape.dispose();

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float dt = Gdx.graphics.getDeltaTime();
        updateView();
        debugRenderer.render(world, b2dView.getCamera().combined);
        physicsStep(dt);
    }

    private void updateView() {
        b2dView.getCamera().position.set(
                new Vector3(mainView.getCamera().position).scl(metersPerPixel())
        );
        mainView.apply(true);
        b2dView.apply();
    }

    private void physicsStep(float dt) {
        this.accumulator += Math.min(dt, 0.25f);
        while(this.accumulator >= timeStep()) {
            world.step(timeStep(), velocityIterations(), positionIterations());
            this.accumulator -= timeStep();
        }
    }

    @Override
    public void resize(int width, int height) {
        mainView.update(width, height, true);
        b2dView.update(width, height);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        texture.dispose();
    }

    public static int worldWidth() {
        return parent.getResources().getInteger(R.integer.libgdx_fullscreen_width);
    }

    public static int worldHeight() {
        return parent.getResources().getInteger(R.integer.libgdx_fullscreen_height);
    }

    private static float getFloat(int id) {
        TypedValue outValue = new TypedValue();
        parent.getResources().getValue(id, outValue, true);
        return outValue.getFloat();
    }

    public static float pixelsPerMeter() {
        //https://stackoverflow.com/questions/3282390/add-floating-point-value-to-android-resources-values
        return getFloat(R.dimen.pixel_per_meter);
    }

    public static float metersPerPixel() {
        return 1f / pixelsPerMeter();
    }

    public static float gravity() {
        return getFloat(R.dimen.gravity);
    }

    private static int velocityIterations() {
        return parent.getResources().getInteger(R.integer.velocity_iterations);
    }

    private static int positionIterations() {
        return parent.getResources().getInteger(R.integer.position_iterations);
    }

    private static float worldStepPerSecond() {
        return getFloat(R.dimen.world_step_per_second);
    }

    private static float timeStep() {
        return 1f / worldStepPerSecond();
    }

}
