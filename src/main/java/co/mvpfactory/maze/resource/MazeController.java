package co.mvpfactory.maze.resource;

import co.mvpfactory.maze.dto.request.MazeRequest;
import co.mvpfactory.maze.dto.request.MazeSolution;
import co.mvpfactory.maze.service.MazeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author UchechukwuOnuoha
 */
@Api
@RestController
@Slf4j
@RequestMapping("/maze")
public class MazeController {

    @Autowired
    MazeService mazeService;

    @PostMapping
    @ApiOperation(value = "Create maze", response = MazeRequest.class)
    public ResponseEntity<?> createMaze(@Valid @RequestBody MazeRequest mazeRequest, Principal principal, BindingResult brs) {
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
        MazeRequest maze = mazeService.createUserMaze(mazeRequest, principal.getName());
        return new ResponseEntity<MazeRequest>(maze, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value = "Get User maze")
    public ResponseEntity<?> getMaze(Principal principal) {
        ArrayList<MazeRequest> mazeList = mazeService.getMazeUserList(principal.getName());
        return new ResponseEntity<ArrayList<MazeRequest>>(mazeList, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{mazeId}/solution")
    @ApiOperation(value = "Get User maze solution")
    public ResponseEntity<?> getMazeSolution(@PathVariable("mazeId") Optional<Long> mazeId, @RequestParam(name = "step", required = true) String step, Principal principal) {
        if (mazeId.isPresent() && (step !=null && !step.trim().isEmpty())) {

            Long retrievedMazeId = mazeId.get();

            MazeSolution solution = mazeService.getMazeSolution(retrievedMazeId, principal.getName(), step);

            return new ResponseEntity<MazeSolution>(solution, new HttpHeaders(), HttpStatus.OK);

        } else {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Missing  variable mazeId");
        }
    }

}
