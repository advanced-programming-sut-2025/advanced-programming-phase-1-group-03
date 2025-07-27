package com.ap;

public class Constraints {
    public final static float WORLD_WIDTH = 48f;
    public final static float WORLD_HEIGHT = 27f;
    public final static float UNIT_SCALE = 1 / 16f;
    public final static int TILE_WIDTH = 16;

    public final static int WORLD_WIDTH_RESOLUTION = 1200;
    public final static int WORLD_HEIGHT_RESOLUTION = 675;

    // We update physic system with 30FPS to prevent huge load on CPU
    public static final float PHYSIC_STEP_INTERVAL = 1 / 60f;
    public static final float CAMERA_SMOOTHING_FACTOR = 1;
    public static final float PLAYER_ANIMATION_FRAME_DURATION =  1/ 8f;

    // It means 1 second in the real world is equal to how many seconds in the game
    public static final float GAME_SPEED = 200f;
    // The game start at this hour
    public static final int START_HOUR = 8;
    public static final int END_HOUR = 22;
    public static final int DARKNESS_END_HOUR = 20;
    public static final int DARKNESS_BEGIN_HOUR = 17;

    public static final int MONTHS_DAYS = 28;
    public static final int MINIMUM_WOOD_TREE_GIVE = 5;
    public static final int MAXIMUM_WOOD_TREE_GIVE = 8;
    public static final int STUMP_GIVEN_WOOD = 3;
    public static final int NUMBER_OF_AXE_NEED_TO_CUT_DOWN_TREE = 4;
    public static final int PLAYER_INITIAL_GOLD = 500;
    public static final int GREEN_HOUSE_WOOD_NEEDED = 100;
    public static final int GREEN_HOUSE_GOLD_NEEDED = 500;


    public static String[] secQuestions = {
            "What is your dad's name?",
            "What is name of your first school?",
            "What is month of your birthday?"
    };

    // in 10 probabilities
    public static final int PROB_OF_GRASS_GIVE_FIBBER = 1;

    public static final int STONE_Z = 1;
    public static int HOE_DIRT_Z = 1;
    public static final int TREE_LEAF_Z = 3;
    public static final int TREE_STUMP_Z = 1;
    public static final int GRASS_Z = 1;
    public static final int WOOD_Z = 1;
    public static final int SHADOW_Z = 1;

    // PLAYER_Z = 2, it was set from tile
}
