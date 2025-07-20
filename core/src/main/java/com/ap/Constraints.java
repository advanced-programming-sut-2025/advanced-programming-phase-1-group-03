package com.ap;

public class Constraints {
    public final static float WORLD_WIDTH = 48f;
    public final static float WORLD_HEIGHT = 27f;
    public final static float UNIT_SCALE = 1 / 16f;

    public final static int WORLD_WIDTH_RESOLUTION = 1200;
    public final static int WORLD_HEIGHT_RESOLUTION = 675;

    // We update physic system with 30FPS to prevent huge load on CPU
    public static final float PHYSIC_STEP_INTERVAL = 1 / 60f;
    public static final float CAMERA_SMOOTHING_FACTOR = 1;
    public static final float PLAYER_ANIMATION_FRAME_DURATION =  1/ 8f;

    // It means 1 second in the real world is equal to how many seconds in the game
    public static final float GAME_SPEED = 2000f;
    // The game start at this hour
    public static final int START_HOUR = 8;
    public static final int END_HOUR = 22;
    public static final int DARKNESS_END_HOUR = 20;
    public static final int DARKNESS_BEGIN_HOUR = 17;

    public static final int MONTHS_DAYS = 28;

    public static String[] secQuestions = {
            "What is your dad's name?",
            "What is name of your first school?",
            "What is month of your birthday?"
    };
}
