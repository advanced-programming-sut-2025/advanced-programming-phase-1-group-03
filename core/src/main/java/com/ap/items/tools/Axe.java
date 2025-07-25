package com.ap.items.tools;

import com.ap.Constraints;
import com.ap.GdxGame;
import com.ap.asset.SoundAsset;
import com.ap.component.Container;
import com.ap.component.Graphic;
import com.ap.component.ItemHolder;
import com.ap.component.Transform;
import com.ap.items.Item;
import com.ap.items.ItemFactory;
import com.ap.items.ItemNames;
import com.ap.items.plant.Tree;
import com.ap.model.Abilities;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;

import java.util.Random;


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
        Item wood = ItemFactory.CreateWood(game.getAssetService());

        if(item.getName().equals(ItemNames.Wood.name())) {
            Helper.removeEntity(entity, engine, world);
            game.getInventory().addItem(wood, 1);
        } else if(item instanceof Tree tree) {
            if(!tree.hasLeaf()) {
                game.getInventory().addItem(wood, Constraints.STUMP_GIVEN_WOOD);
                Helper.removeEntity(entity, engine, world);
                return;
            }

            tree.setHasLeaf(false);
            var leafEntity = Container.mapper.get(entity).getChildren().first();
            Transform transform = Transform.mapper.get(leafEntity);

            float[] degree = new float[1];

            Timer.Task fallTree = new Timer.Task() {
                @Override
                public void run() {
                    transform.setRotationDeg(degree[0] * degree[0]);
                    degree[0] += 0.1f;
                    if(degree[0] * degree[0] >= 85f) {
                        cancel();
                        removeTree(leafEntity, engine, world);
                        game.getInventory().addItem(wood,
                                Helper.random(Constraints.MINIMUM_WOOD_TREE_GIVE, Constraints.MAXIMUM_WOOD_TREE_GIVE));
                    }
                }
            };
            new Timer().scheduleTask(fallTree, 0f, 0.02f);
        }
    }

    private void removeTree(Entity tree, Engine engine, World world) {
        Helper.removeEntity(tree, engine, world);
    }
}
