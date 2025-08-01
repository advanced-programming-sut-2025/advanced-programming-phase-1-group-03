package com.ap.items.tools;

import com.ap.asset.SoundAsset;
import com.ap.component.ItemHolder;
import com.ap.component.MineralNode;
import com.ap.items.Item;
import com.ap.items.ItemFactory;
import com.ap.items.ItemNames;
import com.ap.managers.AbilityManager;
import com.ap.managers.GameUIManager;
import com.ap.model.AbilityType;
import com.ap.model.Weather;
import com.ap.screen.GameScreen;
import com.ap.system.universal.EnergyManager;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class Pickaxe extends Tool {
    private BasicToolLevels currentLevel = BasicToolLevels.Normal;

    public Pickaxe(TextureRegion icon) {
        super("Pickaxe", icon, AbilityType.Mining);
    }

    @Override
    int getEnergyConsumption(GameScreen gameScreen, boolean successful) {
        int amount = 0;
        switch(currentLevel) {
            case Normal : {
                amount = 5;
                break;
            }
            case Copper : {
                amount = 4;
                break;
            }
            case Iron : {
                amount = 3;
                break;
            }
            case Gold : {
                amount = 2;
                break;
            }
            case Iridium : {
                amount = 1;
                break;
            }
        }
        if(successful)
            amount -= 1;
        if(AbilityManager.getInstance().getAbility(AbilityType.Mining).getLevel() == relatedAbility.maxLevel)
            amount -= 1;
        if(gameScreen.getWeatherSystem().getCurrentWeather().equals(Weather.Rain))
            amount = (int)(amount * 1.5f);
        if(gameScreen.getWeatherSystem().getCurrentWeather().equals(Weather.Snow))
            amount *= 2;
        return Math.min(0, -amount);
    }

    @Override
    public void applyItem(WorldObject body, Engine engine, GameScreen game, World world) {
        if(!(body.getUserData() instanceof Entity entity)) {
            EnergyManager.getInstance().advance(getEnergyConsumption(game, false));
            return;
        }
        if (MineralNode.mapper.has(entity)) {
            var mineralNode = MineralNode.mapper.get(entity);
            if(currentLevel.isStrictlyGreater(mineralNode.getType().getLevelNeedToMine())) {
                GameUIManager.instance.showMessageDialog("For mining this mineral you must have at least "
                        + mineralNode.getType().getLevelNeedToMine() + " Pickaxe");
                EnergyManager.getInstance().advance(getEnergyConsumption(game, false));
                return;
            }
            Helper.removeEntity(entity, engine, world);
            game.getAudioService().playSound(SoundAsset.Mineral);
            var mineralType = mineralNode.getType().getMineral();
            game.getInventory().addItem(ItemFactory.instance.CreateMineral(mineralType), 1);
            EnergyManager.getInstance().advance(getEnergyConsumption(game, true));
        }
        if(ItemHolder.mapper.has(entity)) {
            Item item = ItemHolder.mapper.get(entity).getItem();
            if (item.getName().equals(ItemNames.Stone.name())) {
                game.getAudioService().playSound(SoundAsset.Pickaxe);
                Helper.removeEntity(entity, engine, world);
                Item stone = ItemFactory.instance.CreateStone();
                game.getInventory().addItem(stone, 1);
                EnergyManager.getInstance().advance(getEnergyConsumption(game, true));
            }
        }
    }
}
