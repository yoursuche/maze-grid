package co.mvpfactory.maze.mazeprocessor;

import co.mvpfactory.maze.exception.BadRequestException;
import co.mvpfactory.maze.exception.GenericMazeServiceException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author UchechukwuOnuoha
 */
@Slf4j
public class MazeGridProcessor {

    String[] walls;
    String gridSize;
    String entrance;

    public MazeGridProcessor(String[] walls, String gridSize, String entrance) {
        this.walls = walls;
        this.gridSize = gridSize;
        this.entrance = entrance;
    }

    public ArrayList<String> getMazeMinumumStep() {
        ArrayList<String> minimumStep = new ArrayList<>();
        CustomMaze custMaze = null;
        try {
            custMaze = new CustomMaze(walls, entrance, gridSize);
            custMaze.initializeCustomMaze();
            List<Coordinate> possibleExits = custMaze.getGridExitCordinate().possibleExits;
            if (possibleExits.isEmpty()) {
                throw new BadRequestException("Maze Grid has no possible exit path/point");
            }
            BFSMazeSolver bfs = new BFSMazeSolver();
            ArrayList<ArrayList<String>> paths = new ArrayList<>();
            for (Coordinate cord : possibleExits) {
                custMaze.setEnd(cord);
                List<Coordinate> path = bfs.solve(custMaze);
                ArrayList<String> step = new ArrayList<>();
                for (Coordinate coordinate : path) {
                    step.add(custMaze.alphaNumericMaze[coordinate.getX()][coordinate.getY()]);
                }
                paths.add(step);
                custMaze.reset();
            }
            int minimumSize = custMaze.getGridLenght();
            for (ArrayList<String> path : paths) {
                if (path.size() < minimumSize && !path.isEmpty()) {
                    minimumSize = path.size();
                    minimumStep = path;
                }

            }

        } catch (GenericMazeServiceException ex) {
            throw new GenericMazeServiceException(ex.getMessage());
        }
        custMaze = null;
        return minimumStep;
    }

    public ArrayList<String> getMazeMaximumStep() {
        ArrayList<String> maximumStep = new ArrayList<>();
        CustomMaze custMaze = null;
        try {
            custMaze = new CustomMaze(walls, entrance, gridSize);
            custMaze.initializeCustomMaze();
            List<Coordinate> possibleExits = custMaze.getGridExitCordinate().possibleExits;
            if (possibleExits.isEmpty()) {
                throw new BadRequestException("Maze Grid has no possible exit path/point");
            }
            LongestMazePathSolver longestMazeSolver = new LongestMazePathSolver();
            ArrayList<ArrayList<String>> paths = new ArrayList<>();
            for (Coordinate cord : possibleExits) {

                List<Coordinate> path = longestMazeSolver.findLongestPath(custMaze.getCustomIntegerMaze(), custMaze.start, cord);
                ArrayList<String> step = new ArrayList<>();
                if (path != null) {
                    for (Coordinate coordinate : path) {
                        step.add(custMaze.alphaNumericMaze[coordinate.getX()][coordinate.getY()]);
                    }
                    paths.add(step);
                }
               
            }
            int maximumSize = 0;
            for (ArrayList<String> path : paths) {
                if (path.size() > maximumSize && !path.isEmpty()) {
                    maximumSize = path.size();
                    maximumStep = path;
                }

            }

        } catch (GenericMazeServiceException ex) {
            throw new GenericMazeServiceException(ex.getMessage());
        }
        custMaze = null;
        return maximumStep;
    }

}
