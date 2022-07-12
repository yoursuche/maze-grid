
package co.mvpfactory.maze.mazeprocessor;

import java.util.List;

/**
 *
 * @author UchechukwuOnuoha
 */
public class GridExitCordinate {

    String[][] grid;
    List<Coordinate> possibleExits;

    public GridExitCordinate() {
    }

    public GridExitCordinate(String[][] grid) {
        this.grid = grid;

    }

    public String[][] getGrid() {
        return grid;
    }

    public void setGrid(String[][] grid) {
        this.grid = grid;
    }

    public List<Coordinate> getPossibleExits() {
        return possibleExits;
    }

    public void setPossibleExits(List<Coordinate> possibleExits) {
        this.possibleExits = possibleExits;
    }

}

