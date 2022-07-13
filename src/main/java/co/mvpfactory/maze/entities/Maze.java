package co.mvpfactory.maze.entities;

import java.util.Set;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MAZE", indexes = {
    @Index(name = "COP_MZ_INDEX", columnList = "ENTRANCE")})
@Getter
@Setter
public class Maze extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "ENTRANCE", length = 5)
    private String entrance;

    @Column(name = "GRID_SIZE", length = 15)
    private String gridSize;

    @ManyToOne(optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    private AppUser user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "maze", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MazeWall> mazeWalls;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "maze", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MazeSolution> mazeSolutions;

}
