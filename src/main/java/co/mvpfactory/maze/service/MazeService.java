package co.mvpfactory.maze.service;

import co.mvpfactory.maze.dto.request.MazeRequest;
import co.mvpfactory.maze.dto.request.MazeResp;
import co.mvpfactory.maze.dto.request.MazeSolution;
import java.util.ArrayList;

/**
 *
 * @author uchechukwu
 */
public interface MazeService {

    MazeResp createUserMaze(MazeRequest req, String username);

    ArrayList<MazeResp> getMazeUserList(String username);

    MazeSolution getMazeSolution(long mazeId, String username, String stepType);

}
