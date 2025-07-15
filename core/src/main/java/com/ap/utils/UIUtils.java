package com.ap.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class UIUtils {
    public static Drawable getBlinkTextField(Skin skin) {
        Label oneCharSizeCalibrationThrowAway = new Label("|", skin);
        Label largeSizeCalibrationThrowAway = new Label("M", skin);
        Pixmap cursorColor = new Pixmap((int) oneCharSizeCalibrationThrowAway.getWidth(),
                (int) largeSizeCalibrationThrowAway.getHeight(),
                Pixmap.Format.RGB888);
        cursorColor.setColor(Color.BLACK);
        cursorColor.fill();
        return new Image(new Texture(cursorColor)).getDrawable();
    }
}
