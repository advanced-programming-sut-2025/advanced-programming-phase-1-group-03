package com.ap.ui.widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class VoteKickPopup extends Window {

    private static final float PADDING = 10f;

    public interface VoteListener {
        void onAgree(int targetId, String targetName, String requesterName);
        void onDisagree(int targetId, String targetName, String requesterName);
    }

    public VoteKickPopup(String requesterName, String targetName, int targetId,
                         Skin skin, Stage stage, VoteListener listener) {
        super("Vote Kick", skin);

        String message = requesterName + " Requested to kick " + targetName +
                " (ID: " + targetId;
        Label messageLabel = new Label(message, skin);
        messageLabel.setWrap(true);

        TextButton agreeButton = new TextButton("Agree", skin);
        TextButton disagreeButton = new TextButton("Disagree", skin);

        agreeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                listener.onAgree(targetId, targetName, requesterName);
                close();
            }
        });

        disagreeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                listener.onDisagree(targetId, targetName, requesterName);
                close();
            }
        });

        this.defaults().pad(5);
        this.add(messageLabel).width(300).colspan(2).row();
        this.add(agreeButton).padTop(10).padRight(5);
        this.add(disagreeButton).padTop(10).padLeft(5);

        this.pack();

        float x = (stage.getViewport().getWorldWidth() - this.getWidth()) / 2;
        float y = (stage.getViewport().getWorldHeight() - this.getHeight()) / 2;
        this.setPosition(x, y);

        this.setMovable(false);
        this.setResizable(false);
    }

    private void close() {
        this.addAction(Actions.sequence(
                Actions.fadeOut(0.3f),
                Actions.removeActor()
        ));
    }

    public static void show(Stage stage, Skin skin, String requesterName, String targetName,
                            int targetId, VoteListener listener) {
        VoteKickPopup popup = new VoteKickPopup(requesterName, targetName, targetId, skin, stage, listener);
        popup.getColor().a = 0f;
        popup.addAction(Actions.fadeIn(0.3f));
        stage.addActor(popup);
    }
}
