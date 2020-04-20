package menu;

import listeners.InputListenerManager;
import main.*;
import text.TextRendering;
import utils.MathUtils;

public class MenuButton extends MenuComponent {
    private ButtonAction buttonAction;

    public enum ButtonAction {
        NONE, LEAVE_MENU, EXIT_APP, FULL_SCREEN, CREATIVE_MODE
    }

    public MenuButton(String text, ButtonAction buttonAction) {
        setText(text);
        this.buttonAction = buttonAction;
    }

    @Override
    public void update(int position, int gapBetweenComponents) {
        x = (int) Menu.getInstance().getCoordinates().x - width / 2;
        y = (int) Menu.getInstance().getCoordinates().y + (height + gapBetweenComponents) * position;
        setMouseOver(MathUtils.isMouseInsideRectangle(x, y, x + width, y + height));
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
        int scale = 2;
        int textX = x + (width / 2) - (TextRendering.CHARACTER_WIDTH * scale * getText().length() / 2);
        int textY = y + (height / 2) - (TextRendering.CHARACTER_HEIGHT * scale / 2);
        TextRendering.renderText(textX, textY, getText(), scale, true);
    }

    private void performAction(ButtonAction buttonAction) {
        switch (buttonAction) {
            case FULL_SCREEN:
                Window.setFullScreen(!Parameters.isFullScreen());
                break;
            case CREATIVE_MODE:
                break;
            case LEAVE_MENU:
                Menu.getInstance().setShowing(!Menu.getInstance().isShowing());
                break;
            case EXIT_APP:
                ApplicationStatus.setStatus(ApplicationStatus.Status.STOPPED);
                break;
            case NONE:
            default:
                break;
        }
    }
}
