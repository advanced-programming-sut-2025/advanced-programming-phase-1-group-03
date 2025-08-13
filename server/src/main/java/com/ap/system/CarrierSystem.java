package com.ap.system;

import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.asset.SoundAsset;
import com.ap.audio.AudioService;
import com.ap.component.Carrier;
import com.ap.component.Graphic;
import com.ap.component.Player;
import com.ap.component.Transform;

import com.ap.component.items.Barn;
import com.ap.component.items.Well;
import com.ap.items.EntityFactory;
import com.ap.model.ServerPlayer;
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class CarrierSystem extends IteratingSystem {
    private final Engine engine;
    private World world;
    private int playerId;
    private Array<ServerPlayer> players;

    public CarrierSystem(Engine engine, World world, Array<ServerPlayer> players, int playerId) {
        super(Family.all(Carrier.class, Transform.class, Graphic.class).get());
        this.engine = engine;
        this.playerId = playerId;
        this.players = players;
        this.world = world;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Entity player = Helper.getPlayer(engine, playerId);
        assert player != null;
        Transform transform = Transform.mapper.get(entity);
        Vector2 playerPosition = Transform.mapper.get(player).getPosition();

        Vector2 playerSize = Transform.mapper.get(player).getSize();
        transform.setPosition(playerPosition.x + (playerSize.x-transform.getSize().x) / 2f, playerPosition.y + playerSize.y);
        transform.setPosition((int) transform.getPosition().x, (int) transform.getPosition().y);

        if(Helper.canPlace(transform.getPosition(), transform.getSize(), world)) {
            Carrier.mapper.get(entity).setCanPlace(true);
        } else {
            Carrier.mapper.get(entity).setCanPlace(false);
        }
    }


    public void place() {
        if(getEntities().size() == 0) {
            return;
        }

        Entity entity = getEntities().get(0);
        Transform transform = Transform.mapper.get(entity);

        if(!Helper.canPlace(transform.getPosition(), transform.getSize(), world)) {
            return;
        }

        Vector2 position =  new Vector2((int) transform.getPosition().x, (int) transform.getPosition().y);
        if(Barn.mapper.has(entity)) {
            var barn = Barn.mapper.get(entity);
            Helper.addEntity(EntityFactory.instance.CreateBarnEntity(barn.getType(),
                   position, world), engine);
        } else if(Well.mapper.has(entity)) {
            Helper.addEntity(EntityFactory.instance.CreateWellEntity(position, world), engine);
        }
        Helper.removeEntity(entity, engine, world);
        Helper.findPlayer(players, playerId).playerManager.getAudioService().playSound(SoundAsset.PlaceNewItem);
    }
}
