package edu.csuci.appaca.graphics.entities.mainscreen;

import android.util.Log;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.graphics.entities.LabelEntity;

public class ZoomText {

    private LabelEntity label;

    private Viewport viewport;
    private float stateTime;


    private static final float ZOOM_IN_TIME = 0.535f;
    private static final float TIME_TO_LEAVE = 1.5f;
    private static final float END_TIME = 2.0f;

    public ZoomText(int viewportWidth, int viewportHeight) {
        label = new LabelEntity();
        viewport = new FitViewport(viewportWidth, viewportHeight);
        stateTime = 0;
    }


    public void update(float dt) {
        stateTime += dt;
        ((OrthographicCamera) viewport.getCamera()).zoom = zoomFunction(stateTime);
        viewport.apply(true);
        label.update(dt);
    }

    //https://www.desmos.com/calculator/ig6lo1riud

    private static float zoomFunction(float x) {
        if(Float.compare(x, 0) >= 0 && Float.compare(x, ZOOM_IN_TIME) < 0) {
            return zoomIn(x);
        } else if(Float.compare(x, ZOOM_IN_TIME) >= 1 && Float.compare(x, TIME_TO_LEAVE) < 0) {
            return 1.0f;
        } else {
            return zoomOut(x);
        }
    }

    private static float zoomIn(float x) {
        final float h = 0.9f;
        final float a = 1.84f;
        final float b = 0.15f;
        final float c = 2.4f;
        return a * MathUtils.cos((x - h) / b) + c;
    }

    private static float zoomOut(float x) {
        final float a = 20.0f;
        final float h = 1.6f;
        final float k = 0.8f;
        float xMinusHSql = (x - h) * (x - h);
        return a * xMinusHSql + k;
    }

    public boolean isDone() {
        return Float.compare(stateTime, END_TIME) > 0;
    }

    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        Matrix4 poppedProjection = sb.getProjectionMatrix().cpy();
        sb.setProjectionMatrix(viewport.getCamera().combined);
        label.draw(dt, sb, sr);
        sb.setProjectionMatrix(poppedProjection);
    }

    public void setAlign(byte align) {
        label.setAlign(align);
    }

    public void setFont(StaticContentManager.Font font) {
        label.setFont(font);
    }

    public void setText(String text) {
        label.setText(text);
    }

    public void setX(float x) {
        label.setX(x);
    }

    public void setY(float y) {
        label.setY(y);
    }

    public void setPosition(float x, float y) {
        label.setPosition(x, y);
    }

    public void setPosition(Vector2 point) {
        label.setPosition(point);
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

}
