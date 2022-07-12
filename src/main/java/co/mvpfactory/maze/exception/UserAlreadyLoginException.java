package co.mvpfactory.maze.exception;

/**
 *
 * @author uchechukwu
 */
public class UserAlreadyLoginException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserAlreadyLoginException(String message) {
        super(message);
    }
}
