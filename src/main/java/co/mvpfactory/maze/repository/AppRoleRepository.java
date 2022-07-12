package co.mvpfactory.maze.repository;

import co.mvpfactory.maze.entities.AppRole;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AppRoleRepository extends JpaRepository<AppRole, Long> {

    Optional<AppRole> findByNameIgnoreCase(String roleName);

    Boolean existsByNameIgnoreCase(String roleName);

}
