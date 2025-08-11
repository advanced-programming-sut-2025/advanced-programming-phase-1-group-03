package com.ap.input;

import com.badlogic.gdx.scenes.scene2d.Event;

public class UIEvent extends Event {
    private final UICommand command;
    public UIEvent(UICommand command) {
        this.command = command;
    }
    public UICommand getCommand() {
        return command;
    }
}
