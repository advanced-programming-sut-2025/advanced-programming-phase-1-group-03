package com.ap.system;

import com.ap.component.Player;
import com.ap.model.GameData;
import com.ap.ui.widget.Clock;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class PlayerCoinSystem extends EntitySystem {

    private final Clock clock;

    public PlayerCoinSystem(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void update(float deltaTime) {
        clock.setGold(GameData.getInstance().getPlayerGold());

    }

}
