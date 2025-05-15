package phi.ap.model.npcStuff;

import phi.ap.model.Date;
import phi.ap.model.Game;
import phi.ap.model.InventoryManager;
import phi.ap.model.Player;
import phi.ap.model.enums.npcStuff.NPCFriendshipManager;
import phi.ap.model.enums.npcStuff.NPCTypes;
import phi.ap.model.enums.npcStuff.Quests;
import phi.ap.model.items.tools.Backpack;
import phi.ap.model.items.tools.TrashCan;
import phi.ap.model.npcStuff.dialogueStuff.DialogueNode;

public class State { //this class store state between player and NPC, each npc has memory and that's states;
    private Player player;
    private NPCTypes npc;
    private NPCFriendshipManager friendShipManager;
    private Date lastMeet = null;
    private String lastAnswer = "";
    private DialogueNode lastConversation = null;

    private InventoryManager giftReceived = null; // fromPlayer
    private InventoryManager giftSent = null; // to player
    {
        Backpack backpack1 = new Backpack();
        backpack1.getLevelProcess().setCurrentLevel(backpack1.getLevelProcess().getLevelNames().size() - 1);
        giftReceived = new InventoryManager(backpack1, new TrashCan());
        Backpack backpack2 = new Backpack();
        backpack2.getLevelProcess().setCurrentLevel(backpack2.getLevelProcess().getLevelNames().size() - 1);
        giftSent = new InventoryManager(backpack2, new TrashCan());
    }

    public State(Player player, NPCTypes npc) {
        this.player = player;
        this.npc = npc;
        setFriendShipManager(NPCFriendshipManager.Level0);
    }

    public void setFriendShipManager(NPCFriendshipManager friendShipManager) {
        this.friendShipManager = friendShipManager;
        friendShipManager.setUp(this);
    }

    public int getFriendshipLevel() {
        return friendShipManager.getLevelID();
    }

    public Date getCurrentDate() {
        return Game.getInstance().getDate();
    }

    public boolean isQuestActive(Quests quest) {
        return player.getActiveQuests().contains(quest);
    }
    public boolean isQuestDone(Quests quest) {
        return player.getDoneQuests().contains(quest);
    }
    public boolean isQuestActivatedSoFar(Quests quest) {
        return isQuestActive(quest) || isQuestDone(quest);
    }

    public Player getPlayer() {
        return player;
    }

    public NPCTypes getNpc() {
        return npc;
    }

    public Date getLastMeet() {
        return lastMeet;
    }

    public String getLastAnswer() {
        return lastAnswer;
    }

    public DialogueNode getLastConversation() {
        return lastConversation;
    }

    public InventoryManager getGiftReceived() {
        return giftReceived;
    }

    public InventoryManager getGiftSent() {
        return giftSent;
    }

    public void setLastMeet(Date lastMeet) {
        this.lastMeet = lastMeet;
    }

    public void setLastAnswer(String lastAnswer) {
        this.lastAnswer = lastAnswer;
    }

    public void setLastConversation(DialogueNode lastConversation) {
        this.lastConversation = lastConversation;
    }
}
