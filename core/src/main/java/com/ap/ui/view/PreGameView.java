package com.ap.ui.view;

import com.ap.asset.AssetService;
import com.ap.audio.AudioService;
import com.ap.ui.model.PreGameViewModel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

public class PreGameView extends AbstractView<PreGameViewModel> {
    private final AudioService audioService;
    private final AssetService assetService;

    private Image selectedMapImage = null;
    private String selectedMapName = null;
    private Label statusLabel;

    public PreGameView(Stage stage, Skin skin, PreGameViewModel viewModel, AudioService audioService, AssetService assetService) {
        super(stage, skin, viewModel);
        this.audioService = audioService;
        this.assetService = assetService;
        setupUI();
    }

    @Override
    protected void setupUI() {
        setFillParent(true);
        setBackground(skin.getDrawable("Panorama"));

        Table rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.pad(20);


        Table left = new Table(skin);
        left.defaults().pad(10);

        Label nameLabel = new Label("Player Names:", skin);
        TextField player1Field = new TextField("", skin);
        player1Field.setMessageText("Player 1");

        TextField player2Field = new TextField("", skin);
        player2Field.setMessageText("Player 2");

        TextField player3Field = new TextField("", skin);
        player3Field.setMessageText("Player 3");

        left.add(nameLabel).colspan(1).align(Align.center).row();
        left.add(player1Field).width(200).row();
        left.add(player2Field).width(200).row();
        left.add(player3Field).width(200).row();


        Table right = new Table(skin);
        right.defaults().pad(20);

        Label selectMapLabel = new Label("Select Map", skin);
        right.add(selectMapLabel).row();

        Table mapsTable = new Table();

        Image forestMap = createMapImage("graphics/ForestMap.png", "forest");
        Image riverMap = createMapImage("graphics/RiverLandMap.png", "river");

        mapsTable.add(forestMap).size(200, 150).pad(10);
        mapsTable.add(riverMap).size(200, 150).pad(10);

        right.add(mapsTable).row();


        rootTable.add(left).top().left().expand().fill();
        rootTable.add(right).top().right().expand().fill();


        rootTable.row();
        Table buttonRow = new Table();

        TextButton newGameButton = new TextButton("New Game", skin);
        newGameButton.getLabel().setFontScale(1.5f);
        newGameButton.setSize(150, 60);

        TextButton loadGameButton = new TextButton("Load Game", skin);
        loadGameButton.getLabel().setFontScale(1.5f);
        loadGameButton.setSize(150, 60);

        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedMapName == null) {
                    statusLabel.setText("Please select a map.");
                    return;
                }
                // TODO: Logic for starting a new game
                System.out.println("Starting New Game on map: " + selectedMapName);
                statusLabel.setText("Starting New Game...");
            }
        });

        loadGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // TODO: Logic for loading saved game
                System.out.println("Loading Game...");
                statusLabel.setText("Loading saved game...");
            }
        });

        buttonRow.add(newGameButton).padRight(30).width(200).height(60);
        buttonRow.add(loadGameButton).width(200).height(60);

        rootTable.add(buttonRow).colspan(2).padTop(40).center();


        rootTable.row();
        statusLabel = new Label("", skin);
        statusLabel.setColor(Color.RED);
        statusLabel.setFontScale(1.1f);
        rootTable.add(statusLabel).colspan(2).padTop(20).center();

        addActor(rootTable);
    }

    private Image createMapImage(String texturePath, String mapName) {
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        Image image = new Image(texture);
        image.setScaling(Scaling.fit);

        image.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                image.setScale(1.05f); // افکت hover
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                image.setScale(1f);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedMapImage != null) {
                    selectedMapImage.setColor(Color.WHITE);
                }
                selectedMapImage = image;
                selectedMapName = mapName;
                image.setColor(Color.GOLD);
                statusLabel.setText("");
            }
        });

        return image;
    }
}
