package main;

import menu.Menu;
import scene.Camera;
import text.TextRendering;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class UserInterface {
    public static UserInterface instance = null;

    public UserInterface() {
        TextRendering.init();
    }

    public static UserInterface getInstance() {
        if (instance == null) {
            instance = new UserInterface();
        }
        return instance;
    }

    public void render(long timeElapsed) {
        renderDebugUI(timeElapsed);
    }

    private void renderDebugUI(long timeElapsed) {
        if (Parameters.isDebugMode()) {
            if (timeElapsed <= 0) timeElapsed = 1;
            float fps = 1000f / timeElapsed;

            /** DEBUG LINES **/
            glDisable(GL_TEXTURE_2D);
            glDisable(GL_BLEND);
            OpenGLManager.glBegin(GL_LINES);
            glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            glVertex2i(Parameters.getResolutionWidth() / 2, 0);
            glVertex2i(Parameters.getResolutionWidth() / 2, Parameters.getResolutionHeight());
            glVertex2i(0, Parameters.getResolutionHeight() / 2);
            glVertex2i(Parameters.getResolutionWidth(), Parameters.getResolutionHeight() / 2);
            glEnd();
            glEnable(GL_BLEND);

            /** DEBUG TEXT **/
            ArrayList<String> textList = new ArrayList<>();
            int textScale = 2;
            int topMargin = 10;
            int leftMargin = 10;
            int gapBetweenTexts = 10 * textScale;

            textList.add("Show/Hide Debug Info: F1");
            textList.add("Reset Application: F5");
            textList.add("FPS: " + fps);
            textList.add("GPU calls: " + OpenGLManager.GPU_CALLS + 1);
            textList.add("Resolution: " + Parameters.getResolutionWidth() + " x " + Parameters.getResolutionHeight());
            textList.add("Window Size: " + Window.getWidth() + " x " + Window.getHeight());
            textList.add("Camera World Coordinates: (" + (float) Camera.getInstance().getCoordinates().x + ", " + (float) Camera.getInstance().getCoordinates().y + ")");
            textList.add("Camera Zoom: " + (float) Camera.getZoom());

            if (ApplicationStatus.getStatus() == ApplicationStatus.Status.PAUSED) {
                textList.add("APPLICATION PAUSED");
            }

            glEnable(GL_TEXTURE_2D);
            TextRendering.renderText(leftMargin, topMargin, gapBetweenTexts, textList, textScale);
        }

        /** MENU **/
        if (Menu.getInstance().isShowing()) {
            Menu.getInstance().render(timeElapsed);
        }
    }
}
