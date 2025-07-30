package com.ap.component;

import com.ap.model.MineralNodes;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class MineralNode implements Component {
    public final static ComponentMapper<MineralNode> mapper = ComponentMapper.getFor(MineralNode.class);

    private MineralNodes type;
    public MineralNode(MineralNodes type) {
        this.type = type;
    }

    public MineralNodes getType() {
        return type;
    }

    public void setType(MineralNodes type) {
        this.type = type;
    }
}
