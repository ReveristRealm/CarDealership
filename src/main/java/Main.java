import Models.DealerShip;
import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.*;
import java.util.Scanner;

public class Main {
    static DealerShip dealerShip;
    static Connection connection = null;
    static ResultSet results = null;
    static PreparedStatement statement = null;
    static BasicDataSource basicDataSource;
    public static void main(String[] args)
    {
        Scanner userInput = new Scanner(System.in);
        String username = args[0];
        String password = args[1];

        /*
        Connection connection = null;
        ResultSet results = null;
        PreparedStatement statement = null;
        */

        basicDataSource  = new BasicDataSource();
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/dealership");
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);

        VehicleDAO data = new VehicleDAO(basicDataSource);
        try{
            connection = data.getMyDataSource().getConnection();

            String query = "SELECT * FROM dealership;";

            statement = connection.prepareStatement(query);

            results = statement.executeQuery();

            while(results.next()){
                int id = results.getInt("dealership_id");
                String name = results.getString("namee");
                String address = results.getString("address");
                String phone = results.getString("phone");

                System.out.println("Dealership id: " + id);
                System.out.println("Dealership name: " + name);
                System.out.println("Dealership address: " + address);
                System.out.println("Dealership phone: " + phone);
                System.out.println("================================");
            }
            System.out.println("Select by the ID which dealership you want");

            String response = userInput.next();

            String query2 = "SELECT * FROM dealership WHERE dealership_id = ?;";

            statement = connection.prepareStatement(query2);

            statement.setString(1,response);

            results = statement.executeQuery();

            dealerShip = new DealerShip(results.getString("dealership_id"),results.getString("namee"), results.getString("address"), results.getString("phone"));

            loadDealership(dealerShip);

        }catch(Exception e){
            System.out.println();
        }finally{
            if (statement != null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (results != null ){
                try {
                    results.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void loadDealership(DealerShip dealerShip){
        try{
            String query = "SELECT * FROM inventory LEFT JOIN vehicles ON vehicles.vin = inventory.vin WHERE dealership_id = ?;";
            String id = dealerShip.getDealership_id();
            statement = connection.prepareStatement(query);
            statement.setString(1,id);
            results = statement.executeQuery();
            /*
            while(results.next()){
                String id = results.getString("");
            }
             */
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{

        }

    }
}

