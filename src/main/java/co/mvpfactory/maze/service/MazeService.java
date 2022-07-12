package co.mvpfactory.maze.service;

import co.mvpfactory.maze.dto.request.MazeRequest;
import co.mvpfactory.maze.dto.request.MazeSolution;
import java.util.ArrayList;

/**
 *
 * @author uchechukwu
 */
public interface MazeService {

    MazeRequest createUserMaze(MazeRequest req, String username);

    ArrayList<MazeRequest> getMazeUserList(String username);

    MazeSolution getMazeSolution(long mazeId, String username, String stepType);

}
