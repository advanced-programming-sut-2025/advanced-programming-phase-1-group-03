package com.ap.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Vector2;

public class Transform implements Component, Comparable<Transform> {
    public static final ComponentMapper<Transform> mapper = ComponentMapper.getFor(Transform.class);

    // Position of the bottom left corner of sprite
    private Vector2 position;

    private int z;
    private Vector2 scaling;
    private float rotationDeg;
    private Vector2 size;
    private Vector2 origin = null;

    // We use this parameter to adjust rendering order
    private float sortOffsetY;

    private boolean changed = true;


    public Transform() {
    }

    public Transform(Vector2 position, int z, Vector2 scaling, Vector2 size, float rotationDeg, float sortOffsetY) {
        this.position = position;
        this.z = z;
        this.scaling = scaling;
        this.size = size;
        this.rotationDeg = rotationDeg;
        this.sortOffsetY = sortOffsetY;
    }
    public Transform(Vector2 position, int z, Vector2 scaling, Vector2 size, float rotationDeg, float sortOffsetY, Vector2 origin) {
        this(position, z, scaling, size, rotationDeg, sortOffsetY);
        this.origin = origin;
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

    public void setZ(int z) {
        this.z = z;
    }

    public Vector2 getOrigin() {
        return origin;
    }

    public void setRotationDeg(float rotationDeg) {
        if(rotationDeg != this.rotationDeg) {
            setChanged(true);
        }
        this.rotationDeg = rotationDeg;
    }

    public void set(Transform transform) {
        this.z = transform.z;
        this.scaling = transform.scaling;
        this.size = transform.size;
        this.origin = transform.origin;
        this.rotationDeg = transform.rotationDeg;
        this.sortOffsetY = transform.sortOffsetY;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

}
