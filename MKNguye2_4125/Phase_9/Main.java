import java.sql.*;
import java.lang.Math.*;

public class Main{
    public static void main(String[] args){
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:Oracle21c", "MKNguye2", "TheFrenchiestFry678"); //Change these to your user/pass

            String invoiceString = "SELECT L_Quantity, P_Name, P_Price FROM Orders, LineItem, Product WHERE ? = O_Cust AND O_Number = L_Order AND L_Product = P_ID AND O_Date = ?";

            PreparedStatement invoiceStatement = conn.prepareStatement(invoiceString);

            invoiceStatement.setString( 1, args[0] );
            invoiceStatement.setString( 2, args[1] );

            ResultSet invoiceResults = invoiceStatement.executeQuery();

            System.out.println("Product" + " ".repeat(24) + " Quantity  Price/Unit       Total");
            System.out.println("-".repeat(66));

            double productTotal = 0;
            while (invoiceResults.next()){
                String productName = invoiceResults.getString(1);
                int productDigits = productName.length();
                System.out.print( productName + " ".repeat(31-productDigits) );

                int quantity = invoiceResults.getInt(2);
                int quantityDigits = (int) Math.log10( quantity );
                System.out.print( " ".repeat( 9 - quantityDigits+1) + quantity + "  ");

                double productPrice = invoiceResults.getDouble(3);
                int priceDigits = (int) Math.log10( productPrice ) + 3;
                System.out.print( "$" + " ".repeat(9 - priceDigits) + productPrice + "  ");

                productTotal = productPrice*quantity;
                int totalDigits = (int) Math.log10( productTotal ) + 3;
                System.out.println("$" + " ".repeat(9 - totalDigits) + productTotal);

            }

            double shippingCost = 0;

            if (productTotal < 35){
                shippingCost = 10;
            }

            System.out.println();
            System.out.println("Total Product Cost: " + productTotal);
            System.out.println("Shipping Cost:      " + shippingCost);
            System.out.println("Total Due:          " + (productTotal+shippingCost));

            conn.close();

        } catch(Exception e){
            System.out.println(e);
        }
    }
}