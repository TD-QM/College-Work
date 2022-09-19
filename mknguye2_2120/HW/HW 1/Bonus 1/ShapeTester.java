public class ShapeTester{
	public static void main(String[] args){

        // Creation of multiple Point2D objects for the sake of testing
        Point2D p1 = new Point2D(0, 0); 
        Point2D p2 = new Point2D(0, 1); 
        Point2D p3 = new Point2D(1, 1); 
        Point2D p4 = new Point2D(1, 0); 

        Point2D p5 = new Point2D(3, 3);
        Point2D p6 = new Point2D(5, 3);
        
        Point2D p7 = new Point2D(2, 0);
        Point2D p8 = new Point2D(5, 0);

        Point2D p9 = new Point2D(0, 10);
        Point2D p10 = new Point2D(1, 10);

        Point2D p11 = new Point2D(2, 10);
        Point2D p12 = new Point2D(2, 0);

        Point2D p13 = new Point2D(2, 1);
        Point2D p14 = new Point2D(2, 2);
        Point2D p15 = new Point2D(1, 2);

        // Creation of multiple Shape Objects for the sake of testing
        Shape tri1 = new Triangle(p1, p2, p3); // Coordinates: (0,0), (0,1), (1,1)
        Shape tri2 = new Triangle(p2, p3, p4); // Coordinates: (0,1), (1,1), (1,0)
        Shape tri3 = new Triangle(p1, p5, p6); // Coordinates: (0,0), (3,3), (5,3)
        Shape tri4 = new Triangle(p1, p9, p10); // Coordinates: (0,0), (0,10), (1,10)
        Shape tri5 = new Triangle(p10, p11, p12); // Coordinates: (1,10), (2,10), (2,0)

        Shape rect1 = new Rectangle(p1, p2, p3, p4); // Coordinates: (0,0), (0,1), (1,1), (1,0)
        Shape rect2 = new Rectangle(p2, p3, p4, p1); // Coordinates: (0,1), (1,1), (1,0), (0,0)
        Shape rect3 = new Rectangle(p12, p13, p14, p15); // Coordinates: (2,0), (2,1), (2,2), (1,2)
        Shape rect4 = new Rectangle(p1, p2, p13, p7); // Coordinates: (0,0), (0,1), (2,1), (2,0)

        System.out.println(tri1);
        System.out.println("tri1 and tri1 are similar: \t" + tri1.equals(tri1) + "\n");

        System.out.println(tri1);
        System.out.println(tri2);
        System.out.println("tri1 and tri2 are similar: \t" + tri1.equals(tri2) + "\n");

        System.out.println(tri2);
        System.out.println(tri3);
        System.out.println("tri2 and tri3 are similar: \t" + tri2.equals(tri3) + "\n");

        System.out.println(tri4);
        System.out.println(tri5);
        System.out.println("tri4 and tri5 are similar: \t" + tri4.equals(tri5) + "\n");

        System.out.println(tri1);
        System.out.println(rect1);
        System.out.println("tri1 and rect1 are similar: \t" + tri1.equals(rect1) + "\n");

        System.out.println(rect1);
        System.out.println(rect1);
        System.out.println("rect1 and rect1 are similar: \t" + rect1.equals(rect1) + "\n");

        System.out.println(rect1);
        System.out.println(rect2);
        System.out.println("rect1 and rect2 are similar: \t" + rect1.equals(rect2) + "\n");
        
        System.out.println(rect1);
        System.out.println(rect3);
        System.out.println("rect1 and rect3 are similar: \t" + rect1.equals(rect3) + "\n");

        System.out.println(rect2);
        System.out.println(rect3);
        System.out.println("rect2 and rect3 are similar: \t" + rect2.equals(rect3) + "\n");

        System.out.println(rect1);
        System.out.println(rect4);
        System.out.println("rect1 and rect4 are similar: \t" + rect1.equals(rect4) + "\n");

        System.out.println(rect1);
        System.out.println(tri4);
        System.out.println("rect1 and tri4 are similar: \t" + rect1.equals(tri4) + "\n");


        
    }

}
