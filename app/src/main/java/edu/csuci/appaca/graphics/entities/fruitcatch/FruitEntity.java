package edu.csuci.appaca.graphics.entities.fruitcatch;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;

import edu.csuci.appaca.data.gameres.FruitCatchResources;
import edu.csuci.appaca.data.statics.ShopData;
import edu.csuci.appaca.data.statics.StaticFoodItem;
import edu.csuci.appaca.graphics.FruitCatch;
import edu.csuci.appaca.graphics.entities.AbstractSpriteEntity;
import edu.csuci.appaca.utils.ListUtils;

public class FruitEntity extends AbstractSpriteEntity implements Disposable {

    private Texture texture;

    public FruitEntity() {
        super();
        StaticFoodItem food = ListUtils.choose(ShopData.getAllFood());
        texture = new Texture(food.path);
        setImage(texture);
        setSize(imageWidth, imageHeight);
        setX(MathUtils.random(FruitCatchResources.worldWidth() - getWidth()));
        setY(100);
        centerOrigin();
        imageAngle = MathUtils.random(360);
    }

    @Override
    public void update(float dt) {
        applyVelocity(dt);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

}
