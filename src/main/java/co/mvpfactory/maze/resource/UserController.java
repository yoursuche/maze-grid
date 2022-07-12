package co.mvpfactory.maze.resource;

import co.mvpfactory.maze.dto.NewUserReq;
import co.mvpfactory.maze.service.UserService;

import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api
@RestController
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    
    @PostMapping("/user")
    @ApiOperation(value = "Create User", response = NewUserReq.class)
    public ResponseEntity<?> createUser(@Valid @RequestBody NewUserReq userRequest, BindingResult brs) {
        StringBuilder sb = new StringBuilder();
        if (brs.hasErrors()) {
            log.error("BAD REQUEST SERVICE ERROR :::", brs.toString());
            brs.getFieldErrors().forEach((error) -> {
                sb.append(error.getObjectName()).append(" - ").append(error.getDefaultMessage()).append(System.getProperty("line.separator"));
            });

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(sb.toString());

        }

        NewUserReq user = userService.createUser(userRequest);

        return new ResponseEntity<NewUserReq>(user, new HttpHeaders(), HttpStatus.OK);

    }

    
}
