package com.ap.model;

/**
 * We use this class to store current game data
 */
public class GameData {
    private static GameData instance;

    private String loggedUserUsername = null;

    public static GameData getInstance() {
        if (instance == null) {
            instance = new GameData();
        }
        return instance;
    }

    public String getLoggedUserUsername() {
        return loggedUserUsername;
    }

    public void setLoggedUserUsername(String loggedUserUsername) {
        this.loggedUserUsername = loggedUserUsername;
    }
}
