public class ShapeTester{
	public static void main(String[] args){

        // Creation of multiple Point2D objects for the sake of testing
        Point2D p1 = new Point2D(0, 0); 
        Point2D p2 = new Point2D(0, 1); 
        Point2D p3 = new Point2D(1, 1); 


        // Creation of multiple Shape Objects for the sake of testing
        Shape circ1 = new Circle();
        Shape circ2 = new Circle(p1, 1);
        Shape circ3 = new Circle(p1, 3);
        Shape circ4 = new Circle(p2, 2);
        Shape circ5 = new Circle(p3, 4);

        // Creation of a Shape array
        Shape[] shapeArr = new Shape[5];

        // Adding to shapeArr
        shapeArr[0] = circ1;
        shapeArr[1] = circ2;
        shapeArr[2] = circ3;
        shapeArr[3] = circ4;
        shapeArr[4] = circ5;

        int circNum = 0;
        // Looping through each Shape in shapeArr, testing the public methods 
        for (Shape testShape : shapeArr) {
            System.out.println("- Circle " + circNum + " -");
            System.out.println(testShape + "\n");
            circNum++;
        }
    }

}
