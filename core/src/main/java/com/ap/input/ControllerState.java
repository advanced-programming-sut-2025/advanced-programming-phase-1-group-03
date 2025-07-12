package com.ap.input;

public interface ControllerState {
    void keyDown(Command command);

    // Make keyUp optional
    default void keyUp(Command command) {
    }
}
