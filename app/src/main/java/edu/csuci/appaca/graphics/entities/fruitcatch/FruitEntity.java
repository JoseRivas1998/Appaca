package edu.csuci.appaca.graphics.entities.fruitcatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.csuci.appaca.data.gameres.FruitCatchResources;
import edu.csuci.appaca.data.statics.ShopData;
import edu.csuci.appaca.data.statics.StaticFoodItem;
import edu.csuci.appaca.graphics.entities.AbstractSpriteEntity;
import edu.csuci.appaca.utils.ListUtils;

import static edu.csuci.appaca.data.gameres.FruitCatchResources.maxSpawnSpeed;
import static edu.csuci.appaca.data.gameres.FruitCatchResources.minSpawnSpeed;
import static edu.csuci.appaca.data.gameres.FruitCatchResources.worldHeight;
import static edu.csuci.appaca.data.gameres.FruitCatchResources.worldWidth;

public class FruitEntity extends AbstractSpriteEntity implements Disposable {

    private Texture texture;

    public FruitEntity() {
        super();
        StaticFoodItem food = ListUtils.choose(ShopData.getAllFood());
        texture = new Texture(food.path);
        setImage(texture);
        setSize(imageWidth, imageHeight);
        float x;
        float y;
        boolean topBottom = MathUtils.randomBoolean();
        if (topBottom) {
            x = MathUtils.random(worldWidth() - getWidth());
            y = ListUtils.choose(-getHeight(), (float) worldHeight());
        } else {
            x = ListUtils.choose(-getWidth(), (float) worldWidth());
            y = MathUtils.random(worldHeight() - getHeight());
        }
        float angle = MathUtils.atan2(worldHeight() * 0.5f - y, worldWidth() * 0.5f - x);
        float speed = MathUtils.random(minSpawnSpeed(), maxSpawnSpeed());
        setVelocityPolar(speed, angle);
        setPosition(x, y);
        centerOrigin();
        imageAngle = MathUtils.random(360);
    }

    @Override
    public void update(float dt) {
        setVelocityY(getVelocityY() + (FruitCatchResources.gravity() * dt));
        applyVelocity(dt);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    public boolean isTouched(Viewport viewport) {
        if (Gdx.input.isTouched()) {
            Vector2 touchPoint = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            return containsPoint(touchPoint);
        }
        return false;
    }

}
