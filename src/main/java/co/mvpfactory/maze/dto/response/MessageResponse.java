
package co.mvpfactory.maze.dto.response;

/**
 *
 * @author UchechukwuOnuoha
 */
public class MessageResponse {
  private String message;

  public MessageResponse(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
