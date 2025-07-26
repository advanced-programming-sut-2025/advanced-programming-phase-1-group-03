package com.ap.items.tools;

import com.ap.Constraints;
import com.ap.GdxGame;
import com.ap.asset.SoundAsset;
import com.ap.component.ItemHolder;
import com.ap.items.Item;
import com.ap.items.ItemFactory;
import com.ap.items.ItemNames;
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
        if(!ItemHolder.mapper.has(entity)) {
            return;
        }
        Item item = ItemHolder.mapper.get(entity).getItem();
        if(item.getName().equals(ItemNames.Stone.name())) {
            game.getAudioService().playSound(SoundAsset.Pickaxe);
            Helper.removeEntity(entity, engine, world);

            Item stone = ItemFactory.CreateStone(game.getAssetService());
            game.getInventory().addItem(stone, 1);
        }
    }
}
