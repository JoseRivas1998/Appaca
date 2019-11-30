package edu.csuci.appaca.graphics.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.graphics.entities.AbstractEntity;

public class NinepatchButtonEntity extends ButtonEntity {

    private static final float PADDING = 5;

    private static final int LEFT_PATCH = 5;
    private static final int TOP_PATCH = 4;
    private static final int RIGHT_PATCH = 5;
    private static final int BOTTOM_PATCH = 5;


    private Texture background;
    private Texture foreground;
    private NinePatch backgroundPatch;

    private final StaticContentManager.Image foregroundImage;


    public NinepatchButtonEntity(final StaticContentManager.Image foreground) {
        super();
        this.foregroundImage = foreground;
        onResume();
    }

    @Override
    public void update(float dt) {
        throw new UnsupportedOperationException("Ninepatch Buttons do not need to update. Use HandleInput instead.");
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        backgroundPatch.draw(sb, getX(), getY(), getWidth(), getHeight());
        sb.draw(foreground, getX() + PADDING, getY() + PADDING);
    }

    public void onResume() {
        background = StaticContentManager.getTexture(StaticContentManager.Image.BUTTON_BACKGROUND_NINEPATCH);
        this.foreground = StaticContentManager.getTexture(foregroundImage);

        backgroundPatch = new NinePatch(background, LEFT_PATCH, RIGHT_PATCH, TOP_PATCH, BOTTOM_PATCH);

        setSize(this.foreground.getWidth() + PADDING * 2, this.foreground.getHeight() + PADDING * 2);
    }

}
