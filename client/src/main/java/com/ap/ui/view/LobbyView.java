package com.ap.ui.view;

import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.asset.TextureAsset;
import com.ap.audio.AudioService;
import com.ap.ui.actor.SimpleDialog;
import com.ap.ui.model.LobbyViewModel;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;

import java.util.ArrayList;
import java.util.List;

public class LobbyView extends AbstractView<LobbyViewModel> {
    private final AudioService audioService;
    private final AssetService assetService;
    private final TextureAtlas atlas;
    private final Texture grid;

    private List<ServerEntry> servers;

    private Window window;
    private Label statusLabel;

    public LobbyView(Stage stage, Skin skin, LobbyViewModel viewModel, AudioService audioService, AssetService assetService) {
        super(stage, skin, viewModel);
        this.audioService = audioService;
        this.assetService = assetService;
        this.atlas = assetService.get(AtlasAsset.Avatars);
        this.servers = new ArrayList<>();
        this.grid = assetService.get(TextureAsset.Grid);
        viewModel.setRoomsObserver(this::showRooms);
        setupUI();
    }


    @Override
    protected void setupUI() {

        setFillParent(true);
        setBackground(skin.getDrawable("Panorama"));

        showRooms();
    }

    public void showRooms() {
        clearChildren();

        servers = viewModel.getRooms();

        window = new Window("Lobby", skin, "window2");
        window.setMovable(false);
        window.setResizable(false);

        // Set window size manually and disable resizing behavior
        float screenWidth = 1100;
        float screenHeight = 630;
        window.setSize(screenWidth, screenHeight); // ← Force full resolution size
        window.setBounds(0, 0, screenWidth, screenHeight); // ← Force placement
        window.padTop(50); // Optional top padding for title

        Table serverTable = new Table();
        serverTable.top();
        serverTable.defaults().padBottom(6); // ← spacing between server rows

        for (int i = 0; i < servers.size(); i++) {
            ServerEntry entry = servers.get(i);

            TextureRegion avatarRegion = atlas.findRegion(entry.avatarRegionName);
            Image avatar = new Image(avatarRegion);
            avatar.setScaling(Scaling.fit);
            avatar.setSize(64, 64);

            Label nameLabel = new Label("Host: " + entry.hostName, skin);
            String playerText = "(" + entry.playersCount + ") ";
            Label playersLabel = new Label("Players: " + playerText, skin);
            playersLabel.setWrap(true);

            TextButton joinButton = new TextButton("Join", skin);
            joinButton.getLabel().setFontScale(0.95f);
            joinButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(!entry.isPrivate) {
                        joinRoom(entry.roomId, "");
                    } else {
                        openPasswordDialog(entry.roomId);
                    }
                }
            });

            Table content = new Table();
            content.defaults().center().pad(0);
            content.add(avatar).size(64, 64).padLeft(20);
            content.add(nameLabel).width(160).left().padLeft(15);
            content.add(playersLabel).width(500).left().padLeft(15);
            content.add(joinButton).width(90).height(40).padLeft(0);

            Image gridBg = new Image(grid);
            Stack stacked = new Stack();
            stacked.add(gridBg);
            stacked.add(content);

            serverTable.add(stacked).width(1100).height(130).padBottom(10).row(); // ← Increased height
        }

        ScrollPane scrollPane = new ScrollPane(serverTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setForceScroll(false, true);
        scrollPane.setScrollbarsOnTop(true);
        scrollPane.setOverscroll(false, false);
        scrollPane.setScrollY(0);
        scrollPane.layout();

        Table scrollContainer = new Table();
        scrollContainer.top().pad(30); // ← Add more padding around
        scrollContainer.add(scrollPane).width(screenWidth - 50).height(screenHeight - 220); // ← Constrain to fit screen
        window.add(scrollContainer).expand().fill().pad(10);
        window.row();

        Table bottomBar = new Table();
        TextButton hostButton = new TextButton("Host", skin);
        TextButton refreshButton = new TextButton("Refresh", skin);

        OnClick(hostButton, this::host);
        OnClick(refreshButton, viewModel::refresh);

        bottomBar.add(hostButton).width(150).padRight(20);
        bottomBar.add(refreshButton).width(150);
        window.add(bottomBar).pad(10);
        window.row();

        statusLabel = new Label("", skin);
        statusLabel.setColor(Color.YELLOW);
        window.add(statusLabel).padBottom(10);
        window.row();

        add(window); // ← Just add without fill
    }

    private void joinRoom(int roomId, String password) {
        var response = viewModel.joinRoom(roomId, password);
        if(!response.isSuccess()) {
            new SimpleDialog("", response.getData(), skin).show(stage);
        } else {
            viewModel.loadJoinScreen();
        }
    }

    private void openPasswordDialog(int roomId) {
        var dialog = new SimpleDialog("", "Enter the password of room: ", skin);
        var passwordField = new TextField("", skin);
        dialog.addToContent(passwordField);
        dialog.setupEvent(() -> {
            joinRoom(roomId, passwordField.getText());
        });
        dialog.show(stage);
    }

    private void host() {
        var dialog = new SimpleDialog("", "If you want a public room, leave the password empty", skin);
        Table table = new Table();
        var nameField = new TextField("", skin);
        var passwordField = new TextField("", skin);
        var visibility = new CheckBox("", skin);

        table.add(new Label("Name : ", skin));
        table.add(nameField).pad(2).row();
        table.add(new Label("Password : ", skin));
        table.add(passwordField).pad(2).row();
        table.add(new Label("Is visible?", skin));
        table.add(visibility).pad(2).row();

        dialog.addToContent(table);
        dialog.setupEvent(() -> {
            viewModel.hostServer(nameField.getText(), passwordField.getText(), visibility.isChecked());
        });
        dialog.show(stage);
    }

    public record ServerEntry(String hostName, String avatarRegionName, int playersCount, int roomId, boolean isPrivate) {
    }
}
