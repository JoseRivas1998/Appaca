package edu.csuci.appaca.graphics.entities.mainscreen;

import android.content.Context;
import android.content.Intent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.utils.MathFunctions;

public class ClothingDrawer implements Disposable {

    private Viewport viewport;
    private Viewport buttonViewport;
    private final float drawerHeight;
    private final int worldWidth;
    private final int worldHeight;
    private final float hudPadding;
    private final int badgeColorID;

    private boolean isShowing;

    private final Context parent;

    private boolean scrollDown;
    private float pivotX;
    private float minScrollX;

    private Vector2 targetPosition;

    public ClothingDrawer(int worldWidth, int worldHeight, Context parent) {
        this.parent = parent;
        drawerHeight = parent.getResources().getDimension(R.dimen.main_view_food_drawer_height);
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        hudPadding = parent.getResources().getDimension(R.dimen.hud_padding);
        badgeColorID = R.color.pinkPastel;

        isShowing = false;

        this.viewport = new FitViewport(worldWidth, worldHeight);
        this.targetPosition = new Vector2(worldWidth * 0.5f, (worldHeight * 0.5f) + drawerHeight);
        this.viewport.getCamera().position.set(targetPosition, 0);
        this.viewport.getCamera().update();

        this.buttonViewport = new FitViewport(worldWidth, worldHeight);
        this.buttonViewport.getCamera().position.set(targetPosition, 0);
        this.buttonViewport.getCamera().update();

        this.scrollDown = false;
        this.minScrollX = worldWidth * 0.5f;

    }

    public void handleInput() {
        if (Gdx.input.isTouched() && !scrollDown) {
            scrollDown = true;
            Vector2 unprojected = buttonViewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            pivotX = unprojected.x;
        } else if(!Gdx.input.isTouched()) {
            scrollDown = false;
        }
    }

    public void update(float dt) {
        updateViewport(dt);
    }

    private void updateViewport(float dt) {
        moveToTarget();
        this.viewport.apply();
        updateScrollViewport(dt);
        this.buttonViewport.apply();
    }

    private void updateScrollViewport(float dt) {
        float mainViewportY = this.viewport.getCamera().position.y;
        float buttonViewportX = this.buttonViewport.getCamera().position.x;
        if(scrollDown) {
            Vector2 unprojected = buttonViewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            if(Float.compare(unprojected.x, pivotX) != 0) {
                buttonViewportX += (pivotX - unprojected.x);
            }
        }
        buttonViewportX = (float) MathFunctions.clamp(buttonViewportX, minScrollX, worldWidth * 0.5f);
        this.buttonViewport.getCamera().position.set(buttonViewportX, mainViewportY, 0);
        this.buttonViewport.getCamera().update();
    }

    private void moveToTarget() {
        Vector2 camPos = new Vector2(this.viewport.getCamera().position.x, this.viewport.getCamera().position.y);
        if (Float.compare(camPos.y, targetPosition.y) != 0) {
            camPos.y += (targetPosition.y - camPos.y) / 15f;
            if (Math.abs(targetPosition.y - camPos.y) < 1) {
                camPos.y = targetPosition.y;
//                if(!isShowing) clearButtons();
            }
            this.viewport.getCamera().position.set(camPos, 0);
            this.viewport.getCamera().update();
        }
    }

    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        sb.begin();
        sb.setProjectionMatrix(viewport.getCamera().combined);
        sb.draw(StaticContentManager.getTexture(StaticContentManager.Image.FOOD_DRAWER_BG), 0, 0, worldWidth, drawerHeight);
        sb.end();

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setProjectionMatrix(buttonViewport.getCamera().combined);
        float x = worldWidth * 0.5f - 20;
        float y = worldHeight * 0.5f - 20;
        sr.setColor(scrollDown ? Color.GREEN : Color.RED);
        sr.rect(x, y, 40, 40);
        sr.end();

    }

    public void resize(int width, int height) {
        this.viewport.update(width, height);
        this.buttonViewport.update(width, height);
    }

    public void toggle() {
        Gdx.app.log(getClass().getName(), "TOGGLE");
        if (this.isShowing) {
            this.hide();
        } else {
            this.show();
        }
    }

    private void show() {
        this.isShowing = true;
        targetPosition.set(worldWidth * 0.5f, worldHeight * 0.5f);
    }

    private void hide() {
        this.isShowing = false;
        targetPosition.set(worldWidth * 0.5f, (worldHeight * 0.5f) + drawerHeight);
    }

    @Override
    public void dispose() {

    }
}
