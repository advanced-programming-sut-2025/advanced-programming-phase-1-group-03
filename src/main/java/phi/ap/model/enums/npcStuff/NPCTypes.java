package phi.ap.model.enums.npcStuff;

import phi.ap.model.App;
import phi.ap.model.ItemStack;
import phi.ap.model.Tile;
import phi.ap.model.enums.*;
import phi.ap.model.enums.StoreProducts.BlackSmithsProducts;
import phi.ap.model.items.Item;
import phi.ap.model.npcStuff.dialogueStuff.ConditionTypes;
import phi.ap.model.npcStuff.dialogueStuff.DialogueNode;

import java.util.ArrayList;
import java.util.List;

public enum NPCTypes {
    Clint("Clint",
            StoreTypes.Blacksmith,
            null,
            "Blacksmith",
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new Tile("⍢", Colors.fg(17), "")),
    Morris("Morris",
            StoreTypes.JojaMart,
            null,
            "JojaMart manager",
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new Tile("⍢", Colors.fg(17), "")),
    Pierre("Pierre",
            StoreTypes.PierreGeneralStore,
            null,
            "GeneralStore manager",
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new Tile("⍢", Colors.fg(17), "")),
    Robin("Robin",
            StoreTypes.CarpenterShop,
            null,
            "Carpenter",
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new Tile("⍢", Colors.fg(17), "")),
    Willy("Willy",
            StoreTypes.FishShop,
            null,
            "Fisher",
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new Tile("⍢", Colors.fg(188), "")),
    Marnie("Marnie",
            StoreTypes.MarnieRanch,
            null,
            "Rancher",
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new Tile("⍢", Colors.fg(17), "")),
    Gus("Gus",
            StoreTypes.TheStarDropSaloon,
            null,
            "Saloon manager",
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new Tile("⍢", Colors.fg(17), "")),
    Sebastian("Sebastian",
            null,
            NPCHouseTypes.House1,
            "Villager",
            new ArrayList<>(List.of(App.getInstance().getGameService().getItem(AnimalProductTypes.Wool.toString()),
                    App.getInstance().getGameService().getItem(FoodTypes.PumpkinPie.toString()),
                    App.getInstance().getGameService().getItem(FoodTypes.Pizza.toString()))),
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new Tile("⍢", Colors.fg(17), "")),
    Abigail("Abigail",
            null,
            NPCHouseTypes.House2,
            "Villager",
            new ArrayList<>(List.of(App.getInstance().getGameService().getItem(StoneTypes.RegularStone.toString()),
                    App.getInstance().getGameService().getItem(ForagingMineralTypes.Iron.toString()),
                    App.getInstance().getGameService().getItem(FoodTypes.Coffee.toString()))),
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new Tile("⍢", Colors.fg(17), "")),
    Harvey("Harvey",
            null,
            NPCHouseTypes.House3,
            "Villager",
            new ArrayList<>(List.of(App.getInstance().getGameService().getItem(FoodTypes.Coffee.toString()),
                    App.getInstance().getGameService().getItem(FoodTypes.Pickles.toString()),
                    App.getInstance().getGameService().getItem(FoodTypes.Wine.toString()))),
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new Tile("⍢", Colors.fg(17), "")),
    Lia("Lia",
            null,
            NPCHouseTypes.House4,
            "Villager",
            new ArrayList<>(List.of(App.getInstance().getGameService().getItem(FoodTypes.Salad.toString()),
                    App.getInstance().getGameService().getItem(CropsTypes.Grape.toString()),
                    App.getInstance().getGameService().getItem(FoodTypes.Wine.toString()))),
            new ArrayList<>(List.of()),
            new ArrayList<>(List.of()),
            new Tile("⍢", Colors.fg(17), "")),
    RobinJr("RobinJr",
            null,
            NPCHouseTypes.House5,
            "Villager",
            new ArrayList<>(List.of(App.getInstance().getGameService().getItem(FoodTypes.Spaghetti.toString()),
                    App.getInstance().getGameService().getItem("Wood"),
                    App.getInstance().getGameService().getItem(ForagingMineralTypes.IronBar.toString()))),
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
             StoreTypes wp,
             NPCHouseTypes house,
             String job,
             ArrayList<Item> favorites,
             ArrayList<ItemStack> possibleGiftsToSend,
             ArrayList<Quests> quests,
             Tile tile) {
        this.npcName = npcName;
        this.workPlace = wp;
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

    public static NPCTypes findByName(String npcName) {
        npcName = npcName.replaceAll("\\s+", "");
        for (NPCTypes type : NPCTypes.values()) {
            if (type.toString().equalsIgnoreCase(npcName))
                return type;
        }
        return null;
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