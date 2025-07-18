package com.ap.ui.view;

import com.ap.audio.AudioService;
import com.ap.ui.actor.SimpleDialog;
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
        setupUI();
    }


    @Override
    protected void setupUI() {
        setFillParent(true);
        setBackground(skin.getDrawable("Panorama"));

        Window window = new Window("", skin);
        TextField usernameField = new TextField("", skin);
        usernameField.getStyle().cursor = UIUtils.getBlinkTextField(skin);

        window.add(new Label("Username: ", skin));
        window.add(usernameField).pad(8);

        window.row();

        window.add(new Label("Email: ", skin));
        TextField emailField = new TextField("", skin);
        window.add(emailField).pad(8);

        window.row();

        window.add(new Label("Password: ", skin));
        TextField passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        window.add(passwordField).pad(8);

        window.row();

        window.add(new Label("Confirm Password: ", skin));
        TextField confirmPasswordField = new TextField("", skin);
        confirmPasswordField.setPasswordMode(true);
        confirmPasswordField.setPasswordCharacter('*');
        window.add(confirmPasswordField).pad(8);

        window.row();

        window.add(new Label("Nickname: ", skin));
        TextField nicknameField = new TextField("", skin);
        window.add(nicknameField).pad(8);

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

        window.add(new Label("What is name of you first school?", skin, "font20"));
        TextField securityQuestion = new TextField("", skin);
        window.add(securityQuestion).pad(8);

        window.row();

        TextButton submit = new TextButton("ok", skin, "font36");
        AddHoverScale(submit, audioService);
        window.add(submit).colspan(2).pad(8);

        // Setup events
        OnClick(submit, () -> {
            var result = viewModel.submit(usernameField.getText(),
                    emailField.getText(),
                    passwordField.getText(),
                    confirmPasswordField.getText(),
                    nicknameField.getText(),
                    securityQuestion.getText());
            SimpleDialog dialog = new SimpleDialog("Message", result.getData(), skin);
            dialog.setupEvent(() -> {
                if(result.isSuccess()) {
                    viewModel.registerSuccessful(usernameField.getText());
                }
            });
            dialog.show(stage);
        });
        OnClick(maleButton, () -> {
            viewModel.maleButtonClicked(maleButton.isChecked());
            femaleButton.setChecked(false);
        });
        OnClick(femaleButton, () -> {
            viewModel.femaleButtonClicked(femaleButton.isChecked());
            maleButton.setChecked(false);
        });

        add(window);

        setupLoginPage();

    }

    private void setupLoginPage() {
        row();
        add(new Label("Do you have an account?", skin));
        row();
        var loginPageButton =new TextButton("Login page", skin);
        add(loginPageButton);
        AddHoverScale(loginPageButton, audioService);
        OnClick(loginPageButton, viewModel::openLoginPage);
    }
}
