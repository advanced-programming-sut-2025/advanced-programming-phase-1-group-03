package com.ap.client.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class Facing implements Component {
    public static final ComponentMapper<Facing> mapper = ComponentMapper.getFor(Facing.class);

    private FacingDirection direction;

    public Facing(FacingDirection direction) {
        this.direction = direction;
    }

    public FacingDirection getDirection() {
        return direction;
    }

    public void setDirection(FacingDirection direction) {
        this.direction = direction;
    }


    public enum FacingDirection {
        Up, Down, Left, Right;

        // We use this to find the name of the textureRegion in the atlas
        private final String atlasKey;
        FacingDirection() {
            atlasKey = name().toLowerCase();
        }

        public String getAtlasKey() {
            return atlasKey;
        }
    }
}
