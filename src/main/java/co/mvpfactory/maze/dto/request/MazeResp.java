package co.mvpfactory.maze.dto.request;

import java.io.Serializable;
import java.util.ArrayList;
import lombok.Data;

/**
 *
 * @author UchechukwuOnuoha
 */
@Data
public class MazeResp implements Serializable {

    Long mazeId;

    private String entrance;

    private String gridSize;

    ArrayList<String> walls;

}
