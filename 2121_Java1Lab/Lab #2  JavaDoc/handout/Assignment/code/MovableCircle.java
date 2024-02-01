/**
 * The Moveable Circle class utilizes the Movable interface to define the center and radius of a circle in a 2D space. <br>
 * 
 * The shape is able to be moved by using the move methods located within the class. <br>
 * 
 * This class has a single constructor which intakes three integers, two represening the coordinates of the center and one representing the radius.
 */

public class MovableCircle implements Movable {
    // Instance variables
    /**
     * The radius of the circle
     */
    private final int radius;

    /**
     * The center of the circle, represented by a MoveablePoint object
     */
    private final MovablePoint center;

    /**
     * Constructor for the MovablePoint class taking three parameters
     * 
     * @param x         Indicates the x value of the coordinate for the point
     * @param y         Indicates the y value of the coordinate for the point
     * @param radius    Indicates the radius of the circle
     * 
     * @see MovablePoint#MovablePoint(int, int)
     */
    public MovableCircle(int x, int y, int radius) {
        this.radius = radius;
        this.center = new MovablePoint(x, y);
    }

    /**
     * Moves the center of the circle up by 1 value
     * 
     * @see MovablePoint#moveUp()
     */
    @Override
    public void moveUp() {
        center.moveUp();
    }

    /**
     * Moves the center of the circle down by 1 value
     * 
     * @see MovablePoint#moveDown()
     */
    @Override
    public void moveDown() {
        center.moveDown();
    }

    /**
     * Moves the center of the circle to the left by 1 value
     * 
     * @see MovablePoint#moveLeft()
     */
    @Override
    public void moveLeft() {
        center.moveLeft();
    }

    /**
     * Moves the center of the circle to the right by 1 value
     * 
     * @see MovablePoint#moveRight()
     */
    @Override
    public void moveRight() {
        center.moveRight();
    }

    /**
     * Overwritten toString method that returns the name of the class, the radius, and the coordinates of the center
     * 
     * @return The name of the class, the radius of the circle, and the coordinates of the center as a String
     */
    @Override
    public String toString() {
        return "MovableCircle [radius=" + radius + ", center=" + center + "]";
    }

}
