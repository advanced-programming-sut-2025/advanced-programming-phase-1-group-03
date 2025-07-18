package com.ap.utils;

import com.ap.model.GameData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


public class PreferencesManager {
    private final Preferences preferences;
    private final String rememberUserKey = "remember_username";

    public PreferencesManager() {
        preferences = Gdx.app.getPreferences("preferences");
    }
    public void putString(String key, String value) {
        preferences.putString(key, value);
    }
    public boolean containsKey(String key) {
        return preferences.contains(key);
    }
    public String getString(String key) {
        return preferences.getString(key, "");
    }

    public void save() {
        preferences.flush();
    }

    public void removeRememberUser() {
        preferences.remove(rememberUserKey);
        save();
    }
    public void rememberUsername(String username) {
        putString(rememberUserKey, username);
        save();
    }

    public void load() {
        if(containsKey(rememberUserKey)) {
            GameData.getInstance().setLoggedUserUsername(getString(rememberUserKey));
        }
    }
}
