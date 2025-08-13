package com.ap.ui.widget;

import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.screen.GameScreen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

public class EmojiPanel extends Window {

    private final Stage stage;
    private final AssetService assetService;
    private final GameScreen game;
    private final Skin skin;
    private final TextureAtlas atlas;
    private boolean isShowing = false;

    private static EmojiPanel instance;

    private static final float PADDING = 10f;
    private static final Integer[] EMOJI_NAMES = { 0, 16, 5, 3, 6 };

    private EmojiPanel(Skin skin, Stage stage, AssetService assetService, GameScreen game) {
        super("", skin);
        this.game = game;
        this.stage = stage;
        this.skin = skin;
        this.assetService = assetService;
        atlas = assetService.get(AtlasAsset.Emotes);

        this.defaults().pad(5);

        Table emojiTable = new Table();
        for (Integer emojiName : EMOJI_NAMES) {
            TextureRegion region = atlas.findRegion("emote_" + emojiName, 1);
            ImageButton btn = new ImageButton(new TextureRegionDrawable(region));

            float size = 48f;
            btn.getImage().setScaling(Scaling.fit);
            btn.getImageCell().size(size, size);
            emojiTable.add(btn).size(size).pad(5).row();

            btn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.getGameClient().getSender().applyReaction(emojiName);
                    System.out.println("Emoji clicked: " + emojiName);
                }
            });
        }

        TextField messageField = new TextField("", skin);
        messageField.setMessageText("Write your message...");
        messageField.setTextFieldListener((textField, c) -> {
            if (c == '\r' || c == '\n') {
                game.getGameClient().getSender().applyReaction(textField.getText());
                System.out.println("Message sent: " + textField.getText());
                textField.setText("");
            }
        });

        this.add(new Label("Select Emoji", skin)).row();
        this.add(emojiTable).row();
        this.add(messageField).width(200).padTop(10).row();

        this.pack();
        this.setPosition(PADDING, PADDING);
        this.setMovable(false);
        this.setResizable(false);
    }

    public static EmojiPanel getInstance(Skin skin, Stage stage, AssetService assetService, GameScreen game) {
        if (instance == null) {
            instance = new EmojiPanel(skin, stage, assetService, game);
        }
        return instance;
    }

    public void close() {
        this.addAction(Actions.sequence(
                Actions.fadeOut(0.3f),
                Actions.removeActor()
        ));
        isShowing = false;
    }

    public void show() {
        this.getColor().a = 0f;
        this.addAction(Actions.fadeIn(0.3f));
        stage.addActor(this);
        isShowing = true;
    }

    public void toggle() {
        if (!isShowing)
            show();
        else
            close();
    }
}

