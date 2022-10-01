import java.util.InputMismatchException;
import java.util.Scanner;

public class ExceptionExample{
    public static int quotient(int numerator, int denominator) throws ArithmeticException{
        return numerator/denominator;
    }

    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        boolean counter = true;

        do{
            try {

                System.out.print("Enter the integer numerator: ");
                int numerator = input.nextInt();

                System.out.print("\nEnter the integer denominator: ");
                int denominator = input.nextInt();

                int result = quotient(numerator, denominator);

                System.out.printf("\nResult: %d / %d = %d\n", numerator, denominator, result);

            } catch (InputMismatchException errorMessageInputMismatch) {
                System.err.printf("\nException: %s\n", errorMessageInputMismatch);

                input.nextLine();

                System.out.println("You must input integer values, please try again");

            } catch (ArithmeticException errorMessageArithmeticException){

                System.err.printf("\nException: %s\n", errorMessageArithmeticException);

                input.nextLine();

                System.out.println("Zero is an invalid deno minator, please try again");
            }
        } while (counter);
    }
}