package com.ap.system;

import com.ap.component.Graphic;
import com.ap.component.Player;
import com.ap.component.Transform;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class AdjustAlphaSystem extends IteratingSystem {
    private final Engine engine;
    public AdjustAlphaSystem(Engine engine) {
        super(Family.all(Graphic.class, Transform.class).get());
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Entity player = engine.getEntitiesFor(Family.all(Player.class).get()).get(0);

        if(entity == player) {
            return;
        }
        Transform transform = Transform.mapper.get(entity);
        Transform playerTransform = Transform.mapper.get(player);
        Rectangle entityRect = new Rectangle(
                transform.getPosition().x,
                transform.getPosition().y,
                transform.getSize().x,
                transform.getSize().y);
        Rectangle playerRect = new Rectangle(
                playerTransform.getPosition().x + playerTransform.getSize().x/2,
                playerTransform.getPosition().y + playerTransform.getSize().y/2,
                0.1F,
                0.1F);
        if(transform.compareTo(playerTransform) < 0) {
            return;
        }
        Graphic graphic = Graphic.mapper.get(entity);
        float progress = deltaTime * 3;
        float alpha;

        if(entityRect.overlaps(playerRect)) {
            alpha = MathUtils.lerp(graphic.getColor().a, 0.4f, progress);
        } else {
            alpha = MathUtils.lerp(graphic.getColor().a, 1f, progress * 2);
        }

        graphic.getColor().a = alpha;

    }
}
