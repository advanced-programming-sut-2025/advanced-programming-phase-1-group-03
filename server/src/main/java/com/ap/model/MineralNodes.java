package com.ap.model;


import com.ap.items.tools.Tool;
import com.ap.utils.Helper;

public enum MineralNodes {
    Quartz_Node("Quartz Node", 8,  Tool.BasicToolLevels.Iron, Minerals.Quartz),
    Omni("Omni", 5,  Tool.BasicToolLevels.Gold, Minerals.Earth_Crystal,  Minerals.Prismatic_Shard),
    Frozen_Tear_Node("Frozen Tear Node", 7, Tool.BasicToolLevels.Iron, Minerals.Frozen_Tear),
    Fire_Quartz_Node("Fire Quartz Node", 5,  Tool.BasicToolLevels.Iron, Minerals.Fire_Quartz),
    Emerald_Node("Emerald Node", 5, Tool.BasicToolLevels.Iron,Minerals.Emerald),
    Aquamarine_Node("Aquamarine Node", 5,Tool.BasicToolLevels.Iron, Minerals.Aquamarine),
    Ruby_Node("Ruby Node", 5, Tool.BasicToolLevels.Iron, Minerals.Ruby),
    Amethyst_Node("Amethyst Node", 5,  Tool.BasicToolLevels.Iron, Minerals.Amethyst),
    Topaz_Node("Topaz Node", 5, Tool.BasicToolLevels.Iron,Minerals.Topaz),
    Jade_Node("Jade Node", 4,Tool.BasicToolLevels.Iron, Minerals.Jade),
    Diamond_Node("Diamond Node", 3,Tool.BasicToolLevels.Iridium, Minerals.Diamond),

    Copper_Ore_Node("Copper Ore Node", 13,Tool.BasicToolLevels.Normal, Minerals.Copper_Ore),
    Iron_Ore_Node("Iron Ore Node", 10,Tool.BasicToolLevels.Copper, Minerals.Iron_Ore),
    Gold_Ore_Node("Gold Ore Node", 3,Tool.BasicToolLevels.Iron, Minerals.Gold_Ore),
    Iridium_Ore_Node("Iridium Ore Node",  4,Tool.BasicToolLevels.Gold, Minerals.Iridium_Ore),
    Coal_Node("Coal Node", 8,Tool.BasicToolLevels.Copper, Minerals.Coal),
    ;
    final String name;
    final Minerals[] mineralsToGive;
    final int probability;
    final Tool.BasicToolLevels levelNeedToMine;

    MineralNodes(String name, int probability, Tool.BasicToolLevels levelNeedToMine, Minerals... mineralsGive) {
        this.name = name;
        this.mineralsToGive = mineralsGive;
        this.probability = probability;
        this.levelNeedToMine = levelNeedToMine;
    }

    public String getName() {
        return name;
    }

    public Minerals[] getMineralsToGive() {
        return mineralsToGive;
    }
    public int getProbability() {
        return probability;
    }

    public Tool.BasicToolLevels getLevelNeedToMine() {
        return levelNeedToMine;
    }

    public static MineralNodes getRandom() {
        int ind = Helper.random(1, 100);
        int sum = 0;
        for (MineralNodes value : values()) {
            sum += value.getProbability();
            if (ind <= sum) return value;
        }
        return Coal_Node;
    }

    public Minerals getMineral() {
        return mineralsToGive[Helper.random(0, mineralsToGive.length - 1)];
    }
}
