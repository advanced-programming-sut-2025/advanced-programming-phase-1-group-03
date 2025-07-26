package com.ap.input;


import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.HashMap;
import java.util.Map;

public class KeyboardController extends InputAdapter {
    private final static Map<Integer, Command> mapper = new HashMap<>();

    static {
        for(Command command : Command.values()) {
            for(int key : command.getCorrespondingKeys()) {
                mapper.put(key, command);
            }
        }
    }

    private final boolean[] commandState;
    private ControllerState activeController;
    private final Map<Class<? extends ControllerState>, ControllerState> controllerCache = new HashMap<>();

    public KeyboardController(Class<? extends ControllerState> initialController,
                              Engine engine,
                              Stage stage) {

        commandState = new boolean[Command.values().length];

        controllerCache.put(UIControllerState.class, new UIControllerState(stage));

        if(engine != null) {
            controllerCache.put(GameControllerState.class, new GameControllerState(engine));
        }

        setActiveController(initialController);
    }

    private void setActiveController(Class<? extends ControllerState> controllerClass) {
        ControllerState controller = controllerCache.get(controllerClass);
        if(controller == null) {
            throw new GdxRuntimeException("Controller not found");
        }

        // Finishing remain keys up
        for(Command command : Command.values()) {
            if(activeController != null && commandState[command.ordinal()]) {
                activeController.keyUp(command);
            }
            commandState[command.ordinal()] = false;
        }

        activeController = controller;
    }

    @Override
    public boolean keyDown(int keycode) {
        Command command = mapper.get(keycode);
        if(command == null) {
            return false;
        }

        commandState[command.ordinal()] = true;
        activeController.keyDown(command);

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        Command command = mapper.get(keycode);
        if(command == null) {
            return false;
        }

        commandState[command.ordinal()] = false;
        activeController.keyUp(command);

        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        commandState[Command.Click.ordinal()] = true;
        activeController.keyDown(Command.Click);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        commandState[Command.Click.ordinal()] = false;
        activeController.keyUp(Command.Click);
        return true;
    }
}
