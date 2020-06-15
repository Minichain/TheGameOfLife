package utils;

import listeners.InputListenerManager;
import main.Coordinates;

public class MathUtils {

    public static double[] normalizeVector(double[] inputVector) {
        int vectorLength = inputVector.length;
        double[] vectorNormalized = new double[vectorLength];
        double vectorModule = module(inputVector);
        if (vectorModule > 0) {
            for (int i = 0; i < vectorLength; i++) {
                vectorNormalized[i] = inputVector[i] / vectorModule;
            }
        } else {
            vectorNormalized = inputVector;
        }
        return vectorNormalized;
    }

    public static double[] generateOrthonormalVector(double[] v1) {
        double[] v2 = new double[]{1, 0};
        v2[1] = - (v1[0] * v2[0]) / v1[1];
        return v2;
    }

    public static double module(double[] vector) {
        double sum = 0;
        for (int i = 0; i < vector.length; i++) {
            sum += Math.pow(vector[i], 2);
        }
        return Math.sqrt(sum);
    }

    public static double module(double[] coordinates1, double[] coordinates2) {
        return Math.sqrt(Math.pow(coordinates1[0] - coordinates2[0], 2)
                + Math.pow(coordinates1[1] - coordinates2[1], 2));
    }

    public static double module(Coordinates coordinates1, Coordinates coordinates2) {
        return Math.sqrt(Math.pow(coordinates1.x - coordinates2.x, 2)
                + Math.pow(coordinates1.y - coordinates2.y, 2));
    }

    public static boolean isPointInsideCircle(Coordinates point, Coordinates center, double radius) {
        return module(point, center) <= radius;
    }

    public static boolean isPointInsideCircle(double[] point, double[] center, double radius) {
        return module(point, center) <= radius;
    }

    public static boolean isPointInsideTriangle(Coordinates point, Coordinates vertex1, Coordinates vertex2, Coordinates vertex3) {
        double d1, d2, d3;
        boolean has_neg, has_pos;

        d1 = sign(point, vertex1, vertex2);
        d2 = sign(point, vertex2, vertex3);
        d3 = sign(point, vertex3, vertex1);

        has_neg = (d1 < 0) || (d2 < 0) || (d3 < 0);
        has_pos = (d1 > 0) || (d2 > 0) || (d3 > 0);

        return !(has_neg && has_pos);
    }

    private static double sign(Coordinates vertex1, Coordinates vertex2, Coordinates vertex3) {
        return (vertex1.x - vertex3.x) * (vertex2.y - vertex3.y) - (vertex2.x - vertex3.x) * (vertex1.y - vertex3.y);
    }

    public static boolean isMouseInsideRectangle(int x, int y, int x2, int y2) {
        Coordinates mouseCameraCoordinates = InputListenerManager.getMouseCameraCoordinates();
        return x < mouseCameraCoordinates.x && mouseCameraCoordinates.x < x2 && y < mouseCameraCoordinates.y && mouseCameraCoordinates.y < y2;
    }

    public static double[] rotateVector(double[] vector, double angle) {
        return new double[]{Math.cos(angle) * vector[0] - Math.sin(angle) * vector[1], Math.sin(angle) * vector[0] + Math.cos(angle) * vector[1]};
    }

    public static float computeMean(float[] values) {
        float sum = 0f;
        for (float value : values) {
            sum += value;
        }
        return sum / values.length;
    }

    public static double computeMean(double[] values) {
        double sum = 0f;
        for (double value : values) {
            sum += value;
        }
        return sum / values.length;
    }

    public static double inverseCubicFunction(double x) {
        return 1.0 - Math.pow(x, 3);
    }

    public static float inverseCubicFunction(float x) {
        return 1f - (float) Math.pow(x, 3);
    }

    public static double cubicFunction(double x) {
        return Math.pow(x, 3);
    }

    public static float cubicFunction(float x) {
        return (float) Math.pow(x, 3);
    }
}
