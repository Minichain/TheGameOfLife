package menu;

import listeners.InputListenerManager;
import main.OpenGLManager;
import utils.MathUtils;

public class Scroll extends MenuComponent {

    public Scroll(int x, int y, int width, int height) {
    }

    @Override
    public void update(int x, int y) {
        update(x, y, this.width, this.height);
    }

    public void update(int x, int y, int width, int height) {
        this.x = x - width / 2;
        this.width = width;
        this.height = height;

        setMouseOver(MathUtils.isMouseInsideRectangle(this.x, this.y, this.x + this.width, this.y + this.height));
        if (isMouseOver() && InputListenerManager.leftMouseButtonPressed) {
            setPressed(true);
        } else {
            if (!InputListenerManager.leftMouseButtonPressed) {
                setPressed(false);
            }
        }

        if (isPressed()) {
            this.y = (int) InputListenerManager.getMouseWindowCoordinates().y - this.height / 2;
        }

        if (this.y < Menu.getInstance().getMenuScrollBar().y) this.y = Menu.getInstance().getMenuScrollBar().y;
        else if ((this.y + this.height) > (Menu.getInstance().getMenuScrollBar().y + Menu.getInstance().getMenuScrollBar().height))
            this.y = (int) (Menu.getInstance().getMenuScrollBar().y + Menu.getInstance().getMaxMenuHeight() - this.height);
    }

    public void renderBackground() {
        if (isPressed()) {
            OpenGLManager.drawRectangle(x, y, width, height, 0.8, 0.7f);
        } else if (isMouseOver()) {
            OpenGLManager.drawRectangle(x, y, width, height, 0.85, 0.6f);
        } else {
            OpenGLManager.drawRectangle(x, y, width, height, 0.9, 0.5f);
        }
    }

    public void renderInfo() {

    }
}
