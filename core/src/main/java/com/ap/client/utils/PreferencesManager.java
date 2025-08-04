package com.ap.client.utils;

import com.ap.client.model.GameData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


public class PreferencesManager {
    private final Preferences preferences;
    private final String rememberTokenKey = "remember_token";

    public PreferencesManager() {
        preferences = Gdx.app.getPreferences("preferences");
    }
    public void putString(String key, String value) {
        preferences.putString(key, value);
    }
    public boolean containsKey(String key) {
        return preferences.contains(key);
    }

    private String getString(String key) {
        return preferences.getString(key, "");
    }

    public void save() {
        preferences.flush();
    }

    public void rememberToken(String token) {
        putString(rememberTokenKey, token);
        save();
    }
    public String getToken() {
        return getString(rememberTokenKey);
    }
}
