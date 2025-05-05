package phi.ap.model.enums;

import phi.ap.model.items.Item;
import phi.ap.model.items.buildings.Store;
import phi.ap.model.items.relations.Dialogue;

import java.util.ArrayList;

public enum NPCTypes {
    Clint("Clint", null, null, StoreTypes.Blacksmith, null),
    Morris("Morris", null, null, StoreTypes.JojaMart, null),
    Pierre("Pierre", null, null, StoreTypes.PierreGeneralStore, null),
    Robin("Robin", null, null, StoreTypes.CarpenterShop, null),
    Willy("Willy", null, null, StoreTypes.FishShop, null),
    Marnie("Marnie", null, null, StoreTypes.MarnieRanch, null),
    Gus("Gus", null, null, StoreTypes.TheStarDropSaloon, null);
    private String name;
    private ArrayList<Item> favoriteItems;
    private String about;
    private StoreTypes home;
    private ArrayList<Dialogue> dialogues;

    NPCTypes(String name, ArrayList<Item> favoriteItems, String about, StoreTypes home, ArrayList<Dialogue> dialogues) {
        this.name = name;
        this.favoriteItems = favoriteItems;
        this.about = about;
        this.home = home;
        this.dialogues = dialogues;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Item> getFavoriteItems() {
        return favoriteItems;
    }

    public String getAbout() {
        return about;
    }

    public StoreTypes getHome() {
        return home;
    }

    public ArrayList<Dialogue> getDialogues() {
        return dialogues;
    }
}