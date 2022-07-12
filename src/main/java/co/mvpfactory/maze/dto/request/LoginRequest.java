package co.mvpfactory.maze.dto.request;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 *
 * @author UchechukwuOnuoha
 */
@Data
public class LoginRequest implements Serializable{

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
