package co.mvpfactory.maze.exception;


public class GenericMazeServiceException extends RuntimeException {
    public GenericMazeServiceException(String message) {
        super(message);
    }

    public GenericMazeServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
