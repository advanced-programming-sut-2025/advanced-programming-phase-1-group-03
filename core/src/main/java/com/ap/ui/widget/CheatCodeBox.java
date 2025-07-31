package com.ap.ui.widget;

import com.ap.Constraints;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

public class CheatCodeBox extends Group {

    private Stage stage;
    private Skin skin;
    private boolean isShowing;
    private CheatCodeBox instance;
    private static ArrayList<String> history = new ArrayList<>();

    private final float maxWidth;
    private final int maxLine;
    private final float tileHeight = 48;
    private Table historyTable;
    private boolean shouldUpdate;
    private Texture backgroundTexture;
    private TextField input;
    private CheatCodeBox manager;

    public CheatCodeBox(Stage stage, Skin skin) {
        this.stage = stage;
        this.skin = skin;
        maxLine = 10;
        maxWidth = Constraints.WORLD_WIDTH_RESOLUTION / 2f;
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0.7f); // Any color
        pixmap.fill();
        backgroundTexture = new Texture(pixmap);
        pixmap.dispose();
    }

    private void setupUI() {
        setPosition(0, 0);

        input = new TextField("", skin);
        input.setSize(maxWidth + 8, tileHeight);
//        input.setMessageText("Enter command...");

        input.setPosition(-8, -4);
        input.addListener(new InputListener() {
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.F3) {
                    stage.unfocus(input);
                    manager.toggle();
                    return true;
                }
                if (keycode == Input.Keys.ENTER) {
                    String s = input.getText();
                    history.add(s);
                    shouldUpdate = true;
                    input.setText("");
                    return true;
                }
                return false;
            }
        });

        addActor(input);
        shouldUpdate = true;
        updateHistoryTable();
    }

    private void updateHistoryTable() {
        if (!shouldUpdate) return;
        shouldUpdate = false;

        if (historyTable != null) {
            removeActor(historyTable);
        }

        historyTable = new Table();

        Drawable background = new TextureRegionDrawable(new TextureRegion(backgroundTexture));
        historyTable.setBackground(background);
        historyTable.top().left();

        float height = 0;
        for (int i = Math.max(0, history.size() - maxLine); i < history.size(); i++) {
            String s = history.get(i);
            Label label = new Label("~" + s, skin);
            Label.LabelStyle newStyle = new Label.LabelStyle(label.getStyle());
            newStyle.fontColor = Color.GREEN;
            label.setStyle(newStyle);
            label.setWrap(true);
            label.setWidth(maxWidth);
            height += label.getPrefHeight();
            historyTable.add(label).width(maxWidth).left().row();
        }

        historyTable.setWidth(maxWidth);
        historyTable.setHeight(height);
        historyTable.setPosition(0, tileHeight - 4);
        addActor(historyTable);

    }

    public TextField getInput() {
        return input;
    }

    public void setManager(CheatCodeBox manager) {
        this.manager = manager;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (shouldUpdate) updateHistoryTable();
        super.draw(batch, parentAlpha);
    }

    public void toggle() {
        if (!isShowing) {
            instance = new CheatCodeBox(stage, skin);
            instance.setManager(this);
            instance.setupUI();
            stage.addActor(instance);
            stage.setKeyboardFocus(instance.getInput());
        } else {
            stage.getActors().removeValue(instance, true);
        }
        isShowing = !isShowing;
    }

    public boolean isShowing() {
        return isShowing;
    }
}
