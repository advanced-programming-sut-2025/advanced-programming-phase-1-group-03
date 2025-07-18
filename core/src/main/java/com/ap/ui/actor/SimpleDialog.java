package com.ap.ui.actor;

import com.ap.screen.AbstractScreen;
import com.ap.ui.view.AbstractView;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class SimpleDialog extends Dialog {
    private final String title, content;
    private final Skin skin;
    private AbstractView.OnEventConsumer event = null;

    public SimpleDialog(String title, String content, Skin skin) {
        super(title, getStyle(skin));
        this.title = title;
        this.content = content;
        this.skin = skin;
    }

    private static WindowStyle getStyle(Skin skin) {
        WindowStyle style = new WindowStyle();
        style.background = skin.getDrawable("window2");
        style.titleFont = skin.getFont("font24");
        return style;
    }

    public void setupEvent(AbstractView.OnEventConsumer event) {
        this.event = event;
    }
    private void setupUI() {
        Label label = new Label(content, skin);
        label.setColor(skin.getColor("Text"));
        text(label);

        var okButtonStyle = new ImageButton.ImageButtonStyle();
        okButtonStyle.down = skin.getDrawable("ok_button");
        okButtonStyle.up = skin.getDrawable("ok_button");
        okButtonStyle.over = skin.getDrawable("ok_button_hover");
        ImageButton okButton = new ImageButton(okButtonStyle);
        AbstractView.AddHoverScale(okButton, 0.8f, 1.2f, 1f);
        okButton.setTransform(true);
        okButton.setScale(0.8f);

        if(event != null)
            AbstractView.OnClick(okButton, event);

        button(okButton);
    }


    public Dialog show(Stage stage) {
        setupUI();
        return super.show(stage);
    }

}
