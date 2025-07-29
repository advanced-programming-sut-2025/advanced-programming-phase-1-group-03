package com.ap.ui.widget.tabContents;

import com.ap.Constraints;
import com.ap.asset.AssetService;
import com.ap.asset.SoundAsset;
import com.ap.audio.AudioService;
import com.ap.items.Inventory;
import com.ap.items.ItemStack;
import com.ap.ui.widget.ItemContainer;
import com.ap.ui.widget.TooltipHelper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import static java.util.Collections.swap;

public class InventoryTab extends AbstractContent{

    private Inventory inventory;

    private Table itemContainer;
    private Table backpack;

    private float cellWidth;
    private float cellHeight;

    protected TextureRegion border_free;
    protected TextureRegion border_selected;
    protected TextureRegion cell_locked;
    protected TextureRegion cell_empty;

    private ItemCell[][] cells;
    private final int n;
    private final int m;

    private TooltipHelper tooltipHelper;

    private Table tooltip;
    private Label tooltipLabel;

    private int selectedCell = -1;

    public InventoryTab(Stage stage, AssetService assetService, Skin skin, AudioService audioService, int width, int height, Tabs icon, Inventory inventory) {
        super(stage, assetService, skin, audioService, width, height, icon);
        this.inventory = inventory;

        border_free = atlas.findRegion("cell/border", 0);
        border_selected = atlas.findRegion("cell/border", 1);
        cell_locked = atlas.findRegion("cell/locked");
        cell_empty = atlas.findRegion("cell/blank");

        cellWidth = 48;
        cellHeight = 48 ;

        m = ItemContainer.maxSize;
        n = MathUtils.ceil((float) Inventory.maxStorage / m);

        tooltipHelper = TooltipHelper.getTooltip(skin);
        tooltip = new Table();
        tooltipLabel = new Label("I'm tooltip", skin);
        tooltipLabel.setFillParent(true);
        tooltip.add(tooltipLabel).center().expand().fill();
        tooltip.setVisible(false);

        makeStructure();
        loadData();
        addActor(tooltip);


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

        for (int x = 0; x < Inventory.maxStorage; x++) {
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
                        if (!cell.isLocked()) {
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

    }

    @Override
    public void loadData() {
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

            drawable = new TextureRegionDrawable(cell_empty);
            drawable.setMinSize(cellWidth, cellHeight);
            blankCell = new Image(new TextureRegionDrawable(drawable));

            drawable = new TextureRegionDrawable(cell_locked);
            drawable.setMinSize(cellWidth, cellHeight);
            lockedCell = new Image(new TextureRegionDrawable(drawable));

            drawable = new TextureRegionDrawable(border_free);
            drawable.setMinSize(cellWidth, cellHeight);
            freeBorder = new Image(new TextureRegionDrawable(drawable));

            drawable = new TextureRegionDrawable(border_selected);
            drawable.setMinSize(cellWidth, cellHeight);
            selectedBorder = new Image(new TextureRegionDrawable(drawable));

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
            icon = null;
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
