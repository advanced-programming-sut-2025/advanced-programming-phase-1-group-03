package com.ap.ui.widget;

import com.ap.Constraints;
import com.ap.asset.AtlasAsset;
import com.ap.asset.SoundAsset;
import com.ap.packet.PlayerInfo;
import com.ap.screen.GameScreen;
import com.ap.ui.actor.SimpleDialog;
import com.ap.ui.widget.tabContents.AbstractContent;
import com.ap.utils.Helper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

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
    private Group lineGroup;

    private RequestFrom from;
    private RequestTo to;
    private Image up1, up2, down1, down2;

    //username of them, these are active
    private ArrayList<String> offersReceived;
    private ArrayList<String> offersSent;

    @Override
    public void makeStructure() {

        title = new Label("Trade", skin);
        title.setFontScale(2);


        title.setPosition((width - title.getPrefWidth()) / 2, (height - title.getPrefHeight()));
        addActor(title);


    }

    public void loadTrades() {
        int n = 4, m = 8;
        //TODO request for real data
        from = new RequestFrom("user1", Helper.random(0, 7), n, m);
        to = new RequestTo("user2", Helper.random(0, 7), n, m);

        float pWidth = 40;

        up1 = getNewImage(assetService.get(AtlasAsset.Pointers).findRegion("pointer_up"), pWidth, pWidth);
        down1 = getNewImage(assetService.get(AtlasAsset.Pointers).findRegion("pointer_down"), pWidth, pWidth);

        up2 = getNewImage(assetService.get(AtlasAsset.Pointers).findRegion("pointer_up"), pWidth, pWidth);
        down2 = getNewImage(assetService.get(AtlasAsset.Pointers).findRegion("pointer_down"), pWidth, pWidth);

        up1.setSize(pWidth, pWidth);
        down1.setSize(pWidth, pWidth);
        up2.setSize(pWidth, pWidth);
        down2.setSize(pWidth, pWidth);
        up1.pack();
        up2.pack();
        down1.pack();
        down2.pack();


        up1.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //TODO scroll
            }
        });

        up2.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        down1.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        down2.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        up1.addListener(new InputListener() {
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                up1.addAction(Actions.scaleTo(1f, 1.1f, 0.1f));
                audioService.playSound(SoundAsset.HoverButton);
            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                up1.addAction(Actions.scaleTo(1f, 1f, 0.1f));
                TooltipHelper.getTooltip().setVisible(false);
            }
        });

        up2.addListener(new InputListener() {
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                up2.addAction(Actions.scaleTo(1f, 1.1f, 0.1f));
                audioService.playSound(SoundAsset.HoverButton);
            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                up2.addAction(Actions.scaleTo(1f, 1f, 0.1f));
                TooltipHelper.getTooltip().setVisible(false);
            }
        });

        down1.addListener(new InputListener() {
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                down1.addAction(Actions.scaleTo(1f, 1.1f, 0.1f));
                audioService.playSound(SoundAsset.HoverButton);
            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                down1.addAction(Actions.scaleTo(1f, 1f, 0.1f));
                TooltipHelper.getTooltip().setVisible(false);
            }
        });

        down2.addListener(new InputListener() {
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                down2.addAction(Actions.scaleTo(1f, 1.1f, 0.1f));
                audioService.playSound(SoundAsset.HoverButton);
            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                down2.addAction(Actions.scaleTo(1f, 1f, 0.1f));
                TooltipHelper.getTooltip().setVisible(false);
            }
        });

        up1.setPosition(getTileWidth() + m * getTileWidth(), getTileHeight() * (n - 1));
        down1.setPosition(getTileWidth() + m * getTileWidth(), getTileHeight() * (n - 1) - up1.getPrefHeight());

        up2.setPosition(getTileWidth() + m * getTileWidth() + up1.getPrefWidth() + m * getTileWidth(), getTileHeight() * (n - 1));
        down2.setPosition(getTileWidth() + m * getTileWidth() + up1.getPrefWidth() + m * getTileWidth(), getTileHeight() * (n - 1) - up2.getPrefHeight());

        to.setPosition(getTileWidth(), getTileHeight());
        from.setPosition(getTileWidth() + m * getTileWidth() + up1.getPrefWidth(), getTileHeight());

        addActor(up1);
        addActor(up2);
        addActor(down1);
        addActor(down2);
        addActor(to);
        addActor(from);
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

//        gameScreen.getGameClient()


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

        lineGroup = new Group();

        for (int i = 1; i < width / getTileWidth() - 1; i++) {
            Image line = getNewImage(H_thin_line, getTileWidth(), getTileHeight());
            line.setSize(line.getWidth(), line.getHeight());
            line.setPosition(getTileWidth() * i, 0);
            lineGroup.addActor(line);
        }
        lineGroup.setSize(getTileWidth() * ((float) width / getTileWidth()), getTileHeight());
        lineGroup.setPosition(0, downHeight - lineGroup.getHeight());
        addActor(lineGroup);

        loadTrades();

    }

    public void removeActors() {
        removeActor(choosePlayerLabel);
        removeActor(playersTable);
        removeActor(historyButton);
        removeActor(sendRequestButton);
        removeActor(lineGroup);
        removeActor(from);
        removeActor(to);
        removeActor(up1);
        removeActor(up2);
        removeActor(down1);
        removeActor(down2);

    }

    public void toggle() {
        setVisible(!isVisible());
        if (!isVisible()) {
            removeActors();
        }
    }

    protected class RequestFrom extends Group{
        String senderName;
        int avatatIndex;

        int n, m; //n,m >= 3

        float tileWidth = 16;
        float tileHeight = 16;

        float avatarWidth = 48;
        float avatarHeight = 48;

        public RequestFrom(String senderName, int avatarIndex, int n, int m) {
            this.senderName = senderName;
            this.n = n * 2;
            this.m = m * 2;
            this.avatatIndex = avatarIndex;

            assemble();
        }

        private void assemble() {
            from = new Label("From: " + senderName, skin);
            from.setFontScale(0.9f);
            avatar = getNewImage(assetService.get(AtlasAsset.Avatars).findRegion("avatar" + avatatIndex), avatarWidth,avatarHeight);
            acceptButton = new TextButton("Accept", skin);
            rejectButton = new TextButton("Reject", skin);
            acceptButton.getLabel().setColor(Color.GREEN);
            rejectButton.getLabel().setColor(Color.RED);

            from.pack();
            acceptButton.pack();
            rejectButton.pack();

            assembleBackground(this, n, m, tileWidth, tileHeight);

            from.setPosition(tileWidth, tileHeight * (n - 1) - from.getPrefHeight());
            if (avatar != null) {
                avatar.setSize(avatarWidth, avatarHeight);
                avatar.pack();
                avatar.setPosition(tileWidth , tileHeight * (n - 1) - avatarHeight - from.getPrefHeight());
                addActor(avatar);
            }

            acceptButton.setPosition(tileWidth * (m - 1) - acceptButton.getPrefWidth(), tileHeight * (n - 1) - acceptButton.getPrefHeight());
            rejectButton.setPosition(tileWidth * (m - 1) - rejectButton.getPrefWidth(), tileHeight * (n - 1) - rejectButton.getPrefHeight() - acceptButton.getPrefHeight() - 20);

            addActor(from);
            addActor(acceptButton);
            addActor(rejectButton);

        }

        Label from;
        Image avatar;

        TextButton acceptButton;
        TextButton rejectButton;
    }

    protected class RequestTo extends Group{
        String receiverName;
        int avatatIndex;

        int n, m; //n,m >= 3
        float tileWidth = 16;
        float tileHeight = 16;

        float avatarWidth = 48;
        float avatarHeight = 48;

        public RequestTo(String receiverName, int avatarIndex, int n, int m) {
            this.receiverName = receiverName;
            this.n = n * 2;
            this.m = m * 2;
            this.avatatIndex = avatarIndex;

            assemble();
        }

        private void assemble() {
            to = new Label("To: " + receiverName, skin);
            to.setFontScale(0.9f);
            avatar = getNewImage(assetService.get(AtlasAsset.Avatars).findRegion("avatar" + avatatIndex), avatarWidth, avatarHeight);
            waiting = new Label("Waiting...", skin);
            waiting.setColor(Color.BLUE);
            waiting.setFontScale(0.9f);
            waiting.addAction(Actions.forever(Actions.sequence(
                    Actions.fadeOut(1.5f),
                    Actions.fadeIn(1.5f))));
            cancelButton = new TextButton("Reject", skin);
            cancelButton.getLabel().setColor(Color.RED);
            cancelButton.pack();
            waiting.pack();
            to.pack();

            assembleBackground(this, n, m, tileWidth, tileHeight);

            to.setPosition(tileWidth, tileHeight * (n - 1) - to.getPrefHeight());
            if (avatar != null) {
                avatar.setSize(avatarWidth, avatarHeight);
                avatar.pack();
                avatar.setPosition(tileWidth , tileHeight * (n - 1) - avatarHeight - to.getPrefHeight());
                addActor(avatar);
            }

            waiting.setPosition(tileWidth * (m - 1) - waiting.getPrefWidth(), tileHeight * (n - 1) - waiting.getPrefHeight() - cancelButton.getPrefWidth());
            cancelButton.setPosition(tileWidth * (m - 1) - cancelButton.getPrefWidth(), tileHeight * (n - 1) - cancelButton.getPrefHeight());

            addActor(to);
            addActor(waiting);
            addActor(cancelButton);

        }

        Label to;
        Image avatar;
        Label waiting;

        TextButton cancelButton;
    }
}
