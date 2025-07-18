package com.ap.ui.view;

import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.audio.AudioService;
import com.ap.ui.actor.SimpleDialog;
import com.ap.ui.model.ProfileViewModel;
import com.ap.ui.model.SignupViewModel;
import com.ap.utils.UIUtils;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;
import org.w3c.dom.Text;

public class ProfileView extends AbstractView<ProfileViewModel> {
    private final AudioService audioService;
    private final AssetService assetService;
    private final TextureAtlas avatarAtlas;
    private Image avatar;

    public ProfileView(Stage stage, Skin skin, ProfileViewModel viewModel,
                       AudioService audioService, AssetService assetService) {

        super(stage, skin, viewModel);
        this.audioService = audioService;
        this.assetService = assetService;
        avatarAtlas = assetService.get(AtlasAsset.Avatars);
        setupUI();
    }


    @Override
    protected void setupUI() {
        setFillParent(true);
        setBackground(skin.getDrawable("Panorama"));

        Window window = new Window("", skin);

        avatar = new Image(avatarAtlas.findRegion("avatar" + viewModel.getAvatarIndex()));
        avatar.setScaling(Scaling.fit);
        window.add(avatar).maxWidth(100).maxHeight(100).colspan(3);
        window.row();
        var changeAvatarButton = new TextButton("Change Avatar", skin);
        window.add(changeAvatarButton).colspan(3).pad(10).padBottom(5);

        window.row();

        window.add(new Label("Username: ", skin));
        window.add(new Label(viewModel.getUsername(), skin));
        var usernameChangeBtn = new TextButton("Change", skin);
        window.add(usernameChangeBtn).pad(10);

        window.row();

        window.add(new Label("Nickname: ", skin));
        window.add(new Label(viewModel.getNickname(), skin));
        var nicknameChangeBtn = new TextButton("Change", skin);
        window.add(nicknameChangeBtn).pad(10);

        window.row();

        window.add(new Label("Email: ", skin));
        window.add(new Label(viewModel.getEmail(), skin));
        var emailChangeBtn = new TextButton("Change", skin);
        window.add(emailChangeBtn).pad(10);

        window.row();

        window.add(new Label("Games count: ", skin));
        window.add(new Label(String.valueOf(viewModel.getGamesCount()), skin)).pad(10);

        window.row();

        window.add(new Label("Maximum coin in a game: ", skin));
        window.add(new Label(String.valueOf(viewModel.getMaximumCoin()), skin)).pad(10);

        window.row();

        var changePasswordBtn = new TextButton("Change Password", skin);
        window.add(changePasswordBtn).pad(10).colspan(3);

        add(window);

        // Setup events
        OnClick(changeAvatarButton, this::openAvatarChooseWindow);
        OnClick(usernameChangeBtn, this::openChangeUsernameDialog);
        OnClick(nicknameChangeBtn, this::openChangeNicknameDialog);
        OnClick(emailChangeBtn, this::openEmailChangeDialog);
        OnClick(changePasswordBtn, this::openChangePasswordDialog);
    }

    private void openChangePasswordDialog() {
        SimpleDialog dialog = new SimpleDialog("", "Enter your new password:", skin);
        TextField passwordField = new TextField("", skin);
        var generatePasswordBtn = new TextButton("Generate", skin);
        passwordField.getStyle().cursor = UIUtils.getBlinkTextField(skin);
        dialog.addToContent(passwordField);
        dialog.addToContent(generatePasswordBtn);

        OnClick(generatePasswordBtn, ()->{
            new Thread(() -> {
                String password = viewModel.generatePassword();
                passwordField.setText(password);
            }).start();
        });

        dialog.setupEvent(() -> {
            var result = viewModel.changePassword(passwordField.getText());
            new SimpleDialog("", result.getData(), skin).show(stage);
        });
        dialog.show(stage);
    }

    private void openEmailChangeDialog() {
        SimpleDialog dialog = new SimpleDialog("", "Enter your new email: ", skin);
        TextField emailField = new TextField("", skin);
        dialog.addToContent(emailField);
        dialog.setupEvent(() -> {
            var result = viewModel.changeEmail(emailField.getText());
            new SimpleDialog("", result.getData(), skin).setupEvent(viewModel::reload).show(stage);
        });
        dialog.show(stage);
    }

    private void openChangeNicknameDialog() {
        SimpleDialog dialog = new SimpleDialog("", "Enter your new nickname: ", skin);
        TextField nicknameField = new TextField("", skin);
        dialog.addToContent(nicknameField);
        dialog.setupEvent(() -> {
            var result = viewModel.changeNickname(nicknameField.getText());
            new SimpleDialog("", result.getData(), skin).setupEvent(viewModel::reload).show(stage);
        });
        dialog.show(stage);
    }

    private void openChangeUsernameDialog() {
        SimpleDialog dialog = new SimpleDialog("", "Enter your new username: ", skin);
        TextField usernameField = new TextField("", skin);
        dialog.addToContent(usernameField);
        dialog.setupEvent(() -> {
            var result = viewModel.changeUsername(usernameField.getText());
            new SimpleDialog("", result.getData(), skin).setupEvent(viewModel::reload).show(stage);
        });
        dialog.show(stage);
    }

    private void openAvatarChooseWindow() {
        SimpleDialog dialog = new SimpleDialog("", "", skin);
        dialog.getContentTable().add(new Label("Choose your avatar:", skin)).colspan(3).row();

        for(int i = 0; i < 9; i++) {
            final int index = i;
            Image avatar = new Image(avatarAtlas.findRegion("avatar" + i));
            dialog.getContentTable().add(avatar).maxHeight(60).maxWidth(60).pad(10);
            AddHoverScale(avatar, audioService);
            if(i % 3 == 2)
                dialog.getContentTable().row();
            OnClick(avatar, () -> {
                dialog.hide();
                viewModel.chooseAvatar(index);
                viewModel.reload();
            });
        }
        dialog.show(stage);
    }

}
