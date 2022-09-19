/**
 * The Rectangle class defines a rectangle on a 2d space.
 *
 * This class uses the Shape superclass and four Point2D objects to create a reactangle.
 * 
 * @author Kristopher Nguyen
 */
public class Rectangle extends Shape{

    // Instance variables
    private double base;
	private double height;
	private double length1;
	private double length2;
	private double length3;
    private double length4;

	/**
	 * Default constructor for Rectangle, calls the other one by giving it four Point2D objects with coordinates
	 * 	of x = 0 and y = 0.
	 * 
	 * @custom.ensure (super.getVertex(0).getX() == 0.0) && (super.getVertex(0).getY() == 0.0)
	 * @custom.ensure (super.getVertex(1).getX() == 0.0) && (super.getVertex(1).getY() == 0.0)
	 * @custom.ensure (super.getVertex(2).getX() == 0.0) && (super.getVertex(2).getY() == 0.0)
     * @custom.ensure (super.getVertex(3).getX() == 0.0) && (super.getVertex(3).getY() == 0.0)
     * @custom.ensure (base == calcBase())
	 * @custom.ensure (height == calcHeight())
     * @see #Constructor(Point2D, Point2D, Point2D, Point2D)
     * @see Point2D#Constructor(double, double)
	 */
	public Rectangle(){
		this(new Point2D(0, 0), new Point2D(0,0), new Point2D(0,0), new Point2D(0,0));
	}

	/**
	 * Overloaded constructor taking three Point2D objects as arguments for the points of the rectangle.
	 *
	 * @custom.ensure (p1.getY() - p2.getY() < .001) || (p2.getY() - p3.getY() < .001) || (p3.getY() - p4.getY() < .001) || (p4.getY() - p1.getY() < .001)
	 * @custom.ensure (base == calcBase())
	 * @custom.ensure (height == calcHeight())
	 * @param p1	A Point2D object containing the first point
	 * @param p2    A Point2D object containing the second point
	 * @param p3    A Point2D object containing the third point
     * @param p4    A Point2D object containing the fourth point
	 * @see #calcBase()
	 * @see #calcHeight()
     * @see Math#abs(double)
     * @see Shape#getVertex(int)
     * @see Point2D#distance(Point2D)
	 */
	public Rectangle(Point2D p1, Point2D p2, Point2D p3, Point2D p4){
		super(p1, p2, p3, p4);
		this.length1 = Math.abs(super.getVertex(0).distance(super.getVertex(1)));
		this.length2 = Math.abs(super.getVertex(1).distance(super.getVertex(2)));
		this.length3 = Math.abs(super.getVertex(2).distance(super.getVertex(3)));
        this.length4 = Math.abs(super.getVertex(3).distance(super.getVertex(0)));
		base = calcBase();
		height = calcHeight();
	}

	/**
	 * Calculates the length of the base of the Rectangle and stores it into the base instance variable.
	 *
     * @custom.ensure (this.base == length1) || (this.base == length2) || (this.base == length3) || (this.base == length4)
	 * @see Math#abs(double)
     * @see Shape#getVertex(int)
	 * @see Point2D#getY()
	 */
	private double calcBase(){
		if(Math.abs(super.getVertex(0).getY() - super.getVertex(1).getY()) < .001){
			return length1;
		} else if (Math.abs(super.getVertex(1).getY() - super.getVertex(2).getY()) < .001){
			return length2;
		} else if (Math.abs(super.getVertex(2).getY() - super.getVertex(3).getY()) < .001){
			return length3;
		} else if (Math.abs(super.getVertex(3).getY() - super.getVertex(0).getY()) < .001){
			return length4;
		} else {
            return 0;
        }
	}
	
	/**
	 * Calculates the height of the Rectangle by checking the points and seeing if any have similar x coordinates 
	 * 
     * @see Math#abs
     * @see Shape#getVertex(int)
     * @see Point2D#getX()
     * @return Height of the Rectangle
     * @custom.ensure (calcHeight() == length1) || (calcHeight() == length2) || (calcHeight() == length3) || (calcHeight() == length4)
	 */
	private double calcHeight(){
		if(Math.abs(super.getVertex(0).getX() - super.getVertex(1).getX()) <= .001){
            return length1;
        } else if(Math.abs(super.getVertex(1).getX() - super.getVertex(2).getX()) <= .001){
            return length2;
        } else if(Math.abs(super.getVertex(2).getX() - super.getVertex(3).getX()) <= .001){
            return length3;
        } else if(Math.abs(super.getVertex(3).getX() - super.getVertex(0).getX()) <= .001){
            return length4;
        } else {
            return 0;
        }
	}

	/**
	 * @return Returns the base of the rectangle
	 */
    @Override
	public double getBase(){
		return base;
	}

	/**
	 * @return Returns the height of the rectangle
	 */
    @Override
	public double getHeight(){
		return height;
	}

	/**
	 * @return Returns the area of the rectangle
	 */
	@Override
	public double getArea(){
		return base * height;
	}

	/**
	 * @return Returns the perimeter of the rectangle
     * @custom.ensure (getPerimeter == (length1 + length2 + length3 + length4))
	 */
	@Override
	public double getPerimeter(){
		return length1 + length2 + length3 + length4;
	}

	/**
	 * @return 	Returns the base, height, area, and perimeter of the rectangle in a String
	 */
	@Override
	public String toString(){
		return "Shape: Rectangle \nBase: " + getBase() + "\nHeight: " + getHeight() + "\nArea: " + getArea() + "\nPerimeter: " + getPerimeter();
	}
}
