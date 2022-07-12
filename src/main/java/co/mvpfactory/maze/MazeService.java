package co.mvpfactory.maze;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Slf4j
@SpringBootApplication
@EntityScan(basePackages = {"co.mvpfactory.maze.entities"})
public class MazeService {

	public static void main(String[] args) {
		SpringApplication.run(MazeService.class, args);
                log.debug("STARTED Maze-Service Application");
	}
}
