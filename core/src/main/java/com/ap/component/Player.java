package com.ap.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class Player implements Component {
    public static final ComponentMapper<Player> mapper = ComponentMapper.getFor(Player.class);

    public int id;
    public boolean isNotified = false;

    public Player() {
    }

    public Player(int id) {
        this.id = id;
    }
}
