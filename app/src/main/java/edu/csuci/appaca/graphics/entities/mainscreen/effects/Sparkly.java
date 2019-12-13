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

import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.graphics.entities.AbstractSpriteEntity;
import edu.csuci.appaca.graphics.entities.mainscreen.AlpacaEntity;
import edu.csuci.appaca.utils.ActionTimer;
import edu.csuci.appaca.utils.MathFunctions;

public class Sparkly implements Disposable{

    private float MIN_SPARKLE_TIME = 0.05f;
    private float MAX_SPARKLE_TIME = 1.0f;

    private List<Sparkle> sparkles;
    private ActionTimer sparkleSpawn;
    private AlpacaEntity alpaca;

    public Sparkly() {
        sparkles = new ArrayList<>();
        sparkleSpawn = new ActionTimer(MathUtils.random(MIN_SPARKLE_TIME, MAX_SPARKLE_TIME), ActionTimer.ActionTimerMode.RUN_CONTINUOUSLY, new ActionTimer.ActionTimerEvent() {
            @Override
            public void action() {
                sparkleSpawn.setTimer(MathUtils.random(MIN_SPARKLE_TIME, MAX_SPARKLE_TIME));
                sparkles.add(new Sparkle(alpaca));
            }
        });
    }

    public void update(float dt, AlpacaEntity alpaca) {
        this.alpaca = alpaca;
        this.sparkleSpawn.update(dt);
        this.updateSparkles(dt);
    }

    public void draw(float dt, AlpacaEntity alpaca, Viewport viewport, SpriteBatch sb, ShapeRenderer sr) {
        sb.begin();
        sb.setProjectionMatrix(viewport.getCamera().combined);
        for (Sparkle sparkle : sparkles) {
            sparkle.draw(dt, sb, sr);
        }
        sb.end();
    }

    private void updateSparkles(float dt) {
        Iterator<Sparkle> iter = sparkles.iterator();
        while(iter.hasNext()) {
            Sparkle sparkle = iter.next();
            sparkle.update(dt);
            if(Float.compare(sparkle.stateTime, 1f) > 0) {
                iter.remove();
            }
        }
    }

    @Override
    public void dispose() {
        sparkles.clear();
    }

    private class Sparkle extends AbstractSpriteEntity {
        float angularVel;
        float stateTime;
        final float maxScale;

        Sparkle(AlpacaEntity alpaca) {
            super();
            setImage(StaticContentManager.getTexture(StaticContentManager.Image.SPARKLE));
            setSize(imageWidth, imageHeight);
            setCenterX(MathUtils.random(alpaca.getX(), alpaca.getX() + alpaca.getWidth()));
            setCenterY(MathUtils.random(alpaca.getY(), alpaca.getY() + alpaca.getHeight()));
            centerOrigin();
            angularVel = MathUtils.random(-180f, 180f);
            setVelocityY(-10f);
            this.maxScale = MathUtils.random(1f, 2f);
            this.stateTime = 0;
        }

        @Override
        public void update(float dt) {
            setImage(StaticContentManager.getTexture(StaticContentManager.Image.SPARKLE));
            this.imageAngle += angularVel * dt;
            this.applyVelocity(dt);
            stateTime += dt;
        }

        @Override
        public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
            float scale = maxScale * MathUtils.sin(MathUtils.PI * stateTime);
            scale = (float) MathFunctions.clamp(scale, 0, maxScale);
            scaleX = scale;
            scaleY = scale;
            super.draw(dt, sb, sr);

        }



    }
}
