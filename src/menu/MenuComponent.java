package menu;

public abstract class MenuComponent {
    private String text;
    private boolean mouseOver = false;
    private boolean pressed = false;

    public int x = 0;
    public int y = 0;
    public int width = 500;
    public int height = 45;

    public abstract void update(int position, int gapBetweenComponents);

    public abstract void renderBackground();

    public abstract void renderInfo();

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
