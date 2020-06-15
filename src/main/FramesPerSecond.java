package main;

import utils.MathUtils;

/**
 * This class purpose is to update and show fps smoothly when displaying the "debug info".
 * We store the current fps in an array with a fixed size and we compute its mean when we desire
 * to know at how many fps our game is running.
 */
public class FramesPerSecond {
    private static int sizeOfArray = 10;
    private static float[] arrayOfFramesPerSecond = new float[sizeOfArray];
    private static int framesPerSecondIterator = 0;

    public static void update(float fps) {
        arrayOfFramesPerSecond[framesPerSecondIterator] = fps;
        framesPerSecondIterator = (framesPerSecondIterator + 1) % arrayOfFramesPerSecond.length;
    }

    public static float getFramesPerSecond() {
        return MathUtils.computeMean(arrayOfFramesPerSecond);
    }
}
