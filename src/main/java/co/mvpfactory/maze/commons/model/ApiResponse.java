package co.mvpfactory.maze.commons.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author uchechukwu
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ApiResponse {

    private ApiError error;

    public ApiResponse(ApiError apiError) {
        this.error = apiError;
    }
}
