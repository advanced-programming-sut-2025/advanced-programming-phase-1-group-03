package com.ap.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class Network implements Component {
    public final static ComponentMapper<Network> mapper = ComponentMapper.getFor(Network.class);

    private final int id;
    public Network(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
