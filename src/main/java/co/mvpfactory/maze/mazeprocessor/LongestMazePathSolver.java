package co.mvpfactory.maze.mazeprocessor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author UchechukwuOnuoha
 */
public class LongestMazePathSolver {

    final int WALL = 0;
    final int ROAD = 1;

    public List<Coordinate> findLongestPath(int[][] maze, Coordinate start,
            Coordinate end) {
        List<Coordinate> result = null;
        int rows = maze.length;
        int cols = maze[0].length;
        if (start.y < 0 || start.x < 0) {
            return null;
        }
        if (start.y == rows || start.x == cols) {
            return null;
        }
        if (maze[start.y][start.x] == WALL) {
            return null;
        }
        if (start.equals(end)) {
            List<Coordinate> path = new ArrayList<>();
            path.add(start);
            return path;
        }
        maze[start.y][start.x] = WALL;
        Coordinate[] directions = new Coordinate[4];
        directions[0] = new Coordinate(start.y + 1, start.x);
        directions[2] = new Coordinate(start.y, start.x + 1);
        directions[1] = new Coordinate(start.y - 1, start.x);
        directions[3] = new Coordinate(start.y, start.x - 1);
        int maxLength = -1;
        for (Coordinate coordinate : directions) {
            List<Coordinate> path = findLongestPath(maze, coordinate, end);
            if (path != null && path.size() > maxLength) {
                maxLength = path.size();
                path.add(0, start);
                result = path;
            }
        }
        maze[start.x][start.y] = ROAD;
        if (result == null || result.isEmpty()) {
            return null;
        }
        return result;
    }

}
