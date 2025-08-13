package com.ap.ui.widget;

import com.ap.Constraints;
import com.ap.asset.AtlasAsset;
import com.ap.asset.SoundAsset;
import com.ap.model.NetworkItemStack;
import com.ap.model.TradeHistory;
import com.ap.packet.PlayerInfo;
import com.ap.responses.GetActiveTradeResponse;
import com.ap.responses.TradeHistoryResponse;
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
    private int fromIndex;
    private RequestTo to;
    private int toIndex;
    private ArrayList<RequestFrom> fromRequests;
    private ArrayList<RequestTo> toRequests;
    private Image up1, up2, down1, down2;


    @Override
    public void makeStructure() {

        title = new Label("Trade", skin);
        title.setFontScale(2);


        title.setPosition((width - title.getPrefWidth()) / 2, (height - title.getPrefHeight()));
        addActor(title);


    }

    public void loadTrades() {
        removeRequestsActors();
        System.out.println("loading trades...");
        int n = 4, m = 8;

        GetActiveTradeResponse activeTrades = gameScreen.getGameClient().getSender().sendGetActiveTradeRequest();
        fromRequests = new ArrayList<>();
        toRequests = new ArrayList<>();
        for (PlayerInfo playerInfo : activeTrades.from) {
            fromRequests.add(new RequestFrom(playerInfo.username, playerInfo.avatarIndex, n, m));
        }
        for (PlayerInfo playerInfo : activeTrades.to) {
            toRequests.add(new RequestTo(playerInfo.username, playerInfo.avatarIndex, n, m));
        }
        fromIndex = 0;
        toIndex = 0;
        float pWidth = 40;
        if (!fromRequests.isEmpty()) {
            from = fromRequests.get(fromIndex);
            up2 = getNewImage(assetService.get(AtlasAsset.Pointers).findRegion("pointer_up"), pWidth, pWidth);
            down2 = getNewImage(assetService.get(AtlasAsset.Pointers).findRegion("pointer_down"), pWidth, pWidth);
            up2.setSize(pWidth, pWidth);
            down2.setSize(pWidth, pWidth);
            up2.pack();
            down2.pack();
            up2.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    fromIndex = (fromIndex -1 + fromRequests.size()) % fromRequests.size();
                    from = fromRequests.get(fromIndex);
                }
            });
            down2.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    fromIndex = (fromIndex + 1 + fromRequests.size()) % fromRequests.size();
                    from = fromRequests.get(fromIndex);
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
            up2.setPosition(getTileWidth() + m * getTileWidth(), getTileHeight() * (n - 1));
            down2.setPosition(getTileWidth() + m * getTileWidth(), getTileHeight() * (n - 1) - up2.getPrefHeight());

            up2.setPosition(getTileWidth() + m * getTileWidth() + pWidth + m * getTileWidth(), getTileHeight() * (n - 1));
            down2.setPosition(getTileWidth() + m * getTileWidth() + pWidth + m * getTileWidth(), getTileHeight() * (n - 1) - pWidth);

            from.setPosition(getTileWidth() + m * getTileWidth() + pWidth, getTileHeight());
            addActor(from);
            addActor(up2);
            addActor(down2);
        }
        if (!toRequests.isEmpty()) {
            to =  toRequests.get(toIndex);


            up1 = getNewImage(assetService.get(AtlasAsset.Pointers).findRegion("pointer_up"), pWidth, pWidth);
            down1 = getNewImage(assetService.get(AtlasAsset.Pointers).findRegion("pointer_down"), pWidth, pWidth);

            up1.setSize(pWidth, pWidth);
            down1.setSize(pWidth, pWidth);
            up1.pack();
            down1.pack();



            up1.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    toIndex = (toIndex - 1 + toRequests.size()) % toRequests.size();
                    to =  toRequests.get(toIndex);
                }
            });


            down1.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    toIndex = (toIndex + 1 + toRequests.size()) % toRequests.size();
                    to =  toRequests.get(toIndex);
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




            up1.setPosition(getTileWidth() + m * getTileWidth(), getTileHeight() * (n - 1));
            down1.setPosition(getTileWidth() + m * getTileWidth(), getTileHeight() * (n - 1) - up1.getPrefHeight());
            to.setPosition(getTileWidth(), getTileHeight());

            addActor(up1);
            addActor(down1);
            addActor(to);


        }

    }

    public void showHistory() {
        ArrayList<TradeHistory> histories;
        TradeHistoryResponse response = gameScreen.getGameClient().getSender().sendTradeHistoryRequest();
        if(response != null) {
            histories = response.tradeHistory;
        } else {
            histories = new ArrayList<>();
        }

//        for (int i = 0; i < 5; i ++) {
//            var h = new TradeHistory(new PlayerInfo("user1", 0), new PlayerInfo("user2", 0), new ArrayList<>(), new ArrayList<>());
//            histories.add(h);
//        }
        Group gp = new Group();
        assembleBackground(gp, (height / getTileHeight()), (width / getTileWidth()) + 2, getTileWidth(), getTileHeight());
        Table table = new Table();
        for (TradeHistory history : histories) {
            Group group  = new Group();
            float cellWidth = getTileWidth() / 2f;
            float cellHeight = getTileHeight() / 2f;
            assembleBackground(group, 5, 34, cellWidth, cellHeight);

            Label user1 = new Label(history.player1.username, skin);
            user1.pack();
            float iconWidth = getTileWidth();
            float iconHeight = getTileHeight();
            Group icons1 = new Group();
            for (int i = 0; i < history.items1.size(); i++) {
                var item = history.items1.get(i);
                Image image = getNewImage(assetService.get(item.atlasAsset).findRegion(item.atlasKey), iconWidth, iconHeight);
                image.setSize(iconWidth, iconHeight);
                image.pack();
                image.setPosition(i * iconWidth, 0);
                icons1.addActor(image);
            }
            icons1.setSize(history.items1.size() * iconWidth, iconHeight);

            Label user2 = new Label(history.player2.username, skin);
            user2.pack();

            Group icons2 = new Group();
            for (int i = 0; i < history.items2.size(); i++) {
                var item = history.items2.get(i);
                Image image = getNewImage(assetService.get(item.atlasAsset).findRegion(item.atlasKey), iconWidth, iconHeight);
                image.setSize(iconWidth, iconHeight);
                image.pack();
                image.setPosition(i * iconWidth, 0);
                icons2.addActor(image);
            }
            icons2.setSize(history.items2.size() * iconWidth, iconHeight);

            user1.setPosition(cellWidth, (group.getHeight() - user1.getPrefHeight()) / 2f);
            user2.setPosition(group.getWidth() - cellWidth - user2.getPrefWidth(), (group.getHeight() - user2.getPrefHeight()) / 2f);

            icons1.setPosition(cellWidth + user1.getPrefWidth() + 2, (group.getHeight() - icons1.getHeight()) / 2f);
            icons2.setPosition(group.getWidth() - (cellWidth + user2.getPrefWidth() + 2) - icons2.getWidth(), (group.getHeight() - icons2.getHeight()) / 2f);

            Label vs = new Label("vs", skin);
            vs.setFontScale(0.7f);
            vs.pack();
            vs.setPosition((group.getWidth() - vs.getPrefWidth()) / 2, (group.getHeight() - vs.getPrefHeight()) / 2);

            user1.setColor(Color.BLUE);
            user2.setColor(Color.BLUE);


            group.addActor(user1);
            group.addActor(user2);
            group.addActor(icons1);
            group.addActor(icons2);
            group.addActor(vs);
            table.add(group).center();
            table.row();
        }

        TextButton back = new TextButton("Back", skin);
        back.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                stage.getActors().removeValue(gp, true);
            }
        });
        back.setPosition(getTileWidth(), getTileHeight());
        table.pack();
//        table.setPosition(getTileWidth(), height - table.getHeight() - getTileHeight());

        ScrollPane.ScrollPaneStyle style = new ScrollPane.ScrollPaneStyle(skin.get(ScrollPane.ScrollPaneStyle.class));

// Remove scrollbars
        style.hScroll = null;  // Horizontal scrollbar track
        style.hScrollKnob = null; // Horizontal scrollbar knob
        style.vScroll = null;  // Vertical scrollbar track
        style.vScrollKnob = null; // Vertical scrollbar knob


        ScrollPane scrollPane = new ScrollPane(table, style);
        scrollPane.setScrollingDisabled(true, false);
//        scrollPane.setScrollbarsVisible(false);
        scrollPane.setSize(table.getWidth() + 2 * getTileWidth(), (table.getHeight() / histories.size()) * 4);
        scrollPane.setPosition((gp.getWidth() - scrollPane.getPrefWidth()) / 2f - getTileWidth(), gp.getHeight() - getTileHeight() - (table.getHeight() / histories.size()) * 4);
        scrollPane.setFadeScrollBars(false);

        gp.addActor(scrollPane);
        gp.addActor(back);
        gp.setPosition((Constraints.WORLD_WIDTH_RESOLUTION - gp.getWidth()) / 2, (Constraints.WORLD_HEIGHT_RESOLUTION - gp.getHeight()) / 2);
        stage.addActor(gp);
    }

    @Override
    public void loadData() {
        removeActors();
        selectedPlayer = -1;
        var roommates = gameScreen.getGameClient().getSender().sendRoommatesInfoRequest();
        players = roommates.players;
        for (PlayerInfo player : players) {
            if (player.username.equals(roommates.yourInfo.username)) {
                players.remove(player);
                break;
            }
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



        float downHeight = height - title.getPrefHeight() - 30 - 160;

        sendRequestButton = new TextButton("Start Trade", skin);
        historyButton = new TextButton("Trade History", skin);

        sendRequestButton.setPosition(padLeft, downHeight + historyButton.getPrefHeight() + 5);
        historyButton.setPosition(padLeft, downHeight);

        historyButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                showHistory();
            }
        });

        addActor(historyButton);
        addActor(sendRequestButton);

        sendRequestButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (selectedPlayer < 0 || selectedPlayer >= players.size()) {
                    var dialog = new SimpleDialog("", "select a player", skin);
                    dialog.show(stage);
                } else {
                    audioService.playSound(SoundAsset.Gift);
                    gameScreen.getGameClient().getSender().sendTradeStartRequest(players.get(selectedPlayer).username);
                    loadTrades();
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
        removeRequestsActors();
    }
    public void removeRequestsActors() {
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

            acceptButton.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    gameScreen.getGameClient().getSender().sendTradeStartRequest(senderName);
                    audioService.playSound(SoundAsset.Beep);
                    loadTrades();

                }
            });

            rejectButton.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    gameScreen.getGameClient().getSender().sendTradeStarterRejectRequest(senderName);
                    audioService.playSound(SoundAsset.Beep);
                    loadTrades();

                }
            });

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
            cancelButton = new TextButton("Cancel", skin);
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

            cancelButton.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    gameScreen.getGameClient().getSender().sendTradeStarterCancelRequest(receiverName);
                    loadTrades();
                    audioService.playSound(SoundAsset.Beep);
                }
            });

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
