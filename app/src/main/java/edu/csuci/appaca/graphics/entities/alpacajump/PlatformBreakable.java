package edu.csuci.appaca.graphics.entities.alpacajump;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.b2d.PhysicsLayers;
import edu.csuci.appaca.data.b2d.UserData;
import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.graphics.AlpacaJump;
import edu.csuci.appaca.utils.MathFunctions;
import edu.csuci.appaca.utils.b2d.Collidable;

public class PlatformBreakable extends AbstractB2DSpriteEntity implements Collidable {

    private boolean isFading;
    private float alpha;
    private float fadeTime;

    public PlatformBreakable(World world, float yPosition) {
        super();
        setImage(StaticContentManager.getTexture(StaticContentManager.Image.PLATFORM_BREAKABLE));
        setSize(imageWidth, imageHeight);
        centerOrigin();
        float halfWidth = imageWidth * 0.5f;
        float x = MathUtils.random(halfWidth, AlpacaJump.worldWidth() - halfWidth) * AlpacaJump.metersPerPixel();
        initB2DBody(world, new Vector2(x, yPosition));
        super.update(0);
        this.isFading = false;
        this.alpha = 1.0f;
        this.fadeTime = 0.0f;
    }

    @Override
    protected void initB2DBody(World world, Vector2 spawnPoint) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(spawnPoint);

        body = world.createBody(bodyDef);
        body.setUserData(this);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((imageWidth * 0.5f) * AlpacaJump.metersPerPixel(),
                (imageHeight * 0.5f) * AlpacaJump.metersPerPixel());

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;
        fixtureDef.filter.categoryBits = PhysicsLayers.PLATFORM_BREAKABLE;
        fixtureDef.filter.maskBits = PhysicsLayers.ALPACA;
        fixtureDef.isSensor = true;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(UserData.PLATFORM_BREAKABLE);

        shape.dispose();

    }

    public boolean shouldRemove(float bottom) {
        if(getY() + getHeight() < bottom) return true;
        return this.isFading && Float.compare(this.alpha, 0.0f) == 0;
    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        if (this.isFading) {
            this.fadeTime = Math.min(this.fadeTime + dt, AlpacaJump.getFloat(R.dimen.breakable_fade_time));
            this.alpha = (float) MathFunctions.map(this.fadeTime,
                    0.0f, AlpacaJump.getFloat(R.dimen.breakable_fade_time),
                    1.0f, 0f);
        }
        Color oC = new Color(sb.getColor());
        sb.setColor(oC.r, oC.g, oC.b, alpha);
        super.draw(dt, sb, sr);
        sb.setColor(oC);
    }

    @Override
    public void beginContact(Contact contact, Fixture thisFixture, Fixture other) {
        if (UserData.areBothFixtureData(thisFixture, UserData.PLATFORM_BREAKABLE, other, UserData.PLAYER)) {
            if (!this.isFading && other.getBody().getLinearVelocity().y < 0) {
                this.isFading = true;
                this.alpha = 1.0f;
                this.fadeTime = 0.0f;
            }
        }
    }

    @Override
    public void endContact(Contact contact, Fixture thisFixture, Fixture other) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold, Fixture thisFixture, Fixture other) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse, Fixture thisFixture, Fixture other) {

    }
}
