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

public class FoodBadgeButton extends InventoryBadgeButton {

    public FoodBadgeButton(int foodItemId, float size, Context parent) {
        super(foodItemId, size, parent);

    }

    @Override
    protected String getTexturePath() {
        return ShopData.getFood(this.itemId).path;
    }

    @Override
    protected int getItemAmount() {
        return Inventory.getFoodAmount(this.itemId);
    }

    @Override
    public void onClick() {
        StaticFoodItem foodItem = ShopData.getFood(itemId);
        int amount = Inventory.getFoodAmount(itemId);
        if (amount <= 0) return;
        FoodToEat.push(foodItem);
        SaveDataUtils.updateValuesAndSave(this.parent);
        AlpacaFarm.getCurrentAlpaca().incrementHungerStat(foodItem.value);
        Inventory.useFood(foodItem.id);
        SaveDataUtils.save(this.parent);
    }

    @Override
    public void onResume() {
        throw new UnsupportedOperationException("Food Badges Do Not Need To Resume.");
    }
}
