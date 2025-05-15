package phi.ap.model.enums.npcStuff;

import phi.ap.model.ItemStack;
import phi.ap.model.npcStuff.dialogueStuff.Condition;

import java.util.ArrayList;

public enum Quests {
    ;
    Condition activeChecker;
    Condition requirements;
    ArrayList<ItemStack> rewards;
    NPCTypes owner;
}
