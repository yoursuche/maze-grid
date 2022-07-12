package co.mvpfactory.maze.util;

import co.mvpfactory.maze.commons.model.ApiError;
import co.mvpfactory.maze.commons.model.ApiResponse;


/**
 *
 * @author uchechukwu
 */
public class ApiResponseUtil {

    public static ApiResponse error(int value, String message,String status) {
        return new ApiResponse(new ApiError(value, message,status));
    }
}
