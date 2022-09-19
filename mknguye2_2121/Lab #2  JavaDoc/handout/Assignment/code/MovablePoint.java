/**
 * The MoveablePoint class utilizes the Movable interface to define a point on a 2D space. <br>
 * 
 * The point is able to be moved through the move methods located within the class. <br>
 * 
 * It intakes two integer values, which represent the x and y coordinates of the point.
 */

public class MovablePoint implements Movable {
    // Instance variables
    /**
     * Indicates the x value of the coordinate for the point
     */
    int x;

    /**
     * Indicates the y value of the coordinate for the point
     */
    int y;

    /**
     * Constructor for the MovablePoint class taking in 2 ints as parameters
     * 
     * @param x     The x coordinate for the point
     * @param y     The y coordinate for the point
     */
    public MovablePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Moves the point up by 1 value
     */
    @Override
    public void moveUp() {
        y += 1;
    }

    /**
     * Moves the point down by 1 value
     */
    @Override
    public void moveDown() {
        y -= 1;
    }

    /**
     * Moves the point to the left by 1 value
     */
    @Override
    public void moveLeft() {
        x -= 1;
    }

    /**
     * Moves the point to the right by 1 value
     */
    @Override
    public void moveRight() {
        x += 1;
    }

    /**
     * Overwritten toString method that gives the name of the class and coordinates of the point
     * 
     * @return The name of the class followed by the current coordinates of the point as a String
     */
    @Override
    public String toString() {
        return "MovablePoint [x=" + x + ", y=" + y + "]";
    }
}
