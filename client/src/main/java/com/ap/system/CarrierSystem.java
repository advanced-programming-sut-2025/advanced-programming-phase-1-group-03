package com.ap.system;

import com.ap.component.Carrier;
import com.ap.component.Transform;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class CarrierSystem extends IteratingSystem {
    ShapeRenderer shapeRenderer;
    Batch batch;
    public CarrierSystem(Batch batch) {
        super(Family.all(Carrier.class, Transform.class).get());
        this.batch = batch;
        Gdx.app.postRunnable(() -> {
            shapeRenderer = new ShapeRenderer();
        });
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        Gdx.gl.glLineWidth(4);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        var carrier = Carrier.mapper.get(entity);
        var transform = Transform.mapper.get(entity);

        if(carrier.canPlace()) {
            shapeRenderer.setColor(0, 1, 0, 1);
        } else {
            shapeRenderer.setColor(1, 0, 0, 1);
        }
        shapeRenderer.rect((int) transform.getPosition().x, (int)transform.getPosition().y,
                transform.getSize().x, transform.getSize().y);
        shapeRenderer.end();
    }
}
