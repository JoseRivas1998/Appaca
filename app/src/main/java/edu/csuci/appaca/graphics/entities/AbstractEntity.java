package edu.csuci.appaca.graphics.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import edu.csuci.appaca.utils.VectorUtils;

public abstract class AbstractEntity {

    public static final String TAG = AbstractEntity.class.getSimpleName();

    private static int entityCount = 0;

    private Rectangle bounds;
    private Vector2 velocity;
    private int id;

    public AbstractEntity() {
        this.bounds = new Rectangle();
        this.velocity = new Vector2();
        AbstractEntity.entityCount++;
        this.id = AbstractEntity.entityCount;
        Gdx.app.debug(TAG, "Created entity: " + this.toString());
    }

    public abstract void update(float dt);

    public abstract void draw(float dt, SpriteBatch sb, ShapeRenderer sr);

    public float getX() {
        return this.bounds.x;
    }

    public void setX(float x) {
        this.bounds.x = x;
    }

    public float getY() {
        return this.bounds.y;
    }

    public void setY(float y) {
        this.bounds.y = y;
    }

    public Vector2 getPosition() {
        return new Vector2(getX(), getY());
    }

    public void setPosition(float x, float y) {
        setX(x);
        setY(y);
    }

    public void setPosition(Vector2 point) {
        setPosition(point.x, point.y);
    }

    public float getCenterX() {
        return getX() + (getWidth() * 0.5f);
    }

    public void setCenterX(float x) {
        setX(x - (getWidth() * .5f));
    }

    public float getCenterY() {
        return getY() + (getHeight() * 0.5f);
    }

    public void setCenterY(float y) {
        setY(y - (getHeight() * 0.5f));
    }

    public void setCenter(float x, float y) {
        setCenterX(x);
        setCenterY(y);
    }

    public void setCenter(Vector2 center) {
        setCenter(center.x, center.y);
    }

    public Vector2 getCenter() {
        return new Vector2(getCenterX(), getCenterY());
    }

    public float getWidth() {
        return this.bounds.width;
    }

    public void setWidth(float width) {
        this.bounds.width = width;
    }

    public float getHeight() {
        return this.bounds.height;
    }

    public void setHeight(float height) {
        this.bounds.height = height;
    }

    public Vector2 getSize() {
        return new Vector2(getWidth(), getHeight());
    }

    public void setSize(float width, float height) {
        setWidth(width);
        setHeight(height);
    }

    public void setSize(Vector2 size) {
        setSize(size.x, size.y);
    }

    public float getVelocityX() {
        return this.velocity.x;
    }

    public void setVelocityX(float x) {
        this.velocity.x = x;
    }

    public float getVelocityY() {
        return this.velocity.y;
    }

    public void setVelocityY(float y) {
        this.velocity.y = y;
    }

    public void setVelocity(float x, float y) {
        setVelocityX(x);
        setVelocityY(y);
    }

    public void setVelocity(Vector2 velocity) {
        setVelocity(velocity.x, velocity.y);
    }

    public Vector2 getVelocity() {
        return new Vector2(this.velocity);
    }

    public void setVelocityPolar(float speed, float angle) {
        setVelocity(VectorUtils.polarRadians(speed, angle));
    }

    public void setVelocityPolarDegrees(float speed, float angle) {
        setVelocity(VectorUtils.polarDegrees(speed, angle));
    }

    public float getDirection() {
        return MathUtils.atan2(getVelocityY(), getVelocityX());
    }

    public float getDirectionDegrees() {
        return getDirection() * MathUtils.radiansToDegrees;
    }

    public void setDirection(float direction) {
        float speed = VectorUtils.mag(getVelocity());
        setVelocityPolar(speed, direction);
    }

    public void setDirectionDegrees(float direction) {
        setDirection(direction * MathUtils.degreesToRadians);
    }

    public float getSpeed() {
        return VectorUtils.mag(getVelocity());
    }

    public void setSpeed(float speed) {
        float angle = getDirection();
        setVelocityPolar(speed, angle);
    }

    public void applyVelocity(float dt) {
        this.bounds.x += this.velocity.x * dt;
        this.bounds.y += this.velocity.y * dt;
    }

    public boolean collidingWith(Rectangle rectangle) {
        return this.bounds.overlaps(rectangle);
    }

    public boolean collidingWith(AbstractEntity entity) {
        return this.collidingWith(entity.bounds);
    }

    public boolean containsPoint(Vector2 point) {
        return this.bounds.contains(point);
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result;
        if (obj == null || obj.getClass() != this.getClass()) {
            result = false;
        } else {
            AbstractEntity other = (AbstractEntity) obj;
            result = other.id == this.id;
        }
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "@id=" + this.id;
    }

}
