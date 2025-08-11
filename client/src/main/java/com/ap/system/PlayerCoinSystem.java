package com.ap.system;

import com.ap.model.GameData;
import com.ap.ui.widget.Clock;
import com.badlogic.ashley.core.EntitySystem;

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
