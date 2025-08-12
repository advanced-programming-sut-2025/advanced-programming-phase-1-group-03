package com.ap;

public class Configuration {
//    public static final String HOST_IP = "81.31.176.22";
    public static final String HOST_IP = "81.31.176.90";
    public static int TCP_PORT = 54555;
    public static int UDP_PORT = 54777;
    public static int TIMEOUT = 5000;

    // Server run game at this frame rate
    public static float STEP_UPDATING = 1/ 60F;

    // Send time to the players every this time
    public static float NOTIFY_USERS_TIME_STEP = 0.1f;
}
