package com.ap.input;

import com.badlogic.ashley.core.Engine;

public class GameControllerState implements ControllerState{
    private final Engine engine;
    public GameControllerState(Engine engine) {
        this.engine = engine;
    }
    @Override
    public void keyDown(Command command) {

    }
}
