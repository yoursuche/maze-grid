package co.mvpfactory.maze.exception;

import co.mvpfactory.maze.commons.model.ApiResponse;
import co.mvpfactory.maze.util.ApiResponseUtil;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author uchechukwu
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomMazeServiceExceptionController {

    @ResponseBody
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse resourceNotFoundHandler(BadRequestException ex) {
        return ApiResponseUtil.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @ResponseBody
    @ExceptionHandler(GenericMazeServiceException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiResponse resourceNotFoundHandler(GenericMazeServiceException ex) {
        return ApiResponseUtil.error(HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase());
    }

    @ResponseBody
    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse resourceNotFoundHandler(RecordNotFoundException ex) {
        return ApiResponseUtil.error(HttpStatus.NOT_FOUND.value(), ex.getMessage(), HttpStatus.NOT_FOUND.getReasonPhrase());
    }


    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiResponse handleAccessDeniedException(AccessDeniedException ex) {
        return ApiResponseUtil.error(HttpStatus.FORBIDDEN.value(), ex.getMessage(), HttpStatus.FORBIDDEN.getReasonPhrase());
    }

  

}
