package edu.csuci.appaca.data.content;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;

public class StaticContentManager {

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

        Content() {
            loaded = false;
            images = new HashMap<>();
        }
    }

    public static void load() {
        if(Content.INSTANCE.loaded) return;
        loadImages();
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

    public static void dispose() {
        if(!Content.INSTANCE.loaded) return;
        for (Image image : Image.values()) {
            Content.INSTANCE.images.get(image).dispose();
        }
        Content.INSTANCE.images.clear();
        Content.INSTANCE.loaded = false;
    }

}
