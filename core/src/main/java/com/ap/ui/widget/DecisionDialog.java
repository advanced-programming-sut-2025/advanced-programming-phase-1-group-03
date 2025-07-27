package com.ap.ui.widget;

import com.ap.Constraints;
import com.ap.asset.MusicAsset;
import com.ap.audio.AudioService;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;


public class DecisionDialog extends Window {
    private Label questionLabel;
    private Label yesLabel;
    private Label noLabel;
    private String fullText;
    private StringBuilder displayedText;
    private int charIndex;
    private float typingDelay = 0.05f;
    private boolean isTypingComplete = false;
    private Runnable whenOk, whenCancel;

    private AudioService audioService;

    public DecisionDialog(String title, String question, Skin skin, Runnable whenOk, Runnable whenCancel, AudioService audioService) {
        super(title, skin, "window2");

        this.audioService = audioService;
        audioService.playMusicMeanwhile(MusicAsset.Type);

        this.whenCancel = whenCancel;
        this.whenOk = whenOk;

        setModal(true);
        setMovable(false);

        setWidth(Constraints.WORLD_WIDTH_RESOLUTION);
        setHeight(Constraints.WORLD_HEIGHT_RESOLUTION / 4f);
        setPosition(0, 0);

        Table table = new Table();
        add(table).expand().fill();

        questionLabel = new Label("", skin);
        questionLabel.setAlignment(Align.center);
        table.add(questionLabel).expandX().pad(2);
        table.row();

        yesLabel = new Label("Yes", skin);
        noLabel = new Label("No", skin);
        yesLabel.setAlignment(Align.center);
        noLabel.setAlignment(Align.center);

        setupLabelInteractions(yesLabel);
        setupLabelInteractions(noLabel);

        Table yesNo = new Table();
        yesNo.add(yesLabel).pad(20);
        yesNo.add(noLabel);

        table.add(yesNo).expand().fill();

        setQuestionText(question);

    }

    private void setupLabelInteractions(final Label label) {
        label.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                label.setColor(Color.LIGHT_GRAY);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
                label.setColor(Color.WHITE);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (label == yesLabel) {
                    whenOk.run();
                } else if (label == noLabel) {
                    whenCancel.run();
                }
            }
        });
    }

    public void setQuestionText(String text) {
        fullText = text;
        displayedText = new StringBuilder();
        charIndex = 0;
        isTypingComplete = false;

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (charIndex < fullText.length()) {
                    displayedText.append(fullText.charAt(charIndex));
                    questionLabel.setText(displayedText.toString());
                    charIndex++;
                } else {
                    isTypingComplete = true;
                    audioService.stopMusic(MusicAsset.Type);
                    this.cancel();
                }
            }
        }, 0, typingDelay);
    }

    public boolean isTypingComplete() {
        return isTypingComplete;
    }


}
