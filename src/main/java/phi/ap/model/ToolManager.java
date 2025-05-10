package phi.ap.model;

import phi.ap.model.items.products.Fish;
import phi.ap.model.items.tools.*;

public class ToolManager {
    private Hoe hoe;
    private TrashCan trashCan;
    private Backpack backpack;
    private Pickaxe pickaxe;
    private Axe axe;
    private WateringCan wateringCan;
    private FishingPole fishingPole;
    private Scythe scythe;
    private MilkPail milkPail;
    private Shear shear;

    private Tool currentTool = null;

    public Hoe getHoe() {
        return hoe;
    }

    public TrashCan getTrashCan() {
        return trashCan;
    }

    public Backpack getBackpack() {
        return backpack;
    }

    public void setBackpack(Backpack backpack) {
        this.backpack = backpack;
    }


    public Pickaxe getPickaxe() {
        return pickaxe;
    }

    public Axe getAxe() {
        return axe;
    }

    public WateringCan getWateringCan() {
        return wateringCan;
    }

    public FishingPole getFishingPole() {
        return fishingPole;
    }

    public Scythe getScythe() {
        return scythe;
    }

    public MilkPail getMilkPail() {
        return milkPail;
    }

    public Shear getShear() {
        return shear;
    }

    public Tool getCurrentTool() {
        return currentTool;
    }

    public void setCurrentTool(Tool currentTool) {
        this.currentTool = currentTool;
    }

    public void createDefaultTools() {
        this.hoe = new Hoe();
        this.backpack = new Backpack();
        this.trashCan = new TrashCan();
        this.pickaxe = new Pickaxe();
        this.axe = new Axe();
        this.wateringCan = new WateringCan();
        this.scythe = new Scythe();
        this.milkPail = new MilkPail();
        this.shear = new Shear();
    }
    public void addDefaultTools(InventoryManager inventoryManager) {
        inventoryManager.addItem(this.hoe, 1);
        inventoryManager.addItem(this.backpack, 1);
        inventoryManager.addItem(this.trashCan, 1);
        inventoryManager.addItem(this.pickaxe, 1);
        inventoryManager.addItem(this.axe, 1);
        inventoryManager.addItem(this.wateringCan, 1);
        inventoryManager.addItem(this.scythe, 1);
        inventoryManager.addItem(this.milkPail, 1);
        inventoryManager.addItem(this.shear, 1);
    }
}
