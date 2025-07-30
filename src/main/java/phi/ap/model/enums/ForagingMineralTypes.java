package phi.ap.model.enums;

import phi.ap.model.App;
import phi.ap.model.Tile;
import phi.ap.model.enums.Time.Seasons;
import phi.ap.model.items.Item;
import phi.ap.model.items.products.Mineral;

import java.util.ArrayList;

public enum ForagingMineralTypes {
    // Quartz_Node
    Quartz("*", Colors.fg(251), "", "Quartz", Tool.BasicToolLevels.Iron, "A clear crystal commonly found in caves and mines.", 25, 5),

    // Omni
    Earth_Crystal("*", Colors.fg(251), "", "EarthCrystal", Tool.BasicToolLevels.Iron, "A resinous substance found near the surface.", 50, 5),

    FrozenTear("*", Colors.fg(251), "", "FrozenTear", Tool.BasicToolLevels.Iron, "A crystal fabled to be the frozen tears of a yeti.", 75, 5),

    Fire_Quartz("*", Colors.fg(251), "", "FireQuartz", Tool.BasicToolLevels.Iron,"A glowing red crystal commonly found near hot lava.", 100, 5),
    Emerald("*", Colors.fg(251), "", "Emerald", Tool.BasicToolLevels.Iron,"A precious stone with a brilliant green color.", 250, 5),
    Aquamarine("*", Colors.fg(251), "", "Aquamarine", Tool.BasicToolLevels.Iron, "A shimmery blue-green gem.", 180, 5),
    Ruby("*", Colors.fg(251), "", "Ruby",Tool.BasicToolLevels.Iron, "A precious stone that is sought after for its rich color and beautiful luster.", 250, 5),
    Amethyst("*", Colors.fg(251), "", "Amethyst", Tool.BasicToolLevels.Iron,"A purple variant of quartz.", 100, 5),
    Topaz("*", Colors.fg(251), "", "Topaz", Tool.BasicToolLevels.Iron,"Fairly common but still prized for its beauty.", 80, 5),
    Jade("*", Colors.fg(251), "", "Jade", Tool.BasicToolLevels.Iron,"A pale green ornamental stone.", 200, 5),
    Diamond("D", Colors.fg(251), "", "Diamond", Tool.BasicToolLevels.Iron,"A rare and valuable gem.", 750, 5),

    // Omni
    Prismatic_Shard("*", Colors.fg(251), "", "PrismaticShard", Tool.BasicToolLevels.Iron,"A very rare and powerful substance with unknown origins.", 2000, 5),
    Copper_Ore("C", Colors.fg(251), "", "Copper Ore", Tool.BasicToolLevels.normal,"A common ore that can be smelted into bars.", 75, 10),
    Iron_Ore("i", Colors.fg(251), "", "Iron", Tool.BasicToolLevels.copper, "A fairly common ore that can be smelted into bars.", 150, 10),
    Gold_Ore("G", Colors.fg(251), "", "Gold", Tool.BasicToolLevels.Iron, "A precious ore that can be smelted into bars.", 400, 10),
    Iridium_Ore("I", Colors.fg(251), "", "Iridium", Tool.BasicToolLevels.golden, "An exotic ore with many curious properties. Can be smelted into bars.", 100, 10),
    Coal("c", Colors.fg(0), "", "Coal", Tool.BasicToolLevels.copper,"A combustible rock that is useful for crafting and smelting.", 150, 5);

    CopperBar("C", Colors.fg(253), "", "CopperBar", Tool.BasicToolLevels.normal,"A common ore that can be smelted into bars.", 75, 0),
    IronBar("i", Colors.fg(253), "", "IronBar", Tool.BasicToolLevels.copper, "A fairly common ore that can be smelted into bars.", 150, 0),
    GoldBar("G", Colors.fg(251), "", "GoldBar", Tool.BasicToolLevels.Iron, "A precious ore that can be smelted into bars.", 400, 0),
    IridiumBar("I", Colors.fg(251), "", "IridiumBar", Tool.BasicToolLevels.golden, "An exotic ore with many curious properties. Can be smelted into bars.", 100, 0),

    private final int sellPrice;
    private final String description;
    private final String name;
    private Tile tile;
    private Tool.BasicToolLevels levelNeedToMine;
    private int probabilityPercent;
    private Item item;
    ForagingMineralTypes(String symbol, String fgColor, String bgColor, String name, Tool.BasicToolLevels levelNeedToMine,
                         String Description, int sellPrice, int probabilityPercent) {
        this.sellPrice = sellPrice;
        this.description = Description;
        this.name = name;
        this.levelNeedToMine = levelNeedToMine;
        this.tile = new Tile(symbol, fgColor, bgColor);
        this.probabilityPercent = probabilityPercent;
        this.item = new Mineral(1, 1, this);
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

    public Tool.BasicToolLevels getLevelNeedToMine() {

        return levelNeedToMine;
    }

    public int getProbabilityPercent() {
        return probabilityPercent;
    }

    public static ForagingMineralTypes getType(String name) {
        ForagingMineralTypes foragingMineralType;
        try {
            foragingMineralType = ForagingMineralTypes.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
        return foragingMineralType;
    }

    public Item getItem() {
        return item;
    }
    public static ForagingMineralTypes find(String input) {
        input = input.replaceAll("\\s+", "").toLowerCase();
        for (ForagingMineralTypes min : ForagingMineralTypes.values()) {
            if (min.toString().toLowerCase().equals(input)) {
                return min;
            }
        }
        return null;
    }
}
