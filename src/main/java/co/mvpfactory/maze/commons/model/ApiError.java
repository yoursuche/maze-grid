package co.mvpfactory.maze.commons.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author uchechukwu
 */
@AllArgsConstructor
@Data
public class ApiError {

    private Integer statusCode;
    private String message;
    private String status;
}
