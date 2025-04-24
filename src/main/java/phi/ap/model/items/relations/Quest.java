package phi.ap.model.items.relations;

import phi.ap.model.ItemStack;
import phi.ap.model.enums.NPCTypes;

import java.util.ArrayList;

public class Quest {
    private final NPCTypes npc;
    private final ArrayList<ItemStack> requiredItems;
    private final ArrayList<ItemStack> rewardItems;
    private boolean isFinished = false;
    private boolean isActive = false;

    public Quest(NPCTypes npc, ArrayList<ItemStack> requiredItems, ArrayList<ItemStack> rewardItems) {
        this.npc = npc;
        this.requiredItems = requiredItems;
        this.rewardItems = rewardItems;
    }

    public boolean isActive() {
        return isActive;
    }

    public NPCTypes getNpc() {
        return npc;
    }

    public ArrayList<ItemStack> getRequiredItems() {
        return requiredItems;
    }

    public ArrayList<ItemStack> getRewardItems() {
        return rewardItems;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
