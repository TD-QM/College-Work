import java.sql.*;

public class Main{
    public static void main(String[] args){
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:Oracle21c", "MKNguye2", "TheFrenchiestFry678"); //Change these to your user/pass

            Statement state = conn.createStatement();

            ResultSet result = state.executeQuery("SELECT * FROM Product");

            System.out.println("Submission for James Wagner:");
            while(result.next())
                System.out.println(result.getInt(1) + " " +
                result.getString(2) + " " + result.getString(3));
                conn.close();
            }
        catch(Exception e){
            System.out.println(e);
        }
    }
}