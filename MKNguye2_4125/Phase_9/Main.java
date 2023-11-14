import java.sql.*;
import java.lang.Math.*;

public class Main{
    public static void main(String[] args){
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:Oracle21c", "MKNguye2", "TheFrenchiestFry678"); //Change these to your user/pass

            String invoiceString = "SELECT P_Name, L_Quantity, P_Price FROM Orders, LineItem, Product WHERE ? = O_Cust AND O_Number = L_Order AND L_Product = P_ID AND O_Date = ?";
            PreparedStatement invoiceStatement = conn.prepareStatement(invoiceString);
            invoiceStatement.setString( 1, args[0] );
            invoiceStatement.setString( 2, args[1] );
            ResultSet invoiceResults = invoiceStatement.executeQuery();

            String customerName = "SELECT C_Name FROM Customer WHERE C_ID = ?";
            PreparedStatement pStateName = conn.prepareStatement(customerName);
            pStateName.setString(1, args[0]);
            ResultSet customerNameResult = pStateName.executeQuery();

            if(invoiceResults.next()){
                invoiceString = "SELECT P_Name, L_Quantity, P_Price FROM Orders, LineItem, Product WHERE ? = O_Cust AND O_Number = L_Order AND L_Product = P_ID AND O_Date = ?";
                invoiceStatement = conn.prepareStatement(invoiceString);
                invoiceStatement.setString( 1, args[0] );
                invoiceStatement.setString( 2, args[1] );
                invoiceResults = invoiceStatement.executeQuery();

                customerNameResult.next();

                System.out.println(args[1] + " invoice for " + customerNameResult.getString(1) + "\n");
                System.out.println("Product" + " ".repeat(24) + " Quantity  Price/Unit     Total");
                System.out.println("-".repeat(63));

                double orderTotal = 0;
                while (invoiceResults.next()){
                    String productName = invoiceResults.getString(1);
                    int productDigits = productName.length();
                    System.out.print( productName + " ".repeat(31-productDigits) );

                    int quantity = invoiceResults.getInt(2);
                    int quantityDigits = (int) Math.log10( quantity ) + 1;
                    System.out.print( " ".repeat( 8 - quantityDigits+1) + quantity + "  ");

                    double productPrice = invoiceResults.getDouble(3);
                    int priceDigits = (int) Math.log10( productPrice ) + 4;
                    System.out.print( "$" + " ".repeat(8 - priceDigits) + String.format("%.2f", productPrice) + "  ");

                    double productTotal = productPrice*quantity;
                    int totalDigits = (int) Math.log10( productTotal ) + 4;
                    System.out.println("$" + " ".repeat(8 - totalDigits) + String.format("%.2f", productTotal));

                    orderTotal += productTotal;
                }

                double shippingCost = 0;
                double totalDue = orderTotal + shippingCost;

                int orderTotalDigits = (int) Math.log10(orderTotal) + 4;
                int shippingDigits = 4;
                int totalDigits = (int) Math.log10(totalDue) + 4;

                if (orderTotal < 35){
                    shippingCost = 10;
                    shippingDigits = 5;
                }

                System.out.println();
                System.out.println("Total Product Cost :" + " ".repeat(8-orderTotalDigits) + "$" + String.format("%.2f", orderTotal));
                System.out.println("Shipping Cost      :" + " ".repeat(8-shippingDigits) + "$" + String.format("%.2f", shippingCost));
                System.out.println("Total Due          :" + " ".repeat(8-totalDigits) + "$" + String.format("%.2f", (orderTotal+shippingCost)));
            } else {
                
                
            }

            conn.close();

        } catch(Exception e){
            System.out.println(e);
        }
    }
}