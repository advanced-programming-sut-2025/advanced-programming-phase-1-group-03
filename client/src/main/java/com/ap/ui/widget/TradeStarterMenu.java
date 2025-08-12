package com.ap.ui.widget;

import com.ap.Constraints;
import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.asset.SoundAsset;
import com.ap.audio.AudioService;
import com.ap.packet.PlayerInfo;
import com.ap.screen.GameScreen;
import com.ap.ui.actor.SimpleDialog;
import com.ap.ui.widget.tabContents.AbstractContent;
import com.ap.ui.widget.tabContents.Tabs;
import com.ap.utils.Helper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.Random;

public class TradeStarterMenu extends AbstractContent {

    private GameScreen gameScreen;
    public TradeStarterMenu(GameScreen gameScreen) {
        super(gameScreen.getStage(), gameScreen.getAssetService(), gameScreen.getSkin(), gameScreen.getAudioService(), Constraints.tabWidth, Constraints.tabHeight, null);
        this.gameScreen = gameScreen;
        makeStructure();
        stage.addActor(this);
        setVisible(false);
    }

    private ArrayList<PlayerInfo> players;

    private Label title;
    private Label choosePlayerLabel;
    private Table playersTable;
    private ArrayList<Image> playersAvatar;
    private ArrayList<Label> playersUsername;
    private int n;
    private int selectedPlayer = -1;

    private float padLeft = 16;
    private float padDown = 16;

    private float avatarWidth = 95;
    private float avatarHeight = 95;

    private TextButton sendRequestButton;
    private TextButton historyButton;

    @Override
    public void makeStructure() {

        title = new Label("Trade", skin);
        title.setFontScale(2);

//        title.setColor(Color.BLACK);

        title.setPosition((width - title.getPrefWidth()) / 2, (height - title.getPrefHeight()));
        addActor(title);

//        sendRequestButton = new TextButton("Start Trade", skin);
//        sendRequestButton.setPosition(padLeft, padDown);
//
//        historyButton = new TextButton("Trade History", skin);
//        historyButton.setPosition(padLeft + sendRequestButton.getPrefWidth() + 10, padDown);
//
//        addActor(historyButton);
//        addActor(sendRequestButton);
//
//        sendRequestButton.addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//                if (selectedPlayer == -1) {
//                    var dialog = new SimpleDialog("", "select a player", skin);
//                    dialog.show(stage);
//                }
//            }
//        });

    }

    public void loadRequests() {

    }

    @Override
    public void loadData() {
        selectedPlayer = -1;
//        players = gameScreen.getGameClient().getSender().sendRoommatesInfoRequest().players;
        players = new ArrayList<>();
        for (int i = 0; i < Constraints.MAX_PLAYERS_NUMBER; i++) {
            players.add(new PlayerInfo("user" + i, Helper.random(0, 7)));
        }

        n = players.size();

        choosePlayerLabel = new Label("Choose Player:", skin);

        choosePlayerLabel.setFontScale(0.8f);

        playersTable = new Table(skin);

        playersAvatar = new ArrayList<>();
        playersUsername = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            PlayerInfo player = players.get(i);
            Image image = getNewImage(assetService.get(AtlasAsset.Avatars).findRegion("avatar" + player.avatarIndex), avatarWidth, avatarHeight);
            image.setSize(avatarWidth, avatarHeight);
            Label playerName = new Label(player.username, skin);
            playerName.setColor(Color.BLACK);
            playersAvatar.add(image);
            playersUsername.add(playerName);
        }

        for (int i = 0; i < n; i++) {
            Image image = playersAvatar.get(i);
            playersTable.add(image).center().pad(10);
            final int index = i;
            image.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    if (selectedPlayer != -1) {
                        playersAvatar.get(selectedPlayer).moveBy(0, -5);
                        playersUsername.get(selectedPlayer).setColor(Color.BLACK);

                    }
                    selectedPlayer = index;
                    image.moveBy(0, 5);
                    playersUsername.get(selectedPlayer).setColor(Color.YELLOW);
                }
            });
            image.addListener(new InputListener() {
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    image.addAction(Actions.scaleTo(1.2f, 1.2f, 0.1f));
                    audioService.playSound(SoundAsset.HoverButton);
                }
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    image.addAction(Actions.scaleTo(1f, 1f, 0.1f));
                    TooltipHelper.getTooltip().setVisible(false);
                }
            });
        }
        playersTable.row();
        for (int i = 0; i < n; i++) {
            Label playerName = playersUsername.get(i);
            playerName.setWidth(avatarWidth);
            playersTable.add(playerName).center().pad(10);
        }
        playersTable.row();

        choosePlayerLabel.setPosition(padLeft, height - title.getPrefHeight() - 20 - (playersTable.getPrefHeight() / 2f));
        playersTable.setPosition(padLeft + choosePlayerLabel.getPrefWidth(), height - title.getPrefHeight() - 30 - playersTable.getPrefHeight());
        playersTable.pack();

        addActor(choosePlayerLabel);
        addActor(playersTable);



        float downHeight = height - title.getPrefHeight() - 30 - playersTable.getPrefHeight();

        sendRequestButton = new TextButton("Start Trade", skin);
        historyButton = new TextButton("Trade History", skin);

        sendRequestButton.setPosition(padLeft, downHeight + historyButton.getPrefHeight() + 5);
        historyButton.setPosition(padLeft, downHeight);

        addActor(historyButton);
        addActor(sendRequestButton);

        sendRequestButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (selectedPlayer == -1) {
                    var dialog = new SimpleDialog("", "select a player", skin);
                    dialog.show(stage);
                }
            }
        });

        Group lineGroup = new Group();

        for (int i = 1; i < width / getTileWidth() - 1; i++) {
            Image line = getNewImage(H_thin_line, getTileWidth(), getTileHeight());
            line.setSize(line.getWidth(), line.getHeight());
            line.setPosition(getTileWidth() * i, 0);
            lineGroup.addActor(line);
        }
        lineGroup.setSize(getTileWidth() * ((float) width / getTileWidth()), getTileHeight());
        lineGroup.setPosition(0, downHeight - lineGroup.getHeight());
        addActor(lineGroup);

    }

    public void toggle() {
        setVisible(!isVisible());
        if (!isVisible()) {
            removeActor(choosePlayerLabel);
            removeActor(playersTable);
        }
    }
}
