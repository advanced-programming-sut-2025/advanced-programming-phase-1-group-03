package com.ap.client.system;

import com.ap.client.asset.AssetService;
import com.ap.client.asset.AtlasAsset;
import com.ap.client.component.Facing;
import com.ap.client.component.Player;
import com.ap.client.component.Transform;
import com.ap.client.screen.GameScreen;
import com.ap.client.ui.widget.ItemContainer;
import com.ap.client.utils.Helper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
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
        itemContainer.useSelectedItem(Helper.getTopBodyAtPoint(
                new Vector2(tileX, tileY), world, game.getCurrentTiledMap()), engine, game, world
        );
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


}
