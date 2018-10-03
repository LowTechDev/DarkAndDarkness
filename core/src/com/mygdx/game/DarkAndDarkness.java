package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.engine.ResourceManager;
import com.mygdx.game.screens.GameScreen;

public class DarkAndDarkness extends Game {

    public SpriteBatch batch;
    public static final int V_WIDTH = 200;
    public static final int V_HEIGHT = 100;
    public static final float PPM = 100;
    @Override
    public void create() {
        ResourceManager.getInstance().loadTexture("joystickBg", "joystickBg.png");
        ResourceManager.getInstance().loadTexture("joystickKnob", "joystickKnob.png");
        GameScreen gameScreen = new GameScreen(this);
        setScreen(gameScreen);
    }
}
