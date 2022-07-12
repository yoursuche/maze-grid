/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.mvpfactory.maze.serviceImpl;

import co.mvpfactory.maze.dto.request.MazeRequest;
import co.mvpfactory.maze.dto.request.MazeSolution;
import co.mvpfactory.maze.entities.AppUser;
import co.mvpfactory.maze.exception.GenericMazeServiceException;
import co.mvpfactory.maze.repository.AppUserRepository;
import co.mvpfactory.maze.entities.Maze;
import co.mvpfactory.maze.entities.MazeWall;
import co.mvpfactory.maze.enums.StepType;
import co.mvpfactory.maze.mazeprocessor.CustomMaze;
import co.mvpfactory.maze.exception.BadRequestException;
import co.mvpfactory.maze.exception.RecordNotFoundException;
import co.mvpfactory.maze.mazeprocessor.MazeGridProcessor;
import co.mvpfactory.maze.repository.MazeRepository;
import co.mvpfactory.maze.service.MazeService;
import co.mvpfactory.maze.util.Utility;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class MazeServiceImpl implements MazeService {

    AppUserRepository userRepository;
    MazeRepository mazeRepository;

    @Autowired
    public MazeServiceImpl(AppUserRepository userRepository, MazeRepository mazeRepository) {
        this.userRepository = userRepository;
        this.mazeRepository = mazeRepository;

    }

    @Override
    @Transactional
    public MazeRequest createUserMaze(MazeRequest req, String username) {
        MazeRequest resp = null;
        try {
            Optional<AppUser> optionalDbUser = userRepository.findByUsernameIgnoreCase(username);
            if (!optionalDbUser.isPresent()) {
                throw new RecordNotFoundException("No User record found for this user >> " + username);
            }
            String[] walls = new String[req.getWalls().size()];
            walls = req.getWalls().toArray(walls);
            CustomMaze custMaze = new CustomMaze(walls, req.getEntrance(), req.getGridSize());
            custMaze.initializeCustomMaze();
            boolean validEntrance = Utility.gridHasValue(custMaze.getAlphaNumericMaze(), req.getEntrance());
            if (!validEntrance) {
                throw new BadRequestException("Entrance is not a valid point the Maze grid");
            }
            boolean isValidWall = Utility.isValidWall(custMaze.getAlphaNumericMaze(), walls);
            if (!isValidWall) {
                throw new BadRequestException("Walls contain an invalid element");
            }

            Maze maze = new Maze();
            maze.setEntrance(req.getEntrance());
            maze.setGridSize(req.getGridSize());
            maze.setUser(optionalDbUser.get());
            Set<MazeWall> mazeWalls = new HashSet<>();
            for (String wall : req.getWalls()) {
                MazeWall newMazeWall = new MazeWall();
                newMazeWall.setMaze(maze);
                newMazeWall.setWall(wall);
                mazeWalls.add(newMazeWall);
            }
            maze.setMazeWalls(mazeWalls);
            Maze dbMaze = mazeRepository.saveAndFlush(maze);
            req.setMazeId(dbMaze.getId());
            resp = req;
        } catch (GenericMazeServiceException ex) {
            log.error("Error creating user  " + ex.getMessage());
            throw new GenericMazeServiceException("Internal server error");
        }
        return resp;

    }

    @Override
    public ArrayList<MazeRequest> getMazeUserList(String username) {
        ArrayList<MazeRequest> resp = new ArrayList<>();
        try {
            Optional<AppUser> optionalDbUser = userRepository.findByUsernameIgnoreCase(username);
            if (!optionalDbUser.isPresent()) {
                throw new RecordNotFoundException("User record not found");
            }
            List<Maze> mazeList = mazeRepository.findByUser(optionalDbUser.get());
            if (mazeList == null || mazeList.isEmpty()) {
                throw new RecordNotFoundException("No Maze record found for user ==> " + username);
            }
            for (Maze maze : mazeList) {
                MazeRequest maseReq = new MazeRequest();
                maseReq.setEntrance(maze.getEntrance());
                maseReq.setGridSize(maze.getGridSize());
                maseReq.setMazeId(maze.getId());
                ArrayList<String> dbWalls = new ArrayList<>();
                for (MazeWall dbMazeWall : maze.getMazeWalls()) {
                    dbWalls.add(dbMazeWall.getWall());
                }
                maseReq.setWalls(dbWalls);
                resp.add(maseReq);
            }

        } catch (GenericMazeServiceException ex) {
            log.error("Error retrieving maze  " + ex.getMessage());
            throw new GenericMazeServiceException("Internal server error");
        }
        return resp;

    }

    @Override
    public MazeSolution getMazeSolution(long mazeId, String username, String stepType) {
        MazeSolution solution = null;
        try {
            StepType actualStepType = StepType.fromString(stepType);
            if (null == actualStepType) {
                throw new BadRequestException("Invalid step parameter value passed.");
            }

            Optional<AppUser> optionalDbUser = userRepository.findByUsernameIgnoreCase(username);
            if (!optionalDbUser.isPresent()) {
                throw new RecordNotFoundException("User record not found.");
            }
            Optional<Maze> optionDbMaze = mazeRepository.findByUserAndId(optionalDbUser.get(), mazeId);
            if (!optionDbMaze.isPresent()) {
                throw new RecordNotFoundException("No Maze record found for user ==> " + username);
            }
            Maze maze = optionDbMaze.get();
            ArrayList<String> dbWalls = new ArrayList<>();
            for (MazeWall dbMazeWall : maze.getMazeWalls()) {
                dbWalls.add(dbMazeWall.getWall());
            }
            String[] walls = new String[dbWalls.size()];
            walls = dbWalls.toArray(walls);
            MazeGridProcessor processor = new MazeGridProcessor(walls, maze.getGridSize(), maze.getEntrance());
            ArrayList<String> step = null;
            if (actualStepType.equals(StepType.min)) {
                step = processor.getMazeMinumumStep();
                if (step==null || step.size() < 2) {
                    throw new BadRequestException("Maze Grid has no possible exit path/point");
                }

            } else {
                step = processor.getMazeMaximumStep();
                if (step==null || step.size() < 2) {
                    throw new BadRequestException("Maze Grid has no possible exit path/point");
                }
            }
            solution = new MazeSolution(step);
        } catch (GenericMazeServiceException ex) {
            log.error("Error retrieving maze  " + ex.getMessage());
            throw new GenericMazeServiceException(ex.getMessage());
        }
        return solution;

    }

}
