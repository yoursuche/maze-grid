package co.mvpfactory.maze.dto.request;

import java.io.Serializable;
import java.util.ArrayList;
import lombok.Data;

/**
 *
 * @author UchechukwuOnuoha
 */
@Data
public class MazeSolution implements Serializable {

    ArrayList<String> path;

}
