package phi.ap.model;

import phi.ap.model.items.Item;
import phi.ap.model.items.products.Fish;
import phi.ap.model.items.tools.*;

import java.util.ArrayList;

public class ToolManager {

    private Backpack backpack;
    private TrashCan trashCan;

    private Tool currentTool = null;

    public Backpack getBackpack() {
        return backpack;
    }

    public void setBackpack(Backpack backpack) {
        this.backpack = backpack;
    }

    public Tool getCurrentTool() {
        return currentTool;
    }

    public void setCurrentTool(Tool currentTool) {
        this.currentTool = currentTool;
    }

    public void addBackpack() {
        this.backpack = new Backpack();
        this.trashCan = new TrashCan();
    }
    public void addDefaultTools(InventoryManager inventoryManager) {
        inventoryManager.addItem(new Hoe(), 1);
        inventoryManager.addItem(this.backpack, 1);
        inventoryManager.addItem(this.trashCan, 1);
        inventoryManager.addItem(new Pickaxe(), 1);
        inventoryManager.addItem(new Axe(), 1);
        inventoryManager.addItem(new WateringCan(), 1);
        inventoryManager.addItem(new Scythe(), 1);
        inventoryManager.addItem(new MilkPail(), 1);
        inventoryManager.addItem(new Shear(), 1);
    }

    public TrashCan getTrashCan() {
        return trashCan;
    }
}
