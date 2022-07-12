package co.mvpfactory.maze.mazeprocessor;

/**
 *
 * @author UchechukwuOnuoha
 */
public class Coordinate {

    int x;
    int y;
    Coordinate parent;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
        this.parent = null;
    }

    public Coordinate(int x, int y, Coordinate parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinate getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (obj.getClass() != this.getClass())) {
            return false;
        }
        Coordinate point = (Coordinate) obj;
        if (y == point.y && x == point.x) {
            return true;
        }
        return false;
    }

}
