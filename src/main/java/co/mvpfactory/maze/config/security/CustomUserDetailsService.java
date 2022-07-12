package co.mvpfactory.maze.config.security;

import co.mvpfactory.maze.entities.AppUser;
import co.mvpfactory.maze.repository.AppUserRepository;
import java.util.Optional;
import javax.persistence.NonUniqueResultException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    AppUserRepository userRepository;

   

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        Optional<AppUser> user = null;
        try {
            user = userRepository.findByUsernameIgnoreCase(username);
        } catch (NonUniqueResultException ex) {
            log.error("Error" + ex.getMessage());
            throw new BadCredentialsException("Bad credentials");
        }

        if (user == null || !user.isPresent()) {
            throw new BadCredentialsException("Bad credentials");
        }
        new AccountStatusUserDetailsChecker().check(user.get());
        AppUser currentUser = user.get();

        return currentUser;
    }

}
