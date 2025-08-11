package com.ap.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;

public class Container implements Component {
    public final static ComponentMapper<Container> mapper = ComponentMapper.getFor(Container.class);

    private Array<Entity> children = new Array<>();
    public Container() {}
    public Container(Entity... entities) {
        for (Entity entity : entities) {
            children.add(entity);
        }
    }
    public Array<Entity> getChildren() {
        return children;
    }

    public void setChildren(Array<Entity> children) {
        this.children = children;
    }
}
