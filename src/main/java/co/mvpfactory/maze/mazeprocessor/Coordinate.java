/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

}
