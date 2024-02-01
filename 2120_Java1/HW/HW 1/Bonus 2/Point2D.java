
/** class Point2D - models a point in 2 dimensions
    @author  Dr. Summa
*/
public class Point2D {

    private double[] coords;

    /** default constructor - this constructor calls the other one
        giving default x and y coordinates of 0.0 , 0.0
        @custom.ensure this.getX() == 0.0 && this.getY() == 0.0
    */
    public Point2D() {
        this(0.0,0.0);
    }

    /** overloaded constructor taking two doubles as arguments for the
     x and y coordinates
        @param  x  a double containing the initial x coordinate
        @param  y  a double containing the initial y coordinate
        @custom.ensure this.getX() == x && this.getY() == y
    */

    public Point2D(double x, double y) {
        coords = new double[2];
        coords[0] = x;
        coords[1] = y;
    }

    // getter or query methods to get the coordinates
    /**
      @return returns the current x coordinate value
    */
    public double getX() {
        return coords[0];
    }

    /**
      @return returns the current y coordinate value
    */
    public double getY() {
        return coords[1];
    }

    /**
      @return returns an array of double of size 2 where the first element is the x coordinate and the second element is the y coordinate
    */
    public double[] getCoords() {
        return coords;
    }

    /**
      @custom.ensure this.getX() == newX
    */
    public void setX(double newX) {
        coords[0] = newX;
    }

    /**
      @custom.ensure this.getY() == newY
    */
    public void setY(double newY) {
        coords[1] = newY;
    }

    /**
      @custom.require coords.length == 2
      @custom.ensure this.getX() == coords[0] && this.getY() == coords[1] 
    */
    public void setCoords(double[] coords) {
        this.coords = coords;
    }

    /**
      @custom.ensure this.getX() == x && this.getY() == y 
    */
    public void moveTo(double x, double y) {
        this.coords[0] = x;
        this.coords[1] = y;
    }

    /**
      @custom.ensure this.getX() == old.getX() + x && 
                     this.getY() == old.getY() + y 
    */
    public void moveBy(double dx, double dy) {
        this.coords[0] += dx;
        this.coords[1] += dy;
    }

    /** calculates this distance from the current point to another point,
       accessed by a reference passed in to this method.
       @param other a reference to another point
       @return this distance between this point and the argument point passed in
    */
    public double distance(Point2D other) {
        double dX = this.getX() - other.getX();
        double dY = this.getY() - other.getY();
        double dist = Math.sqrt( dX * dX + dY * dY );
        return dist;
    }

    /** calculates this distance from the current point to another point,
       accessed by a reference passed in to this method.
       @param o a reference to another point
       @return a boolean - true if the argument's coordinates are within the tolerance value of the current point
    */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Point2D) {
            Point2D other = (Point2D)o;
            if ( Math.abs (this.getY() - other.getY()) < 0.01 &&
                 Math.abs (this.getX() - other.getX()) < 0.01     )
                return true;
        }
        return false; // if the thing passed in is not a Point2D, how can they be equal?!?!?
        

    }

    @Override
    public String toString() {
        return "x: " + coords[0] + " y: " + coords[1];
    }


} // end class
