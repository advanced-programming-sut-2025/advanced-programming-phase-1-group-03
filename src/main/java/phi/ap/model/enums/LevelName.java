package phi.ap.model.enums;

public enum LevelName {
    copper("Copper"),
    iron("Iron"),
    golden("Golden"),
    iridium("Iridium"),
    training("Training"),
    bamboo("Bamboo"),
    fiberGlass("FiberGlass"),
    initial("Initial"),
    big("big"),
    deluxe("Deluxe"),
    normal("Normal"),
    silver("Silver"),
    ;
    private String name;

    LevelName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
