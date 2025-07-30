package phi.ap.model.enums;

public enum Tool.BasicToolLevels {
    copper("Copper", 1.1),
    Iron("Iron", 1.25),
    golden("Golden", 1.5),
    iridium("Iridium", 2),
    training("Training", 1),
    bamboo("Bamboo", 1.25),
    fiberGlass("FiberGlass", 1.5),
    initial("Initial", 1),
    big("big", 1.2),
    deluxe("Deluxe", 1.3),
    normal("Normal", 1),
    silver("Silver", 1.3),
    ;
    private String name;
    double sellPrinceCoefficient;

    Tool.BasicToolLevels(String name, double sellPrinceCoefficient) {
        this.name = name;
        this.sellPrinceCoefficient = sellPrinceCoefficient;
    }

    public String getName() {
        return name;
    }

    public double getSellPrinceCoefficient() {
        return sellPrinceCoefficient;
    }
}
