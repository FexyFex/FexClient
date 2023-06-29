package me.fexclient;


public class MinecraftFexClientConfig {
    public static String windowTitle = "Minecraft Beta 1.1_02 FexClient";
    public static String versionText = "Beta 1.1_02 FexClient";

    public static boolean useUniformBrightness = false;
    public static float uniformBrightness = 1.0f;

    public static boolean useStaticTime = false;
    public static long staticTime = 0L;

    public static boolean useInstaMine = false;

    public static boolean useFullHealth = false;

    public static boolean doFalling = false;

    public static boolean tunnelerActive = false;

    public static boolean requestedLogout = false;

    public static boolean useXRay = false;

    private MinecraftFexClientConfig() {}


    public static void resetToDefault() {
        useUniformBrightness = false;
        uniformBrightness = 1.0f;

        useStaticTime = false;
        staticTime = 0L;
        useInstaMine = false;
        useFullHealth = false;
        doFalling = false;

        tunnelerActive = false;
        requestedLogout = false;
        useXRay = false;
    }
}
