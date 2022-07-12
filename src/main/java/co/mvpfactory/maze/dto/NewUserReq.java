/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.mvpfactory.maze.dto;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 *
 * @author Uchechukwu Onuoha
 */
@Data
public class NewUserReq implements Serializable {

    private Long userId;

    @NotBlank(message = "Username cannot be empty")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    private String password;

}
