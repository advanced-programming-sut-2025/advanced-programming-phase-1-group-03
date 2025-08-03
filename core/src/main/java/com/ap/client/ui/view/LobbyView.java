package com.ap.client.ui.view;

import com.ap.client.asset.AssetService;
import com.ap.client.asset.AtlasAsset;
import com.ap.client.audio.AudioService;
import com.ap.client.ui.model.LobbyViewModel;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

import java.util.ArrayList;
import java.util.List;

public class LobbyView extends AbstractView<LobbyViewModel> {
    private final AudioService audioService;
    private final AssetService assetService;
    private final TextureAtlas atlas;

    private Window window;
    private Label statusLabel;

    public LobbyView(Stage stage, Skin skin, LobbyViewModel viewModel, AudioService audioService, AssetService assetService) {
        super(stage, skin, viewModel);
        this.audioService = audioService;
        this.assetService = assetService;
        this.atlas = assetService.get(AtlasAsset.Avatars);
        setupUI();
    }


    @Override
    protected void setupUI() {
        setFillParent(true);
        setBackground(skin.getDrawable("Panorama"));

        window = new Window("Lobby", skin, "window2");
        window.setSize(800, 500);
        window.setMovable(false);
        window.setResizable(false);

        window.setPosition(
                stage.getWidth() / 2f - window.getWidth() / 2f,
                stage.getHeight() / 2f - window.getHeight() / 2f
        );

        List<ServerEntry> servers = new ArrayList<>();
        servers.add(new ServerEntry("Alice", "avatar0", List.of("Alice", "Bob")));
        servers.add(new ServerEntry("Max", "avatar1", List.of("Max", "John", "Clara")));
        servers.add(new ServerEntry("Nina", "avatar2", List.of("Nina")));
        servers.add(new ServerEntry("Dev", "avatar3", List.of("Dev", "Lina", "Rob", "Jade")));

        Table serverTable = new Table();
        serverTable.defaults().pad(10).left();

        for (int i = 0; i < servers.size(); i++) {
            ServerEntry entry = servers.get(i);

            Table row = new Table();

            TextureRegion avatarRegion = atlas.findRegion(entry.avatarRegionName);
            Image avatar = new Image(avatarRegion);
            avatar.setScaling(Scaling.fit);
            avatar.setSize(64, 64);

            Label nameLabel = new Label("Host: " + entry.hostName, skin);
            Label playersLabel = new Label("Players: " + String.join(", ", entry.players), skin);
            playersLabel.setWrap(true);

            row.add(avatar).size(64, 64).padRight(20);
            row.add(nameLabel).width(150).padRight(20).align(Align.left);
            row.add(playersLabel).width(400).align(Align.left);
            row.row();

            int finalIndex = i;
            row.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    statusLabel.setText("Selected Server: " + entry.hostName);
                }
            });

            serverTable.add(row).expandX().fillX().row();
        }

        ScrollPane scrollPane = new ScrollPane(serverTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setForceScroll(false, true);

        window.add(scrollPane).expand().fill().pad(10);
        window.row();

        Table buttonRow = new Table();
        TextButton hostButton = new TextButton("Host", skin);
        TextButton refreshButton = new TextButton("Refresh", skin);

        hostButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                statusLabel.setText("Hosting new server...");
            }
        });

        refreshButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                statusLabel.setText("Refreshing server list...");
            }
        });

        buttonRow.add(hostButton).width(150).padRight(20);
        buttonRow.add(refreshButton).width(150);
        window.add(buttonRow).pad(10);
        window.row();

        statusLabel = new Label("", skin);
        statusLabel.setColor(com.badlogic.gdx.graphics.Color.YELLOW);
        window.add(statusLabel).padBottom(10);
        window.row();

        stage.addActor(window);
    }

    private static class ServerEntry {
        public final String hostName;
        public final String avatarRegionName;
        public final List<String> players;

        public ServerEntry(String hostName, String avatarRegionName, List<String> players) {
            this.hostName = hostName;
            this.avatarRegionName = avatarRegionName;
            this.players = players;
        }
    }
}
