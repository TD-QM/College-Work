public class Testing {
    public static void main(String[] args){

        Deck test = new Deck();
        System.out.println(test.toString());

        test.shuffle();
        System.out.println("\n" + test.toString());

        System.out.println("\n Drawn: " + test.draw());
        System.out.println("\n" + test.toString());

    }
}
