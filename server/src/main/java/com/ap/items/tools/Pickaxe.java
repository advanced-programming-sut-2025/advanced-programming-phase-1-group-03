package com.ap.items.tools;

import com.ap.asset.AtlasAsset;
import com.ap.asset.SoundAsset;
import com.ap.component.ItemHolder;
import com.ap.component.MineralNode;
import com.ap.items.Item;
import com.ap.items.ItemFactory;
import com.ap.items.ItemNames;
import com.ap.managers.PlayerManager;
import com.ap.model.AbilityType;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class Pickaxe extends Tool {
    private BasicToolLevels currentLevel = BasicToolLevels.Normal;

    public Pickaxe(AtlasAsset atlasAsset, String atlasKey) {
        super("Pickaxe", atlasAsset, atlasKey, AbilityType.Mining);
    }

    @Override
    public int getEnergyConsumption() {
        return switch(currentLevel) {
            case Normal -> 5;
            case Copper -> 4;
            case Iron -> 3;
            case Gold -> 2;
            case Iridium -> 1;
        };
    }

    @Override
    public void applyItem(WorldObject body, Engine engine, PlayerManager playerManager, World world) {
        super.applyItem(body, engine, playerManager, world);
        if(!(body.getUserData() instanceof Entity entity)) {
            return;
        }
        if (MineralNode.mapper.has(entity)) {
            var mineralNode = MineralNode.mapper.get(entity);
            if(currentLevel.isStrictlyGreater(mineralNode.getType().getLevelNeedToMine())) {
                playerManager.getMessageSender().sendMessage("For mining this mineral you must have at least "
                        + mineralNode.getType().getLevelNeedToMine() + " Pickaxe");
                return;
            }
            Helper.removeEntity(entity, engine, world);
            playerManager.getAudioService().playSound(SoundAsset.Mineral);
            var mineralType = mineralNode.getType().getMineral();
            playerManager.getInventory().addItem(ItemFactory.instance.CreateMineral(mineralType), 1);
        }
        if(ItemHolder.mapper.has(entity)) {
            Item item = ItemHolder.mapper.get(entity).getItem();
            if (item.getName().equals(ItemNames.Stone.name())) {
                playerManager.getAudioService().playSound(SoundAsset.Pickaxe);
                Helper.removeEntity(entity, engine, world);
                Item stone = ItemFactory.instance.CreateStone();
                playerManager.getInventory().addItem(stone, 1);
            }
        }
    }
}
