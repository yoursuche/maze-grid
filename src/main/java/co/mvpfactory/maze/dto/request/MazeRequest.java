package co.mvpfactory.maze.dto.request;

import co.mvpfactory.maze.validations.GridSizeValidation;
import co.mvpfactory.maze.validations.MazePointValidation;
import java.io.Serializable;
import java.util.ArrayList;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author UchechukwuOnuoha
 */
@Data
public class MazeRequest implements Serializable {

    @NotBlank
    @MazePointValidation
    private String entrance;

    @GridSizeValidation
    @NotBlank
    private String gridSize;
    @NotNull(message = "Wall cannot be empty or null.")
    ArrayList<String> walls;

}
