package edu.csuci.appaca.graphics.entities.alpacarun;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import edu.csuci.appaca.data.MiniGames;
import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.graphics.entities.LabelEntity;

import static edu.csuci.appaca.data.gameres.AlpacaRunResources.*;

public class AlpacaRunHUD {

    private LabelEntity scoreLabel;
    private LabelEntity highScoreLabel;

    public AlpacaRunHUD() {
        scoreLabel = new LabelEntity();
        scoreLabel.setFont(StaticContentManager.Font.ALPACA_JUMP_MAIN);
        scoreLabel.setAlign(LabelEntity.TOP_RIGHT);
        scoreLabel.setPosition(worldWidth() - hudPadding(), worldHeight() - hudPadding());

        highScoreLabel = new LabelEntity();
        highScoreLabel.setFont(StaticContentManager.Font.ALPACA_JUMP_MAIN);
        highScoreLabel.setAlign(LabelEntity.TOP_LEFT);
        highScoreLabel.setPosition(hudPadding(), worldHeight() - hudPadding());

    }

    public void update(float dt, int score, int highScore) {
        scoreLabel.setText(String.format(scoreFormat(), score));
        scoreLabel.update(dt);

        highScoreLabel.setText(String.format(highScoreFormat(), Math.max(score, highScore)));
        highScoreLabel.update(dt);
    }

    public void draw(float dt, SpriteBatch sb, ShapeRenderer sr) {
        scoreLabel.draw(dt, sb, sr);
        highScoreLabel.draw(dt, sb, sr);
    }

}
