package com.ap.items.plant;

import com.ap.asset.SoundAsset;
import com.ap.component.Dirt;
import com.ap.component.ItemHolder;
import com.ap.items.EntityFactory;
import com.ap.items.Item;
import com.ap.items.ItemFactory;
import com.ap.managers.GameUIManager;
import com.ap.model.CropsType;
import com.ap.model.Season;
import com.ap.screen.GameScreen;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Seed extends Item {
    private CropsType belongingCropType;
    public Seed(TextureRegion icon, CropsType belongingCropType) {
        super(belongingCropType.name() + " Seeds", 64, icon, 0);
        this.belongingCropType = belongingCropType;
    }

    @Override
    public void applyItem(Body body, Engine engine, GameScreen game, World world) {
        if(!(body.getUserData() instanceof Entity dirt)) {
            return;
        }
        // It's not dirt or not plowed
        if(!Dirt.mapper.has(dirt) || !Dirt.mapper.get(dirt).isPlowed()) {
            return;
        }
        Season currentSeason = game.getTimeSystem().getSeason();
        if(!belongingCropType.getSeasonList().contains(currentSeason)) {
            GameUIManager.instance.showMessageDialog("This seed is not belonging to the current season!");
            return;
        }
        game.getAudioService().playSound(SoundAsset.HoeHit);
        // Reduce from inventory
        game.getInventory().removeItem(ItemFactory.instance.CreateSeed(belongingCropType), 1);

        Entity crop = EntityFactory.instance.CreateCropEntity(body.getPosition(), belongingCropType, world);

        engine.addEntity(crop);
    }
}
