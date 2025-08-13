package com.ap.ui.widget;

import com.ap.Constraints;
import com.ap.model.NetworkItemStack;
import com.ap.packet.PlayerInfo;
import com.ap.packet.TradeRoomStarter;
import com.ap.screen.GameScreen;
import com.ap.ui.widget.tabContents.AbstractContent;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

public class TradeMenu extends AbstractContent {
    private float cellWidth;
    private float cellHeight;

    protected TextureRegion border_free;
    protected TextureRegion border_selected;
    protected TextureRegion cell_locked;
    protected TextureRegion cell_empty;

    private TradeMenu instance;
    private GameScreen gameScreen;

    public TradeMenu(GameScreen gameScreen) {
        super(gameScreen.getStage(), gameScreen.getAssetService(), gameScreen.getSkin(), gameScreen.getAudioService(), Constraints.WORLD_WIDTH_RESOLUTION - 32, Constraints.WORLD_HEIGHT_RESOLUTION - 64, null);
        this.gameScreen = gameScreen;
        border_free = atlas.findRegion("cell/border", 0);
        border_selected = atlas.findRegion("cell/border", 1);
        cell_locked = atlas.findRegion("cell/locked");
        cell_empty = atlas.findRegion("cell/blank");
        cellWidth = 48;
        cellHeight = 48;
    }


    @Override
    public void makeStructure() {

    }

    @Override
    public void loadData() {

    }

    public void makeInstance(TradeRoomStarter starter) {
        instance = new TradeMenu(gameScreen);
        instance.show(starter);
        stage.addActor(instance);
    }

    private Player[] players;
    private Group[] playerGroups;

    public void show(TradeRoomStarter starter) {
        players = new Player[2];
        players[0] = new Player(starter.playerInfo1, starter.inventory1);
        players[1] = new Player(starter.playerInfo2, starter.inventory2);
        int infoBoxN = 18;
        int infoBoxM = 5;
        playerGroups = new Group[2];
        Group[] infoBox = new Group[2];
        for (int i = 0; i < 2; i++) {
            infoBox[i] = new Group();
            assembleBackground(infoBox[i], infoBoxN, infoBoxM, getTileWidth(), getTileHeight());
            infoBox[i].setPosition(0, 0);
        }
        for (int i = 0; i < 2; i++) {
            playerGroups[i] = new Group();
            Table buffer = players[i].bufferTable;
            Table inventory = players[i].inventoryTable;

            buffer.setSize(cellWidth * players[i].bufferSize, cellHeight);
            inventory.setSize(cellWidth * players[i].m, cellHeight * players[i].n);

            inventory.setPosition(infoBoxM * getTileWidth(), (infoBoxN * getTileHeight()) / 2f - inventory.getHeight());
            buffer.setPosition(infoBoxM * getTileWidth() + (inventory.getWidth() - buffer.getWidth()) / 2, infoBoxN * getTileHeight() - 5 * getTileHeight() - buffer.getHeight());

            playerGroups[i].addActor(buffer);
            playerGroups[i].addActor(inventory);
            playerGroups[i].addActor(infoBox[i]);
        }

        playerGroups[0].setPosition(getTileWidth(), getTileHeight());
        playerGroups[1].setPosition(width / 2f, getTileHeight());

        addActor(playerGroups[0]);
        addActor(playerGroups[1]);
    }

    private class Player{
        PlayerInfo playerInfo;
        ArrayList<NetworkItemStack> inventoryCore;
        ArrayList<Integer> buffer;
        ArrayList<Integer> inventory;

        ItemCell[][] cells;
        ItemCell[] bufferCells;

        Table bufferTable;
        Table inventoryTable;

        int bufferSize;
        int n, m;

        public Player(PlayerInfo playerInfo, ArrayList<NetworkItemStack> invCore) {
            this.playerInfo = playerInfo;
            this.inventoryCore = invCore;
            bufferSize = Constraints.MAX_TRADE_BUFFER_SIZE;
            bufferCells = new ItemCell[bufferSize];
            m = 8;
            n = (int) Math.ceil((float) Constraints.MAX_STORAGE_SIZE / m);
            cells = new ItemCell[n][m];

            buffer = new ArrayList();
            inventory = new ArrayList();
            for (int i = 0; i < inventoryCore.size(); i++) {
                inventory.add(i);
            }

            for (int i = 0; i < bufferSize; i++) {
                bufferCells[i] = new ItemCell();
                bufferCells[i].free();
                ItemCell cell = bufferCells[i];
                bufferCells[i].addListener(new InputListener() {
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        if (cell.isHasItem()) {
                            Vector2 stageCoords = cell.localToStageCoordinates(new Vector2(x, y));
                            TooltipHelper.getTooltip().setVisible(true);
                            TooltipHelper.getTooltip().getTitle().setText(cell.getItem().name);
                            TooltipHelper.getTooltip().getTitle().setFontScale(1f);
                            TooltipHelper.getTooltip().pack();
                            TooltipHelper.getTooltip().setPosition(stageCoords.x, stageCoords.y);

                            cell.getIcon().addAction(Actions.scaleTo(1.2f, 1.2f, 0.1f));
                            cell.getNumber().addAction(Actions.scaleTo(1.2f, 1.2f, 0.1f));
                        }
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        TooltipHelper.getTooltip().setVisible(false);
                        if (cell.isHasItem()) {
                            cell.getIcon().addAction(Actions.scaleTo(1f, 1f, 0.1f));
                            cell.getNumber().addAction(Actions.scaleTo(1f, 1f, 0.1f));
                        }
                    }
                });
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    cells[i][j] = new ItemCell();
                    ItemCell cell = cells[i][j];
                    cell.free();
                    if (i * m + j >= inventoryCore.size()) cell.setLocked(true);
                    cells[i][j].addListener(new InputListener() {
                        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                            if (cell.isHasItem()) {
                                Vector2 stageCoords = cell.localToStageCoordinates(new Vector2(x, y));
                                TooltipHelper.getTooltip().setVisible(true);
                                TooltipHelper.getTooltip().getTitle().setText(cell.getItem().name);
                                TooltipHelper.getTooltip().getTitle().setFontScale(1f);
                                TooltipHelper.getTooltip().pack();
                                TooltipHelper.getTooltip().setPosition(stageCoords.x, stageCoords.y);

                                cell.getIcon().addAction(Actions.scaleTo(1.2f, 1.2f, 0.1f));
                                cell.getNumber().addAction(Actions.scaleTo(1.2f, 1.2f, 0.1f));
                            }
                        }
                        public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                            TooltipHelper.getTooltip().setVisible(false);
                            if (cell.isHasItem()) {
                                cell.getIcon().addAction(Actions.scaleTo(1f, 1f, 0.1f));
                                cell.getNumber().addAction(Actions.scaleTo(1f, 1f, 0.1f));
                            }
                        }
                    });
                }
            }

            bufferTable = new Table();
            inventoryTable = new Table();
            for (int i = 0; i < bufferSize; i++) {
                bufferTable.add(bufferCells[i]);
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    inventoryTable.add(cells[i][j]);
                }
                inventoryTable.row();
            }

            bufferTable.pack();
            inventoryTable.pack();
        }

        public void updateUi() {
            for (int i = 0; i < bufferSize; i++) {
                bufferCells[i].free();
                if (i < buffer.size()) {
                    bufferCells[i].addItem(inventoryCore.get(buffer.get(i)), buffer.get(i));
                }
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    int x = i * m + j;
                    if (x >= inventoryCore.size()) {
                        if (cells[i][j].isHasItem()) cells[i][j].free();
                    } else {
                        if (!cells[i][j].isHasItem()) cells[i][j].addItem(inventoryCore.get(inventory.get(x)), inventory.get(x));
                        else if (cells[i][j].index != inventory.get(x)) {
                            cells[i][j].free();
                            cells[i][j].addItem(inventoryCore.get(inventory.get(x)), inventory.get(x));
                        }
                    }
                }
            }
        }

    }

    class ItemCell extends Group {

        private Image blankCell;
        private Image lockedCell;
        private Image freeBorder;
        private Image selectedBorder;

        private boolean isLocked = false;
        private boolean isSelected = false;
        private boolean hasItem = false;

        private NetworkItemStack item;
        private int index;
        private Image icon;
        private Label number;
        public ItemCell() {
            super();
            TextureRegionDrawable drawable;

            blankCell = getNewImage(cell_empty, cellWidth, cellHeight);
            lockedCell = getNewImage(cell_locked, cellWidth, cellHeight);
            freeBorder = getNewImage(border_free, cellWidth, cellHeight);
            selectedBorder = getNewImage(border_selected, cellWidth, cellHeight);

            blankCell.setSize(cellWidth, cellHeight);
            lockedCell.setSize(cellWidth, cellHeight);
            freeBorder.setSize(cellWidth, cellHeight);
            selectedBorder.setSize(cellWidth, cellHeight);

            lockedCell.setVisible(false);
            selectedBorder.setVisible(false);
            addActor(blankCell);
            addActor(lockedCell);
            addActor(freeBorder);
            addActor(selectedBorder);
            setSize(cellWidth, cellHeight);
        }

        public void free() {
            item = null;
            if (icon != null) removeActor(icon);
            if (number != null) removeActor(number);
            icon = null;
            number = null;
            hasItem = false;
        }

        public void setHasItem(boolean hasItem) {
            this.hasItem = hasItem;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
            selectedBorder.setVisible(selected);
        }

        public void setLocked(boolean locked) {
            isLocked = locked;
            lockedCell.setVisible(locked);
        }

        public boolean isSelected() {
            return isSelected;
        }

        public boolean isLocked() {
            return isLocked;
        }

        public boolean isHasItem() {
            return hasItem;
        }

        public NetworkItemStack getItem() {
            return item;
        }

        public int getIndex() {
            return index;
        }

        public Image getIcon() {
            return icon;
        }

        public Label getNumber() {
            return number;
        }

        public boolean addItem(NetworkItemStack item, int index) {
            if (hasItem) return false;
            if (isLocked) return false;
            this.item = item;
            this.index = index;
            icon = getNewImage(assetService.get(item.atlasAsset).findRegion(item.atlasKey), cellWidth, cellHeight);
            hasItem = true;
            icon.setSize(cellWidth, cellHeight);
            addActor(icon);
            icon.setVisible(true);
            number = new Label(item.amount + "", skin);
            number.setFontScale(1f);
            Label.LabelStyle newStyle = new Label.LabelStyle(number.getStyle());
            newStyle.fontColor = Color.RED;
            number.setStyle(newStyle);
            number.setPosition(cellWidth - number.getWidth() - 5, -6);
            if (item.amount <= 1) number.setVisible(false);
            addActor(number);
            return true;
        }


    }

}
