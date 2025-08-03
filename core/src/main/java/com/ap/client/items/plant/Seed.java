package com.ap.client.items.plant;

import com.ap.client.asset.MapAsset;
import com.ap.client.asset.SoundAsset;
import com.ap.client.component.Dirt;
import com.ap.client.items.EntityFactory;
import com.ap.client.items.Item;
import com.ap.client.items.ItemFactory;
import com.ap.client.managers.GameUIManager;
import com.ap.client.model.CropsType;
import com.ap.client.model.Season;
import com.ap.client.screen.GameScreen;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class Seed extends Item {
    private CropsType belongingCropType;
    public Seed(TextureRegion icon, CropsType belongingCropType) {
        super(belongingCropType.name() + " Seeds", 64, icon, 0);
        this.belongingCropType = belongingCropType;
    }

    @Override
    public void applyItem(WorldObject body, Engine engine, GameScreen game, World world) {
        if(!(body.getUserData() instanceof Entity dirt)) {
            return;
        }
        // It's not dirt or not plowed
        if(!Dirt.mapper.has(dirt) || !Dirt.mapper.get(dirt).isPlowed()) {
            return;
        }
        Season currentSeason = game.getTimeSystem().getSeason();
        if(!belongingCropType.getSeasonList().contains(currentSeason)
                && game.getCurrentMap() != MapAsset.Greenhouse) {
            GameUIManager.instance.showMessageDialog("This seed is not belonging to the current season!");
            return;
        }
        game.getAudioService().playSound(SoundAsset.HoeHit);
        // Reduce from inventory
        game.getInventory().removeItem(ItemFactory.instance.CreateSeed(belongingCropType), 1);

        Entity crop = EntityFactory.instance.CreateCropEntity(body.getPosition(), belongingCropType, world, dirt);

        engine.addEntity(crop);
    }
}
