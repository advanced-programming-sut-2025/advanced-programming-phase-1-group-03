package phi.ap.model.enums;

import phi.ap.model.App;
import phi.ap.model.Tile;
import phi.ap.model.enums.Time.Seasons;

import java.util.ArrayList;

public enum ForagingMineralTypes {
    Quartz("*", Colors.fg(251), "", "Quartz", LevelName.iron, "A clear crystal commonly found in caves and mines.", 25, 5),
    EarthCrystal("*", Colors.fg(251), "", "EarthCrystal", LevelName.iron, "A resinous substance found near the surface.", 50, 5),
    FrozenTear("*", Colors.fg(251), "", "FrozenTear", LevelName.iron, "A crystal fabled to be the frozen tears of a yeti.", 75, 5),
    FireQuartz("*", Colors.fg(251), "", "FireQuartz", LevelName.iron,"A glowing red crystal commonly found near hot lava.", 100, 5),
    Emerald("*", Colors.fg(251), "", "Emerald", LevelName.iron,"A precious stone with a brilliant green color.", 250, 5),
    Aquamarine("*", Colors.fg(251), "", "Aquamarine", LevelName.iron, "A shimmery blue-green gem.", 180, 5),
    Ruby("*", Colors.fg(251), "", "Ruby",LevelName.iron, "A precious stone that is sought after for its rich color and beautiful luster.", 250, 5),
    Amethyst("*", Colors.fg(251), "", "Amethyst", LevelName.iron,"A purple variant of quartz.", 100, 5),
    Topaz("*", Colors.fg(251), "", "Topaz", LevelName.iron,"Fairly common but still prized for its beauty.", 80, 5),
    Jade("*", Colors.fg(251), "", "Jade", LevelName.iron,"A pale green ornamental stone.", 200, 5),
    Diamond("D", Colors.fg(251), "", "Diamond", LevelName.iron,"A rare and valuable gem.", 750, 5),
    PrismaticShard("*", Colors.fg(251), "", "PrismaticShard", LevelName.iron,"A very rare and powerful substance with unknown origins.", 2000, 5),
    Copper("C", Colors.fg(251), "", "Copper", LevelName.normal,"A common ore that can be smelted into bars.", 75, 10),
    CopperBar("C", Colors.fg(253), "", "CopperBar", LevelName.normal,"A common ore that can be smelted into bars.", 75, 0),
    Iron("i", Colors.fg(251), "", "Iron", LevelName.copper, "A fairly common ore that can be smelted into bars.", 150, 10),
    IronBar("i", Colors.fg(253), "", "IronBar", LevelName.copper, "A fairly common ore that can be smelted into bars.", 150, 0),
    Gold("G", Colors.fg(251), "", "Gold", LevelName.iron, "A precious ore that can be smelted into bars.", 400, 10),
    GoldBar("G", Colors.fg(251), "", "GoldBar", LevelName.iron, "A precious ore that can be smelted into bars.", 400, 0),
    Iridium("I", Colors.fg(251), "", "Iridium", LevelName.golden, "An exotic ore with many curious properties. Can be smelted into bars.", 100, 10),
    IridiumBar("I", Colors.fg(251), "", "IridiumBar", LevelName.golden, "An exotic ore with many curious properties. Can be smelted into bars.", 100, 0),
    Coal("c", Colors.fg(251), "", "Coal", LevelName.copper,"A combustible rock that is useful for crafting and smelting.", 150, 5);
    private final int sellPrice;
    private final String description;
    private final String name;
    private Tile tile;
    private LevelName levelNeedToMine;
    private int probabilityPercent;
    ForagingMineralTypes(String symbol, String fgColor, String bgColor, String name, LevelName levelNeedToMine,
                         String Description, int sellPrice, int probabilityPercent) {
        this.sellPrice = sellPrice;
        this.description = Description;
        this.name = name;
        this.levelNeedToMine = levelNeedToMine;
        this.tile = new Tile(symbol, fgColor, bgColor);
        this.probabilityPercent = probabilityPercent;
    }
    public String getDescription() {
        return this.description;
    }
    public String getName() {
        return this.name;
    }
    public int getSellPrice() {
        return this.sellPrice;
    }

    public Tile getTile() {
        return tile;
    }

    public static ForagingMineralTypes getRandom() {
        int ind = App.getInstance().getRandomNumber(1, 100);
        int sum = 0;
        for (ForagingMineralTypes value : values()) {
            sum += value.getProbabilityPercent();
            if (ind <= sum) return value;
        }
        return Coal;
    }

    public LevelName getLevelNeedToMine() {

        return levelNeedToMine;
    }

    public int getProbabilityPercent() {
        return probabilityPercent;
    }
}
