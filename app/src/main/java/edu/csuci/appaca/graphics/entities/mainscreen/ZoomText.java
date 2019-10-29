package edu.csuci.appaca.graphics.entities.mainscreen;

import android.util.Log;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.graphics.entities.LabelEntity;

public class ZoomText {

    private LabelEntity label;

    private Viewport viewport;

    public ZoomText(int viewportWidth, int viewportHeight) {
        label = new LabelEntity();
        viewport = new FitViewport(viewportWidth, viewportHeight);
    }


    public void update(float dt) {
        ((OrthographicCamera) viewport.getCamera()).zoom += dt;
        Log.i(getClass().getName(), String.valueOf(((OrthographicCamera) viewport.getCamera()).zoom));
        viewport.apply(true);
        label.update(dt);
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
