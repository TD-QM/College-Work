/**
 * The Circle class defines a circle on a 2d space.
 *
 * This class uses the Shape superclass, a Point2D object, and a floating point value representing the radius to create a circle.
 * 
 * @author Kristopher Nguyen
 */

public class Circle extends Shape{
	
	// Instance variables
	private double base;
	private double height;
    private double radius;

	/**
	 * Default constructor for Circle, calls the other one by giving it three Point2D objects with coordinates
	 * 	of x = 0 and y = 0.
	 * 
	 * @custom.ensure (super.getVertex(0).getX() == 0.0) && (super.getVertex(0).getY() == 0.0)
	 * @custom.ensure (super.getVertex(1).getX() == 0.0) && (super.getVertex(1).getY() == 0.0)
	 * @custom.ensure (super.getVertex(2).getX() == 0.0) && (super.getVertex(2).getY() == 0.0)
	 * @see #Constructor(Point2D, Point2D, Point2D)
	 * @see Point2D#Constructor(double, double)
	 */
	public Circle(){
		this(new Point2D(0, 0), 0);
	}

	/**
	 * Overloaded constructor taking a Point2D object and a floating point value as arguments for the center and radius of the circle respectively.
	 *
	 * @custom.ensure (base == calcBase())
	 * @custom.ensure (height == calcHeight())
     * @custom.ensure (radius == getRadius())
	 * @param p1	    A Point2D object containing the center
	 * @param radius    A floating point value representing the radius
	 * @see #calcBase()
	 * @see #calcHeight()
	 */
	public Circle(Point2D p1, double radius){
		// Invoking the superclass's constructor
		super(p1);

        this.radius = radius;

		// Calculating base and height
		base = calcBase();
		height = calcHeight();
	}


	/**
	 * Calculates the length of the base of the Circle by looking at the radius.
	 *
	 * @return The length of the base of the circle
	 */
	private double calcBase(){
		return radius*2;
	}
	
	/**
	 * Calculates the height of the Circle by looking at the radius.
	 * 
     * @return The length of the height of the circle
	 */
	private double calcHeight(){
        return radius*2;
	}

	/**
	 * @return Returns the base of the circle
	 */
	public double getBase(){
		return base;
	}

	/**
	 * @return Returns the height of the circle
	 */
	public double getHeight(){
		return height;
	}

	/**
     * @see Math#PI
	 * @return Returns the area of the circle
	 */
	@Override
	public double getArea(){
		return Math.PI * radius * radius;
	}

	/**
     * @see Math#PI
	 * @return Returns the perimeter of the circle
	 */
	@Override
	public double getPerimeter(){
		return 2 * Math.PI * radius;
	}

    /**
	 * @return Returns the radius of the circle
	 */
    public double getRadius(){
        return radius;
    }

	/**
	 * @return 	Returns the radius, area, and perimeter of the triangle in a String
	 */
	@Override
	public String toString(){
		return "Shape: Circle \nRadius: " + getRadius() + "\nArea: " + getArea() + "\nPerimeter: " + getPerimeter();
	}

}
