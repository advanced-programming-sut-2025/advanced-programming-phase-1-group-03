package com.ap.ui.widget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class TooltipHelper extends Table {
    private static TooltipHelper INSTANCE = null;
    private Label title;
    private TooltipHelper(Skin skin) {
        super(skin);

        title = new Label("I'm tooltip", skin);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0.7f); // Any color
        pixmap.fill();

        Texture texture = new Texture(pixmap);
        Drawable background = new TextureRegionDrawable(new TextureRegion(texture));
        setBackground(background);

        pixmap.dispose();

        Label.LabelStyle newStyle = new Label.LabelStyle(title.getStyle());
        newStyle.fontColor = Color.WHITE;
        title.setStyle(newStyle);
        title.setFontScale(1f);
        add(title).pad(15);
        pack();
        setPosition(10, 10);
        setVisible(false);
    }
    public static TooltipHelper getTooltip(Skin skin) {
        if (INSTANCE == null) {
            INSTANCE = new TooltipHelper(skin);
        }
        return INSTANCE;
    }

    public Label getTitle() {
        return title;
    }
}
