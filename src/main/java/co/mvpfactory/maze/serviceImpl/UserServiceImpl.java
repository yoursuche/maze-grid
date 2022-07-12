/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.mvpfactory.maze.serviceImpl;

import co.mvpfactory.maze.dto.NewUserReq;
import co.mvpfactory.maze.exception.BadRequestException;
import co.mvpfactory.maze.exception.GenericMazeServiceException;
import co.mvpfactory.maze.repository.AppUserRepository;
import co.mvpfactory.maze.service.UserService;
import co.mvpfactory.maze.entities.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    AppUserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public NewUserReq createUser(NewUserReq req) {
        NewUserReq resp = null;
        try {

            Boolean userNameExist = userRepository.existsByUsernameIgnoreCase(req.getUsername());
            if (userNameExist) {
                throw new BadRequestException("Username already in use");
            }

            AppUser appUser = new AppUser();
            appUser.setPassword(passwordEncoder.encode(req.getPassword()));
            appUser.setUsername(req.getUsername());
            appUser = userRepository.save(appUser);
            req.setUserId(appUser.getId());
            resp = req;
        } catch (GenericMazeServiceException ex) {
            log.error("Error creating user  " + ex.getMessage());
            throw new GenericMazeServiceException("Internal server error");
        }
        return resp;

    }

}
