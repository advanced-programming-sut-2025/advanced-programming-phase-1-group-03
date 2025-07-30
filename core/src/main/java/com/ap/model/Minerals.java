package com.ap.model;

import com.ap.items.tools.Tool;

public enum Minerals {
    // Quartz_Node
    Quartz("Quartz", "A clear crystal commonly found in caves and mines.", 25),

    // Omni
    Earth_Crystal("EarthCrystal", "A resinous substance found near the surface.", 50),

    Frozen_Tear("FrozenTear",  "A crystal fabled to be the frozen tears of a yeti.", 75),

    Fire_Quartz("FireQuartz","A glowing red crystal commonly found near hot lava.", 100),
    Emerald("Emerald", "A precious stone with a brilliant green color.", 250),
    Aquamarine("Aquamarine",  "A shimmery blue-green gem.", 180),
    Ruby("Ruby", "A precious stone that is sought after for its rich color and beautiful luster.", 250),
    Amethyst("Amethyst","A purple variant of quartz.", 100),
    Topaz("Topaz", "Fairly common but still prized for its beauty.", 80),
    Jade("Jade","A pale green ornamental stone.", 200),
    Diamond("Diamond", "A rare and valuable gem.", 2500),

    // Omni
    Prismatic_Shard("PrismaticShard","A very rare and powerful substance with unknown origins.", 2000),
    Copper_Ore("Copper Ore", "A common ore that can be smelted into bars.", 75),
    Iron_Ore("Iron","A fairly common ore that can be smelted into bars.", 150),
    Gold_Ore("Gold","A precious ore that can be smelted into bars.", 400),
    Iridium_Ore("Iridium", "An exotic ore with many curious properties. Can be smelted into bars.", 100),
    Coal("Coal","A combustible rock that is useful for crafting and smelting.", 150);

    private final int sellPrice;
    private final String description;
    private final String name;

    Minerals(String name, String Description, int sellPrice){
        this.sellPrice = sellPrice;
        this.description = Description;
        this.name = name;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }



}
