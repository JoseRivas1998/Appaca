package edu.csuci.appaca.graphics.entities.mainscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.csuci.appaca.data.content.StaticContentManager;

public class FoodDrawer {

    private Viewport viewport;
    private final float drawerHeight;
    private final int worldWidth;
    private final int worldHeight;
    private Vector2 targetPosition;

    private boolean isShowing;

    public FoodDrawer(int worldWidth, int worldHeight, float drawerHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.drawerHeight = drawerHeight;
        this.viewport = new StretchViewport(worldWidth, worldHeight);
        this.targetPosition = new Vector2(worldWidth * 0.5f, (worldHeight * 0.5f) + drawerHeight);
        this.viewport.getCamera().position.set(targetPosition, 0);
        this.viewport.getCamera().update();
        this.isShowing = false;
    }

    public void update(float dt) {
        updateViewport();
    }

    private void updateViewport() {
        moveToTarget();
        this.viewport.apply();
    }

    private void moveToTarget() {
        Vector2 camPos = new Vector2(this.viewport.getCamera().position.x, this.viewport.getCamera().position.y);
        if(Float.compare(camPos.y, targetPosition.y) != 0) {
            camPos.y += (targetPosition.y - camPos.y) / 15f;
            if(Math.abs(targetPosition.y - camPos.y) < 1e-3) {
                camPos.y = targetPosition.y;
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
    }

    public void resize(int width, int height) {
        this.viewport.update(width, height);
    }

    public void toggle() {
        if(this.isShowing) {
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

}
