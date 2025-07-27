package com.ap.system;

import com.ap.component.Player;
import com.ap.ui.widget.Clock;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class PlayerCoinSystem extends IteratingSystem {

    private final Clock clock;

    public PlayerCoinSystem(Clock clock) {
        super(Family.all(Player.class).get());
        this.clock = clock;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Player player = entity.getComponent(Player.class);
        clock.setGold(player.getGold());
    }
}
