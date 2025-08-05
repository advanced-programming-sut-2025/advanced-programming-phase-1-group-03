package com.ap.client.ui.view;

import com.ap.client.Constraints;
import com.ap.client.asset.AssetService;
import com.ap.client.asset.AtlasAsset;
import com.ap.client.asset.TextureAsset;
import com.ap.client.audio.AudioService;
import com.ap.client.ui.model.JoiningViewModel;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;
import java.util.List;

public class JoiningView extends AbstractView<JoiningViewModel> {

    private final AudioService audioService;
    private final AssetService assetService;
    private final TextureAtlas atlas;
    private final Texture gridTexture;

    private List<Player> players = new ArrayList<>();
    private TextButton startGameButton;
    private Window window;

    public JoiningView(Stage stage, Skin skin, JoiningViewModel viewModel, AudioService audioService, AssetService assetService) {
        super(stage, skin, viewModel);
        this.audioService = audioService;
        this.assetService = assetService;
        this.atlas = assetService.get(AtlasAsset.Avatars);
        this.gridTexture = assetService.get(TextureAsset.Grid2); // load grid2 texture

        setFillParent(true);
        setBackground(skin.getDrawable("Panorama"));

        setupConnectingUI();

        viewModel.setUIRunnable(this::setupUI);
    }

    @Override
    protected void setupUI() {
        clearChildren();

        players = viewModel.getPlayers();

        window = new Window("", skin);
        window.setMovable(false);
        window.setResizable(false);
        window.pad(20);
        window.setSize(500, 500);

        // Set window background to grid2 texture
        TextureRegion gridRegion = new TextureRegion(gridTexture);
        window.setBackground(new TextureRegionDrawable(gridRegion));

        Table grid = new Table();

        // 👉 You can control top spacing here
        Table topRow = new Table();
        topRow.padTop(/** <-- control top margin here, e.g., 100 */ 100);

        // 👉 You can control horizontal spacing between first row players here
        topRow.add(createPlayerSlot(0)).padRight(/** right padding */ -50).expand().fill();
        topRow.add(createPlayerSlot(1)).padLeft(/** left padding */ -50).expand().fill();

        // 👉 You can control vertical spacing between rows here
        Table bottomRow = new Table();
        bottomRow.padTop(/** <-- spacing between rows */ -100);

        // 👉 You can control horizontal spacing between second row players here
        bottomRow.add(createPlayerSlot(2)).padRight(/** right padding */ -50).expand().fill();
        bottomRow.add(createPlayerSlot(3)).padLeft(/** left padding */ -50).expand().fill();

        grid.add(topRow).expand().fill().row();
        grid.add(bottomRow).expand().fill().row();

        // Start button
        boolean amIHost = viewModel.amIHost();
        if(amIHost) {
            startGameButton = new TextButton("Start Game", skin);
            startGameButton.setDisabled(true);
            OnClick(startGameButton, viewModel::sendStartGame);
        }


        window.add(grid).expand().fill().padBottom(20).row();
        window.add(startGameButton).width(200).height(50);

        add(window);
    }



    private Table createPlayerSlot(int index) {
        Table slot = new Table(skin);

        if (index < players.size()) {
            Player p = players.get(index);
            slot.add(p.getView(skin, assetService.get(AtlasAsset.Avatars))).expand().center();
        } else {
            slot.add(new Label("Waiting...", skin)).center();
        }

        return slot;
    }

    private void checkStartGameCondition() {
        startGameButton.setDisabled(players.size() < 2);
    }

    public static class Player {
        private final String name;
        private final int avatarIndex;

        public Player(String name, int avatarIndex) {
            this.name = name;
            this.avatarIndex = avatarIndex;
        }

        public Table getView(Skin skin, TextureAtlas atlas) {
            Table table = new Table(skin);

            var avatar = atlas.findRegion("avatar" + avatarIndex);
            Image avatarImage = new Image(new TextureRegionDrawable(avatar));
            avatarImage.setSize(64, 64);

            Label nameLabel = new Label(name, skin);

            table.add(avatarImage).size(64, 64).row();
            table.add(nameLabel).padTop(10);

            return table;
        }
    }
}
