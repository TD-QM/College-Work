/**
 * The Moveability of a class is enabled by implementing the Moveable interface. <br>
 * 
 * The Moveable interface is used to build moveable shapes and points in a 2D space and to move them in that space. <br>
 * 
 * How the methods are implemented is up to the class that implements it. <br>
 */
public interface Movable {
    /**
     * This interface method moves shape/point up.
     */
    void moveUp();

    /**
     * This interface method moves the shape/point down.
     */
    void moveDown();

    /**
     * This interface method moves the shape/point to the left.
     */
    void moveLeft();

    /**
     * This interface method moves the shape/point to the right.
     */
    void moveRight();
}
