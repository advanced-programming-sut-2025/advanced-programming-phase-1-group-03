package com.ap.system;

import com.ap.component.Animation2D;
import com.ap.component.Emote;
import com.ap.component.Transform;
import com.ap.model.EmoteType;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;

public class EmoteSystem extends IteratingSystem {

    public EmoteSystem() {
        super(Family.all(Emote.class, Transform.class).get());
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Emote emote = Emote.mapper.get(entity);
        emote.setTimeState(emote.getTimeState() + deltaTime);

        switch (emote.getCurrentEmote()) {
            case Opening -> {
                if (emote.getTimeState() >= Emote.openingTimeLimit) {
                    emote.setCurrentEmote(emote.getEmote());
                    Animation2D.mapper.get(entity).setPlayMode(Animation.PlayMode.LOOP);
                    emote.setTimeState(0);
                }
            }
            case Closing -> {
                if (emote.getTimeState() >= Emote.openingTimeLimit) {
                    getEngine().removeEntity(entity);
                }
            }
            default -> {
                if (emote.getTimeState() >= emote.getDuration() && emote.getDuration() >= 0) {
                    emote.setCurrentEmote(EmoteType.Closing);
                    emote.setTimeState(0);
                    Animation2D.mapper.get(entity).setPlayMode(Animation.PlayMode.NORMAL);
                }
            }
        }

        Transform transform = Transform.mapper.get(entity);
        float x = emote.getFather().getPosition().x + emote.getFather().getSize().x / 2f - transform.getSize().x / 2f;
        float y = emote.getFather().getPosition().y + emote.getFather().getSize().y;
        transform.setPosition(x, y);
    }
}
