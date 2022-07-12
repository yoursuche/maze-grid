package co.mvpfactory.maze.repository;

import co.mvpfactory.maze.entities.AppUser;
import co.mvpfactory.maze.entities.Maze;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface MazeRepository extends JpaRepository<Maze, Long> {

    List<Maze> findByUser(AppUser user);

    Optional<Maze> findByUserAndId(AppUser user, Long Id);

}
