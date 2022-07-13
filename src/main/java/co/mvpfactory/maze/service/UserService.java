
package co.mvpfactory.maze.service;

import co.mvpfactory.maze.dto.NewUserReq;
import co.mvpfactory.maze.dto.NewUserResp;

/**
 *
 * @author uchechukwu
 */
public interface UserService {

    NewUserResp createUser(NewUserReq req);

  

}
