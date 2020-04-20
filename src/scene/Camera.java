package scene;

import listeners.InputListenerManager;
import main.Coordinates;
import main.Parameters;
import utils.MathUtils;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;

public class Camera {
    private static Camera instance = null;
    private Coordinates coordinates;
    private static double xInitialCoordinate = Scene.getInitialCoordinates().x;
    private static double yInitialCoordinate = Scene.getInitialCoordinates().y;
    private static double zoom = 0.12;
    private static double freeCameraSpeed = 1.0;

    public Camera() {
        coordinates = new Coordinates(xInitialCoordinate, yInitialCoordinate);
    }

    public static Camera getInstance() {
        if (instance == null) {
            instance = new Camera();
        }
        return instance;
    }

    /**
     * Coordinates where the Camera is centered
     * */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double x, double y) {
        coordinates.x = x;
        coordinates.y = y;
    }

    /**
     * Amount of pixels we are able to see in the X axis.
     * */
    public static double getWidth() {
        return Parameters.getResolutionWidth() / zoom;
    }

    /**
     * Amount of pixels we are able to see in the Y axis.
     * */
    public static double getHeight() {
        return Parameters.getResolutionHeight() / zoom;
    }

    public static double getZoom() {
        return zoom;
    }

    public static void setZoom(double zoom) {
        Camera.zoom = zoom;
    }

    public void reset() {
        this.setCoordinates(4000, 4000);
        setZoom(0.12);
    }

    public void update(long timeElapsed) {
        double[] movement = computeMovementVector(timeElapsed, freeCameraSpeed);
        Camera.getInstance().setCoordinates((Camera.getInstance().getCoordinates().x + (movement[0] / getZoom())),
                (Camera.getInstance().getCoordinates().y + (movement[1] / getZoom())));
    }

    public double[] computeMovementVector(long timeElapsed, double speed) {
        double[] movement = new double[2];
        if (InputListenerManager.isKeyPressed(GLFW_KEY_S)) {
            movement[1] = 1;
        }
        if (InputListenerManager.isKeyPressed(GLFW_KEY_A)) {
            movement[0] = -1;
        }
        if (InputListenerManager.isKeyPressed(GLFW_KEY_W)) {
            movement[1] = -1;
        }
        if (InputListenerManager.isKeyPressed(GLFW_KEY_D)) {
            movement[0] = 1;
        }

        movement = MathUtils.normalizeVector(movement);
        movement[0] *= timeElapsed * speed;
        movement[1] *= timeElapsed * speed;

        return movement;
    }
}