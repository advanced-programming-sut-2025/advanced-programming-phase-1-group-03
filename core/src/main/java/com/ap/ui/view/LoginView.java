package com.ap.ui.view;

import com.ap.audio.AudioService;
import com.ap.model.Result;
import com.ap.ui.actor.SimpleDialog;
import com.ap.ui.model.LoginViewModel;
import com.ap.utils.UIUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class LoginView extends AbstractView<LoginViewModel> {
    private final AudioService audioService;
    public LoginView(Stage stage, Skin skin, LoginViewModel viewModel, AudioService audioService) {
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

        window.add(new Label("Password: ", skin));
        TextField passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        window.add(passwordField).pad(8);


        window.row();

        window.add(new Label("Remember me", skin)).pad(8);
        CheckBox rememberCheckbox = new CheckBox("", skin);
        window.add(rememberCheckbox);

        window.row();

        TextButton submit = new TextButton("Submit", skin, "font36");
        window.add(submit).colspan(2).pad(8);

        // Setup events
        OnClick(submit, () -> {
            var result = viewModel.submit(usernameField.getText(),
                    passwordField.getText());
            SimpleDialog dialog = new SimpleDialog("Message", result.getData(), skin);
            dialog.setupEvent(() -> {
                if(result.isSuccess()) {
                    viewModel.loginSuccessful(usernameField.getText(), rememberCheckbox.isChecked());
                }
            });
            dialog.show(stage);
        });
        add(window);
        setupRegisterPage();
    }

    private void setupRegisterPage() {
        row();
        add(new Label("You don't have an account?", skin));
        row();
        var registerButton =new TextButton("registration page", skin);
        add(registerButton);
        AddHoverScale(registerButton, audioService);
        OnClick(registerButton, viewModel::openRegisterPage);
    }
}
