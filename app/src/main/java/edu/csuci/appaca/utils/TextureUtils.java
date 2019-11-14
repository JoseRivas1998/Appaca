package edu.csuci.appaca.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureUtils {

    public static TextureRegion[][] spriteSheet(Texture texture, int rows, int cols) {
        int tileWidth = texture.getWidth() / cols;
        int tileHeight = texture.getHeight() / rows;
        return TextureRegion.split(texture, tileWidth, tileHeight);
    }

    public static TextureRegion[] spriteStrip(Texture texture, int cells) {
        return spriteSheet(texture, 1, cells)[0];
    }

}
