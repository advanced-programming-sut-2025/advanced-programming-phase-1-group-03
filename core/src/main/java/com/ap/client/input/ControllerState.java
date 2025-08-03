package com.ap.client.input;

public interface ControllerState {
    void keyDown(Command command);

    // Make keyUp optional
    default void keyUp(Command command) {
    }

}
