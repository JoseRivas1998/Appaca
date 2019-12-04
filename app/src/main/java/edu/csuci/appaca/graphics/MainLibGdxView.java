package edu.csuci.appaca.graphics;

import android.content.Context;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.Alpaca;
import edu.csuci.appaca.data.AlpacaFarm;
import edu.csuci.appaca.data.CurrencyManager;
import edu.csuci.appaca.data.FoodToEat;
import edu.csuci.appaca.data.PendingCoins;
import edu.csuci.appaca.data.SaveDataUtils;
import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.graphics.entities.LabelEntity;
import edu.csuci.appaca.graphics.entities.mainscreen.AlpacaEntity;
import edu.csuci.appaca.graphics.entities.mainscreen.ClothingDrawer;
import edu.csuci.appaca.graphics.entities.mainscreen.ClothingEntity;
import edu.csuci.appaca.graphics.entities.mainscreen.EatingFood;
import edu.csuci.appaca.graphics.entities.mainscreen.FoodDrawer;
import edu.csuci.appaca.graphics.entities.mainscreen.Heart;
import edu.csuci.appaca.graphics.entities.mainscreen.HoseHead;
import edu.csuci.appaca.graphics.entities.mainscreen.PetDetector;
import edu.csuci.appaca.graphics.entities.mainscreen.Shears;
import edu.csuci.appaca.graphics.entities.mainscreen.WaterDrop;
import edu.csuci.appaca.graphics.entities.mainscreen.ZoomText;
import edu.csuci.appaca.graphics.ui.ButtonEntity;
import edu.csuci.appaca.graphics.ui.NinepatchButtonEntity;
import edu.csuci.appaca.graphics.ui.SpriteButtonEntity;
import edu.csuci.appaca.utils.ActionTimer;
import edu.csuci.appaca.utils.ShearUtils;
import edu.csuci.appaca.utils.VectorUtils;

public class MainLibGdxView extends ApplicationAdapter {

    private final Context parent;

    private final int VIEWPORT_WIDTH;
    private final int VIEW_HEIGHT;
    private final float HUD_PADDING;
    private final float FOOD_DRAWER_HEIGHT;
    private static final double HAPPINESS_PER_PET = (Alpaca.MAX_STAT - Alpaca.MIN_STAT) * 0.001f;

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;

    private Viewport viewport;

    private AlpacaEntity alpaca;
    private ClothingEntity clothingEntity;
    private PetDetector petDetector;

    private List<Heart> hearts;

    private Stack<Integer> coinPopupsToAdd;
    private List<ZoomText> zoomTexts;

    private EatingFood foodEating;

    private HoseHead hoseHead;
    private Shears shears;

    private static final float MIN_DROP_TIME = 0.05f;
    private static final float MAX_DROP_TIME = 0.15f;
    private static final float TOOLBOX_ITEM_SMOOTHING = 15f;
    private List<WaterDrop> waterDrops;
    private ActionTimer waterDropTimer;
    private static final double HYGIENE_PER_DROP = (Alpaca.MAX_STAT - Alpaca.MIN_STAT) * 0.01f;

    private enum HeldItem {
        NONE, HOSE, SHEARS
    }

    private ButtonEntity prevButton;
    private ButtonEntity nextButton;

    private HeldItem currentlyHeld = HeldItem.NONE;

    private FoodDrawer foodDrawer;
    private ClothingDrawer clothingDrawer;

    private boolean shouldToggleFoodDrawer;
    private boolean shouldToggleClothingDrawer;
    private boolean hideDown;

    private SpriteButtonEntity toolboxButton;
    private boolean toolboxOpen;
    private boolean toolboxOpening;
    private Vector2 hoseHeadTarget;
    private Vector2 shearsTarget;
    private boolean prevShearCollide;

    public MainLibGdxView(Context parent) {
        this.parent = parent;
        VIEWPORT_WIDTH = parent.getResources().getInteger(R.integer.main_view_libgdx_width);
        VIEW_HEIGHT = parent.getResources().getInteger(R.integer.main_view_libgdx_height);
        HUD_PADDING = parent.getResources().getDimension(R.dimen.hud_padding);
        FOOD_DRAWER_HEIGHT = parent.getResources().getDimension(R.dimen.main_view_food_drawer_height);
    }

    @Override
    public void create() {
        viewport = new StretchViewport(VIEWPORT_WIDTH, VIEW_HEIGHT);
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        alpaca = new AlpacaEntity(VIEWPORT_WIDTH, VIEW_HEIGHT);
        this.petDetector = new PetDetector(alpaca);
        hearts = new ArrayList<>();
        StaticContentManager.load();
        coinPopupsToAdd = new Stack<>();
        zoomTexts = new ArrayList<>();
        clothingEntity = new ClothingEntity();
        foodEating = null;
        hoseHead = new HoseHead(viewport, VIEWPORT_WIDTH, VIEW_HEIGHT);
        waterDrops = new ArrayList<>();
        waterDropTimer = new ActionTimer(getDropTime(), ActionTimer.ActionTimerMode.RUN_CONTINUOUSLY, new ActionTimer.ActionTimerEvent() {
            @Override
            public void action() {
                waterDropTimer.setTimer(getDropTime());
                waterDrops.add(new WaterDrop(hoseHead));
            }
        });

        this.foodDrawer = new FoodDrawer(VIEWPORT_WIDTH, VIEW_HEIGHT, FOOD_DRAWER_HEIGHT, HUD_PADDING, R.color.pinkPastel, parent);
        this.shouldToggleFoodDrawer = false;
        this.clothingDrawer = new ClothingDrawer(VIEWPORT_WIDTH, VIEW_HEIGHT, parent);
        this.shouldToggleClothingDrawer = false;
        initButtons();
        hideDown = false;
        toolboxOpen = false;
        hoseHeadTarget = new Vector2();
        shearsTarget = new Vector2();

        shears = new Shears(viewport, VIEWPORT_WIDTH, VIEW_HEIGHT);
        prevShearCollide = false;
    }

    private void initButtons() {
        prevButton = new NinepatchButtonEntity(StaticContentManager.Image.ARROW_LEFT);
        prevButton.setX(HUD_PADDING);
        prevButton.setCenterY(VIEW_HEIGHT * 0.5f);
        prevButton.setClickListener(new ButtonEntity.ClickListener() {
            @Override
            public void onClick() {
                AlpacaFarm.prev();
            }
        });

        nextButton = new NinepatchButtonEntity(StaticContentManager.Image.ARROW_RIGHT);
        nextButton.setX(VIEWPORT_WIDTH - nextButton.getWidth() - HUD_PADDING);
        nextButton.setCenterY(VIEW_HEIGHT * 0.5f);
        nextButton.setClickListener(new ButtonEntity.ClickListener() {
            @Override
            public void onClick() {
                AlpacaFarm.next();
            }
        });

        toolboxButton = new SpriteButtonEntity(StaticContentManager.Image.TOOLBOX_CLOSED);
        toolboxButton.setPosition(HUD_PADDING, HUD_PADDING);
        toolboxButton.setClickListener(new ButtonEntity.ClickListener() {
            @Override
            public void onClick() {
                if (foodDrawer.isShowing() || clothingDrawer.isShowing()) return;
                if (!toolboxOpen) {
                    openToolbox();
                }
            }
        });
    }

    private float getDropTime() {
        return MathUtils.random(MIN_DROP_TIME, MAX_DROP_TIME);
    }

    @Override
    public void render() {
        //2196F3
        Gdx.gl.glClearColor(0x21 / 255f, 0x96 / 255f, 0xf3 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float dt = Gdx.graphics.getDeltaTime();
        handleInput(dt);
        update(dt);
        draw(dt);

    }

    private void handleInput(float dt) {
        if (currentlyHeld == HeldItem.NONE) {
            petDetector.handleInput(viewport);
            handleButtonInputs();
            foodDrawer.handleInput();
            clothingDrawer.handleInput();
        }
    }

    private void handleButtonInputs() {
        prevButton.handleInput(viewport);
        nextButton.handleInput(viewport);
        toolboxButton.handleInput(viewport);
    }

    private void update(float dt) {
        updateToolboxOpeningClosing();
        updateHoldingItem(dt);
        addPendingCoins();
        addHearts();
        updateHearts(dt);
        updateZoomTexts(dt);
        updateFoodEating(dt);
        updateClothingEntity(dt);
        updateWaterDrops(dt);
        viewport.apply(true);
        updateFoodDrawer(dt);
        updateClothingDrawer(dt);
        updateHideDrawers();
        toolboxButton.update(dt);
    }

    private void updateToolboxOpeningClosing() {
        if (toolboxOpening) {
            shears.setX(shears.getX() + ((shearsTarget.x - shears.getX()) / TOOLBOX_ITEM_SMOOTHING));
            shears.setY(shears.getY() + ((shearsTarget.y - shears.getY()) / TOOLBOX_ITEM_SMOOTHING));
            hoseHead.setX(hoseHead.getX() + ((hoseHeadTarget.x - hoseHead.getX()) / TOOLBOX_ITEM_SMOOTHING));
            hoseHead.setY(hoseHead.getY() + ((hoseHeadTarget.y - hoseHead.getY()) / TOOLBOX_ITEM_SMOOTHING));
            if (VectorUtils.dist(shears.getPosition(), shearsTarget) < 1 &&
                    VectorUtils.dist(hoseHead.getPosition(), hoseHeadTarget) < 1) {
                if(toolboxOpening) toolboxOpening = false;
            }
        }
    }

    private void updateHideDrawers() {
        if (foodDrawer.isShowing() || clothingDrawer.isShowing()) {
            if (!hideDown) {
                if (Gdx.input.justTouched()) {
                    Vector2 touchPoint = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
                    if (touchPoint.y > FOOD_DRAWER_HEIGHT) {
                        hideDown = true;
                    }
                }
            } else {
                if (!Gdx.input.isTouched()) {
                    Vector2 touchPoint = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
                    if (touchPoint.y > FOOD_DRAWER_HEIGHT) {
                        if (foodDrawer.isShowing()) foodDrawer.toggle();
                        if (clothingDrawer.isShowing()) clothingDrawer.toggle();
                    }
                    hideDown = false;
                }
            }
        } else {
            hideDown = false;
        }
    }

    private void updateFoodDrawer(float dt) {
        if (this.shouldToggleFoodDrawer) {
            this.shouldToggleFoodDrawer = false;
            if (clothingDrawer.isShowing()) clothingDrawer.toggle();
            foodDrawer.toggle();
        }
        foodDrawer.update(dt);
    }

    private void updateClothingDrawer(float dt) {
        if (this.shouldToggleClothingDrawer) {
            this.shouldToggleClothingDrawer = false;
            if (foodDrawer.isShowing()) foodDrawer.toggle();
            this.clothingDrawer.toggle();
        }
        clothingDrawer.update(dt);
    }

    private void updateWaterDrops(float dt) {
        Iterator<WaterDrop> iter = waterDrops.iterator();
        while (iter.hasNext()) {
            WaterDrop drop = iter.next();
            drop.update(dt);
            if (drop.collidingWith(alpaca) && !drop.isHasCounted()) {
                drop.count();
                SaveDataUtils.updateValuesAndSave(parent);
                AlpacaFarm.getCurrentAlpaca().incrementHygieneStat(HYGIENE_PER_DROP);
                SaveDataUtils.save(parent);
            }
            if (drop.getY() + drop.getHeight() < 0) {
                iter.remove();
            }
        }
    }

    private void updateHoldingItem(float dt) {
        switch (currentlyHeld) {
            case NONE:
                updatePetting(dt);
                updateHoseHead(dt);
                updateShears(dt);
                break;
            case HOSE:
                updateHoseHead(dt);
                break;
            case SHEARS:
                updateShears(dt);
                break;
        }
    }

    private void updateShears(float dt) {
        if (!toolboxOpen || toolboxOpening) return;
        shears.update(dt);
        switch (currentlyHeld) {
            case SHEARS:
                if(!shears.isHeld()) {
                    currentlyHeld = HeldItem.NONE;
                } else {
                    shearsCollisions();
                }
                break;
            case NONE:
                if(shears.isHeld()) {
                    currentlyHeld = HeldItem.SHEARS;
                    prevShearCollide = shears.collidingWith(alpaca);
                }
        }
    }

    private void shearsCollisions() {
        boolean colliding = shears.collidingWith(alpaca);
        if(colliding && !prevShearCollide) {
            shear();
        }
        prevShearCollide = colliding;
    }

    private void updateHoseHead(float dt) {
        if (!toolboxOpen || toolboxOpening) return;
        hoseHead.update(dt);
        switch (currentlyHeld) {
            case HOSE:
                if (!hoseHead.isHeld()) {
                    currentlyHeld = HeldItem.NONE;
                } else {
                    waterDropTimer.update(dt);
                }
                break;
            case NONE:
                if (hoseHead.isHeld()) {
                    currentlyHeld = HeldItem.HOSE;
                    waterDropTimer.reset();
                }
                break;
        }
    }

    private void updateClothingEntity(float dt) {
        clothingEntity.update(dt);
        if (clothingEntity.shouldChangeTexture()) {
            clothingEntity.updateClothesTexture(alpaca);
        }
    }

    private void updateFoodEating(float dt) {
        if (foodEating != null) {
            foodEating.update(dt);
            if (foodEating.done()) {
                StaticContentManager.playSound(StaticContentManager.SoundEffect.FOOD_SELECT);
                foodEating.dispose();
                foodEating = null;
            }
        } else if (!FoodToEat.isEmpty()) {
            foodEating = new EatingFood(FoodToEat.pop(), alpaca);
        }
    }

    private void addPendingCoins() {
        if (PendingCoins.hasPendingCoins()) {
            coinPopupsToAdd.push(PendingCoins.getCoinsAndEmpty());
        }
    }

    private void updateHearts(float dt) {
        Iterator<Heart> iter = hearts.iterator();
        while (iter.hasNext()) {
            Heart heart = iter.next();
            if (heart.getY() > VIEW_HEIGHT) {
                iter.remove();
            } else {
                heart.update(dt);
            }
        }
    }

    private void addHearts() {
        while (!petDetector.heartsEmpty()) {
            hearts.add(new Heart(petDetector.popHeartsToAdd()));
        }
    }

    private void updateZoomTexts(float dt) {
        addZoomTexts();
        Iterator<ZoomText> iter = zoomTexts.iterator();
        while (iter.hasNext()) {
            ZoomText zoomText = iter.next();
            zoomText.update(dt);
            if (zoomText.isDone()) iter.remove();
        }
    }

    private void addZoomTexts() {
        if (zoomTexts.isEmpty()) {
            if (!coinPopupsToAdd.isEmpty()) {
                int coins = coinPopupsToAdd.pop();
                ZoomText zoomText = new ZoomText(VIEWPORT_WIDTH, VIEW_HEIGHT);
                zoomText.setFont(StaticContentManager.Font.ALPACA_JUMP_START);
                zoomText.setAlign(LabelEntity.MIDDLE_CENTER);
                zoomText.setPosition(VIEWPORT_WIDTH * 0.5f, VIEW_HEIGHT * 0.5f);
                zoomText.setText("+" + coins);
                zoomTexts.add(zoomText);
            }
        }
    }

    private void updatePetting(float dt) {
        this.petDetector.update(dt);
        if (this.petDetector.isJustPet()) {
            double happiness = MainLibGdxView.HAPPINESS_PER_PET * this.petDetector.getNumPets();
            this.petDetector.notJustPet();
            SaveDataUtils.updateValuesAndSave(parent);
            AlpacaFarm.getCurrentAlpaca().incrementHappinessStat(happiness);
            SaveDataUtils.save(parent);
        }
    }

    private void draw(float dt) {
        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.draw(
                StaticContentManager.getTexture(StaticContentManager.Image.MAIN_SCREEN_BG),
                0, 0, VIEWPORT_WIDTH, VIEW_HEIGHT
        );
        alpaca.draw(dt, spriteBatch, shapeRenderer);
        clothingEntity.draw(dt, spriteBatch, shapeRenderer);
        if (foodEating != null) foodEating.draw(dt, spriteBatch, shapeRenderer);
        if (toolboxOpen){
            hoseHead.draw(dt, spriteBatch, shapeRenderer);
            shears.draw(dt, spriteBatch, shapeRenderer);
        }
        toolboxButton.draw(dt, spriteBatch, shapeRenderer);
        for (WaterDrop waterDrop : waterDrops) {
            waterDrop.draw(dt, spriteBatch, shapeRenderer);
        }
        for (Heart heart : hearts) {
            heart.draw(dt, spriteBatch, shapeRenderer);
        }
        for (ZoomText zoomText : zoomTexts) {
            zoomText.draw(dt, spriteBatch, shapeRenderer);
        }
        prevButton.draw(dt, spriteBatch, shapeRenderer);
        nextButton.draw(dt, spriteBatch, shapeRenderer);
        spriteBatch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
//        petDetector.draw(dt, spriteBatch, shapeRenderer);
        shapeRenderer.end();

        foodDrawer.draw(dt, spriteBatch, shapeRenderer);
        clothingDrawer.draw(dt, spriteBatch, shapeRenderer);

    }

    public void shear() {
        Alpaca currentAlpaca = AlpacaFarm.getCurrentAlpaca();
        int coinsToGet = ShearUtils.getShearValue(currentAlpaca, parent);
        coinPopupsToAdd.push(coinsToGet);
        CurrencyManager.gainCurrencyOther(coinsToGet);
        currentAlpaca.setLastShearTimeToNow();
        SaveDataUtils.updateValuesAndSave(parent);
    }

    public void toggleFoodDrawer() {
        this.shouldToggleFoodDrawer = true;
    }

    public void toggleClothingDrawer() {
        this.shouldToggleClothingDrawer = true;
    }

    private void openToolbox() {
        toolboxOpen = true;
        toolboxOpening = true;
        hoseHead.setPosition(toolboxButton.getPosition());
        hoseHeadTarget.set(randomVectorNotColliding(hoseHead.getWidth(), hoseHead.getHeight()));
        shears.setPosition(toolboxButton.getPosition());
        shearsTarget.set(randomVectorNotColliding(shears.getWidth(), shears.getHeight()));
        toolboxButton.setImage(StaticContentManager.Image.TOOLBOX_OPEN);
    }

    private Vector2 randomVectorNotColliding(float width, float height) {
        Rectangle rectangle = new Rectangle();
        rectangle.width = width;
        rectangle.height = height;
        do {
            rectangle.x = MathUtils.random(0, VIEWPORT_WIDTH - rectangle.width);
            rectangle.y = MathUtils.random(0, VIEW_HEIGHT - rectangle.height);
        } while (rectangleCollidingWithAlpacaOrButtons(rectangle));
        return new Vector2(rectangle.x, rectangle.y);
    }

    private boolean rectangleCollidingWithAlpacaOrButtons(Rectangle r) {
        if (alpaca.collidingWith(r)) return true;
        if (prevButton.collidingWith(r)) return true;
        if (nextButton.collidingWith(r)) return true;
        return toolboxButton.collidingWith(r);
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("MainLibGdxView", String.format("%d, %d", width, height));
        viewport.update(width, height, true);
        for (ZoomText zoomText : zoomTexts) {
            zoomText.resize(width, height);
        }
        foodDrawer.resize(width, height);
        clothingDrawer.resize(width, height);
    }

    @Override
    public void pause() {
        StaticContentManager.dispose();
    }

    @Override
    public void resume() {
        StaticContentManager.load();
        prevButton.onResume();
        nextButton.onResume();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
        if (foodEating != null) {
            foodEating.dispose();
            foodEating = null;
        }
        foodDrawer.dispose();
        clothingDrawer.dispose();
        StaticContentManager.dispose();
    }
}
