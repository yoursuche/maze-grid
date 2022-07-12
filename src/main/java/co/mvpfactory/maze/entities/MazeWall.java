package co.mvpfactory.maze.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MAZE_WALL", indexes = {
    @Index(name = "COP_MZ_WA_INDEX", columnList = "WALL,MAZE_ID")})
@Getter
@Setter
public class MazeWall extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "WALL", length = 5)
    private String wall;

    @ManyToOne(optional = false)
    @JoinColumn(name = "MAZE_ID", nullable = false)
    private Maze maze;

}
