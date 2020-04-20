package menu;

import main.*;
import text.TextRendering;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;

public class Menu {
    private static Menu instance = null;
    private ArrayList<MenuComponent> listOfMenuComponents;
    private boolean showing;
    private int gapBetweenComponents = 30;
    private Coordinates coordinates;

    public Menu() {
        listOfMenuComponents = new ArrayList<>();

        MenuButton resumeApp = new MenuButton("Resume Application", MenuButton.ButtonAction.LEAVE_MENU);
        listOfMenuComponents.add(resumeApp);

        MenuButton fullScreen = new MenuButton("Enable/Disable FullScreen", MenuButton.ButtonAction.FULL_SCREEN);
        listOfMenuComponents.add(fullScreen);

        MenuSlideBar effectSoundLevel = new MenuSlideBar("Sound Effects", MenuSlideBar.SliderAction.EFFECT_SOUND_LEVEL);
        listOfMenuComponents.add(effectSoundLevel);

        MenuSlideBar musicSoundLevel = new MenuSlideBar("Music", MenuSlideBar.SliderAction.MUSIC_SOUND_LEVEL);
        listOfMenuComponents.add(musicSoundLevel);

        MenuSelector resolutionSelector = new MenuSelector("Resolution");
        listOfMenuComponents.add(resolutionSelector);

        MenuButton exitApp = new MenuButton("Exit Application", MenuButton.ButtonAction.EXIT_APP);
        listOfMenuComponents.add(exitApp);
    }

    public static Menu getInstance() {
        if (instance == null) {
            instance = new Menu();
        }
        return instance;
    }

    public boolean isShowing() {
        return showing;
    }

    public void setShowing(boolean showing) {
        if (showing) ApplicationStatus.setStatus(ApplicationStatus.Status.PAUSED);
        else ApplicationStatus.setStatus(ApplicationStatus.Status.RUNNING);
        this.showing = showing;
    }

    public ArrayList<MenuComponent> getListOfMenuComponents() {
        return listOfMenuComponents;
    }

    public void render(long timeElapsed) {
        float menuHeight = 0f;
        for (int i = 0; i < listOfMenuComponents.size(); i++) {
            if (i > 0) menuHeight += gapBetweenComponents;
            menuHeight += listOfMenuComponents.get(i).height;
        }

        coordinates = new Coordinates((float) Parameters.getResolutionWidth() / 2, (float) Parameters.getResolutionHeight() / 2 - menuHeight / 2);

        for (int i = 0; i < listOfMenuComponents.size(); i++) {
            listOfMenuComponents.get(i).update(i, gapBetweenComponents);
        }

        glDisable(GL_TEXTURE_2D);
        OpenGLManager.glBegin(GL_TRIANGLES);
        for (int i = 0; i < listOfMenuComponents.size(); i++) {
            MenuComponent component = listOfMenuComponents.get(i);
            component.renderBackground();
        }
        glEnd();

        TextRendering.fontSpriteWhite.bind();
        glEnable(GL_TEXTURE_2D);
        OpenGLManager.glBegin(GL_QUADS);
        for (int i = 0; i < listOfMenuComponents.size(); i++) {
            MenuComponent component = listOfMenuComponents.get(i);
            component.renderInfo();
        }
        glEnd();
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}
