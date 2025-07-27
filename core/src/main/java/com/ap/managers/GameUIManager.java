package com.ap.managers;

import com.ap.audio.AudioService;
import com.ap.system.ControllerSystem;
import com.ap.ui.widget.DecisionDialog;
import com.ap.ui.widget.MessageDialog;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameUIManager {
    public static GameUIManager instance = new GameUIManager();
    private Stage stage;
    private Skin skin;
    private Engine engine;
    private AudioService audioService;

    public void setup(Stage stage, Skin skin, AudioService audioService) {
        this.stage = stage;
        this.skin = skin;
        this.audioService = audioService;
    }

    public DecisionDialog showDecisionDialog(String question, Runnable whenOk, Runnable whenCancel) {
        var controller = engine.getSystem(ControllerSystem.class);
        if(controller != null) {
            controller.reset();
        }
        DecisionDialog dialog = new DecisionDialog("",question, skin, whenOk, whenCancel, audioService);
        stage.addActor(dialog);
        return dialog;
    }

    public void showMessageDialog(String text) {
        var controller = engine.getSystem(ControllerSystem.class);
        if(controller != null) {
            controller.reset();
        }
        MessageDialog dialog = new MessageDialog("",text, skin,stage, audioService);
        stage.addActor(dialog);
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
