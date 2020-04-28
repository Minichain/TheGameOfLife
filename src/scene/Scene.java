package scene;

import audio.OpenALManager;
import main.*;
import utils.MathUtils;

import static org.lwjgl.opengl.GL11.*;

public class Scene {
    private static Scene instance = null;

    private static int renderDistance = 1000; //TODO This should depend on the Window and Camera parameters
    private static int updateDistance = 1250; //TODO This should depend on... what?

    private static Coordinates initialCoordinates;

    private static long timeElapsedSinceLastUpdate;

    private Scene() {
        initialCoordinates = new Coordinates(4000, 4000);
        timeElapsedSinceLastUpdate = 0;
    }

    public static Scene getInstance() {
        if (instance == null) {
            instance = new Scene();
        }
        return instance;
    }

    public void restart() {
        Camera.getInstance().reset();
        CellMap.loadMap();
    }

    public void update(long timeElapsed) {
        OpenALManager.playSound(OpenALManager.MUSIC_01);

        if (ApplicationStatus.getStatus() != ApplicationStatus.Status.RUNNING) {
            return;
        }

        renderDistance = (int) (1000.0 / Camera.getZoom());

        timeElapsedSinceLastUpdate += timeElapsed;
        if (timeElapsedSinceLastUpdate < 25) {
            return;
        }

        for (int i = 0; i < CellMap.getArrayOfCells().length; i++) {
            for (int j = 0; j < CellMap.getArrayOfCells()[0].length; j++) {
                int aliveCellsAround = 0;
                float[] averageColorFromCellsAround = new float[3];
                for (int ii = i - 1; ii <= i + 1; ii++) {
                    for (int jj = j - 1; jj <= j + 1; jj++) {
                        int iii, jjj;
                        iii = ii % CellMap.getArrayOfCells().length;
                        jjj = jj % CellMap.getArrayOfCells()[0].length;
                        if (ii < 0) iii = CellMap.getArrayOfCells().length - 1;
                        if (jj < 0) jjj = CellMap.getArrayOfCells()[0].length - 1;

                        if ((iii != i || jjj != j) && CellMap.getArrayOfCells()[iii][jjj].isAlive()) {
                            aliveCellsAround++;
                            averageColorFromCellsAround[0] += CellMap.getArrayOfCells()[iii][jjj].getColor()[0];
                            averageColorFromCellsAround[1] += CellMap.getArrayOfCells()[iii][jjj].getColor()[1];
                            averageColorFromCellsAround[2] += CellMap.getArrayOfCells()[iii][jjj].getColor()[2];
                        }
                    }
                }

                Cell cell = CellMap.getArrayOfCells()[i][j];

                /** IF THE CELL IS ALIVE **/
                if (cell.isAlive()) {
                    if (aliveCellsAround < 2) { //Too low population around it
                        cell.setFutureStatus(Cell.Status.DEAD);
                    } else if (aliveCellsAround > 3) {  //Overpopulation
                        cell.setFutureStatus(Cell.Status.DEAD);
                    }
                }
                /** IF THE CELL IS DEAD **/
                else {
                    if (aliveCellsAround == 3) {
                        cell.setFutureStatus(Cell.Status.ALIVE);
                        cell.setColor(averageColorFromCellsAround[0] / 3f, averageColorFromCellsAround[1] / 3f, averageColorFromCellsAround[2] / 3f);
                    }
                }
            }
        }

        /** UPDATE CELL'S STATUS **/
        for (int i = 0; i < CellMap.getArrayOfCells().length; i++) {
            for (int j = 0; j < CellMap.getArrayOfCells()[0].length; j++) {
                Cell cell = CellMap.getArrayOfCells()[i][j];
                cell.updateStatus();
            }
        }

        timeElapsedSinceLastUpdate = 0;
    }

    public static Coordinates getInitialCoordinates() {
        return initialCoordinates;
    }

    public void render() {
        int oneAxisDistance = (int) (renderDistance * Math.sin(Math.PI / 2));
        Coordinates topLeftWorldCoordinates = new Coordinates(Camera.getInstance().getCoordinates().x - oneAxisDistance, Camera.getInstance().getCoordinates().y - oneAxisDistance);
        Coordinates topLeftCellCoordinates = Coordinates.worldCoordinatesToCellCoordinates(topLeftWorldCoordinates.x, topLeftWorldCoordinates.y);

        Coordinates topRightWorldCoordinates = new Coordinates(Camera.getInstance().getCoordinates().x + oneAxisDistance, Camera.getInstance().getCoordinates().y - oneAxisDistance);
        Coordinates topRightCellCoordinates = Coordinates.worldCoordinatesToCellCoordinates(topRightWorldCoordinates.x, topRightWorldCoordinates.y);

        Coordinates bottomLeftWorldCoordinates = new Coordinates(Camera.getInstance().getCoordinates().x - oneAxisDistance, Camera.getInstance().getCoordinates().y + oneAxisDistance);
        Coordinates bottomLeftCellCoordinates = Coordinates.worldCoordinatesToCellCoordinates(bottomLeftWorldCoordinates.x, bottomLeftWorldCoordinates.y);

        glDisable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        OpenGLManager.glBegin(GL_TRIANGLES);

        for (int i = (int) topLeftCellCoordinates.x; i < topRightCellCoordinates.x; i++) {
            for (int j = (int) topLeftCellCoordinates.y; j < bottomLeftCellCoordinates.y; j++) {
                if (0 < i && i < CellMap.getArrayOfCells().length
                        && 0 < j && j < CellMap.getArrayOfCells()[0].length) {
                    double scale = Camera.getZoom();
                    int x = i * Cell.CELL_WIDTH;
                    int y = j * Cell.CELL_HEIGHT;
                    double distanceBetweenPlayerAndCell = MathUtils.module(Camera.getInstance().getCoordinates(), new Coordinates(x, y));
                    CellMap.drawCell(i, j, x, y, scale, (float) (renderDistance - distanceBetweenPlayerAndCell) / renderDistance);
                }
            }
        }

        glEnd();
    }
}
