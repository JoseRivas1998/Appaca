package edu.csuci.appaca.graphics.ui;

import android.content.Context;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;

import edu.csuci.appaca.data.AlpacaFarm;
import edu.csuci.appaca.data.FoodToEat;
import edu.csuci.appaca.data.Inventory;
import edu.csuci.appaca.data.SaveDataUtils;
import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.data.statics.ShopData;
import edu.csuci.appaca.data.statics.StaticFoodItem;
import edu.csuci.appaca.graphics.entities.LabelEntity;

public class FoodBadgeButton extends ButtonEntity implements ButtonEntity.ClickListener, Disposable {

    private final int foodItemId;
    private final Context parent;

    private final static float SPACING = 7.5f;

    private Texture texture;
    private LabelEntity amountLabel;

    public FoodBadgeButton(int foodItemId, float size, Context parent) {
        this.foodItemId = foodItemId;
        this.parent = parent;
        StaticFoodItem foodItem = ShopData.getFood(foodItemId);
        this.texture = new Texture(foodItem.path);
        setSize(size, size);
        this.setClickListener(this);

        this.amountLabel = new LabelEntity();
        this.amountLabel.setAlign(LabelEntity.BOTTOM_RIGHT);
        this.amountLabel.setFont(StaticContentManager.Font.ALPACA_JUMP_MAIN);

    }

    @Override
    public void update(float dt) {
        int amount = Inventory.getFoodAmount(foodItemId);
        this.amountLabel.setText(String.valueOf(amount));
        this.amountLabel.setPosition(getX() + getWidth(), getY());
        this.amountLabel.update(dt);
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        sb.draw(this.texture, getX(), getY(), getWidth(), getHeight());
    }

    public void drawBadgeBase(ShapeRenderer sr) {
        float width = this.amountLabel.getWidth();
        float height = this.amountLabel.getHeight();
        float size = Math.max(width, height);
        float x = this.amountLabel.getX() - size - SPACING;
        float y = this.amountLabel.getY() - SPACING * 2;
        size += SPACING * 2;
        sr.ellipse(x, y, size, size);
    }

    public void drawBadgeText(SpriteBatch sb) {
        this.amountLabel.draw(0, sb, null);
    }

    @Override
    public void onClick() {
        StaticFoodItem foodItem = ShopData.getFood(foodItemId);
        int amount = Inventory.getFoodAmount(foodItemId);
        if (amount <= 0) return;
        FoodToEat.push(foodItem);
        SaveDataUtils.updateValuesAndSave(this.parent);
        AlpacaFarm.getCurrentAlpaca().incrementHungerStat(foodItem.value);
        Inventory.useFood(foodItem.id);
        SaveDataUtils.save(this.parent);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    @Override
    public void onResume() {
        throw new UnsupportedOperationException("Food Badges Do Not Need To Resume.");
    }
}
