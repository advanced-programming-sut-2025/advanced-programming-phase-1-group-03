package com.ap;

public class Constraints {
    public final static float WORLD_WIDTH = 48f;
    public final static float WORLD_HEIGHT = 27f;
    public final static float UNIT_SCALE = 1 / 16f;

    public final static int WORLD_WIDTH_RESOLUTION = 1200;
    public final static int WORLD_HEIGHT_RESOLUTION = 675;

    // We update physic system with 30FPS to prevent huge load on CPU
    public static final float PHYSIC_STEP_INTERVAL = 1 / 30f;
    public static final float CAMERA_SMOOTHING_FACTOR = 4;
}
