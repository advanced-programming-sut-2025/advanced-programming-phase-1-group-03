package com.ap.input;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class UIControllerState implements ControllerState{
    private Stage stage;
    public UIControllerState(Stage stage) {
        this.stage = stage;
    }
    @Override
    public void keyDown(Command command) {

    }
}
