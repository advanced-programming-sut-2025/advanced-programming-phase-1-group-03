package com.ap.ui.widget.tabContents;

import com.ap.Constraints;
import com.ap.asset.AtlasAsset;
import com.ap.asset.SoundAsset;
import com.ap.items.Inventory;
import com.ap.items.ItemStack;
import com.ap.screen.GameScreen;
import com.ap.ui.widget.ItemContainer;
import com.ap.ui.widget.TooltipHelper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

public class InventoryTab extends AbstractContent{

    private Inventory inventory;
    private GameScreen gameScreen;

    private Table itemContainer;
    private Table backpack;

    private Group trashCan;

    private float cellWidth;
    private float cellHeight;

    protected TextureRegion border_free;
    protected TextureRegion border_selected;
    protected TextureRegion cell_locked;
    protected TextureRegion cell_empty;

    private Array<TextureRegion> canBodiesTexture;
    private Array<TextureRegion> canTopsTexture;
    private Array<Image> canBodies;
    private Array<Image> canTops;

    private ItemCell[][] cells;
    private final int n;
    private final int m;

    private TooltipHelper tooltipHelper;


    private int selectedCell = -1;

    public InventoryTab(GameScreen gameScreen, int width, int height, Tabs icon) {
        super(gameScreen.getStage(), gameScreen.getAssetService(), gameScreen.getSkin(), gameScreen.getAudioService(), width, height, icon);
        this.gameScreen = gameScreen;
        this.inventory = gameScreen.getInventory();

        border_free = atlas.findRegion("cell/border", 0);
        border_selected = atlas.findRegion("cell/border", 1);
        cell_locked = atlas.findRegion("cell/locked");
        cell_empty = atlas.findRegion("cell/blank");

        TextureAtlas canAssets = gameScreen.getAssetService().get(AtlasAsset.Toggles);

        canBodiesTexture = new Array<>();
        canTopsTexture = new Array<>();
        for (int i = 0; i <= Inventory.maxTrashCanLevel; i++) {
            TextureRegion body = canAssets.findRegion("can/level", i);
            TextureRegion top = canAssets.findRegion("can/top_level", i);
            canBodiesTexture.add(body);
            canTopsTexture.add(top);
        }

        cellWidth = 48;
        cellHeight = 48 ;

        m = ItemContainer.maxSize;
        n = MathUtils.ceil((float) Constraints.MAX_STORAGE_SIZE / m);

        tooltipHelper = TooltipHelper.getTooltip();

        makeStructure();
        loadData();


    }

        @Override
    public void makeStructure() {


        cells = new ItemCell[n][m];
        itemContainer = new Table();
        for (int i = 0; i < m; i++) {
            cells[0][i] = new ItemCell();
            itemContainer.add(cells[0][i]).size(cellWidth, cellHeight);
        }
        itemContainer.setSize(cellWidth * m, cellHeight);

        backpack = new Table();
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < m; j++) {
                cells[i][j] = new ItemCell();
                backpack.add(cells[i][j]).size(cellWidth, cellHeight);
                cells[i][j].setLocked(true);
            }
            backpack.row();
        }
        backpack.setSize(cellWidth * m, cellHeight * (n - 1));


        itemContainer.setPosition((width - itemContainer.getWidth()) / 2f, height - 50 - itemContainer.getHeight());
        backpack.setPosition((width - backpack.getWidth()) / 2f, itemContainer.getY() - 10 - backpack.getHeight());
        addActor(itemContainer);
        addActor(backpack);

        for (int x = 0; x < Constraints.MAX_STORAGE_SIZE; x++) {
            int i = x / m;
            int j = x % m;
            ItemCell cell = cells[i][j];
            float cellX = j * cellWidth;
            float cellY;
            if (i == 0) {
                cellY = itemContainer.getY();
            } else {
                cellY = backpack.getY() + (n - 1 - i) * cellHeight;
            }
            final int id = x;
            cells[i][j].addListener(new InputListener() {
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    if (cell.isHasItem()) {
                        Vector2 stageCoords = cell.localToStageCoordinates(new Vector2(x, y));
                        tooltipHelper.setVisible(true);
                        tooltipHelper.getTitle().setText(cell.getItem().getItem().getName());
                        tooltipHelper.getTitle().setFontScale(1f);
                        tooltipHelper.pack();
                        tooltipHelper.setPosition(stageCoords.x, stageCoords.y);

                        cell.getIcon().addAction(Actions.scaleTo(1.2f, 1.2f, 0.1f));
                        cell.getNumber().addAction(Actions.scaleTo(1.2f, 1.2f, 0.1f));
                    }
                }
                public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    tooltipHelper.setVisible(false);
                    if (cell.isHasItem()) {
                        cell.getIcon().addAction(Actions.scaleTo(1f, 1f, 0.1f));
                        cell.getNumber().addAction(Actions.scaleTo(1f, 1f, 0.1f));
                    }
                }
            });
            cells[i][j].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (selectedCell == -1) {
                        if (!cell.isLocked() && cell.isHasItem()) {
                            selectedCell = id;
                            cell.setSelected(true);
                        }
                    } else if (selectedCell == id) {
                        selectedCell = -1;
                        cell.setSelected(false);
                    } else {
                        if (!cell.isLocked() && cell.isHasItem()) {
                            swapItems(selectedCell, id);
                            cells[selectedCell / m][selectedCell % m].setSelected(false);
                            cell.setSelected(false);
                            selectedCell = -1;
                        }
                    }
                    if (cell.isHasItem()) {
                        audioService.playSound(SoundAsset.HoverButton);
                    }
                }
            });
        }

        addTrashCan();

    }

    private void addTrashCan() {
        canBodies = new Array<>();
        canTops = new Array<>();
        float canBodyWidth = 50; //original 16 * 26
        float canBodyHeight = canBodyWidth * 26f / 16f;
        float canTopWidth = canBodyWidth * 18f / 16f; // original 18 * 10
        float canTopHeight = canTopWidth * 10f / 18f;
        for (int i = 0; i < Inventory.maxTrashCanLevel; i++) {
            Image body = getNewImage(canBodiesTexture.get(i), canBodyWidth, canBodyHeight);
            Image top = getNewImage(canTopsTexture.get(i), canTopWidth, canTopHeight);
            canBodies.add(body);
            canTops.add(top);
        }

        trashCan = new Group();
        trashCan.setSize(canTopWidth, canBodyHeight);
        trashCan.setPosition((width - trashCan.getWidth()) / 2f, 100);

        for (int i = 0; i < Inventory.maxTrashCanLevel; i++) {
            Image body = canBodies.get(i);
            Image top = canTops.get(i);
            body.setPosition((canTopWidth - canBodyWidth) / 2f, 0);
            top.setPosition(0, canBodyHeight - canTopHeight);
            body.setVisible(false);
            top.setVisible(false);
            trashCan.addActor(body);
            trashCan.addActor(top);
            top.setOrigin(canTopWidth, 0);
            trashCan.addListener(new InputListener() {
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    top.addAction(Actions.rotateBy(-45, 0.3f));
                }
                public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    top.addAction(Actions.rotateBy(45, 0.3f));
                }
            });
            trashCan.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    if (selectedCell == -1) {
                        return;
                    }
                    if (inventory.getSize() == 1) return;

                    gameScreen.getGameClient().getSender().sendRemoveItemInventoryRequest(
                            cells[selectedCell / m][selectedCell % m].getItem().getItem().getName(),
                            cells[selectedCell / m][selectedCell % m].getItem().getAmount());
                    //TODO fix lag
                    loadInventory();
                }
            });


        }
        addActor(trashCan);
    }

    @Override
    public void loadData() {
        loadInventory();

        for (int i = 0; i < Inventory.maxTrashCanLevel; i++) {
            canBodies.get(i).setVisible(false);
            canTops.get(i).setVisible(false);
        }
        canBodies.get(inventory.getTrashCanLevel()).setVisible(true);
        canTops.get(inventory.getTrashCanLevel()).setVisible(true);

    }

    private void loadInventory() {
        selectedCell = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                cells[i][j].free();
                cells[i][j].setSelected(false);
            }
        }
        for (int x = 0; x < inventory.getMaxSize(); x++) {
            int i = x / m;
            int j = x % m;
            cells[i][j].setLocked(false);
//            System.out.println(i + "," + j);
        }
        for (int x = 0; x < inventory.getSize(); x++) {
            int i = x / m;
            int j = x % m;
            cells[i][j].addItem(inventory.getItems().get(x));
        }
    }

    public void swapItems(int x, int y) {
        if (x >= inventory.getSize() || y >= inventory.getSize()) return;
        int i1, j1, i2, j2;
        i1 = x / m;
        j1 = x % m;
        i2 = y / m;
        j2 = y % m;
        ItemStack item1 = cells[i1][j1].item;
        ItemStack item2 = cells[i2][j2].item;
        inventory.getItems().set(x, item2);
        inventory.getItems().set(y, item1);
        cells[i1][j1].free();
        cells[i2][j2].free();
        cells[i1][j1].addItem(item2);
        cells[i2][j2].addItem(item1);
    }

    class ItemCell extends Group {

        private Image blankCell;
        private Image lockedCell;
        private Image freeBorder;
        private Image selectedBorder;

        private boolean isLocked = false;
        private boolean isSelected = false;
        private boolean hasItem = false;

        private ItemStack item;
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

        public ItemStack getItem() {
            return item;
        }

        public Image getIcon() {
            return icon;
        }

        public Label getNumber() {
            return number;
        }

        public boolean addItem(ItemStack item) {
            if (hasItem) return false;
            if (isLocked) return false;
            this.item = item;
            TextureRegionDrawable drawable = new TextureRegionDrawable(item.getItem().getIcon());
            drawable.setMinSize(cellWidth, cellHeight);
            icon = new Image(new TextureRegionDrawable(drawable));
            hasItem = true;
            icon.setSize(cellWidth, cellHeight);
            addActor(icon);
            icon.setVisible(true);
            number = new Label(item.getAmount() + "", skin);
            number.setFontScale(1f);
            Label.LabelStyle newStyle = new Label.LabelStyle(number.getStyle());
            newStyle.fontColor = Color.RED;
            number.setStyle(newStyle);
            number.setPosition(cellWidth - number.getWidth() - 5, -6);
            if (item.getAmount() <= 1) number.setVisible(false);
            addActor(number);
            return true;
        }


    }
}
