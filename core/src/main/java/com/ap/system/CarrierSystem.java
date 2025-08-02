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
import com.ap.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class CarrierSystem extends IteratingSystem {
    private Batch batch;
    private final Engine engine;
    private final TextureRegion selectedItemTexture;
    private ShapeRenderer shapeRenderer;
    private AudioService audioService;
    private World world;

    public CarrierSystem(Engine engine, AssetService assetService, Batch batch, World world, AudioService audioService) {
        super(Family.all(Carrier.class, Transform.class, Graphic.class).get());
        this.engine = engine;
        this.audioService = audioService;
        this.batch = batch;
        this.world = world;
        selectedItemTexture = assetService.get(AtlasAsset.UI).findRegion("SelectedItem");
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Entity player = engine.getEntitiesFor(Family.all(Player.class).get()).get(0);
        Transform transform = Transform.mapper.get(entity);
        Vector2 playerPosition = Transform.mapper.get(player).getPosition();

        Vector2 playerSize = Transform.mapper.get(player).getSize();
        transform.getPosition().set(playerPosition).add((playerSize.x-transform.getSize().x) / 2f, playerSize.y);
        transform.getPosition().set((int) transform.getPosition().x, (int) transform.getPosition().y);

        Gdx.gl.glLineWidth(4);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        if(Helper.canPlace(transform.getPosition(), transform.getSize(), world)) {
            shapeRenderer.setColor(0, 1, 0, 1);
        } else {
            shapeRenderer.setColor(1, 0, 0, 1);
        }
        shapeRenderer.rect((int) transform.getPosition().x, (int)transform.getPosition().y,
                transform.getSize().x, transform.getSize().y);
        shapeRenderer.end();
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
        audioService.playSound(SoundAsset.PlaceNewItem);
    }
}
