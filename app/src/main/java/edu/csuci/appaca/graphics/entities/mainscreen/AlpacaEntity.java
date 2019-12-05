package edu.csuci.appaca.graphics.entities.mainscreen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;

import edu.csuci.appaca.data.Alpaca;
import edu.csuci.appaca.data.AlpacaFarm;
import edu.csuci.appaca.graphics.entities.AbstractSpriteEntity;
import edu.csuci.appaca.utils.TextureUtils;

public class AlpacaEntity extends AbstractSpriteEntity implements Disposable {

    private static final int NUM_ROWS = 1;
    private static final int NUM_COLS = 2;

    private static final int MEDIUM_WOOL_ROW = 0;

    private static final int NEUTRAL_COL = 0;
    private static final int SMILING_COL = 1;

    private Texture currentTexture;
    private TextureRegion[][] spritesheet;
    private final int worldWidth;
    private final int worldHeight;
    private int currentRow;
    private int currentCol;

    public AlpacaEntity(int worldWidth, int worldHeight) {
        super();
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.currentRow = MEDIUM_WOOL_ROW;
        this.currentCol = NEUTRAL_COL;
        updateCurrentTexture(worldWidth, worldHeight);
    }

    public void updateCurrentTexture(int worldWidth, int worldHeight) {
        if(currentTexture != null) currentTexture.dispose();
        Alpaca alpaca = AlpacaFarm.getCurrentAlpaca();
        currentTexture = new Texture(alpaca.getPath());
        spritesheet = TextureUtils.spriteSheet(currentTexture, NUM_ROWS, NUM_COLS);
    }

    @Override
    public void dispose() {
        currentTexture.dispose();
        currentTexture = null;
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        setImage(spritesheet[currentRow][currentCol]);
        setSize(imageWidth, imageHeight);
        setCenter(worldWidth * 0.5f, worldHeight * 0.5f);
        super.draw(dt, sb, sr);
    }

    public void setPetting(boolean petting) {
        this.currentCol = petting ? SMILING_COL : NEUTRAL_COL;
    }

}
