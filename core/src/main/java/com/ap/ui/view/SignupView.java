package com.ap.ui.view;

import com.ap.audio.AudioService;
import com.ap.ui.model.MainViewModel;
import com.ap.ui.model.SignupViewModel;
import com.ap.utils.UIUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.github.tommyettinger.textra.Styles;

public class SignupView extends AbstractView<SignupViewModel> {
    private final AudioService audioService;
    public SignupView(Stage stage, Skin skin, SignupViewModel viewModel, AudioService audioService) {
        super(stage, skin, viewModel);
        this.audioService = audioService;
    }


    @Override
    protected void setupUI() {
        setFillParent(true);
        setBackground(skin.getDrawable("Panorama"));

        Window window = new Window("", skin);
        TextField usernameField = new TextField("", skin);
        usernameField.getStyle().cursor = UIUtils.getBlinkTextField(skin);

        window.add(new Label("Username: ", skin));
        window.add(usernameField).pad(10);

        window.row();

        window.add(new Label("Email: ", skin));
        TextField emailField = new TextField("", skin);
        window.add(emailField).pad(10);

        window.row();

        window.add(new Label("Password: ", skin));
        TextField passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        window.add(passwordField).pad(10);

        window.row();

        window.add(new Label("Confirm Password: ", skin));
        TextField confirmPasswordField = new TextField("", skin);
        confirmPasswordField.setPasswordMode(true);
        confirmPasswordField.setPasswordCharacter('*');
        window.add(confirmPasswordField).pad(10);

        window.row();

        window.add(new Label("Nickname: ", skin));
        TextField nicknameField = new TextField("", skin);
        window.add(nicknameField).pad(10);

        window.row();

        window.add(new Label("Gender: ", skin));
        var maleStyle = new CheckBox.CheckBoxStyle();

        var femaleStyle = new CheckBox.CheckBoxStyle();


        maleStyle.checkboxOn = skin.getDrawable("maleSelected");
        maleStyle.checkboxOff = skin.getDrawable("male");
        maleStyle.font = skin.getFont("font24");


        femaleStyle.checkboxOn = skin.getDrawable("femaleSelected");
        femaleStyle.checkboxOff = skin.getDrawable("female");
        femaleStyle.font = skin.getFont("font24");

        CheckBox maleButton = new CheckBox("", maleStyle);
        CheckBox femaleButton = new CheckBox("", femaleStyle);

        maleButton.setTransform(true);
        maleButton.setScale(0.75f);
        femaleButton.setTransform(true);
        femaleButton.setScale(0.75f);

        Table genderButtonTable = new Table();
        genderButtonTable.add(maleButton).pad(2);
        genderButtonTable.add(femaleButton).pad(2);
        window.add(genderButtonTable);

        window.row();

        TextButton submit = new TextButton("ok", skin, "font36");
        window.add(submit).colspan(2).pad(20);
        add(window);

        // Setup events
        OnClick(submit, () -> {
            viewModel.submit(usernameField.getText(),
                    emailField.getText(),
                    passwordField.getText(),
                    confirmPasswordField.getText(),
                    nicknameField.getText());
        });
        OnClick(maleButton, () -> {
            viewModel.maleButtonClicked();
            femaleButton.setChecked(false);
        });
        OnClick(femaleButton, () -> {
            viewModel.femaleButtonClicked();
            maleButton.setChecked(false);
        });
    }
}
