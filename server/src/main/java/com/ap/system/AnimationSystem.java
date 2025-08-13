package com.ap.system;

import com.ap.Constraints;
import com.ap.asset.AtlasAsset;
import com.ap.component.Animation2D;
import com.ap.component.Facing;
import com.ap.component.Graphic;
import com.ap.component.Network;
import com.ap.model.ServerPlayer;
import com.ap.notifiers.AnimationNotifier;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Array;

public class AnimationSystem extends IteratingSystem {
    private Array<ServerPlayer> players;
    private int engineId;

    public AnimationSystem(Array<ServerPlayer> players) {
        super(Family.all(Animation2D.class, Facing.class, Graphic.class, Network.class).get());
        this.players = players;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        engineId = Helper.getEngineId(engine);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Animation2D animation2D = Animation2D.mapper.get(entity);
        Facing.FacingDirection facing = Facing.mapper.get(entity).getDirection();

        int id = Network.mapper.get(entity).getId();
        if(animation2D.shouldUpdate() || animation2D.getFacingDirection() != facing) {
            updateAnimation(animation2D, facing, id);
        }
    }

    // Assets must be in the following format:
    // atlasKey/type_facing.png

    private void updateAnimation(Animation2D animation2D, Facing.FacingDirection facing, int id) {
        AtlasAsset atlasAsset = animation2D.getAtlasAsset();
        String atlasKey = animation2D.getAtlasKey();
        Animation2D.AnimationType type = animation2D.getAnimationType();
        String combinedKey = (!atlasKey.isEmpty() ? atlasKey+ "/" : "") +type.getAtlasKey() + "_" + facing.getAtlasKey();
        animation2D.setAnimation(facing);
        Helper.sendToAllTCP(players, new AnimationNotifier(
                combinedKey, atlasAsset, id, engineId,animation2D.getSpeed(), animation2D.getPlayMode()
        ));
    }

}
