package co.mvpfactory.maze.entities;

import co.mvpfactory.maze.enums.StepType;
import java.util.Set;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MAZE_SOLUTION", indexes = {
    @Index(name = "COP_MZ_SO_INDEX", columnList = "POINT,STEP_TYPE,MAZE_ID")})
@Getter
@Setter
public class MazeSolution extends BaseEntity {

    private static final long serialVersionUID = 1L;
    @Column(name = "POINT", length = 5)
    private String point;

    @Enumerated(EnumType.STRING)
    @Column(name = "STEP_TYPE", nullable = false)
    private StepType stepType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "MAZE_ID", nullable = false)
    private Maze maze;

}
