package edu.csuci.appaca.data.content;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;

public class StaticContentManager {

    public enum Font {
        ALPACA_JUMP_MAIN(
                "alpaca-jump/fnt/please_write_me_a_song.ttf", 24, new Color(0x77_77_77_FF),
                0, Color.BLACK,
                0, 0, Color.BLACK
        );
        public final String path;
        public final int fontSize;
        public final Color fontColor;
        public final float borderWidth;
        public final Color borderColor;
        public final int shadowOffsetX;
        public final int shadowOffsetY;
        public final Color shadowColor;

        Font(String path, int fontSize, Color fontColor, float borderWidth, Color borderColor, int shadowOffsetX, int shadowOffsetY, Color shadowColor) {
            this.path = path;
            this.fontSize = fontSize;
            this.fontColor = fontColor;
            this.borderWidth = borderWidth;
            this.borderColor = borderColor;
            this.shadowOffsetX = shadowOffsetX;
            this.shadowOffsetY = shadowOffsetY;
            this.shadowColor = shadowColor;
        }

        public FreeTypeFontGenerator.FreeTypeFontParameter toParam() {
            FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
            param.size = this.fontSize;
            param.color = this.fontColor;
            if (this.borderWidth > 0) {
                param.borderWidth = this.borderWidth;
                param.borderColor = this.borderColor;
            }
            if (this.shadowOffsetX > 0 || this.shadowOffsetY > 0) {
                param.shadowOffsetX = this.shadowOffsetX;
                param.shadowOffsetY = this.shadowOffsetY;
                param.shadowColor = this.shadowColor;
            }
            return param;
        }
    }

    public enum Image {
        PLATFORM("alpaca-jump/img/platform.png"),
        ALPACA_JUMP_BG("alpaca-jump/img/bkgd3_2.jpg"),
        ALPACA_JUMP_PLAYER("alpaca-jump/img/alpaca_shop_item.png");
        final String path;

        Image(String path) {
            this.path = path;
        }
    }

    private enum Content {
        INSTANCE;
        boolean loaded;
        Map<Image, Texture> images;
        Map<Font, BitmapFont> fonts;
        GlyphLayout gl;

        Content() {
            loaded = false;
            images = new HashMap<>();
            fonts = new HashMap<>();
            gl = new GlyphLayout();
        }
    }

    public static void load() {
        if (Content.INSTANCE.loaded) return;
        loadImages();
        loadFonts();
        Content.INSTANCE.loaded = true;
    }

    private static void loadFonts() {
        for (Font font : Font.values()) {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(font.path));
            BitmapFont bmf = generator.generateFont(font.toParam());
            Content.INSTANCE.fonts.put(font, bmf);
            generator.dispose();
        }
    }

    private static void loadImages() {
        for (Image image : Image.values()) {
            Texture texture = new Texture(image.path);
            Content.INSTANCE.images.put(image, texture);
        }
    }

    public static Texture getTexture(Image image) {
        load();
        return Content.INSTANCE.images.get(image);
    }

    public static TextureRegion getTextureRegion(Image image) {
        return new TextureRegion(getTexture(image));
    }

    public BitmapFont getFont(Font font) {
        load();
        return Content.INSTANCE.fonts.get(font);
    }

    public float getWidth(Font font, String s) {
        Content.INSTANCE.gl.setText(getFont(font), s);
        return Content.INSTANCE.gl.width;
    }

    public float getWidth(Font font, String s, float targetWidth, int halign, boolean wrap) {
        Content.INSTANCE.gl.setText(getFont(font), s, getFont(font).getColor(), targetWidth, halign, wrap);
        return Content.INSTANCE.gl.width;
    }

    public float getHeight(Font font, String s) {
        Content.INSTANCE.gl.setText(getFont(font), s);
        return Content.INSTANCE.gl.height;
    }

    public float getHeight(Font font, String s, float targetWidth, int halign, boolean wrap) {
        Content.INSTANCE.gl.setText(getFont(font), s, getFont(font).getColor(), targetWidth, halign, wrap);
        return Content.INSTANCE.gl.height;
    }


    public static void dispose() {
        if (!Content.INSTANCE.loaded) return;
        for (Image image : Image.values()) {
            Content.INSTANCE.images.get(image).dispose();
        }
        for (Font font : Font.values()) {
            Content.INSTANCE.fonts.get(font).dispose();
        }
        Content.INSTANCE.images.clear();
        Content.INSTANCE.loaded = false;
    }

}
