package edu.csuci.appaca.graphics.entities.mainscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Stack;

import edu.csuci.appaca.graphics.entities.AbstractEntity;

public class PetDetector extends AbstractEntity {

    private boolean isPetting;
    private Vector2 previousPoint;
    private float swipeAngle;
    private float previousSwipeAngle;
    private int numPets;
    private boolean justPet;
    private Stack<Vector2> heartsToAdd;

    private final float THRESHOLD = MathUtils.PI / 4.0f;

    public PetDetector(AbstractEntity abstractEntity) {
        super();
        this.updateBoundingEntity(abstractEntity);
        this.isPetting = false;
        this.swipeAngle = 0.0f;
        this.previousSwipeAngle = 0.0f;
        this.numPets = 0;
        this.justPet = true;
        heartsToAdd = new Stack<>();
    }

    private void updatePetting(Viewport viewport) {
        if (!Gdx.input.isTouched()) {
            isPetting = false;
            previousPoint = null;
            this.justPet = true;
            return;
        }
        Vector2 touchPoint = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

        float dY = touchPoint.y - previousPoint.y;
        float dX = touchPoint.x - previousPoint.x;
        swipeAngle = MathUtils.atan2(dY, dX);

        if(Float.compare(Math.abs(swipeAngle - previousSwipeAngle), THRESHOLD) > 0) {
            this.numPets++;
            this.heartsToAdd.push(touchPoint);
        }

        this.previousSwipeAngle = swipeAngle;
        previousPoint = touchPoint;
    }

    private void updateNotPetting(Viewport viewport) {
        if (Gdx.input.isTouched()) {
            Vector2 touchPoint = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            if(containsPoint(touchPoint)) {
                this.numPets = 0;
                this.isPetting = true;
                this.previousPoint = new Vector2(touchPoint);
            }
        }
    }

    public void handleInput(Viewport viewport) {

        this.justPet = false;
        if (isPetting) {
            updatePetting(viewport);
        } else {
            updateNotPetting(viewport);
        }

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        Color originalColor = new Color(sr.getColor());

        sr.setColor(isPetting ? Color.RED : originalColor);
        sr.rect(getX(), getY(), getWidth(), getHeight());
        sr.setColor(originalColor);
    }

    public void updateBoundingEntity(AbstractEntity abstractEntity) {
        this.setSize(abstractEntity.getSize());
        this.setPosition(abstractEntity.getPosition());
    }

    public boolean isJustPet() {
        return this.justPet;
    }

    public void notJustPet() {
        this.justPet = false;
    }

    public int getNumPets() {
        return this.numPets;
    }

    public boolean heartsEmpty() {
        return this.heartsToAdd.isEmpty();
    }

    public Vector2 popHeartsToAdd() {
        return this.heartsToAdd.pop();
    }

}
