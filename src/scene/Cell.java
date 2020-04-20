package scene;

import main.Coordinates;
import main.OpenGLManager;

public class Cell {
    public static int CELL_WIDTH = 16;
    public static int CELL_HEIGHT = 16;

    private boolean alive;
    private float[] color;

    public Cell() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
        color = new float[]{(float) Math.random(), (float) Math.random(), (float) Math.random()};
    }

    public float[] getColor() {
        return color;
    }

    public void setColor(float r, float g, float b) {
        this.color = new float[]{r, g, b};
    }

    public void draw(double x, double y, double scale, boolean isCameraCoordinates) {
        Coordinates cameraCoordinates = new Coordinates(x, y);
        if (!isCameraCoordinates) cameraCoordinates = cameraCoordinates.toCameraCoordinates();

        int width = (int) (CELL_WIDTH * scale);
        int height = (int) (CELL_HEIGHT * scale);

        // To prevent gaps between cells when the zoom is not an integer multiple of 2
        if (Camera.getZoom() % 2 != 0) {
            width++;
            height++;
        }

        if (alive) {
            OpenGLManager.drawRectangle((int) cameraCoordinates.x, (int) cameraCoordinates.y, width, height, 1.0, color[0], color[1], color[2]);
        } else {
            OpenGLManager.drawRectangle((int) cameraCoordinates.x, (int) cameraCoordinates.y, width, height, 1.0, 0f, 0f, 0f);
        }
    }
}
