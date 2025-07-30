package com.ap.items.tools;

import com.ap.Constraints;
import com.ap.GdxGame;
import com.ap.asset.SoundAsset;
import com.ap.component.ItemHolder;
import com.ap.component.MineralNode;
import com.ap.items.Item;
import com.ap.items.ItemFactory;
import com.ap.items.ItemNames;
import com.ap.managers.GameUIManager;
import com.ap.model.Abilities;
import com.ap.screen.GameScreen;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

public class Pickaxe extends Tool {
    private BasicToolLevels currentLevel = BasicToolLevels.Normal;

    public Pickaxe(TextureRegion icon) {
        super("Pickaxe", icon, Abilities.Mining);
    }

    @Override
    int getEnergyConsumption() {
        return 0;
    }

    @Override
    public void applyItem(Body body, Engine engine, GameScreen game, World world) {
        if(!(body.getUserData() instanceof Entity entity)) {
            return;
        }
        if (MineralNode.mapper.has(entity)) {
            var mineralNode = MineralNode.mapper.get(entity);
            if(currentLevel.isStrictlyGreater(mineralNode.getType().getLevelNeedToMine())) {
                GameUIManager.instance.showMessageDialog("For mining this mineral you must have at least "
                        + mineralNode.getType().getLevelNeedToMine() + " Pickaxe");
                return;
            }
            Helper.removeEntity(entity, engine, world);
            game.getAudioService().playSound(SoundAsset.Mineral);
            var mineralType = mineralNode.getType().getMineral();
            game.getInventory().addItem(ItemFactory.instance.CreateMineral(mineralType), 1);
        }
        if(ItemHolder.mapper.has(entity)) {
            Item item = ItemHolder.mapper.get(entity).getItem();
            if (item.getName().equals(ItemNames.Stone.name())) {
                game.getAudioService().playSound(SoundAsset.Pickaxe);
                Helper.removeEntity(entity, engine, world);

                Item stone = ItemFactory.instance.CreateStone();
                game.getInventory().addItem(stone, 1);
            }
        }
    }
}
