package edu.csuci.appaca.graphics.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.csuci.appaca.graphics.entities.AbstractEntity;

public abstract class ButtonEntity extends AbstractEntity {

    private boolean touchDown;

    private ClickListener clickListener;

    public ButtonEntity() {
        super();
        touchDown = false;
        clickListener = null;
    }

    public boolean handleInput(Viewport viewport) {
        if(touchDown) {
            return updateTouchingDown(viewport);
        } else {
            updateAwaitingTouch(viewport);
        }
        return false;
    }

    private boolean updateTouchingDown(Viewport viewport) {
        if(!Gdx.input.isTouched()) {
            Vector2 touchPoint = getTouchPoint(viewport);
            if(containsPoint(touchPoint)) {
                if(clickListener != null) {
                    clickListener.onClick();
                    touchDown = false;
                    return true;
                } else {
                    Gdx.app.error(getClass().getName(), "This button was not given a click listener!");
                }
            }
            touchDown = false;
        }
        return false;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public abstract void onResume();

    private void updateAwaitingTouch(Viewport viewport) {
        if(Gdx.input.justTouched()) {
            Vector2 touchPoint = getTouchPoint(viewport);
            if(containsPoint(touchPoint)) {
                touchDown = true;
            }
        }
    }

    private Vector2 getTouchPoint(Viewport viewport) {
        return viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
    }

    public interface ClickListener {
        void onClick();
    }

}
