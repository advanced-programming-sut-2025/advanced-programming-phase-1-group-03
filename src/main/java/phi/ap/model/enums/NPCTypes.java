package phi.ap.model.enums;

import phi.ap.model.items.Item;
import phi.ap.model.items.relations.Dialogue;

import java.util.ArrayList;

public enum NPCTypes {
    ;
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