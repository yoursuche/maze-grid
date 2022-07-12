package co.mvpfactory.maze.mazeprocessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author UchechukwuOnuoha
 */
@Slf4j
public class CustomMaze {

    String[] walls;
    String entrance;
    String gridSize;
    final String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    final String delimiter = "x";
    final int ROAD = 1;
    final int WALL = 0;
    int[][] customIntegerMaze;
    String[][] alphaNumericMaze;
    boolean[][] visited;
    Coordinate start;
    Coordinate end;
    GridExitCordinate gridExitCordinate;
    int gridLenght;

    public CustomMaze(String[] walls, String entrance, String gridSize) {
        this.walls = walls;
        this.entrance = entrance;
        this.gridSize = gridSize;
    }

    public void initializeCustomMaze() {
        try {
            String[] dimensions = gridSize.split(delimiter);
            int row = Integer.parseInt(dimensions[0]);
            int col = Integer.parseInt(dimensions[1]);
            gridLenght = row * col;
            visited = new boolean[row][col];
            alphaNumericMaze = convertToAlphaNumericGridMaze(alphabets, row, col);
            customIntegerMaze = convertToCustomuGridMaze(alphaNumericMaze, walls, row, col);
            gridExitCordinate = initGridExitCordinate(alphaNumericMaze, customIntegerMaze);
            start = getAlphaNumGridPoints(entrance, alphaNumericMaze);

        } catch (NumberFormatException ex) {
            log.error("Maze Grid Initialization error >>> " + ex.getMessage());
        }
    }

    public String[][] convertToAlphaNumericGridMaze(String alphabets, int gridRow, int gridCol) {
        char[] alpha = alphabets.toCharArray();
        String[][] grid = new String[gridRow][gridCol];
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                int rownum = row + 1;
                String rowString = String.valueOf(rownum);
                grid[row][col] = alpha[col] + rowString;
            }

        }
        return grid;
    }

    public int[][] convertToCustomuGridMaze(String[][] InGrid, String[] walls, int gridRow, int gridCol) {
        int[][] grid = new int[gridRow][gridCol];
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (Arrays.stream(walls).anyMatch(InGrid[row][col]::equals)) {
                    grid[row][col] = 0;
                } else {
                    grid[row][col] = 1;
                }

            }
        }
        return grid;
    }

    public GridExitCordinate initGridExitCordinate(String[][] alpahGrid, int[][] customGrid) {
        List<Coordinate> possibleExitPoints = new ArrayList<>();
        GridExitCordinate gCord = new GridExitCordinate(alpahGrid);
        for (int row = 0; row < customGrid.length; row++) {
            for (int col = 0; col < customGrid[row].length; col++) {
                if (row == customGrid.length - 1) {
                    if (customGrid[row][col] == 1) {
                        possibleExitPoints.add(new Coordinate(row, col));

                    }

                }

            }

        }
        gCord.setPossibleExits(possibleExitPoints);

        return gCord;
    }

    public Coordinate getAlphaNumGridPoints(String entrance, String[][] alpahGrid) {
        Coordinate entranceCordinate = null;
        for (int row = 0; row < alpahGrid.length; row++) {
            for (int col = 0; col < alpahGrid[row].length; col++) {

                if (alpahGrid[row][col].equals(entrance)) {
                    entranceCordinate = new Coordinate(row, col);
                }
            }

        }
        return entranceCordinate;
    }

    public int getHeight() {
        return customIntegerMaze.length;
    }

    public int getWidth() {
        return customIntegerMaze[0].length;
    }

    public Coordinate getEntry() {
        return start;
    }

    public void setEnd(Coordinate end) {
        this.end = end;
    }

    public boolean isExit(int x, int y) {
        return x == end.getX() && y == end.getY();
    }

    public boolean isStart(int x, int y) {
        return x == start.getX() && y == start.getY();
    }

    public boolean isExplored(int row, int col) {
        return visited[row][col];
    }

    public boolean isWall(int row, int col) {
        return customIntegerMaze[row][col] == WALL;
    }

    public void setVisited(int row, int col, boolean value) {
        visited[row][col] = value;
    }

    public boolean isValidLocation(int row, int col) {
        if (row < 0 || row >= getHeight() || col < 0 || col >= getWidth()) {
            return false;
        }
        return true;
    }

    public void reset() {
        for (boolean[] visited1 : visited) {
            Arrays.fill(visited1, false);
        }
    }

    public int[][] getCustomIntegerMaze() {
        return customIntegerMaze;
    }

    public String[][] getAlphaNumericMaze() {
        return alphaNumericMaze;
    }

    public GridExitCordinate getGridExitCordinate() {
        return gridExitCordinate;
    }

    public int getGridLenght() {
        return gridLenght;
    }

}
