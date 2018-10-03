package com.mygdx.game.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ResourceManager {

    private static ResourceManager instance = new ResourceManager();
    private final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
    private final HashMap<String, Texture> textures = new HashMap<String, Texture>();

    private ResourceManager() {
    }

    public void loadTexture(String id, String file) {
        Texture t = new Texture(Gdx.files.internal(file));
        t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        textures.put(id, t);
    }

    public Sprite loadSprite(String id, String file) {
        Sprite sprite = new Sprite(new Texture(Gdx.files.internal(file)));
        sprite.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        sprites.put(id, sprite);
        return sprite;
    }

    public Sprite loadSprite(String id, String file, float scaleX, float scaleY) {
        Sprite sprite = new Sprite(new Texture(Gdx.files.internal(file)));
        sprite.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        sprite.setScale(scaleX, scaleY);
        sprite.setOrigin(0, 0);
        sprites.put(id, sprite);
        return sprite;
    }

    public Texture getTexture(String id) {
        return textures.get(id);
    }


    public Sprite getSprite(String id) {
        return sprites.get(id);
    }

    public void dispose() {
        Iterator it = textures.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            ((Texture)pair.getValue()).dispose();
            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    public static ResourceManager getInstance() {
        return instance;
    }

}
