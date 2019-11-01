package edu.csuci.appaca.graphics.entities.alpacajump;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.csuci.appaca.R;
import edu.csuci.appaca.data.content.StaticContentManager;
import edu.csuci.appaca.graphics.AlpacaJump;
import edu.csuci.appaca.graphics.entities.LabelEntity;

public class AJHUD {

    private LabelEntity scoreLabel;
    private Viewport viewport;
    private LabelEntity highScoreLabel;

    public AJHUD() {
        scoreLabel = new LabelEntity();
        scoreLabel.setFont(StaticContentManager.Font.ALPACA_JUMP_MAIN);
        highScoreLabel = new LabelEntity();
        highScoreLabel.setFont(StaticContentManager.Font.ALPACA_JUMP_MAIN);
        highScoreLabel.setAlign(LabelEntity.TOP_RIGHT);
        viewport = new FitViewport(AlpacaJump.worldWidth(), AlpacaJump.worldHeight());
    }

    public void update(float dt, int score, int highScore) {
        viewport.apply(true);
        updateScore(dt, score);
        updateHighScore(dt, score, highScore);
    }

    private void updateHighScore(float dt, int score, int highScore) {
        int displayScore = Math.max(score, highScore);
        highScoreLabel.setText(String.format(AlpacaJump.getString(R.string.alpaca_jump_high_score_format), displayScore));
        highScoreLabel.setX(AlpacaJump.worldWidth() - AlpacaJump.getDimension(R.dimen.hud_padding));
        highScoreLabel.setY(AlpacaJump.worldHeight() - AlpacaJump.getDimension(R.dimen.hud_padding));
        highScoreLabel.update(dt);
    }

    private void updateScore(float dt, int score) {
        scoreLabel.setText(String.format(AlpacaJump.getString(R.string.alpaca_jump_score_format), score));
        scoreLabel.setX(AlpacaJump.getDimension(R.dimen.hud_padding));
        scoreLabel.setY(AlpacaJump.worldHeight() - AlpacaJump.getDimension(R.dimen.hud_padding));
        scoreLabel.update(dt);
    }

    public void draw(float dt, SpriteBatch sb) {
        Matrix4 projection = sb.getProjectionMatrix();
        sb.setProjectionMatrix(viewport.getCamera().combined);
        scoreLabel.draw(dt, sb, null);
        highScoreLabel.draw(dt, sb, null);
        sb.setProjectionMatrix(projection);
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

}
