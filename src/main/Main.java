package main;

import static org.lwjgl.glfw.GLFW.*;

public class Main {
    public static void main(String[] args) {
        long timeElapsed = 0;
        long lastUpdateTime = System.currentTimeMillis();
        long maxTimeBetweenFrames = 1000 / Parameters.getFramesPerSecond();
        long timeSpentUpdatingAndRendering;

        if (!glfwInit()) {
            System.err.println("GLFW Failed to initialize!");
            System.exit(0);
        }

        Application.start();

        while (!glfwWindowShouldClose(Window.getWindow()) && ApplicationStatus.getStatus() != ApplicationStatus.Status.STOPPED) {
            try {
                //Compute the time elapsed since the last frame
                ApplicationStatus.setRuntime(ApplicationStatus.getRuntime() + timeElapsed);
                timeElapsed = System.currentTimeMillis() - lastUpdateTime;
                lastUpdateTime = System.currentTimeMillis();

                glfwPollEvents();

                Application.update(timeElapsed);
                Application.render(timeElapsed);

                glfwSwapBuffers(Window.getWindow());

                timeSpentUpdatingAndRendering = System.currentTimeMillis() - lastUpdateTime;
                //Wait time until processing next frame. FPS locked.
                if (timeSpentUpdatingAndRendering < maxTimeBetweenFrames) {
                    Thread.sleep(maxTimeBetweenFrames - timeSpentUpdatingAndRendering);
                }
            } catch (InterruptedException e) {
                System.out.println(e);
                exit(1);
            }
        }

        exit(0);
    }

    public static void exit(int status) {
        Application.stop();
        glfwTerminate();

        System.exit(status);
    }
}