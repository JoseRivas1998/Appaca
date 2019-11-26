package edu.csuci.appaca.graphics.entities.mainscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.graphics.entities.AbstractSpriteEntity;
import edu.csuci.appaca.utils.VectorUtils;

public class HoseHead extends AbstractSpriteEntity {

    private boolean isHeld;
    private Viewport viewport;
    private final int WORLD_WIDTH;
    private final int WORLD_HEIGHT;

    public HoseHead(Viewport viewport, int world_width, int world_height) {
        super();
        WORLD_WIDTH = world_width;
        WORLD_HEIGHT = world_height;
        setImage(StaticContentManager.getTexture(StaticContentManager.Image.SHOWER_HEAD));
        centerOrigin();
        setSize(imageWidth, imageHeight);
        setPosition(100, 100);
        isHeld = false;
        this.viewport = viewport;
    }

    @Override
    public void update(float dt) {
        setImage(StaticContentManager.getTexture(StaticContentManager.Image.SHOWER_HEAD));
        if (!isHeld) {
            if (Gdx.input.justTouched()) {
                Vector2 touchPoint = new Vector2(Gdx.input.getX(), Gdx.input.getY());
                isHeld = containsPoint(viewport.unproject(touchPoint));
            }
        } else {
            if (Gdx.input.isTouched()) {
                Vector2 touchPoint = new Vector2(Gdx.input.getX(), Gdx.input.getY());
                setCenter(viewport.unproject(touchPoint));
            } else {
                isHeld = false;
            }
        }
        setPosition(VectorUtils.clampVector(getPosition(),
                0, 0,
                WORLD_WIDTH - getWidth(), WORLD_HEIGHT - getHeight()));
    }

    public boolean isHeld() {
        return isHeld;
    }
}