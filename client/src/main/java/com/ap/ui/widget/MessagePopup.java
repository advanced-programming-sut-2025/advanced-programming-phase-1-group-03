package com.ap.ui.widget;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.Gdx;

public class MessagePopup extends Window {

    private static final float DISPLAY_TIME = 4f;
    private static final float PADDING = 10f;

    public MessagePopup(String sender, String message, Skin skin, Stage stage) {
        super("", skin);

        Label senderLabel = new Label("From: " + sender, skin, "font36");
        Label messageLabel = new Label(message, skin);
        messageLabel.setWrap(true);

        this.defaults().pad(5);
        this.add(senderLabel).row();
        this.add(messageLabel).width(200).row();

        this.pack();

        float x = stage.getViewport().getWorldWidth() - this.getWidth() - PADDING;
        float y = PADDING;
        this.setPosition(x, y);

        this.setMovable(false);
        this.setResizable(false);

        this.addListener(event -> {
            if (event.toString().equals("touchDown")) {
                this.addAction(Actions.sequence(
                        Actions.fadeOut(0.3f),
                        Actions.removeActor()
                ));
                return true;
            }
            return false;
        });

        this.addAction(Actions.sequence(
                Actions.delay(DISPLAY_TIME),
                Actions.fadeOut(0.5f),
                Actions.removeActor()
        ));
    }

    public static void show(Stage stage, Skin skin, String sender, String message) {
        MessagePopup popup = new MessagePopup(sender, message, skin, stage);
        popup.getColor().a = 0f;
        popup.addAction(Actions.fadeIn(0.3f));
        stage.addActor(popup);
    }
}