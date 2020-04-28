package scene;

public class CellMap {
    /** CELLS **/
    private static int numOfHorizontalCells = 500;
    private static int numOfVerticalCells = 500;
    private static Cell[][] arrayOfCells;

    public static void loadMap() {
        arrayOfCells = new Cell[numOfHorizontalCells][numOfVerticalCells];
        for (int i = 0; i < numOfHorizontalCells; i++) {
            for (int j = 0; j < numOfVerticalCells; j++) {
                arrayOfCells[i][j] = new Cell();
                if (Math.random() < 0.1) {
                    arrayOfCells[i][j].setStatus(Cell.Status.ALIVE);
                }
            }
        }
    }

    public static Cell[][] getArrayOfCells() {
        return arrayOfCells;
    }

    public static int getNumOfHorizontalCells() {
        return numOfHorizontalCells;
    }

    public static int getNumOfVerticalCells() {
        return numOfVerticalCells;
    }

    public static void drawCell(int i, int j, int x, int y, double scale, float distanceFactor) {
        if (0 < i && i < CellMap.getArrayOfCells().length && 0 < j && j < CellMap.getArrayOfCells()[0].length) {
            CellMap.getArrayOfCells()[i][j].draw(x, y, scale, false);
        }
    }
}
