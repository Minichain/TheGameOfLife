package main;

import listeners.InputListenerManager;
import menu.Menu;
import scene.Camera;
import scene.CellMap;
import text.TextRendering;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class UserInterface {
    public static UserInterface instance = null;
    private ArrayList<String> debugTextList;

    public UserInterface() {
        debugTextList = new ArrayList<>();
        TextRendering.init();
    }

    public static UserInterface getInstance() {
        if (instance == null) {
            instance = new UserInterface();
        }
        return instance;
    }

    public void update(long timeElapsed) {
        /** UPDATE FPS **/
        if (timeElapsed <= 0) timeElapsed = 1;
        float fps = 1000f / timeElapsed;
        FramesPerSecond.update(fps);

        /** UPDATE DEBUG TEXT **/
        if (Parameters.isDebugMode()) {
            debugTextList.clear();

            debugTextList.add("OpenGL version " + glGetString(GL_VERSION));
            debugTextList.add("Show/Hide Debug Info: F1");
            debugTextList.add("Reset Application: F5");
            debugTextList.add("FPS: " + String.format("%.2f", FramesPerSecond.getFramesPerSecond()));
            debugTextList.add("GPU calls: " + OpenGLManager.GPU_CALLS);
            debugTextList.add("Resolution: " + Parameters.getResolutionWidth() + " x " + Parameters.getResolutionHeight());
            debugTextList.add("Window Size: " + Window.getWidth() + " x " + Window.getHeight());
            debugTextList.add("Num of Tiles: " + CellMap.getArrayOfCells().length + " x " + CellMap.getArrayOfCells()[0].length);
            debugTextList.add("Camera World Coordinates: (" + (float) Camera.getInstance().getCoordinates().x + ", " + (float) Camera.getInstance().getCoordinates().y + ")");
            debugTextList.add("Camera Zoom: " + (float) Camera.getZoom());
            debugTextList.add("Mouse Camera Coordinates: (" + (float) InputListenerManager.getMouseCameraCoordinates().x + ", " + (float) InputListenerManager.getMouseCameraCoordinates().y + ")");
            debugTextList.add("Mouse World Coordinates: (" + (float) InputListenerManager.getMouseWorldCoordinates().x + ", " + (float) InputListenerManager.getMouseWorldCoordinates().y + ")");
            debugTextList.add("Mouse Window Coordinates: (" + InputListenerManager.getMouseWindowCoordinates().x + ", " + InputListenerManager.getMouseWindowCoordinates().y + ")");
            if (ApplicationStatus.getStatus() == ApplicationStatus.Status.PAUSED) {
                debugTextList.add("APPLICATION PAUSED");
            }
        }
    }

    public void render(long timeElapsed) {
        renderDebugUI(timeElapsed);
    }

    private void renderDebugUI(long timeElapsed) {
        if (Parameters.isDebugMode()) {
            /** DEBUG TEXT **/
            float textScale = 2f * Parameters.getResolutionFactor();
            float topMargin = 10;
            float leftMargin = 10;
            float gapBetweenTexts = 10 * textScale;

            TextRendering.renderText(leftMargin, topMargin, gapBetweenTexts, debugTextList, textScale);

            /** DEBUG LINES **/
            glDisable(GL_BLEND);

            glDisable(GL_TEXTURE_2D);
            OpenGLManager.glBegin(GL_LINES);
            glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            glVertex2i(Parameters.getResolutionWidth() / 2, 0);
            glVertex2i(Parameters.getResolutionWidth() / 2, Parameters.getResolutionHeight());
            glVertex2i(0, Parameters.getResolutionHeight() / 2);
            glVertex2i(Parameters.getResolutionWidth(), Parameters.getResolutionHeight() / 2);
            glEnd();

            glEnable(GL_BLEND);
        }

        /** MENU **/
        if (Menu.getInstance().isShowing()) {
            Menu.getInstance().render(timeElapsed);
        }
    }
}
