package co.mvpfactory.maze.repository;

import co.mvpfactory.maze.entities.AppRole;
import co.mvpfactory.maze.entities.AppUser;
import co.mvpfactory.maze.entities.UserRole;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    Optional<UserRole> findByAppRole(AppRole roleName);

    Optional<UserRole> findByAppUser(AppUser roleName);

}
