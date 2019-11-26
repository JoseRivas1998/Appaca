package edu.csuci.appaca.graphics;

import android.app.Activity;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.data.gameres.AlpacaRunResources;
import edu.csuci.appaca.graphics.entities.LabelEntity;
import edu.csuci.appaca.graphics.entities.alpacarun.Ground;
import edu.csuci.appaca.graphics.entities.alpacarun.AlpacaRunHUD;
import edu.csuci.appaca.graphics.entities.alpacarun.Obstacle;
import edu.csuci.appaca.graphics.entities.alpacarun.Player;
import edu.csuci.appaca.utils.ActionTimer;

import static edu.csuci.appaca.data.gameres.AlpacaRunResources.*;

public class AlpacaRun extends ApplicationAdapter {

    private final Activity parent;

    private SpriteBatch sb;
    private ShapeRenderer sr;
    private Viewport viewport;

    private LabelEntity tapToStart;

    private boolean started;

    private Ground ground;

    private ActionTimer obstacleSpawn;
    private List<Obstacle> obstacles;

    private Player player;
    private int score;

    private AlpacaRunHUD hud;

    public AlpacaRun(Activity parent) {
        this.parent = parent;
        AlpacaRunResources.load(parent);
    }

    @Override
    public void create() {
        sb = new SpriteBatch();
        sr = new ShapeRenderer();
        viewport = new FitViewport(worldWidth(), worldHeight());

        tapToStart = new LabelEntity();
        tapToStart.setText("Tap to Start!");
        tapToStart.setPosition(worldWidth() * 0.5f, worldHeight() * 0.5f);
        tapToStart.setFont(StaticContentManager.Font.ALPACA_JUMP_START);
        tapToStart.setAlign(LabelEntity.MIDDLE_CENTER);

        started = false;

        ground = new Ground();

        player = new Player(ground);

        obstacles = new ArrayList<>();

        obstacleSpawn = new ActionTimer(MathUtils.random(minSpawnTime(), maxSpawnTime()), ActionTimer.ActionTimerMode.RUN_CONTINUOUSLY);
        obstacleSpawn.setActionTimerEvent(new ActionTimer.ActionTimerEvent() {
            @Override
            public void action() {
                obstacles.add(new Obstacle(ground));
                obstacleSpawn.setTimer(MathUtils.random(minSpawnTime(), maxSpawnTime()));
            }
        });

        score = 0;

        hud = new AlpacaRunHUD();

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(bgColor().r, bgColor().g, bgColor().b, bgColor().a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float dt = Gdx.graphics.getDeltaTime();

        viewport.apply(true);

        drawPlayingState(dt);
        if(started) {
            updatePlayingState(dt);
        } else {
            updateStartingState(dt);
            drawStartingState(dt);
        }

        renderHud(dt);

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        sb.dispose();
        sr.dispose();
    }

    private void renderHud(float dt) {
        hud.update(dt, score, 0);
        sb.begin();
        sb.setProjectionMatrix(viewport.getCamera().combined);
        hud.draw(dt, sb, sr);
        sb.end();
    }

    private void updatePlayingState(float dt) {
        player.handleInput();
        ground.update(dt);
        player.update(dt);
        spawnObstacles(dt);
        updateObstacles(dt);
        playerGroundCollisions();
    }

    private void playerGroundCollisions() {
        if(player.collidingWith(ground)) {
            if(!player.isOnGround()) {
                player.setY(ground.getHeight());
                player.setVelocityY(0);
            }
            player.setOnGround(true);
        }
    }

    private void updateObstacles(float dt) {
        Iterator<Obstacle> iter = obstacles.iterator();
        while(iter.hasNext()) {
            Obstacle obstacle = iter.next();
            obstacle.update(dt);
            if(obstacle.getX() + obstacle.getWidth() < 0) {
                iter.remove();
                continue;
            }
            if(obstacle.getCenterX() < player.getCenterX() && !obstacle.hasGotPoint()) {
                obstacle.earnPoint();
                score++;
            }
        }
    }

    private void spawnObstacles(float dt) {
        obstacleSpawn.update(dt);
    }

    private void drawPlayingState(float dt) {
        sb.begin();
        sb.setProjectionMatrix(viewport.getCamera().combined);
        ground.draw(dt, sb, sr);
        for (Obstacle obstacle : obstacles) {
            obstacle.draw(dt, sb, sr);
        }
        player.draw(dt, sb, sr);
        sb.end();
    }

    private void drawStartingState(float dt) {
        sb.begin();
        sb.setProjectionMatrix(viewport.getCamera().combined);
        tapToStart.draw(dt, sb, sr);
        sb.end();
    }

    private void updateStartingState(float dt) {
        if(Gdx.input.justTouched()) started = true;
        tapToStart.update(dt);
    }

}
