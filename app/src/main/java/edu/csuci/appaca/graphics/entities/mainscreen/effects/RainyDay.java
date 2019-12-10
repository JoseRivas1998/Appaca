package edu.csuci.appaca.graphics.entities.mainscreen.effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.graphics.entities.AbstractSpriteEntity;
import edu.csuci.appaca.graphics.entities.mainscreen.AlpacaEntity;

public class RainyDay implements Disposable {

    private static final float DROP_LENGTH = 75f;
    private static final float DROP_GRAVITY = -9800f;
    private RainCloud rainCloud;
    private List<RainDrop> rainDrops;

    public RainyDay() {
        this.rainCloud = new RainCloud();
        this.rainDrops = new ArrayList<>();
    }

    public void update(float dt, AlpacaEntity alpaca) {
        rainCloud.update(dt);
        rainCloud.setCenterX(alpaca.getCenterX());
        rainCloud.setY(alpaca.getY() + alpaca.getHeight() + rainCloud.getHeight() * 0.25f);
        if(MathUtils.randomBoolean(0.85f)) {
            addRainDrop();
        }
        updateRainDrops(dt);
    }

    public void draw(float dt, AlpacaEntity alpacaEntity, Viewport viewport, SpriteBatch sb, ShapeRenderer sr) {
        sb.begin();
        sb.setProjectionMatrix(viewport.getCamera().combined);
        rainCloud.draw(dt, sb, sr);
        sb.end();

        Color color = new Color(sr.getColor());
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(new Color(0xD2_D2_FF_FF));
        sr.setProjectionMatrix(viewport.getCamera().combined);
        for (RainDrop rainDrop : rainDrops) {
            rainDrop.draw(sr);
        }
        sr.setColor(color);
        sr.end();

    }

    @Override
    public void dispose() {
        rainDrops.clear();
    }

    private void addRainDrop() {
        float x = MathUtils.random(rainCloud.getX(), rainCloud.getX() + rainCloud.getWidth());
        float y = MathUtils.random(rainCloud.getY() - DROP_LENGTH, rainCloud.getY() + DROP_LENGTH * 0.5f);
        rainDrops.add(new RainDrop(x, y));
    }

    private void updateRainDrops(float dt) {
        Iterator<RainDrop> iter = rainDrops.iterator();
        while(iter.hasNext()) {
            RainDrop drop = iter.next();
            drop.update(dt);
            if(Float.compare(drop.pos.y + DROP_LENGTH, 0) < 0) {
                iter.remove();;
            }
        }
    }

    private class RainCloud extends AbstractSpriteEntity {

        RainCloud() {
            setImage(StaticContentManager.getTexture(StaticContentManager.Image.RAIN_CLOUD));
            setSize(imageWidth, imageHeight);
        }

        @Override
        public void update(float dt) {
            setImage(StaticContentManager.getTexture(StaticContentManager.Image.RAIN_CLOUD));
        }
    }

    private class RainDrop {
        Vector2 pos;
        float velY;

        public RainDrop(float x, float y) {
            this.pos = new Vector2(x, y);
            this.velY = 0;
        }

        public void update(float dt) {
            this.velY += DROP_GRAVITY * dt;
            pos.y += this.velY * dt;
        }

        public void draw(ShapeRenderer sr) {
            sr.line(this.pos.x, this.pos.y, this.pos.x, this.pos.y + DROP_LENGTH);
        }

    }

}
