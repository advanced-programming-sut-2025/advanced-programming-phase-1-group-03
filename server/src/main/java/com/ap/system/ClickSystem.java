package com.ap.system;


import com.ap.component.Clickable;
import com.ap.component.Physic;
import com.ap.component.Transform;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class ClickSystem extends EntitySystem {
    private ImmutableArray<Entity> clickableEntities;

    public ClickSystem() {
    }

    @Override
    public void addedToEngine(Engine engine) {
        clickableEntities = engine.getEntitiesFor(
                Family.all(Clickable.class, Transform.class).get()
        );
    }

    public void processClick(float worldX, float worldY, int button, String itemName, int itemAmount) {
        for (Entity entity : clickableEntities) {

            Vector2 pos;
            Transform transform = Transform.mapper.get(entity);
            if (Physic.mapper.has(entity)) {
                pos = Physic.mapper.get(entity).getBody().getPosition();
            } else {
                pos = transform.getPosition();
            }
            if (worldX > pos.x  &&
                worldX < pos.x + transform.getSize().x &&
                worldY > pos.y  &&
                worldY < pos.y + transform.getSize().y ) {
                System.out.println("found entity");
                Clickable clickable = Clickable.mapper.get(entity);
                clickable.setClicked(true);
                clickable.setButtonClicked(button);
                clickable.setItemName(itemName != null ? itemName : "");
                clickable.setItemAmount(itemName != null ? itemAmount : 0);
                break;
            }
        }
    }


}
