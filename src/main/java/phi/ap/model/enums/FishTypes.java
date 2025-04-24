package phi.ap.model.enums;

import phi.ap.model.enums.Time.Seasons;

public enum FishTypes {
    Salmon("Salmon", 75, Seasons.Fall);
    private String name;
    private int sellPrice;
    private Seasons season;
    FishTypes(String name, int price, Seasons season ) {
        this.name = name;
        this.sellPrice = price;
        this.season = season;
    }
}
