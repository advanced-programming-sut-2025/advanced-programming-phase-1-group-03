package phi.ap.model.enums;

import phi.ap.model.items.Item;

import java.util.ArrayList;

public enum AbilityType {
    Farming,
    Extraction,
    Foraging,
    Fishing
    ;
    private int level = 0;
    public void setLevel(int value) {

        this.level = value;
    }
    public int getLevel() {

        return level;
    }
    public static int MAX_VALUE = 4;
}
