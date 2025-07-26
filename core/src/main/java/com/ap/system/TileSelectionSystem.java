package com.ap.system;

import com.ap.GdxGame;
import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.component.Controller;
import com.ap.component.Facing;
import com.ap.component.Player;
import com.ap.component.Transform;
import com.ap.items.Inventory;
import com.ap.screen.GameScreen;
import com.ap.ui.widget.ItemContainer;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class TileSelectionSystem extends IteratingSystem {
    private final Batch batch;
    private final TextureRegion selectedItemTexture;
    private Engine engine;
    private GameScreen game;
    private final ItemContainer itemContainer;
    private final World world;

    int tileX;
    int tileY;

    public TileSelectionSystem(Batch batch,
                               ItemContainer itemContainer, Stage stage, Engine engine, World world, GameScreen game) {
        super(Family.all(Player.class, Facing.class, Transform.class).get());
        this.batch = batch;
        this.world = world;
        this.itemContainer = itemContainer;
        this.engine = engine;
        this.game = game;

        AssetService assetService = game.getAssetService();
        selectedItemTexture = assetService.get(AtlasAsset.UI).findRegion("SelectedItem");
    }

    public void click() {
        itemContainer.useSelectedItem(getTopBodyAtPoint(new Vector2(tileX, tileY)), engine, game, world);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Facing facing = Facing.mapper.get(entity);
        Transform transform = Transform.mapper.get(entity);
        tileX = (int) Math.ceil(transform.getPosition().x);
        tileY = (int) Math.ceil(transform.getPosition().y);
        switch (facing.getDirection()) {
            case Up -> tileY ++;
            case Down -> tileY --;
            case Left -> tileX --;
            case Right -> tileX ++;
        }
        selectTile(tileX, tileY);
    }

    private void selectTile(int tileX, int tileY) {
        batch.begin();
        batch.draw(selectedItemTexture, tileX, tileY, 1, 1);
        batch.end();
    }

    private Body getTopBodyAtPoint(Vector2 tilePos) {
        tilePos.add(new Vector2(0.5f, 0.5f));
        final Body[] topBody = {null};
        final int[] highestZIndex = {-1};

        world.QueryAABB(new QueryCallback() {
            @Override
            public boolean reportFixture(Fixture fixture) {
                if(fixture.getBody().getUserData() instanceof Entity entity) {
                    if (Transform.mapper.has(entity)) {
                        Transform transform = Transform.mapper.get(entity);
                        if (transform.getZ() > highestZIndex[0]) {
                            highestZIndex[0] = transform.getZ();
                            topBody[0] = fixture.getBody();
                        }
                    }
                } else if(fixture.getBody().getUserData() instanceof TiledMapTile tile) {
                    if(highestZIndex[0] != -1) {
                        return true;
                    }
                    highestZIndex[0] = 0;
                    topBody[0] = fixture.getBody();
                }
                return true;
            }
        }, tilePos.x - 0.01f, tilePos.y - 0.01f, tilePos.x + 0.01f, tilePos.y + 0.01f);

        return topBody[0];
    }
}
