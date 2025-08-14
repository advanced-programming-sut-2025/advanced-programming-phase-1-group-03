package com.ap.system;

import com.ap.asset.AssetService;
import com.ap.asset.AtlasAsset;
import com.ap.component.Facing;
import com.ap.component.Player;
import com.ap.component.Transform;
import com.ap.screen.GameScreen;
import com.ap.ui.widget.ItemContainer;
import com.ap.utils.Helper;
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
    private GameScreen game;
    private final ItemContainer itemContainer;

    int tileX;
    int tileY;

    public TileSelectionSystem(Batch batch,
                               ItemContainer itemContainer, GameScreen game) {
        super(Family.all(Player.class, Facing.class, Transform.class).get());
        this.batch = batch;
        this.itemContainer = itemContainer;
        this.game = game;

        AssetService assetService = game.getAssetService();
        selectedItemTexture = assetService.get(AtlasAsset.UI).findRegion("SelectedItem");
    }

    public void click() {
        itemContainer.useSelectedItem(game, tileX, tileY);
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

        batch.begin();
        batch.draw(itemContainer.getSelectedItem().getItem().getIcon(),
                transform.getPosition().x +0.6f, transform.getPosition().y +0.6f,
                1, 1);
        batch.end();
    }

    private void selectTile(int tileX, int tileY) {
        batch.begin();
        batch.draw(selectedItemTexture, tileX, tileY, 1, 1);
        batch.end();
    }


    public void hug() {
        game.getGameClient().getSender().hug(tileX, tileY);
    }

    public void gift() {
        game.getGameClient().getSender().gift(itemContainer.getSelectedIndex());
    }
}
