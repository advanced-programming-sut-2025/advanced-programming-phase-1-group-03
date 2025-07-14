package com.ap.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Vector2;

public class Transform implements Component, Comparable<Transform> {
    public static final ComponentMapper<Transform> mapper = ComponentMapper.getFor(Transform.class);

    // Position of the bottom left corner of sprite
    private final Vector2 position;

    private final int z;
    private final Vector2 scaling;
    private final float rotationDeg;
    private final Vector2 size;

    // We use this parameter to adjust rendering order
    private final float sortOffsetY;

    public Transform(Vector2 position, int z, Vector2 scaling, Vector2 size, float rotationDeg, float sortOffsetY) {
        this.position = position;
        this.z = z;
        this.scaling = scaling;
        this.size = size;
        this.rotationDeg = rotationDeg;
        this.sortOffsetY = sortOffsetY;
    }

    @Override
    public int compareTo(Transform other) {
        if(this.z != other.z)
            return Float.compare(this.z, other.z);
        if(this.position.y + this.sortOffsetY != other.position.y + other.sortOffsetY)
            return Float.compare(this.position.y + this.sortOffsetY, other.position.y + other.sortOffsetY);
        return Float.compare(this.position.x, other.position.x);
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getZ() {
        return z;
    }

    public float getRotationDeg() {
        return rotationDeg;
    }

    public float getSortOffsetY() {
        return sortOffsetY;
    }

    public Vector2 getScaling() {
        return scaling;
    }

    public Vector2 getSize() {
        return size;
    }
}
