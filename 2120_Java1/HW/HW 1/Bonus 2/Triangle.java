/**
 * The Triangle class defines a triangle on a 2d space.
 *
 * This class uses the Shape superclass and three Point2D objects to create a triangle.
 * 
 * @author Kristopher Nguyen
 */

public class Triangle extends Shape{
	
	// Instance variables
	private double base;
	private double height;
	private double[] length = new double[3];

	/**
	 * Default constructor for Triangle, calls the other one by giving it three Point2D objects with coordinates
	 * 	of x = 0 and y = 0.
	 * 
	 * @custom.ensure (super.getVertex(0).getX() == 0.0) && (super.getVertex(0).getY() == 0.0)
	 * @custom.ensure (super.getVertex(1).getX() == 0.0) && (super.getVertex(1).getY() == 0.0)
	 * @custom.ensure (super.getVertex(2).getX() == 0.0) && (super.getVertex(2).getY() == 0.0)
	 * @see #Constructor(Point2D, Point2D, Point2D)
	 * @see Point2D#Constructor(double, double)
	 */
	public Triangle(){
		this(new Point2D(0, 0), new Point2D(0,0), new Point2D(0,0));
	}

	/**
	 * Overloaded constructor taking three Point2D objects as arguments for the points of the triangle.
	 *
	 * @custom.ensure (Math.abs(p1.getY() - p2.getY()) < .001) || (Math.abs(p2.getY() - p3.getY()) < .001) || (Math.abs(p3.getY() - p1.getY()) < .001)
	 * @custom.ensure (base == calcBase())
	 * @custom.ensure (height == calcHeight())
	 * @param p1	A Point2D object containing the first point
	 * @param p2    A Point2D object containing the second point
	 * @param p3    A Point2D object containing the third point
	 * @see #calcBase()
	 * @see #calcHeight()
     * @see Math#abs(double)
     * @see Shape#getVertex(int)
     * @see Point2D#distance(Point2D)
	 */
	public Triangle(Point2D p1, Point2D p2, Point2D p3){
		// Invoking the superclass's constructor
		super(p1, p2, p3);

		// Adding the length between each of the points
		this.length[0] = Math.abs(super.getVertex(0).distance(super.getVertex(1)));
		this.length[1] = Math.abs(super.getVertex(1).distance(super.getVertex(2)));
		this.length[2] = Math.abs(super.getVertex(2).distance(super.getVertex(0)));

		// Calculating base and height
		base = calcBase();
		height = calcHeight();
	}


	/**
	 * Calculates the length of the base of the Triangle and stores it into the base instance variable.
	 *
	 * @see Math#abs(double)
	 * @see Shape#getVertex(int)
	 * @see Point2D#getY()
	 * @return The length of the base of the triangle
	 */
	private double calcBase(){
		// Check to see if any of the Y values are similar. If they are, make the distance between those two points the base
		if(Math.abs(super.getVertex(0).getY() - super.getVertex(1).getY()) < .001){
			return length[0];
		} else if (Math.abs(super.getVertex(1).getY() - super.getVertex(2).getY()) < .001){
			return length[1];
		} else if (Math.abs(super.getVertex(2).getY() - super.getVertex(0).getY()) < .001){
			return length[2];
		} else {
			return 0;
		}

	}
	
	/**
	 * Calculates the height of the Triangle by looking at the highest and lowest point of the triangle
	 * 
	 * @see Shape#getVertex(int)
	 * @see Point2D#getY()
	 * @return The difference between the highest and lowest Y values
	 * @custom.ensure (calcHeight >= 0)
	 */
	private double calcHeight(){

		// Make variables for the highest and lowest Y values
		double highest = super.getVertex(0).getY();
		double lowest = super.getVertex(1).getY();

		// For loop to search for the highest Y value
		for(int i = 0; i < 3; i++){
			if (highest < super.getVertex(i).getY()){
				highest = super.getVertex(i).getY();
			}
		}

		// For loop to search for the lowest Y value
		for(int i = 0; i < 3; i++){
			if (lowest > super.getVertex(i).getY()){
				lowest = super.getVertex(i).getY();
			}
		}

		// Return the difference between the two
		return highest - lowest;
		
	}

	/**
	 * @return Returns the base of the triangle
	 */
	public double getBase(){
		return base;
	}

	/**
	 * @return Returns the height of the triangle
	 */
	public double getHeight(){
		return height;
	}

	/**
	 * @return Returns the area of the triangle
	 */
	@Override
	public double getArea(){
		return .5 * base * height;
	}

	/**
	 * @return Returns the perimeter of the triangle
	 */
	@Override
	public double getPerimeter(){
		return length[0] + length[1] + length[2];
	}

	/**
	 * @return 	Returns the base, height, area, and perimeter of the triangle in a String
	 */
	@Override
	public String toString(){
		return "Shape: Triangle \nBase: " + getBase() + "\nHeight: " + getHeight() + "\nArea: " + getArea() + "\nPerimeter: " + getPerimeter();
	}

}
