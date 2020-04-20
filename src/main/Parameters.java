package main;

import audio.OpenALManager;
import enums.Resolution;

public class Parameters {
    private static boolean debugMode = false;

    /** GRAPHIC/DISPLAY SETTINGS **/
    private static int framesPerSecond = 60;
    private static boolean fullScreen = false;
    private static Resolution resolution = Resolution.RESOLUTION_1600_900;

    /** AUDIO PARAMETERS **/
    private static float musicSoundLevel = 0.3f;
    private static float effectSoundLevel = 0.3f;
    private static float ambienceSoundLevel = 0.3f;

    public static int getFramesPerSecond() {
        return framesPerSecond;
    }

    public static void setFramesPerSecond(int framesPerSecond) {
        Parameters.framesPerSecond = framesPerSecond;
    }

    public static int getResolutionWidth() {
        return resolution.getResolution()[0];
    }

    public static int getResolutionHeight() {
        return resolution.getResolution()[1];
    }

    public static void setResolution(Resolution resolution) {
        Parameters.resolution = resolution;
        Window.setWindowSize(resolution.getResolution()[0], resolution.getResolution()[1]);
    }

    public static boolean isFullScreen() {
        return fullScreen;
    }

    public static void setFullScreen(boolean fullScreen) {
        Parameters.fullScreen = fullScreen;
    }

    public static boolean isDebugMode() {
        return debugMode;
    }

    public static void setDebugMode(boolean debugMode) {
        Parameters.debugMode = debugMode;
    }

    public static float getMusicSoundLevel() {
        return musicSoundLevel;
    }

    public static void setMusicSoundLevel(float soundLevel) {
        OpenALManager.onMusicLevelChange(soundLevel);
        Parameters.musicSoundLevel = soundLevel;
    }

    public static float getEffectSoundLevel() {
        return effectSoundLevel;
    }

    public static void setEffectSoundLevel(float soundLevel) {
        OpenALManager.onEffectLevelChange(soundLevel);
        Parameters.effectSoundLevel = soundLevel;
    }

    public static float getAmbienceSoundLevel() {
        return ambienceSoundLevel;
    }

    public static void setAmbienceSoundLevel(float soundLevel) {
        OpenALManager.onEffectLevelChange(soundLevel);
        Parameters.ambienceSoundLevel = soundLevel;
    }
}
