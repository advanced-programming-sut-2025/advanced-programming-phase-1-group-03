package com.ap.items.tools;

import com.ap.GdxGame;
import com.ap.asset.SoundAsset;
import com.ap.component.ItemHolder;
import com.ap.items.Item;
import com.ap.items.ItemFactory;
import com.ap.items.ItemNames;
import com.ap.model.Abilities;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Axe extends Tool {
    public Axe(TextureRegion icon) {
        super("Axe", icon, Abilities.Foraging);
    }

    @Override
    int getEnergyConsumption() {
        return 0;
    }

    @Override
    public void applyItem(Body body, Engine engine, GdxGame game, World world) {
        if(!(body.getUserData() instanceof Entity entity)) {
            return;
        }
        if(!ItemHolder.mapper.has(entity)) {
            return;
        }
        game.getAudioService().playSound(SoundAsset.Axe);
        Item item = ItemHolder.mapper.get(entity).getItem();
        if(item.getName().equals(ItemNames.Wood.name())) {
            Helper.removeEntity(entity, engine, world);
            Item wood = ItemFactory.CreateWood(game.getAssetService());
            game.getInventory().addItem(wood, 1);
        }
    }
}
