package phi.ap.model.enums.npcStuff;

import phi.ap.model.App;
import phi.ap.model.ItemStack;
import phi.ap.model.Tile;
import phi.ap.model.enums.*;
import phi.ap.model.enums.Time.Seasons;
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
            NPCHouseTypes.SebastianHouse,
            "Villager",
            new ArrayList<>(List.of(App.getInstance().getGameService().getItem(AnimalProductTypes.Wool.toString()),
                    App.getInstance().getGameService().getItem(FoodTypes.PumpkinPie.toString()),
                    App.getInstance().getGameService().getItem(FoodTypes.Pizza.toString()))),
            new ArrayList<>(List.of(new ItemStack(App.getInstance().getGameService().getItem(FoodTypes.Cookie.toString()), 1),
                    new ItemStack(App.getInstance().getGameService().getItem(CropsTypes.Blueberry.toString()), 5))),
            new ArrayList<>(List.of()),
            new Tile("⍢", Colors.fg(87), "")) {

        public void makeDialogue() {
            DialogueNode root = new DialogueNode("...", ConditionTypes.always(), false, "start");
            DialogueNode firstMeet = new DialogueNode("Hmmm, Have I seen you before?", ConditionTypes.firstMeet(), false, "firstMeet");
            DialogueNode firstMeet2 = new DialogueNode("Aww, you're the new one, wish you luck", ConditionTypes.always(), false, "firstMeet2");
            root.addChild(firstMeet);
            firstMeet.addChild(firstMeet2);

            DialogueNode friendShip3 = new DialogueNode("What's up bro?", ConditionTypes.friendshipMore(3), false, "friendship");
            root.addChild(friendShip3);


            DialogueNode rainyDay = new DialogueNode("Such a beautiful day, I always loved rain", ConditionTypes.weather(Weather.Rain));
            DialogueNode stormDay = new DialogueNode("It's such a heavy storm, you need to go back to your house", ConditionTypes.weather(Weather.Storm));
            DialogueNode snowDay = new DialogueNode("It's ffrreeeezing out there", ConditionTypes.weather(Weather.Snow));
            root.addChild(rainyDay);
            root.addChild(stormDay);
            root.addChild(snowDay);
            friendShip3.addChild(rainyDay);
            friendShip3.addChild(stormDay);
            friendShip3.addChild(snowDay);

            DialogueNode seasonSummer = new DialogueNode("I love summer!", ConditionTypes.season(Seasons.Summer), false, "season");
            root.addChild(seasonSummer);
            friendShip3.addChild(seasonSummer);

            DialogueNode hi = new DialogueNode("Is everything fine?", ConditionTypes.always(), false, "hi");
            root.addChild(hi);
            this.setDialogueTreeNode(root);
        }

    },
    Abigail("Abigail",
            null,
            NPCHouseTypes.AbigailHouse,
            "Villager",
            new ArrayList<>(List.of(App.getInstance().getGameService().getItem(StoneTypes.RegularStone.toString()),
                    App.getInstance().getGameService().getItem(ForagingMineralTypes.Iron.toString()),
                    App.getInstance().getGameService().getItem(FoodTypes.Coffee.toString()))),
            new ArrayList<>(List.of(new ItemStack(App.getInstance().getGameService().getItem(FoodTypes.Spaghetti.toString()), 1),
                    new ItemStack(App.getInstance().getGameService().getItem(ForagingMineralTypes.Copper.toString()), 5))),
            new ArrayList<>(List.of()),
            new Tile("⍢", Colors.fg(118), "")){
        public void makeDialogue() {
            DialogueNode root = new DialogueNode("...", ConditionTypes.always(), false, "start");

            DialogueNode friendship0 = new DialogueNode("Go away stranger!", ConditionTypes.friendshipLess(0), false, "friendshipLess");
            root.addChild(friendship0);
            DialogueNode friendship1 = new DialogueNode("I have no business with you!", ConditionTypes.friendshipLess(1), false, "friendshipLess");
            root.addChild(friendship1);
            DialogueNode friendship2 = new DialogueNode("Hello friend", ConditionTypes.friendshipLess(2), false, "friendshipLess");
            root.addChild(friendship2);
            DialogueNode friendShip3 = new DialogueNode("wanna stay:)?", ConditionTypes.friendshipMore(3), false, "friendship");
            root.addChild(friendShip3);

            DialogueNode seasonSummer = new DialogueNode("summerTime sadness:(", ConditionTypes.season(Seasons.Summer), false, "season");
            friendship2.addChild(seasonSummer);
            friendShip3.addChild(seasonSummer);

            DialogueNode rainyDay = new DialogueNode("wanna walk under the rain?", ConditionTypes.weather(Weather.Rain));
            DialogueNode stormDay = new DialogueNode("It's such a heavy storm, stay here it's dangerous", ConditionTypes.weather(Weather.Storm));
            DialogueNode snowDay = new DialogueNode("It's cold", ConditionTypes.weather(Weather.Snow));
            friendship2.addChild(rainyDay);
            friendship2.addChild(stormDay);
            friendship2.addChild(snowDay);
            friendShip3.addChild(rainyDay);
            friendShip3.addChild(stormDay);
            friendShip3.addChild(snowDay);

            this.setDialogueTreeNode(root);
        }
    },
    Harvey("Harvey",
            null,
            NPCHouseTypes.HarveyHouse,
            "Villager",
            new ArrayList<>(List.of(App.getInstance().getGameService().getItem(FoodTypes.Coffee.toString()),
                    App.getInstance().getGameService().getItem(FoodTypes.Pickles.toString()),
                    App.getInstance().getGameService().getItem(FoodTypes.Wine.toString()))),
            new ArrayList<>(List.of(new ItemStack(App.getInstance().getGameService().getItem(FishTypes.Crimsonfish.toString()), 1),
                    new ItemStack(App.getInstance().getGameService().getItem(CropsTypes.CoffeeBean.toString()), 5))),
            new ArrayList<>(List.of()),
            new Tile("⍢", Colors.fg(0), "")){
        public void makeDialogue() {
            DialogueNode root = new DialogueNode("...", ConditionTypes.always(), false, "start");
            DialogueNode firstMeet = new DialogueNode("Hello rookie!", ConditionTypes.firstMeet(), false, "firstMeet");
            DialogueNode firstMeet2 = new DialogueNode("if you ever need any help, I'm right here", ConditionTypes.always(), false, "firstMeet2");
            root.addChild(firstMeet);
            firstMeet.addChild(firstMeet2);

            DialogueNode friendShip3 = new DialogueNode("Such a shame there is no bank here, we could be rich!", ConditionTypes.friendshipMore(3), false, "friendship");
            root.addChild(friendShip3);


            DialogueNode rainyDay = new DialogueNode("Rain? More like the sky crying over my bad luck!", ConditionTypes.weather(Weather.Rain));
            DialogueNode stormDay = new DialogueNode("Let's fly in the wind!", ConditionTypes.weather(Weather.Storm));
            DialogueNode snowDay = new DialogueNode("Looks like the sky decided to turn into a big freezer! Great", ConditionTypes.weather(Weather.Snow));
            root.addChild(rainyDay);
            root.addChild(stormDay);
            root.addChild(snowDay);
            friendShip3.addChild(rainyDay);
            friendShip3.addChild(stormDay);
            friendShip3.addChild(snowDay);

            DialogueNode seasonSummer = new DialogueNode("If the summer weren't so hot, I would be white!", ConditionTypes.season(Seasons.Summer), false, "season");
            root.addChild(seasonSummer);
            friendShip3.addChild(seasonSummer);

            DialogueNode hi = new DialogueNode("What's up?", ConditionTypes.always(), false, "hi");
            root.addChild(hi);
            this.setDialogueTreeNode(root);
        }
    },
    Lia("Lia",
            null,
            NPCHouseTypes.LiaHouse,
            "Villager",
            new ArrayList<>(List.of(App.getInstance().getGameService().getItem(FoodTypes.Salad.toString()),
                    App.getInstance().getGameService().getItem(CropsTypes.Grape.toString()),
                    App.getInstance().getGameService().getItem(FoodTypes.Wine.toString()))),
            new ArrayList<>(List.of(new ItemStack(App.getInstance().getGameService().getItem(FoodTypes.Coffee.toString()), 1),
                    new ItemStack(App.getInstance().getGameService().getItem(ForagingMineralTypes.Emerald.toString()), 5))),
            new ArrayList<>(List.of()),
            new Tile("⍢", Colors.fg(198), "")){
        public void makeDialogue() {
            DialogueNode root = new DialogueNode("...", ConditionTypes.always(), false, "start");
            DialogueNode firstMeet = new DialogueNode("Hello new one!", ConditionTypes.firstMeet(), false, "firstMeet");
            DialogueNode firstMeet2 = new DialogueNode("Welcome to our village, I hope you have a great moments here", ConditionTypes.always(), false, "firstMeet2");
            root.addChild(firstMeet);
            firstMeet.addChild(firstMeet2);

            DialogueNode friendShip3 = new DialogueNode("Wanna hang out?", ConditionTypes.friendshipMore(3), false, "friendship");
            root.addChild(friendShip3);


            DialogueNode rainyDay = new DialogueNode("You're so wet, don't you have an umbrella?", ConditionTypes.weather(Weather.Rain));
            DialogueNode stormDay = new DialogueNode("Thunder, It scares me", ConditionTypes.weather(Weather.Storm));
            DialogueNode snowDay = new DialogueNode("Let's make a snowman.", ConditionTypes.weather(Weather.Snow));
            root.addChild(rainyDay);
            root.addChild(stormDay);
            root.addChild(snowDay);

            DialogueNode seasonSummer = new DialogueNode("It's such a cool season for swim, I love it!", ConditionTypes.season(Seasons.Summer), false, "season");
            root.addChild(seasonSummer);
            friendShip3.addChild(seasonSummer);

            DialogueNode hi = new DialogueNode("how are you dear?", ConditionTypes.always(), false, "hi");
            root.addChild(hi);
            this.setDialogueTreeNode(root);
        }
    },
    RobinJr("RobinJr",
            null,
            NPCHouseTypes.RobinJrHouse,
            "Villager",
            new ArrayList<>(List.of(App.getInstance().getGameService().getItem(FoodTypes.Spaghetti.toString()),
                    App.getInstance().getGameService().getItem("Wood"),
                    App.getInstance().getGameService().getItem(ForagingMineralTypes.IronBar.toString()))),
            new ArrayList<>(List.of(new ItemStack(App.getInstance().getGameService().getItem(FoodTypes.Beer.toString()), 1),
                    new ItemStack(App.getInstance().getGameService().getItem(ForagingMineralTypes.CopperBar.toString()), 5))),
            new ArrayList<>(List.of()),
            new Tile("⍢", Colors.fg(123), "")){
        public void makeDialogue() {
            DialogueNode root = new DialogueNode("...", ConditionTypes.always(), false, "start");
            DialogueNode firstMeet = new DialogueNode("Ah, a new face in these old lands... The winds carried your scent long before your feet touched this soil.", ConditionTypes.firstMeet(), false, "firstMeet");
            DialogueNode firstMeet2 = new DialogueNode("Tell me, do you seek purpose, or has purpose found you?", ConditionTypes.always(), false, "firstMeet2");
            root.addChild(firstMeet);
            firstMeet.addChild(firstMeet2);

            DialogueNode friendShip3 = new DialogueNode("We've walked far together. Few earn such trust.", ConditionTypes.friendshipMore(3), false, "friendship");
            root.addChild(friendShip3);


            DialogueNode rainyDay = new DialogueNode("Rain is the earth’s way of whispering. Listen closely… Even the clouds have stories to tell, if your heart is quiet enough.", ConditionTypes.weather(Weather.Rain));
            DialogueNode stormDay = new DialogueNode("Hold steady. Storms test more than just the sky.", ConditionTypes.weather(Weather.Storm));
            DialogueNode snowDay = new DialogueNode("Snow is the memory of the sky falling gently to rest. Cold, yes… but in it lies a peaceful kind of death, and the promise of return.", ConditionTypes.weather(Weather.Snow));
            root.addChild(rainyDay);
            root.addChild(stormDay);
            root.addChild(snowDay);
            friendShip3.addChild(rainyDay);
            friendShip3.addChild(stormDay);
            friendShip3.addChild(snowDay);

            DialogueNode seasonSummer = new DialogueNode("Summer's warmth is a gift, but also a test. Life grows wild in its light, but remember — not all that flourishes is meant to last.", ConditionTypes.season(Seasons.Summer), false, "season");
            root.addChild(seasonSummer);
            friendShip3.addChild(seasonSummer);

            DialogueNode hi = new DialogueNode("How you're day is going?", ConditionTypes.always(), false, "hi");
            root.addChild(hi);
            this.setDialogueTreeNode(root);

        }
    },
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

    public void setDialogueTreeNode(DialogueNode dialogueTreeNode) {
        this.dialogueTreeNode = dialogueTreeNode;
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