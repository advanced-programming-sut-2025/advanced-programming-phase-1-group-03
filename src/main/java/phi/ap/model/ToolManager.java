package phi.ap.model;

import phi.ap.model.items.tools.Backpack;
import phi.ap.model.items.tools.Hoe;
import phi.ap.model.items.tools.TrashCan;

public class ToolManager {
    private Hoe hoe;
    private TrashCan trashCan;
    private Backpack backpack;

    public Hoe getHoe() {
        return hoe;
    }

    public void setHoe(Hoe hoe) {
        this.hoe = hoe;
    }

    public TrashCan getTrashCan() {
        return trashCan;
    }

    public void setTrashCan(TrashCan trashCan) {
        this.trashCan = trashCan;
    }

    public Backpack getBackpack() {
        return backpack;
    }

    public void setBackpack(Backpack backpack) {
        this.backpack = backpack;
    }
}
