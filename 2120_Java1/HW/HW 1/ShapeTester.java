public class ShapeTester{
	public static void main(String[] args){

        // Creation of multiple Point2D objects for the sake of testing
        Point2D p1 = new Point2D(0, 0); 
        Point2D p2 = new Point2D(0, 1); 
        Point2D p3 = new Point2D(1, 1); 
        Point2D p4 = new Point2D(1, 0); 

        Point2D p5 = new Point2D(3, 3);
        Point2D p6 = new Point2D(5, 3);
        
        Point2D p7 = new Point2D(0,3);
        Point2D p8 = new Point2D(5,0);

        Point2D p9 = new Point2D(0,10);
        Point2D p10 = new Point2D(1,10);

        // Creation of multiple Shape Objects for the sake of testing
        Shape tri1 = new Triangle(p1, p2, p3); // Coordinates: (0,0), (0,1), (1,1)
        Shape tri2 = new Triangle(p2, p3, p1); // Coordinates: (0,1), (1,1), (0,0)
        Shape tri3 = new Triangle(p1, p5, p6); // Coordinates: (0,0), (3,3), (5,3)
        Shape tri4 = new Triangle(p1, p6, p8); // Coordinates: (0,0), (5,3), (5,0)
        Shape tri5 = new Triangle(p8, p1, p6); // Coordinates: (5,0), (0,0), (5,3)

        Shape rect1 = new Rectangle(p1, p2, p3, p4); // Coordinates: (0,0), (0,1), (1,1), (1,0)
        Shape rect2 = new Rectangle(p2, p3, p4, p1); // Coordinates: (0,1), (1,1), (1,0), (0,0)
        Shape rect3 = new Rectangle(p1, p7, p6, p8); // Coordinates: (0,0), (0,3), (5,3), (5,0)
        Shape rect4 = new Rectangle(p1, p9, p10, p4); // Coordinates: (0,0), (0,10), (1,10), (1,0)
        Shape rect5 = new Rectangle(p10, p4, p1, p9); // Coordinates: (1,10), (1,0), (0,0), (0,10)

        // Creation of a Shape array
        Shape[] shapeArr = new Shape[10];

        // Adding to shapeArr
        shapeArr[0] = tri1;
        shapeArr[1] = tri2;
        shapeArr[2] = tri3;
        shapeArr[3] = tri4;
        shapeArr[4] = tri5;
        shapeArr[5] = rect1;
        shapeArr[6] = rect2;
        shapeArr[7] = rect3;
        shapeArr[8] = rect4;
        shapeArr[9] = rect5;

        int triNum = 0;
        int recNum = 0;
        // Looping through each Shape in shapeArr, testing the public methods 
        for (Shape testShape : shapeArr) {
            if (testShape instanceof Triangle){
                triNum++;
                System.out.println("- Triangle " + triNum + " -");
                System.out.println(testShape);
            } else if (testShape instanceof Rectangle){
                recNum++;
                System.out.println("- Rectangle " + recNum + " -");
                System.out.println(testShape);
            }
            System.out.println();
        }
    }

}
