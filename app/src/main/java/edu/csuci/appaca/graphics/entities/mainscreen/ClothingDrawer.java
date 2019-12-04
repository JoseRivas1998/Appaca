package edu.csuci.appaca.graphics.entities.mainscreen;

import android.content.Context;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Iterator;

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.Alpaca;
import edu.csuci.appaca.data.AlpacaFarm;
import edu.csuci.appaca.data.Inventory;
import edu.csuci.appaca.data.SaveDataUtils;
import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.data.statics.ShopData;
import edu.csuci.appaca.data.statics.StaticClothesItem;
import edu.csuci.appaca.graphics.ui.ButtonEntity;
import edu.csuci.appaca.graphics.ui.ClothingBadgeButton;
import edu.csuci.appaca.graphics.ui.SpriteButtonEntity;
import edu.csuci.appaca.utils.ListUtils;
import edu.csuci.appaca.utils.MathFunctions;
import edu.csuci.appaca.utils.ResourceUtils;

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
    private float maxScrollX;

    private Vector2 targetPosition;

    private SpriteButtonEntity removeButton;
    private Array<ClothingBadgeButton> clothingBadgeButtons;

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
        this.maxScrollX = worldWidth * 0.5f;

        this.removeButton = new SpriteButtonEntity(StaticContentManager.Image.X_2, drawerHeight - (hudPadding * 2));
        this.removeButton.setPosition(hudPadding, hudPadding);
        this.removeButton.setClickListener(new ButtonEntity.ClickListener() {
            @Override
            public void onClick() {
                confirmClothes(Alpaca.NO_CLOTHING);
                loadClothingButtons();
            }
        });

        this.clothingBadgeButtons = new Array<>();

    }

    public void handleInput() {
        if (Gdx.input.isTouched() && !scrollDown) {
            scrollDown = true;
            Vector2 unprojected = buttonViewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            pivotX = unprojected.x;
        } else if(!Gdx.input.isTouched()) {
            scrollDown = false;
        }
        if(this.isShowing) {
            this.removeButton.handleInput(this.buttonViewport);
            for (ClothingBadgeButton clothingBadgeButton : clothingBadgeButtons) {
                if(clothingBadgeButton.handleInput(buttonViewport))break;
            }
        }
    }

    public void update(float dt) {
        this.removeButton.update(dt);
        updateViewport(dt);
        for (ClothingBadgeButton clothingBadgeButton : clothingBadgeButtons) {
            clothingBadgeButton.update(dt);
        }
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
        buttonViewportX = (float) MathFunctions.clamp(buttonViewportX, worldWidth * 0.5f, maxScrollX);
        this.buttonViewport.getCamera().position.set(buttonViewportX, mainViewportY, 0);
        this.buttonViewport.getCamera().update();
    }

    private void moveToTarget() {
        Vector2 camPos = new Vector2(this.viewport.getCamera().position.x, this.viewport.getCamera().position.y);
        if (Float.compare(camPos.y, targetPosition.y) != 0) {
            camPos.y += (targetPosition.y - camPos.y) / 15f;
            if (Math.abs(targetPosition.y - camPos.y) < 1) {
                camPos.y = targetPosition.y;
                if(!isShowing) clearButtons();
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

        drawButtons(dt, sb, sr);

    }

    private void drawButtons(float dt, SpriteBatch sb, ShapeRenderer sr) {
        sb.begin();
        sb.setProjectionMatrix(buttonViewport.getCamera().combined);
        removeButton.draw(dt, sb, sr);
        for (ClothingBadgeButton clothingBadgeButton : clothingBadgeButtons) {
            clothingBadgeButton.draw(dt, sb, sr);
        }
        sb.end();

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setProjectionMatrix(buttonViewport.getCamera().combined);
        sr.setColor(ResourceUtils.libGDXColor(this.parent, this.badgeColorID));
        for (ClothingBadgeButton clothingBadgeButton : clothingBadgeButtons) {
            clothingBadgeButton.drawBadgeBase(sr);
        }
        sr.end();

        sb.begin();
        sb.setProjectionMatrix(buttonViewport.getCamera().combined);
        for (ClothingBadgeButton clothingBadgeButton : clothingBadgeButtons) {
            clothingBadgeButton.drawBadgeText(sb);
        }
        sb.end();

    }

    public void resize(int width, int height) {
        this.viewport.update(width, height);
        this.buttonViewport.update(width, height);
    }

    public void toggle() {
        if (this.isShowing) {
            this.hide();
        } else {
            this.show();
        }
    }

    private void show() {
        this.isShowing = true;
        targetPosition.set(worldWidth * 0.5f, worldHeight * 0.5f);
        loadClothingButtons();
    }

    private void hide() {
        this.isShowing = false;
        targetPosition.set(worldWidth * 0.5f, (worldHeight * 0.5f) + drawerHeight);
    }

    private void clearButtons() {
        Iterator<ClothingBadgeButton> iter = clothingBadgeButtons.iterator();
        while (iter.hasNext()) {
            ClothingBadgeButton button = iter.next();
            button.dispose();
            iter.remove();
        }
    }

    private void loadClothingButtons() {
        clearButtons();
        final float size = drawerHeight - (hudPadding * 2);
        float x = this.removeButton.getX() + this.removeButton.getWidth() + hudPadding;
        for (final StaticClothesItem clothesItem : ShopData.getAllClothes()) {
            int amount = Inventory.getClothesAmount(clothesItem.id);
            if(amount > 0) {
                ClothingBadgeButton button = new ClothingBadgeButton(clothesItem.id, size, this.parent, new ListUtils.Consumer<ClothingBadgeButton>() {
                    @Override
                    public void accept(ClothingBadgeButton clothingBadgeButton) {
                        confirmClothes(clothesItem.id);
                        loadClothingButtons();
                    }
                });
                button.setPosition(x, hudPadding + (size * 0.5f - (button.getHeight() * 0.5f)));
                clothingBadgeButtons.add(button);
                x += size + (hudPadding * 2);
            }
        }
        this.maxScrollX = Math.max(x - worldWidth * 0.5f, worldWidth * 0.5f);
        this.maxScrollX = Math.max(x - worldWidth * 0.5f, worldWidth * 0.5f);
    }

    private void confirmClothes(int clothesId) {
        if (AlpacaFarm.getCurrentAlpaca().getClothing() != clothesId) {
            SaveDataUtils.updateValuesAndSave(this.parent);
            Inventory.useClothes(clothesId);
            AlpacaFarm.getCurrentAlpaca().setClothing(clothesId);
            SaveDataUtils.save(this.parent);
            //finish();
        }
    }

    public boolean isShowing() {
        return isShowing;
    }

    @Override
    public void dispose() {
        clearButtons();
    }
}
