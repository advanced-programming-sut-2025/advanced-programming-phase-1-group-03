package phi.ap.model.enums;

import phi.ap.model.App;
import phi.ap.model.Tile;
import phi.ap.model.enums.Time.Seasons;

import java.util.ArrayList;

public enum ForagingMineralTypes {
    QUARTZ("*", Colors.fg(251), "", "Quartz", LevelName.iron, "A clear crystal commonly found in caves and mines.", 25),
    EARTH_CRYSTAL("*", Colors.fg(251), "", "Earth Crystal", LevelName.iron, "A resinous substance found near the surface.", 50),
    FROZEN_TEAR("*", Colors.fg(251), "", "Frozen Tear", LevelName.iron, "A crystal fabled to be the frozen tears of a yeti.", 75),
    FIRE_QUARTZ("*", Colors.fg(251), "", "Fire Quartz", LevelName.iron,"A glowing red crystal commonly found near hot lava.", 100),
    EMERALD("*", Colors.fg(251), "", "Emerald", LevelName.iron,"A precious stone with a brilliant green color.", 250),
    AQUAMARINE("*", Colors.fg(251), "", "Aquamarine", LevelName.iron, "A shimmery blue-green gem.", 180),
    RUBY("*", Colors.fg(251), "", "Ruby",LevelName.iron, "A precious stone that is sought after for its rich color and beautiful luster.", 250),
    AMETHYST("*", Colors.fg(251), "", "Amethyst", LevelName.iron,"A purple variant of quartz.", 100),
    TOPAZ("*", Colors.fg(251), "", "Topaz", LevelName.iron,"Fairly common but still prized for its beauty.", 80),
    JADE("*", Colors.fg(251), "", "Jade", LevelName.iron,"A pale green ornamental stone.", 200),
    DIAMOND("D", Colors.fg(251), "", "Diamond", LevelName.iron,"A rare and valuable gem.", 750),
    PRISMATIC_SHARD("*", Colors.fg(251), "", "Prismatic Shard", LevelName.iron,"A very rare and powerful substance with unknown origins.", 2000),
    COPPER("C", Colors.fg(251), "", "Copper", LevelName.normal,"A common ore that can be smelted into bars.", 5),
    IRON("i", Colors.fg(251), "", "Iron", LevelName.copper, "A fairly common ore that can be smelted into bars.", 10),
    GOLD("G", Colors.fg(251), "", "Gold", LevelName.iron, "A precious ore that can be smelted into bars.", 25),
    IRIDIUM("I", Colors.fg(251), "", "Iridium", LevelName.golden, "An exotic ore with many curious properties. Can be smelted into bars.", 100),
    COAL("c", Colors.fg(251), "", "Coal", LevelName.copper,"A combustible rock that is useful for crafting and smelting.", 15);
    private final int sellPrice;
    private final String description;
    private final String name;
    private Tile tile;
    private LevelName levelNeedToMine;
    ForagingMineralTypes(String symbol, String fgColor, String bgColor, String name, LevelName levelNeedToMine,
                         String Description, int sellPrice) {
        this.sellPrice = sellPrice;
        this.description = Description;
        this.name = name;
        this.levelNeedToMine = levelNeedToMine;
        this.tile = new Tile(symbol, fgColor, bgColor);
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
        int ind = App.getInstance().getRandomNumber(0, ForagingMineralTypes.values().length - 1);
        return ForagingMineralTypes.values()[ind];
    }

    public LevelName getLevelNeedToMine() {
        return levelNeedToMine;
    }
}
