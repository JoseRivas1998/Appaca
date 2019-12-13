package edu.csuci.appaca.graphics.ui;

import android.content.Context;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;

import edu.csuci.appaca.data.Inventory;
import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.graphics.entities.LabelEntity;

public abstract class InventoryBadgeButton extends ButtonEntity implements ButtonEntity.ClickListener, Disposable {

    protected final int itemId;
    protected final Context parent;

    private final static float SPACING = 10f;

    private Texture texture;
    private LabelEntity amountLabel;

    public InventoryBadgeButton(int itemId, float size, Context parent) {
        this.itemId = itemId;
        this.parent = parent;
        this.texture = new Texture(getTexturePath());
        float ratio = (float) this.texture.getHeight() / this.texture.getWidth();
        setSize(size, size * ratio);
        this.setClickListener(this);

        this.amountLabel = new LabelEntity();
        this.amountLabel.setAlign(LabelEntity.BOTTOM_RIGHT);
        this.amountLabel.setFont(StaticContentManager.Font.ALPACA_JUMP_MAIN);

    }

    @Override
    public void update(float dt) {
        int amount = getItemAmount();
        this.amountLabel.setText(String.valueOf(amount));
        this.amountLabel.setPosition(getX() + getWidth(), getY());
        this.amountLabel.update(dt);
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        sb.draw(this.texture, getX(), getY(), getWidth(), getHeight());
    }

    protected abstract String getTexturePath();

    protected abstract int getItemAmount();

    public void drawBadgeBase(ShapeRenderer sr) {
        float width = this.amountLabel.getWidth();
        float height = this.amountLabel.getHeight();
        float size = Math.max(width, height);
        float x = this.amountLabel.getX() - size;
        if(getItemAmount() >= 10) x -= SPACING * 0.5f;
        float y = this.amountLabel.getY() - SPACING;
        size += SPACING * 2;
        sr.ellipse(x, y, size, size);
    }

    public void drawBadgeText(SpriteBatch sb) {
        this.amountLabel.draw(0, sb, null);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    @Override
    public void onResume() {
        throw new UnsupportedOperationException("Inventory Badges Do Not Need To Resume.");
    }
}
