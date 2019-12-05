package edu.csuci.appaca.graphics.ui;

import android.content.Context;

import edu.csuci.appaca.data.Inventory;
import edu.csuci.appaca.data.statics.ShopData;
import edu.csuci.appaca.utils.ListUtils;

public class ClothingBadgeButton extends InventoryBadgeButton {

    private ListUtils.Consumer<ClothingBadgeButton> reloadButtonsConsumer;

    public ClothingBadgeButton(int itemId, float size, Context parent, ListUtils.Consumer<ClothingBadgeButton> reloadButtonsConsumer) {
        super(itemId, size, parent);
        this.reloadButtonsConsumer = reloadButtonsConsumer;
    }

    @Override
    protected String getTexturePath() {
        return ShopData.getClothes(this.itemId).path;
    }

    @Override
    protected int getItemAmount() {
        return Inventory.getClothesAmount(this.itemId);
    }

    @Override
    public void onClick() {
        reloadButtonsConsumer.accept(this);
    }
}
