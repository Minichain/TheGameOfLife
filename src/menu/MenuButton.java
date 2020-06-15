package menu;

import listeners.InputListenerManager;
import main.*;
import text.TextRendering;
import utils.MathUtils;

public class MenuButton extends MenuComponent {
    private ButtonAction buttonAction;

    public enum ButtonAction {
        NONE, LEAVE_MENU, EXIT_GAME, FULL_SCREEN, SHADERS
    }

    public MenuButton(String text, ButtonAction buttonAction) {
        setText(text);
        this.buttonAction = buttonAction;
    }

    @Override
    public void update(int x, int y) {
        int width = (int) (500f * Parameters.getResolutionFactor());
        int height = (int) (50f * Parameters.getResolutionFactor());
        update(x, y, width, height);
    }

    @Override
    public void update(int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
        this.x = x - width / 2;
        this.y = y;

        setMouseOver(MathUtils.isMouseInsideRectangle(this.x, this.y, this.x + this.width, this.y + this.height));
        if (isMouseOver() && InputListenerManager.leftMouseButtonPressed) {
            setPressed(true);
        } else {
            if (isPressed() && isMouseOver()) {
                performAction(buttonAction);
            }
            setPressed(false);
        }
    }

    @Override
    public void renderBackground() {
        if (isPressed()) {
            OpenGLManager.drawRectangle(x, y, width, height, 0.8, 0.2f);
        } else if (isMouseOver()) {
            OpenGLManager.drawRectangle(x, y, width, height, 0.65, 0.4f);
        } else {
            OpenGLManager.drawRectangle(x, y, width, height, 0.5, 0.6f);
        }
    }

    @Override
    public void renderInfo() {
        String text;
        float scale = 2 * Parameters.getResolutionFactor();
        switch (buttonAction) {
            case FULL_SCREEN:
                if (Parameters.isFullScreen()) {
                    text = "Disable Full Screen";
                } else {
                    text = "Enable Full Screen";
                }
                break;
            case SHADERS:
                if (Parameters.isShadersEnabled()) {
                    text = "Disable Shaders";
                } else {
                    text = "Enable Shaders";
                }
                break;
            case NONE:
                text = "NONE";
                break;
            default:
                text = getText();
                break;
        }
        int textX = (int) (x + (width / 2f) - (TextRendering.CHARACTER_WIDTH * scale * text.length() / 2f));
        int textY = (int) (y + (height / 2f) - (TextRendering.CHARACTER_HEIGHT * scale / 2f));
        TextRendering.renderText(textX, textY, text, scale, true);
    }

    private void performAction(ButtonAction buttonAction) {
        switch (buttonAction) {
            case FULL_SCREEN:
                Window.setFullScreen(!Parameters.isFullScreen());
                break;
            case LEAVE_MENU:
                Menu.getInstance().setShowing(!Menu.getInstance().isShowing());
                break;
            case SHADERS:
                Parameters.setShadersEnabled(!Parameters.isShadersEnabled());
                break;
            case EXIT_GAME:
                ApplicationStatus.setStatus(ApplicationStatus.Status.STOPPED);
                break;
            case NONE:
            default:
                break;
        }
    }
}
