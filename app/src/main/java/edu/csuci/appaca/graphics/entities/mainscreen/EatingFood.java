package edu.csuci.appaca.graphics.entities.mainscreen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.data.statics.StaticFoodItem;
import edu.csuci.appaca.graphics.entities.AbstractSpriteEntity;
import edu.csuci.appaca.utils.TextureUtils;

public class EatingFood extends AbstractSpriteEntity implements Disposable {

    private static final float CENTER_X = 165;
    private static final float CENTER_Y = 253;
    private Texture spriteSheet;

    private TextureRegion currentFrame;
    private Animation<TextureRegion> animation;
    private TextureRegion previousFrame;
    private float stateTime;

    public EatingFood(StaticFoodItem foodItem, AlpacaEntity alpaca) {
        super();
        spriteSheet = new Texture(foodItem.anim);
        TextureRegion[] frames = TextureUtils.spriteStrip(spriteSheet, foodItem.frames);
        animation = new Animation<>(foodItem.frameDuration, frames);
        currentFrame = frames[0];
        previousFrame = null;
        stateTime = 0;
        setImage(currentFrame);
        setSize(imageWidth, imageHeight);
        setCenter(alpaca.getX() + CENTER_X, alpaca.getY() + CENTER_Y);
    }

    @Override
    public void update(float dt) {
        stateTime += dt;
        currentFrame = animation.getKeyFrame(stateTime);
        if(currentFrame != previousFrame) {
            setImage(currentFrame);
            StaticContentManager.playSound(StaticContentManager.SoundEffect.FOOD_SELECT);
        }
        previousFrame = currentFrame;
    }

    public boolean done() {
        return Float.compare(stateTime, animation.getAnimationDuration()) > 0;
    }

    @Override
    public void dispose() {
        spriteSheet.dispose();
    }
}
