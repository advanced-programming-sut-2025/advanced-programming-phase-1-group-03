package com.ap.input;

import com.badlogic.gdx.scenes.scene2d.Event;

public class UIEvent extends Event {
    private final Command command;
    public UIEvent(Command command) {
        this.command = command;
    }
    public Command getCommand() {
        return command;
    }
}
