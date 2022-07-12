package co.mvpfactory.maze.resource;

import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Api
@RestController
@Slf4j
public class IndexController {

    @GetMapping("/index")
    public ResponseEntity<?> index() {
        return ResponseEntity.ok("Maze Grid Services/API(s) is up.");
    }
}
