package com.ap.client.system;

import com.ap.client.component.Clickable;
import com.ap.client.component.Physic;
import com.ap.client.component.Transform;
import com.ap.client.component.items.FarmAnimal;
import com.ap.client.model.GameData;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class ClickSystem extends EntitySystem implements InputProcessor {
    private ImmutableArray<Entity> clickableEntities;
    private final Camera camera;
    public ClickSystem(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void addedToEngine(Engine engine) {
        clickableEntities = engine.getEntitiesFor(
                Family.all(Clickable.class, Physic.class, Transform.class).get()
        );
    }

    public void notifyClick(Entity entity, int button) {
        if (FarmAnimal.mapper.has(entity)) {
            FarmAnimal farmAnimal = FarmAnimal.mapper.get(entity);
            farmAnimal.setClicked(true);
            farmAnimal.setButtonClicked(button);
//            GameData.getInstance()
        }
    }

    @Override
    public boolean keyDown(int i) {
            return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }



        @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 worldPos = camera.unproject(new Vector3(screenX, screenY, 0));

        System.out.println(clickableEntities.size());
        for (Entity e : clickableEntities) {
            Physic physic = Physic.mapper.get(e);
            Transform transform = Transform.mapper.get(e);

            Vector2 pos = physic.getBody().getPosition();
            if (worldPos.x > pos.x  &&
                    worldPos.x < pos.x + transform.getSize().x &&
                    worldPos.y > pos.y  &&
                    worldPos.y < pos.y + transform.getSize().y ) {
                System.out.println("found");
                notifyClick(e, button);
//                return true;
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
