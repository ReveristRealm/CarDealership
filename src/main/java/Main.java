import Models.DealerShip;
import Models.Vehicles;
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

            int response = userInput.nextInt();

            String query2 = "SELECT * FROM dealership WHERE dealership_id = ?;";

            statement = connection.prepareStatement(query2);

            statement.setInt(1,response);

            results = statement.executeQuery();

            while(results.next()) {
                dealerShip = new DealerShip(results.getString("dealership_id"), results.getString("namee"), results.getString("address"), results.getString("phone"));
                System.out.println(dealerShip);
            }
            loadDealership();
            boolean truee = true;
        while(truee) {
            System.out.println("Select what option you would like ?");
            System.out.println("1) By price range?");
            System.out.println("2) By make/model");
            System.out.println("3) By year range");
            System.out.println("4) By color");
            System.out.println("5) By mileage range");
            System.out.println("6) Exit");

            int option = userInput.nextInt();

            switch (option) {
                case 1:
                    System.out.println("What is the starting number?");
                    int start = userInput.nextInt();
                    System.out.println("What is the ending number");
                    int end = userInput.nextInt();
                    SearchByPriceRange(start,end);
                    break;
                case 2:
                    System.out.println("What is the make of the car");
                    String make = userInput.next();
                    System.out.println("What is the model of the car");
                    String model = userInput.next();
                    SearchByMakeModel(make,model);
                    break;
                case 3:
                    System.out.println("What is the starting year?");
                    int start2 = userInput.nextInt();
                    System.out.println("What is the ending year");
                    int end2 = userInput.nextInt();
                    SearchByYearRange(start2,end2);
                    break;
                case 4:
                    System.out.println("What is the color you want?");
                    String color = userInput.next();
                    SearchByColor(color);
                    break;
                case 5:
                    System.out.println("What is starting mileage");
                    int smile = userInput.nextInt();
                    System.out.println("What is ending mileage");
                    int emile = userInput.nextInt();
                    SearchByYMileageRange(smile,emile);
                    break;
                case 6:
                    truee = false;
                    break;
                default:
                    System.out.println("Wrong input, Try again");
            }
        }
        }catch(Exception e){
            System.out.println();
        }
    }

    public static void loadDealership(){
        try{
            String query = "SELECT * FROM inventory LEFT JOIN vehicles ON vehicles.vin = inventory.vin WHERE dealership_id = ?;";
            String id = dealerShip.getDealership_id();
            statement = connection.prepareStatement(query);
            statement.setString(1,id);
            results = statement.executeQuery();

            while(results.next()){
                String vin = results.getString("vin");
                int year = results.getInt("yearr");
                String make = results.getString("make");
                String model = results.getString("model");
                String color = results.getString("color");
                int odometer = results.getInt("odometer");
                double price = results.getDouble("price");

                Vehicles car = new Vehicles(vin,year,make,model,color,odometer,price);
                dealerShip.addVehicle(car);
                System.out.println(car);
                System.out.println("===========================");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static void SearchByPriceRange(int start, int end) {
       for (Vehicles vehicles: dealerShip.getInventory()){
           if (vehicles.getPrice() > start && vehicles.getPrice() < end) {
               System.out.println(vehicles);
               System.out.println("==============");
           }
       }
    }
    public static void SearchByMakeModel(String make, String model){
        for (Vehicles vehicles: dealerShip.getInventory()){
            if (vehicles.getMake().equalsIgnoreCase(make) && vehicles.getModel().equalsIgnoreCase(model)){
                System.out.println(vehicles);
                System.out.println("==============");
            }
        }
    }
    public static void SearchByYearRange(int start, int end){
        for (Vehicles vehicles: dealerShip.getInventory()){
                if (vehicles.getYear() > start && vehicles.getYear() < end) {
                System.out.println(vehicles);
                System.out.println("==============");
            }
        }
    }
    public static void SearchByColor(String color){
        for (Vehicles vehicles: dealerShip.getInventory()){
            if (vehicles.getColor().equalsIgnoreCase(color)){
                System.out.println(vehicles);
                System.out.println("==============");
            }
        }
    }
    public static void SearchByYMileageRange(int start, int end){
        for (Vehicles vehicles: dealerShip.getInventory()){
            if (vehicles.getOdometer() > start && vehicles.getOdometer() < end) {
                System.out.println(vehicles);
                System.out.println("==============");
            }
        }
    }

}

