package com.ap.component;

import com.ap.Constraints;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class Player implements Component {
    public static final ComponentMapper<Player> mapper = ComponentMapper.getFor(Player.class);
    private int gold = Constraints.PLAYER_INITIAL_GOLD;

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}
