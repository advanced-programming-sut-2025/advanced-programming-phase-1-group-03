package com.ap.items.plant;

import com.ap.asset.AtlasAsset;
import com.ap.asset.MapAsset;
import com.ap.asset.SoundAsset;
import com.ap.component.Dirt;
import com.ap.items.EntityFactory;
import com.ap.items.Item;
import com.ap.items.ItemFactory;
import com.ap.managers.PlayerManager;
import com.ap.model.CropsType;
import com.ap.model.Season;
import com.ap.notifiers.ShowMessageNotifier;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class Seed extends Item {
    private CropsType belongingCropType;
    public Seed(AtlasAsset atlasAsset, String atlasKey, CropsType belongingCropType) {
        super(belongingCropType.name() + " Seeds", 64, atlasAsset, atlasKey, 0);
        this.belongingCropType = belongingCropType;
    }

    @Override
    public void applyItem(WorldObject body, Engine engine, PlayerManager playerManager, World world) {
        if(!(body.getUserData() instanceof Entity dirt)) {
            return;
        }
        // It's not dirt or not plowed
        if(!Dirt.mapper.has(dirt) || !Dirt.mapper.get(dirt).isPlowed()) {
            return;
        }
        Season currentSeason = playerManager.getTimeSystem().getSeason();
        if(!belongingCropType.getSeasonList().contains(currentSeason)
                && playerManager.getCurrentMapAsset() != MapAsset.Greenhouse) {
            playerManager.getPlayer().connection.sendTCP(new ShowMessageNotifier("This seed is not belonging to the current season!"));
            return;
        }
        playerManager.getAudioService().playSound(SoundAsset.HoeHit);
        // Reduce from inventory
        playerManager.getInventory().removeItem(ItemFactory.instance.CreateSeed(belongingCropType), 1);

        Entity crop = EntityFactory.instance.CreateCropEntity(body.getPosition(), belongingCropType, world, dirt);
        Helper.addEntity(crop, engine);
    }
}
