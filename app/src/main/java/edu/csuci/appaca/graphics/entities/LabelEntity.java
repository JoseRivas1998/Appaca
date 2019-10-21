package edu.csuci.appaca.graphics.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import edu.csuci.appaca.data.content.StaticContentManager;

public class LabelEntity extends AbstractEntity {

    public static final byte TOP = 1 << 0;
    public static final byte MIDDLE = 1 << 1;
    public static final byte BOTTOM = 1 << 2;

    public static final byte LEFT = 1 << 3;
    public static final byte CENTER = 1 << 4;
    public static final byte RIGHT = 1 << 5;

    public static final byte TOP_LEFT = TOP | LEFT;
    public static final byte TOP_RIGHT = TOP | RIGHT;
    public static final byte BOTTOM_LEFT = BOTTOM | LEFT;
    public static final byte BOTTOM_RIGHT = BOTTOM | RIGHT;
    public static final byte MIDDLE_CENTER = MIDDLE | CENTER;

    private String text;
    private byte align;
    private StaticContentManager.Font font;
    private Vector2 textPos;

    public LabelEntity() {
        super();
        this.text = "";
        textPos = new Vector2();
        align = TOP_LEFT;
    }

    @Override
    public void update(float dt) {
        setWidth(StaticContentManager.getWidth(font, this.text));
        setHeight(StaticContentManager.getHeight(font, this.text));
        if ((align & LEFT) != 0) {
            textPos.x = getX();
        } else if ((align & CENTER) != 0) {
            textPos.x = getX() - (getWidth() * .5f);
        } else if ((align & RIGHT) != 0) {
            textPos.x = getX() - getWidth();
        }
        if ((align & TOP) != 0) {
            textPos.y = getY();
        } else if ((align & MIDDLE) != 0) {
            textPos.y = getY() + (getHeight() * .5f);
        } else if ((align & BOTTOM) != 0) {
            textPos.y = getY() + getHeight();
        }
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        StaticContentManager.getFont(font).draw(sb, this.text, textPos.x, textPos.y);
    }

    public void setAlign(byte align) {
        this.align = align;
    }

    public void setFont(StaticContentManager.Font font) {
        this.font = font;
    }

    public void setText(String text) {
        this.text = text;
    }

}
