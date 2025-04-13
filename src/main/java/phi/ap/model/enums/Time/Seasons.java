package phi.ap.model.enums.Time;

public enum Seasons {
    Spring("Spring"),
    Summer("Summer"),
    Fall("Fall"),
    Winter("Winter"),
    ;

    public String name;
    Seasons(String name) {
        this.name = name;
    }
    public final static int days = 28;
}
