package edu.csuci.appaca.graphics;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import edu.csuci.appaca.R;
import edu.csuci.appaca.activities.GameOverActivity;
import edu.csuci.appaca.data.HighScore;
import edu.csuci.appaca.data.MiniGames;
import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.graphics.entities.LabelEntity;
import edu.csuci.appaca.graphics.entities.alpacajump.AJHUD;
import edu.csuci.appaca.graphics.entities.alpacajump.AbstractB2DSpriteEntity;
import edu.csuci.appaca.graphics.entities.alpacajump.Platform;
import edu.csuci.appaca.graphics.entities.alpacajump.Player;
import edu.csuci.appaca.utils.b2d.BasicContactListener;

public class AlpacaJump extends ApplicationAdapter {

    private static final boolean DEBUG = false;

    private static Activity parent;

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;

    private Viewport mainView;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Viewport b2dView;
    private float accumulator;

    private List<Platform> platforms;
    private float maxPlatformY;
    private Set<AbstractB2DSpriteEntity> toRemove;

    private Player player;

    private boolean playing;

    private Texture bg;
    private AJHUD hud;
    private int score;
    private int highScore;

    private LabelEntity tapToStart;

    public AlpacaJump(Activity parent) {
        AlpacaJump.parent = parent;
    }

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_INFO);
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        mainView = new FitViewport(worldWidth(), worldHeight());
        mainView.getCamera().position.set(worldWidth() * 0.5f, worldHeight() * 0.5f, 0f);
        StaticContentManager.load();
        initPhys();
        platforms = new ArrayList<>();
        toRemove = new HashSet<>();
        maxPlatformY = 0;
        player = new Player(world);
        playing = false;
        bg = StaticContentManager.getTexture(StaticContentManager.Image.ALPACA_JUMP_BG);
        hud = new AJHUD();
        score = 0;
        highScore = HighScore.getHighScore(MiniGames.ALPACA_JUMP);
        tapToStart = new LabelEntity();
        tapToStart.setText(getString(R.string.top_to_start));
        tapToStart.setFont(StaticContentManager.Font.ALPACA_JUMP_START);
        tapToStart.setAlign(LabelEntity.MIDDLE_CENTER);
        tapToStart.setX(worldWidth() * 0.5f);
        tapToStart.setY(worldHeight() * 0.5f);
    }

    private void initPhys() {
        this.accumulator = 0;
        this.world = new World(new Vector2(0, gravity()), true);
        this.world.setContactListener(new BasicContactListener());
        debugRenderer = new Box2DDebugRenderer();
        b2dView = new FitViewport(worldWidth() * metersPerPixel(), worldHeight() * metersPerPixel());
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float dt = Gdx.graphics.getDeltaTime();
        if(playing) {
            updatePlaying(dt);
            hud.update(dt, score, highScore);
            draw(dt);
            physicsStep(dt);
        } else {
            updatePreGame(dt);
            hud.update(dt, 0, highScore);
            draw(dt);
        }
    }

    private void updatePreGame(float dt) {
        updateView();
        createPlatforms();
        tapToStart.update(dt);
        if(Gdx.input.justTouched()) {
            playing = true;
            player.jump();
        }
    }

    private void updatePlaying(float dt) {
        player.handleInput(dt);
        player.update(dt);
        score = (int) Math.max(score, player.getBody().getTransform().getPosition().y);
        updateView();
        createPlatforms();
        findPlatformsToRemove();
        removeEntities();
        checkDeath();
    }

    private void checkDeath() {
        float bottom = mainView.getCamera().position.y - (worldHeight() * 0.5f);
        float playerTop = player.getY() + player.getHeight();
        if(Float.compare(playerTop, bottom) < 0) {
            Intent intent = new Intent(parent, GameOverActivity.class);
            intent.putExtra("score", score);
            intent.putExtra("return", MiniGames.ALPACA_JUMP.ordinal());
            parent.startActivity(intent);
            parent.finish();
        }
    }

    private void removeEntities() {
        Iterator<AbstractB2DSpriteEntity> iter = toRemove.iterator();
        while(iter.hasNext()) {
            AbstractB2DSpriteEntity entity = iter.next();
            world.destroyBody(entity.getBody());
            iter.remove();
        }
    }

    private void findPlatformsToRemove() {
        Iterator<Platform> iter = platforms.iterator();
        float bottom = mainView.getCamera().position.y - (worldHeight() * 0.5f);
        while(iter.hasNext()) {
            Platform platform = iter.next();
            if(platform.getY() + platform.getHeight() < bottom) {
                toRemove.add(platform);
                iter.remove();
            }
        }
    }

    private void createPlatforms() {
        float targetY = b2dView.getCamera().position.y + (worldHeight() * metersPerPixel());
        while (maxPlatformY < targetY) {
            maxPlatformY += MathUtils.random(getFloat(R.dimen.min_y_distance), getFloat(R.dimen.max_y_distance));
            platforms.add(new Platform(world, maxPlatformY));
        }
    }

    private void draw(float dt) {
        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(mainView.getCamera().combined);
        float bgX = mainView.getCamera().position.x - (worldWidth() * 0.5f);
        float bgY = mainView.getCamera().position.y - (worldHeight() * 0.5f);
        spriteBatch.draw(bg, bgX, bgY, worldWidth(), worldHeight());
        for (Platform platform : platforms) {
            platform.draw(dt, spriteBatch, shapeRenderer);
        }
        player.draw(dt, spriteBatch, shapeRenderer);
        hud.draw(dt, spriteBatch);
        if(!playing) {
            tapToStart.draw(dt, spriteBatch, shapeRenderer);
        }
        spriteBatch.end();
        if(DEBUG) debugRenderer.render(world, b2dView.getCamera().combined);
    }

    private void updateView() {
        mainView.getCamera().position.y = Math.max(player.getCenterY(), mainView.getCamera().position.y);
        b2dView.getCamera().position.set(
                new Vector3(mainView.getCamera().position).scl(metersPerPixel())
        );
        mainView.apply();
        b2dView.apply();
    }

    private void physicsStep(float dt) {
        this.accumulator += Math.min(dt, 0.25f);
        while (this.accumulator >= timeStep()) {
            world.step(timeStep(), velocityIterations(), positionIterations());
            this.accumulator -= timeStep();
        }
    }

    @Override
    public void resize(int width, int height) {
        mainView.update(width, height);
        b2dView.update(width, height);
        hud.resize(width, height);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
        AlpacaJump.parent = null;
        StaticContentManager.dispose();
    }

    public static int worldWidth() {
        return parent.getResources().getInteger(R.integer.libgdx_fullscreen_width);
    }

    public static int worldHeight() {
        return parent.getResources().getInteger(R.integer.libgdx_fullscreen_height);
    }

    public static int getInt(int id) {
        return parent.getResources().getInteger(id);
    }

    public static float getDimension(int id) {
        return parent.getResources().getDimension(id);
    }

    public static String getString(int id) {
        return parent.getResources().getString(id);
    }

    public static float getFloat(int id) {
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
