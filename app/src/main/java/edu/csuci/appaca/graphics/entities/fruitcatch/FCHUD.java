package edu.csuci.appaca.graphics.entities.fruitcatch;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.data.gameres.FruitCatchResources;
import edu.csuci.appaca.graphics.entities.LabelEntity;

import static edu.csuci.appaca.data.gameres.FruitCatchResources.highScoreFormat;
import static edu.csuci.appaca.data.gameres.FruitCatchResources.scoreFormat;
import static edu.csuci.appaca.data.gameres.FruitCatchResources.worldHeight;
import static edu.csuci.appaca.data.gameres.FruitCatchResources.worldWidth;
import static edu.csuci.appaca.data.gameres.FruitCatchResources.hudSpacing;

public class FCHUD {

    private LabelEntity scoreLabel;
    private LabelEntity highScoreLabel;

    public FCHUD() {
        scoreLabel = new LabelEntity();
        scoreLabel.setFont(StaticContentManager.Font.ALPACA_JUMP_MAIN);
        scoreLabel.setAlign(LabelEntity.TOP_LEFT);
        scoreLabel.setPosition(hudSpacing(), worldHeight() - hudSpacing());

        highScoreLabel = new LabelEntity();
        highScoreLabel.setFont(StaticContentManager.Font.ALPACA_JUMP_MAIN);
        highScoreLabel.setAlign(LabelEntity.TOP_RIGHT);
        highScoreLabel.setPosition(worldWidth() - hudSpacing(), worldHeight() - hudSpacing());
    }

    public void update(float dt, int score, int highScore) {
        scoreLabel.setText(String.format(scoreFormat(), score));
        highScoreLabel.setText(String.format(highScoreFormat(), Math.max(score, highScore)));
        scoreLabel.update(dt);
        highScoreLabel.update(dt);
    }

    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        scoreLabel.draw(dt, sb, sr);
        highScoreLabel.draw(dt, sb, sr);
    }

}
