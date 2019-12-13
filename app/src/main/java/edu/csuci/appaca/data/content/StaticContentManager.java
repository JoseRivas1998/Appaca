package edu.csuci.appaca.data.content;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;
import java.util.Map;

public class StaticContentManager {

    public enum Font {
        ALPACA_JUMP_MAIN(
                "alpaca-jump/fnt/please_write_me_a_song.ttf", 72, Color.BLACK,
                3, Color.BLACK,
                0, 0, Color.BLACK
        ),
        ALPACA_JUMP_START(
                "alpaca-jump/fnt/please_write_me_a_song.ttf", 92, Color.BLACK,
                3, Color.BLACK,
                2, 2, Color.WHITE
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
        PLATFORM("alpaca-jump/img/platform_final.png"),
        ALPACA_JUMP_BG("alpaca-jump/img/bkgd3_2.jpg"),
        ALPACA_JUMP_PLAYER("alpaca-jump/img/skin_01.png"),
        HEART("heart.png"),
        MAIN_SCREEN_BG("backgrounds/appacabackground.png"),
        SPRING_DOWN("alpaca-jump/img/spring_down.png"),
        SPRING_EXTENDED("alpaca-jump/img/spring-extended.png"),
        PLATFORM_BREAKABLE("alpaca-jump/img/platform_breakable.png"),
        FRUIT_CATCH_BACKGROUND("backgrounds/fruitcatch_background.png"),
        SHOWER_HEAD("shower_head.png"),
        WATER_DROP("water_drop.png"),
        GROUND_TILE("alpaca-run/ground_tile.png"),
        OBSTACLE_SMALL("alpaca-run/obstacle_small.png"),
        OBSTACLE_MEDIUM("alpaca-run/obstacle_medium.png"),
        OBSTACLE_LARGE("alpaca-run/obstacle_large.png"),
        ALPACA_RUN_PLAYER("alpaca-run/player.png"),
        BACKGROUND_TILE("alpaca-run/background_tile.png"),
        BUTTON_BACKGROUND_NINEPATCH("ui/button_background_ninepatch.png"),
        ARROW_RIGHT("ui/arrow_right.png"),
        ARROW_LEFT("ui/arrow_left.png"),
        FOOD_DRAWER_BG("ui/food_drawer_bg.png"),
        X_2("ui/x_2.png"),
        TOOLBOX_OPEN("toolbox_open.png"),
        TOOLBOX_CLOSED("toolbox_closed.png"),
        SHEARS("shears.png"),
        RAINBOW("effects/rainbow.png"),
        RAIN_CLOUD("effects/raincloud.png"),
        DIRT_CLOUD("effects/dirtcloud.png"),
        SPARKLE("effects/sparkle.png");
        final String path;

        Image(String path) {
            this.path = path;
        }
    }

    public enum SoundEffect {
        NORMAL_BOUNCE("alpaca-jump/sound/normalbounce.mp3"),
        SPRING_BOUNCE("alpaca-jump/sound/springbounce.mp3"),
        PLATFORM_BREAK("alpaca-jump/sound/platformbreak.mp3"),
        FOOD_SELECT("sound/foodsound.mp3");
        final String path;

        SoundEffect(String path) {
            this.path = path;
        }
    }

    private enum Content {
        INSTANCE;
        boolean loaded;
        Map<Image, Texture> images;
        Map<Font, BitmapFont> fonts;
        Map<SoundEffect, Sound> sounds;
        GlyphLayout gl;

        Content() {
            loaded = false;
            images = new HashMap<>();
            fonts = new HashMap<>();
            gl = new GlyphLayout();
            sounds = new HashMap<>();
        }
    }

    public static void load() {
        if (Content.INSTANCE.loaded) return;
        loadImages();
        loadFonts();
        loadSounds();
        Content.INSTANCE.loaded = true;
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

    private static void loadFonts() {
        for (Font font : Font.values()) {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(font.path));
            BitmapFont bmf = generator.generateFont(font.toParam());
            Content.INSTANCE.fonts.put(font, bmf);
            generator.dispose();
        }
    }

    public static BitmapFont getFont(Font font) {
        load();
        return Content.INSTANCE.fonts.get(font);
    }

    public static float getWidth(Font font, String s) {
        Content.INSTANCE.gl.setText(getFont(font), s);
        return Content.INSTANCE.gl.width;
    }

    public static float getWidth(Font font, String s, float targetWidth, int halign, boolean wrap) {
        Content.INSTANCE.gl.setText(getFont(font), s, getFont(font).getColor(), targetWidth, halign, wrap);
        return Content.INSTANCE.gl.width;
    }

    public static float getHeight(Font font, String s) {
        Content.INSTANCE.gl.setText(getFont(font), s);
        return Content.INSTANCE.gl.height;
    }

    public static float getHeight(Font font, String s, float targetWidth, int halign, boolean wrap) {
        Content.INSTANCE.gl.setText(getFont(font), s, getFont(font).getColor(), targetWidth, halign, wrap);
        return Content.INSTANCE.gl.height;
    }

    private static void loadSounds() {
        for (SoundEffect soundEffect : SoundEffect.values()) {
            Content.INSTANCE.sounds.put(soundEffect, Gdx.audio.newSound(Gdx.files.internal(soundEffect.path)));
        }
    }

    public static Sound getSound(SoundEffect sound) {
        load();
        return Content.INSTANCE.sounds.get(sound);
    }

    public static void playSound(SoundEffect sound) {
        getSound(sound).play();
    }

    public static void dispose() {
        if (!Content.INSTANCE.loaded) return;
        for (Image image : Image.values()) {
            Content.INSTANCE.images.get(image).dispose();
        }
        for (Font font : Font.values()) {
            Content.INSTANCE.fonts.get(font).dispose();
        }
        for (SoundEffect sound : SoundEffect.values()) {
            Content.INSTANCE.sounds.get(sound).dispose();
        }
        Content.INSTANCE.sounds.clear();
        Content.INSTANCE.images.clear();
        Content.INSTANCE.fonts.clear();
        Content.INSTANCE.loaded = false;
    }

}
