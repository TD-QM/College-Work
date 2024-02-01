/**
 * The MoveableRectangle Class utilizes the Moveable interface to define a rectangle on a 2D space. <br> 
 * 
 * The shape is able to be moved across by using the mvoe methods located within the class. <br>
 * 
 * This class has a single constructor that takes four int arguments, those being the coordinates of the top left and bottom right point.
 */

public class MovableRectangle implements Movable {
    // Instance variables
    /**
     * MovablePoint object representing the top left point in the rectangle
     */
    private final MovablePoint topLeft;

    /**
     * MovablePoint object representing the bottom right point in the rectangle
     */
    private final MovablePoint bottomRight;

    /**
     * Consctructor for the MovableRectangle class taking in four parameters
     * 
     * @param x1    The x coordinate of the top left point
     * @param y1    the y coordinate of the top left point
     * @param x2    the x coordinate of the bottom right point
     * @param y2    the y coordinate of the bottom right point
     * 
     * @see MovablePoint#MovablePoint(int, int)
     */
    public MovableRectangle(int x1, int y1, int x2, int y2) {
        this.topLeft = new MovablePoint(x1, y1);
        this.bottomRight = new MovablePoint(x2, y2);
    }

    /**
     * Moves the rectangle up by 1 value
     * 
     * @see MovablePoint#moveUp()
     */
    @Override
    public void moveUp() {
        topLeft.moveUp();
        bottomRight.moveUp();
    }

    /**
     * Moves the rectangle down by 1 value
     * 
     * @see MovablePoint#moveDown()
     */
    @Override
    public void moveDown() {
        topLeft.moveDown();
        bottomRight.moveDown();
    }

    /**
     * Moves the rectangle to the left by 1 value
     * 
     * @see MovablePoint#moveLeft()
     */
    @Override
    public void moveLeft() {
        topLeft.moveLeft();
        bottomRight.moveLeft();
    }

    /**
     * Moves the rectangle to the right by 1 value
     * 
     * @see MovablePoint#moveRight()
     */
    @Override
    public void moveRight() {
        topLeft.moveRight();
        bottomRight.moveRight();
    }

    /**
     * Overwritten toString method that returns the name of the class as well as the coordinates of the top left and bottom right points
     * 
     * @return The name of the class and the coordinates of the top left and bottom right points as a String
     */
    @Override
    public String toString() {
        return "MovableRectangle [topLeft=" + topLeft + ", bottomRight=" + bottomRight + "]";
    }
}
