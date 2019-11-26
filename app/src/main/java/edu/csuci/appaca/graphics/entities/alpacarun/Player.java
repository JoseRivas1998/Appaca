package edu.csuci.appaca.graphics.entities.alpacarun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.graphics.entities.AbstractSpriteEntity;
import edu.csuci.appaca.utils.TextureUtils;

import static edu.csuci.appaca.data.gameres.AlpacaRunResources.gravity;
import static edu.csuci.appaca.data.gameres.AlpacaRunResources.jumpVel;
import static edu.csuci.appaca.data.gameres.AlpacaRunResources.playerRunAnimSpeed;
import static edu.csuci.appaca.data.gameres.AlpacaRunResources.worldWidth;

public class Player extends AbstractSpriteEntity {

    private boolean onGround;

    private float stateTime;
    private Animation<TextureRegion> anim;

    public Player(Ground ground) {
        super();
        Texture spritesheet = StaticContentManager.getTexture(StaticContentManager.Image.ALPACA_RUN_PLAYER);
        TextureRegion[] frames = TextureUtils.spriteStrip(spritesheet, 2);
        anim = new Animation<TextureRegion>(playerRunAnimSpeed(), frames);
        setImage(frames[0]);
        setSize(imageWidth, imageHeight);
        setCenterX(worldWidth() * .15f);
        setY(ground.getHeight() - 3);
        stateTime = 0;
    }

    public void handleInput() {
        if(onGround && Gdx.input.justTouched()) {
            jump();
        }
    }

    @Override
    public void update(float dt) {
        if(onGround) {
            stateTime += dt;
        } else {
            setVelocityY(getVelocityY() + gravity() * dt);
        }
        applyVelocity(dt);
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        setImage(anim.getKeyFrame(stateTime, true));
        super.draw(dt, sb, sr);
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public void jump() {
        onGround = false;
        setVelocityY(jumpVel());
    }

}
