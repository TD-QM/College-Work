import java.math.BigInteger;

import javax.security.auth.Subject;

public class FibonacciCalculator{

  public static BigInteger fib(BigInteger num){

    BigInteger two = BigInteger.valueOf(2);

    if (num.equals(BigInteger.ZERO) || num.equals(BigInteger.ONE)){
      return num;
    } else {  
      return fib(num.subtract(BigInteger.ONE)).add(fib(num.subtract(two)));
    }
  }


  public static void main(String[] args){
    for(int i = 0; i <= 15; i++){
      System.out.printf("Fibonacci of %d id: %d\n", i, fib(BigInteger.valueOf(i)));
    }
  }

}