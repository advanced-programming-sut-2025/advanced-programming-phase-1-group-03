package com.ap.client.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class Player implements Component {
    public static final ComponentMapper<Player> mapper = ComponentMapper.getFor(Player.class);

}
