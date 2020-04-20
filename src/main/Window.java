package main;

import audio.OpenALManager;
import listeners.InputListenerManager;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowPosCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;

public class Window {
    private static GLFWWindowSizeCallback windowSizeCallback;
    private static GLFWWindowPosCallback windowPosCallback;
    private static long window = -1;
    private static int width = Parameters.getResolutionWidth();
    private static int height = Parameters.getResolutionHeight();
    private static int positionX = 50;
    private static int positionY = 50;
    private static float[] cameraWindowScaleFactor;

    public static void start() {
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
        long window = glfwCreateWindow(Window.getWidth(), Window.getHeight(), "ElwynnProject", 0, 0);
        setWindow(window);
        glfwShowWindow(window);
        glfwMakeContextCurrent(window);
        OpenGLManager.prepareOpenGL();
        OpenALManager.prepareOpenAL();
        glfwPollEvents();

        /** INPUT LISTENER **/
        InputListenerManager.initMyInputListener();
        setFullScreen(Parameters.isFullScreen());

        /** CALLBACKS **/
//        initWindowSizeCallBack();
        initWindowPosCallBack();

        setWindowPosition(positionX, positionY);
        setWindowSize(width, height);
    }

    private static void setWindowPosition(int x, int y) {
        glfwSetWindowPos(getWindow(), x, y);
        positionX = x;
        positionY = y;
    }

    public static void setWindowSize(int w, int h) {
        glfwSetWindowSize(window, w, h);
        glViewport(0, 0, Parameters.getResolutionWidth(), Parameters.getResolutionHeight());
        width = w;
        height = h;
        cameraWindowScaleFactor = new float[]{(float) width / (float) Parameters.getResolutionWidth(), (float) height / (float) Parameters.getResolutionHeight()};
    }

    private static void initWindowSizeCallBack() {
        windowSizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                setWindowSize(width, height);
            }
        };
        glfwSetWindowSizeCallback(getWindow(), windowSizeCallback);
    }

    private static void initWindowPosCallBack() {
        windowPosCallback = new GLFWWindowPosCallback() {
            @Override
            public void invoke(long window, int x, int y) {
                setWindowPosition(x, y);
            }
        };
        glfwSetWindowPosCallback(getWindow(), windowPosCallback);
    }

    public static void setFullScreen(boolean fullScreen) {
        Parameters.setFullScreen(fullScreen);
        try {
            long monitor = glfwGetPrimaryMonitor();
            if (fullScreen) {
                GLFWVidMode vidMode = glfwGetVideoMode(monitor);
                if (vidMode != null) {
                    glfwSetWindowMonitor(getWindow(), monitor, 0, 0, Parameters.getResolutionWidth(), Parameters.getResolutionHeight(), Parameters.getFramesPerSecond());
                }
            } else {
                glfwSetWindowMonitor(getWindow(), 0, positionX, positionY, Window.getWidth(), Window.getHeight(), 0);
            }
        } catch (Exception e) {
            System.err.print("Error setting full screen to " + fullScreen);
            Parameters.setFullScreen(!fullScreen);
        }
    }

    public static long getWindow() {
        return window;
    }

    public static void setWindow(long window) {
        Window.window = window;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static float[] getCameraWindowScaleFactor() {
        return cameraWindowScaleFactor;
    }
}
