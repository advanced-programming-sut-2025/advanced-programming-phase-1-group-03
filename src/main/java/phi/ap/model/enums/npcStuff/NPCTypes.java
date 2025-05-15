package phi.ap.model.enums.npcStuff;

import phi.ap.model.ItemStack;
import phi.ap.model.Tile;
import phi.ap.model.enums.Colors;
import phi.ap.model.enums.StoreTypes;
import phi.ap.model.items.Item;
import phi.ap.model.npcStuff.dialogueStuff.ConditionTypes;
import phi.ap.model.npcStuff.dialogueStuff.DialogueNode;

import java.util.ArrayList;
import java.util.List;

public enum NPCTypes {
    Clint("Clint",
            StoreTypes.Blacksmith,
            null,
            "Villager",
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new Tile("⍢", Colors.fg(17), "")),
    Morris("Morris",
            StoreTypes.JojaMart,
            null,
            "Villager",
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new Tile("⍢", Colors.fg(17), "")),
    Pierre("Pierre",
            StoreTypes.PierreGeneralStore,
            null,
            "Villager",
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new Tile("⍢", Colors.fg(17), "")),
    Robin("Robin",
            StoreTypes.CarpenterShop,
            null,
            "Villager",
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new Tile("⍢", Colors.fg(17), "")),
    Willy("Willy",
            StoreTypes.FishShop,
            null,
            "Villager",
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new Tile("⍢", Colors.fg(188), "")),
    Marnie("Marnie",
            StoreTypes.MarnieRanch,
            null,
            "Villager",
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new Tile("⍢", Colors.fg(17), "")),
    Gus("Gus",
            StoreTypes.TheStarDropSaloon,
            null,
            "Villager",
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new Tile("⍢", Colors.fg(17), "")),
    NPC1("NPC1",
            null,
            NPCHouseTypes.House1,
            "Villager",
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new Tile("⍢", Colors.fg(17), "")),
    NPC2("NPC2",
            null,
            NPCHouseTypes.House2,
            "Villager",
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new Tile("⍢", Colors.fg(17), "")),
    NPC3("NPC3",
            null,
            NPCHouseTypes.House3,
            "Villager",
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new Tile("⍢", Colors.fg(17), "")),
    NPC4("NPC4",
            null,
            NPCHouseTypes.House4,
            "Villager",
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new Tile("⍢", Colors.fg(17), "")),
    NPC5("NPC5",
            null,
            NPCHouseTypes.House5,
            "Villager",
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new Tile("⍢", Colors.fg(17), "")),
    ;
    private String npcName;
    private String job;
    private StoreTypes workPlace;
    private NPCHouseTypes house;
    private ArrayList<Item> favorites;
    private ArrayList<ItemStack> possibleGiftsToSend;
    private ArrayList<Quests> quests;
    private DialogueNode dialogueTreeNode;
    private Tile tile;

    NPCTypes(String npcName,
             StoreTypes workPlace,
             NPCHouseTypes house,
             String job,
             ArrayList<Item> favorites,
             ArrayList<ItemStack> possibleGiftsToSend,
             ArrayList<Quests> quests,
             Tile tile) {
        this.npcName = npcName;
        this.workPlace = workPlace;
        this.house = house;
        this.job = job;
        this.favorites = favorites;
        this.possibleGiftsToSend = possibleGiftsToSend;
        this.quests = quests;
        this.tile = tile;
        makeDialogue();
    }

    public void makeDialogue() {
        DialogueNode root = new DialogueNode("...", ConditionTypes.always(), false, "start");
        DialogueNode Greeting = new DialogueNode("Hi there", ConditionTypes.lastMeetDiffMore(24), false, "greeting");
        root.addChild(Greeting);
        dialogueTreeNode = root;
    }

    public String getNpcName() {
        return npcName;
    }

    public StoreTypes getWorkPlace() {
        return workPlace;
    }

    public NPCHouseTypes getHouse() {
        return house;
    }

    public String getJob() {
        return job;
    }

    public ArrayList<Item> getFavorites() {
        return favorites;
    }

    public ArrayList<ItemStack> getPossibleGiftsToSend() {
        return possibleGiftsToSend;
    }

    public ArrayList<Quests> getQuests() {
        return quests;
    }

    public DialogueNode getDialogueTreeNode() {
        return dialogueTreeNode;
    }

    public Tile getTile() {
        return tile;
    }
}