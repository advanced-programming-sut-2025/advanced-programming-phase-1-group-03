package com.ap.system;

import com.ap.Constraints;
import com.ap.component.Growable;
import com.ap.component.ItemHolder;
import com.ap.items.EntityFactory;
import com.ap.items.plant.Crop;
import com.ap.model.CropsType;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

public class GiantCropManager  {
    private int mapWidth;
    private int mapHeight;
    private final World world;
    private Engine engine;

    public GiantCropManager(TiledMap map, World world, Engine engine) {
        mapWidth = map.getProperties().get("width", Integer.class);
        mapHeight = map.getProperties().get("height", Integer.class);
        this.world = world;
        this.engine = engine;
    }

    public void checkGiant() {
        for(int i = 0; i < mapWidth - 3; i++) {
            for(int j = 0; j < mapHeight - 3; j++) {
                if(canFormGiant(i, j)) {
                    if(new Random().nextInt(10) < Constraints.PROB_OF_GRASS_GIVE_FIBBER) {
                        giantPrepare(i, j, engine, world);
                    }
                }
            }
        }
    }

    private void giantPrepare(int x, int y, Engine engine, World world) {
        CropsType cropType = null;

        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Body top = Helper.getTopBodyAtPoint(new Vector2(x + i, y + j), world);
                var entity = (Entity) top.getUserData();

                Crop crop = (Crop) ItemHolder.mapper.get(entity).getItem();
                cropType = crop.getType();
                Helper.removeEntity((Entity)top.getUserData(), engine, world);
            }
        }
        engine.addEntity(EntityFactory.instance.CreateGiantCropEntity(new Vector2(x, y), cropType, world));

    }

    private boolean canFormGiant(int startX, int startY) {
        int validCount = 1;
        CropsType type = null;

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                Body top = Helper.getTopBodyAtPoint(new Vector2(startX + i, startY + j), world);
                // Check that at this point a crop exists
                if (top.getUserData() instanceof Entity entity && Growable.mapper.has(entity)) {
                    Growable growable = Growable.mapper.get(entity);
                    if(!(ItemHolder.mapper.get(entity).getItem() instanceof Crop crop)) {
                        continue;
                    }
                    // Check that all the crops are at the max level
                    if(!growable.canProduce()) {
                        continue;
                    }
                    if(type == null) {
                        type = crop.getType();
                        if(!type.isCanBecomeGiant()) {
                            return false;
                        }
                    } else {
                        if(type.equals(crop.getType())) {
                            validCount++;
                        }
                    }
                }
            }
        }
        return validCount == 9;
    }

}
