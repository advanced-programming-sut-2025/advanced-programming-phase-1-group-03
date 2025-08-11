package com.ap;


import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class ServerRunner {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Server");
        config.setWindowedMode(1, 1);
        config.setInitialVisible(false);

        new Lwjgl3Application(new Server(), config);
    }
}
