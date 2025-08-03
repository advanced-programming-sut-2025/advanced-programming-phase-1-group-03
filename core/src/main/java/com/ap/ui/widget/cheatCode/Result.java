package com.ap.ui.widget.cheatCode;

public record Result(boolean success, String message) {
    @Override
    public boolean success() {
        return success;
    }

    @Override
    public String message() {
        return message;
    }
}
