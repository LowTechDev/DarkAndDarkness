package com.mygdx.game.controllers;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.engine.ResourceManager;

public class JoystickControl {

    public final static int JOYSTICK_WIDTH = 300;
    public final static int JOYSTICK_HEIGHT = 300;

    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;

    public JoystickControl(int x, int y) {
        touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", ResourceManager.getInstance().getTexture("joystickBg"));
        touchpadSkin.add("touchKnob", ResourceManager.getInstance().getTexture("joystickKnob"));
        touchpadStyle = new Touchpad.TouchpadStyle();
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        touchpad = new Touchpad(0, touchpadStyle);
        touchpad.setBounds(x, y, JOYSTICK_WIDTH, JOYSTICK_HEIGHT);
        touchpad.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                return false;
            }
        });
    }

    public Touchpad getTouchpad() {
        return touchpad;
    }

    public void checkTouchPad() {
    }

    public void addTouchPad(Stage stage) {
        stage.addActor(touchpad);
    }
}
