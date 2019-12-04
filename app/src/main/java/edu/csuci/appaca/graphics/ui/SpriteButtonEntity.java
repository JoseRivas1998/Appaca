package edu.csuci.appaca.graphics.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import edu.csuci.appaca.data.content.StaticContentManager;

public class SpriteButtonEntity extends ButtonEntity {

    private Texture texture;
    private StaticContentManager.Image image;

    public SpriteButtonEntity(StaticContentManager.Image image, float size) {
        super();
        this.image = image;
        update(0);
        setSize(size, size);
    }

    @Override
    public void onResume() {
        throw new UnsupportedOperationException("Sprite Button Entities Do Not Resume");
    }

    @Override
    public void update(float dt) {
        this.texture = StaticContentManager.getTexture(this.image);
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        sb.draw(this.texture, getX(), getY(), getWidth(), getHeight());
    }
}
