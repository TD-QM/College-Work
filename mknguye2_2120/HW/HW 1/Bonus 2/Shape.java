/**
 * The Shape class defines the vertices of a shape in a 2D space using the Point2D object.
 *
 * This class is only an abstract class for all shapes that exist within a 2D space. 
 * The number of points and the location of said points are left to the subclasses.
 * 
 * @author 	Kristopher Nguyen
 */
public abstract class Shape { 

	// Instance variables
	/** Point2D array for storing the vertices of a shape */
	protected Point2D[] vertices; 
	
	/**
	 * Constructs a Shape object that stores a varying number of Point2D objects into an array. 
	 *
	 * @param points	A varying number of Point2D values that are put into the array verticies.
	 */
	public Shape(Point2D ... points){
		vertices = new Point2D[points.length];
		for (int i = 0; i < points.length; i++){
			vertices[i] = points[i];
		}

	}
	
	/**
	 * Returns the Point2D object at a specified index
	 * 
	 * @custom.require (index < vertices.length) && (index >= vertices.length)
	 * @param index 	The index of a point on the shape
	 * @return 		The Point2D object at the specified index
	 */
	public Point2D getVertex(int index){
		return vertices[index];	
	}

	/**
	 * Returns the height of the shape.
	 * 
	 * @return		The base of the shape in a double
	 */
	public abstract double getBase();

	/**
	 * Returns the height of the shape.
	 * 
	 * @return		The height of the shape in a double
	 */
	public abstract double getHeight();

	/**
	 * Returns the area of the shape
	 *
	 * @return 		The area of the shape in a double
	 */
	public abstract double getArea();

	/**
	 * Returns the perimeter of the shape
	 *
	 * @return 		The perimeter of the shape in a double
	 */
	public abstract double getPerimeter();

	/**
	 * Returns the base, height, area, and perimeter of the shape.
	 * 
	 * @return 		The base, height, area, and perimeter of the shape in a String
	 */
	@Override
	public abstract String toString();
	
}
