package edu.csuci.appaca.graphics.entities.mainscreen.effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.csuci.appaca.data.Alpaca;
import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.graphics.entities.AbstractSpriteEntity;
import edu.csuci.appaca.graphics.entities.mainscreen.AlpacaEntity;
import edu.csuci.appaca.utils.ActionTimer;
import edu.csuci.appaca.utils.MathFunctions;

public class DirtyBoi implements Disposable{

    private float MIN_DIRT_TIME = 0.05f;
    private float MAX_DIRT_TIME = 1.0f;

    private List<DirtCloud> dirtClouds;
    private ActionTimer cloudSpawn;
    private AlpacaEntity alpaca;

    public DirtyBoi() {
        dirtClouds = new ArrayList<>();
        cloudSpawn = new ActionTimer(MathUtils.random(MIN_DIRT_TIME, MAX_DIRT_TIME), ActionTimer.ActionTimerMode.RUN_CONTINUOUSLY, new ActionTimer.ActionTimerEvent() {
            @Override
            public void action() {
                cloudSpawn.setTimer(MathUtils.random(MIN_DIRT_TIME, MAX_DIRT_TIME));
                dirtClouds.add(new DirtCloud(alpaca));
            }
        });
    }

    public void update(float dt, AlpacaEntity alpaca) {
        this.alpaca = alpaca;
        this.cloudSpawn.update(dt);
        this.updateClouds(dt);
    }

    public void draw(float dt, AlpacaEntity alpaca, Viewport viewport, SpriteBatch sb, ShapeRenderer sr) {
        sb.begin();
        sb.setProjectionMatrix(viewport.getCamera().combined);
        for (DirtCloud dirtCloud : dirtClouds) {
            dirtCloud.draw(dt, sb, sr);
        }
        sb.end();
    }

    private void updateClouds(float dt) {
        Iterator<DirtCloud> iter = dirtClouds.iterator();
        while(iter.hasNext()) {
            DirtCloud dirtCloud = iter.next();
            dirtCloud.update(dt);
            if(Float.compare(dirtCloud.stateTime, 1f) > 0) {
                iter.remove();
            }
        }
    }

    @Override
    public void dispose() {
        dirtClouds.clear();
    }

    private class DirtCloud extends AbstractSpriteEntity {
        float angularVel;
        float stateTime;

        DirtCloud(AlpacaEntity alpaca) {
            super();
            setImage(StaticContentManager.getTexture(StaticContentManager.Image.DIRT_CLOUD));
            setSize(imageWidth, imageHeight);
            setCenterX(MathUtils.random(alpaca.getX(), alpaca.getX() + alpaca.getWidth()));
            setCenterY(MathUtils.random(alpaca.getY(), alpaca.getY() + alpaca.getHeight()));
            centerOrigin();
            angularVel = MathUtils.random(-90f, 90f);
            setVelocityY(-10f);
            this.stateTime = 0;
        }

        @Override
        public void update(float dt) {
            this.imageAngle += angularVel * dt;
            this.applyVelocity(dt);
            stateTime += dt;
        }

        @Override
        public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
            Color c = new Color(sb.getColor());
            float a = (float) MathFunctions.map(stateTime, 0.0, 1.0, 1.0, 0.0);
            a = (float) MathFunctions.clamp(a, 0.0, 1.0);
            sb.setColor(1f, 1f, 1f, a);
            super.draw(dt, sb, sr);
            sb.setColor(c);
        }
    }
}
