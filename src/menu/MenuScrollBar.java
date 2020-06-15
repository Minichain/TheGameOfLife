package menu;

import listeners.InputListenerManager;
import main.OpenGLManager;
import utils.MathUtils;

import static org.lwjgl.opengl.GL11.*;

public class MenuScrollBar extends MenuComponent {
    private Scroll scroll;

    public MenuScrollBar() {
        scroll = new Scroll(0, 0, 0, 0);
    }

    @Override
    public void update(int x, int y) {
        update(x, y, this.width, this.height);
    }

    @Override
    public void update(int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
        this.x = x - width / 2;
        this.y = y;

        int scrollWidth = 12;
        int maxMenuHeight = (int) (Menu.getInstance().getMaxMenuHeight());
        int menuHeight = (int) (Menu.getInstance().getMenuHeight());
        scroll.update(x, -1, this.width, maxMenuHeight * maxMenuHeight / menuHeight);

        setMouseOver(MathUtils.isMouseInsideRectangle(this.x, this.y, this.x + this.width, this.y + this.height));
        if (isMouseOver() && InputListenerManager.leftMouseButtonPressed) {
            setPressed(true);
        } else {
            if (isPressed() && isMouseOver()) {
                //Do something
            }
            setPressed(false);
        }
    }

    @Override
    public void renderBackground() {
        OpenGLManager.glBegin(GL_TRIANGLES);

        if (scroll.isPressed()) {
            OpenGLManager.drawRectangle(x, y, width, height, 0.8, 0.2f);
        } else if (isMouseOver()) {
            OpenGLManager.drawRectangle(x, y, width, height, 0.65, 0.4f);
        } else {
            OpenGLManager.drawRectangle(x, y, width, height, 0.5, 0.6f);
        }

        scroll.renderBackground();

        glEnd();
    }

    @Override
    public void renderInfo() {

    }

    public Scroll getScroll() {
        return scroll;
    }
}
