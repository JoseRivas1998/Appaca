package edu.csuci.appaca.graphics.entities.mainscreen;

import android.content.Context;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.csuci.appaca.data.Inventory;
import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.data.statics.ShopData;
import edu.csuci.appaca.data.statics.StaticFoodItem;
import edu.csuci.appaca.graphics.ui.FoodBadgeButton;
import edu.csuci.appaca.utils.ResourceUtils;

public class FoodDrawer implements Disposable {

    private Viewport viewport;
    private final float drawerHeight;
    private final int worldWidth;
    private final int worldHeight;
    private final float hudPadding;
    private final int badgeColorID;
    private Vector2 targetPosition;

    private boolean isShowing;

    private final Context parent;

    private List<FoodBadgeButton> foodBadgeButtons;

    public FoodDrawer(int worldWidth, int worldHeight, float drawerHeight, float hudPadding, int badgeColorID, Context parent) {
        this.parent = parent;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.drawerHeight = drawerHeight;
        this.hudPadding = hudPadding;
        this.badgeColorID = badgeColorID;
        this.viewport = new StretchViewport(worldWidth, worldHeight);
        this.targetPosition = new Vector2(worldWidth * 0.5f, (worldHeight * 0.5f) + drawerHeight);
        this.viewport.getCamera().position.set(targetPosition, 0);
        this.viewport.getCamera().update();
        this.isShowing = false;

        this.foodBadgeButtons = new ArrayList<>();

    }

    public void handleInput() {
        if(this.isShowing) {
            for (FoodBadgeButton foodBadgeButton : foodBadgeButtons) {
                foodBadgeButton.handleInput(viewport);
            }
        }
    }

    public void update(float dt) {
        updateViewport();
        for (FoodBadgeButton foodBadgeButton : foodBadgeButtons) {
            foodBadgeButton.update(dt);
        }
    }

    private void updateViewport() {
        moveToTarget();
        this.viewport.apply();
    }

    private void moveToTarget() {
        Vector2 camPos = new Vector2(this.viewport.getCamera().position.x, this.viewport.getCamera().position.y);
        if(Float.compare(camPos.y, targetPosition.y) != 0) {
            camPos.y += (targetPosition.y - camPos.y) / 15f;
            if(Math.abs(targetPosition.y - camPos.y) < 1) {
                camPos.y = targetPosition.y;
                if(!isShowing) clearButtons();
            }
            this.viewport.getCamera().position.set(camPos, 0);
            this.viewport.getCamera().update();
        }
    }

    private void clearButtons() {
        Iterator<FoodBadgeButton> iter = foodBadgeButtons.iterator();
        while(iter.hasNext()) {
            FoodBadgeButton foodBadgeButton = iter.next();
            foodBadgeButton.dispose();
            iter.remove();
        }
    }

    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        sb.begin();
        sb.setProjectionMatrix(viewport.getCamera().combined);
        sb.draw(StaticContentManager.getTexture(StaticContentManager.Image.FOOD_DRAWER_BG), 0, 0, worldWidth, drawerHeight);
        for (FoodBadgeButton foodBadgeButton : foodBadgeButtons) {
            foodBadgeButton.draw(dt, sb, sr);
        }
        sb.end();

        Color color = new Color(sr.getColor());
        sr.setColor(ResourceUtils.libGDXColor(parent, badgeColorID));
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setProjectionMatrix(viewport.getCamera().combined);
        for (FoodBadgeButton foodBadgeButton : foodBadgeButtons) {
            foodBadgeButton.drawBadgeBase(sr);
        }
        sr.end();
        sr.setColor(color);
        sb.begin();
        sb.setProjectionMatrix(viewport.getCamera().combined);
        for (FoodBadgeButton foodBadgeButton : foodBadgeButtons) {
            foodBadgeButton.drawBadgeText(sb);
        }
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

    @Override
    public void dispose() {
        clearButtons();
    }

    private void show() {
        this.isShowing = true;
        targetPosition.set(worldWidth * 0.5f, worldHeight * 0.5f);
        loadFoodButtons();
    }

    private void loadFoodButtons() {
        clearButtons();
        final float size = drawerHeight - (hudPadding * 2);
        float x = hudPadding;
        for (StaticFoodItem foodItem : ShopData.getAllFood()) {
            int amount = Inventory.getFoodAmount(foodItem.id);
            if(amount > 0) {
                FoodBadgeButton foodBadgeButton = new FoodBadgeButton(foodItem.id, size, parent);
                foodBadgeButton.setPosition(x, hudPadding);
                foodBadgeButtons.add(foodBadgeButton);
                x += size + (hudPadding * 2);
            }
        }
    }

    private void hide() {
        this.isShowing = false;
        targetPosition.set(worldWidth * 0.5f, (worldHeight * 0.5f) + drawerHeight);
    }
}
