import Models.DealerShip;
import Models.LeaseContract;
import Models.SalesContract;
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

        basicDataSource  = new BasicDataSource();
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/dealership");
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);

        VehicleDAO data = new VehicleDAO(basicDataSource);
        try
        {
            connection = data.getMyDataSource().getConnection();

            String query = "SELECT * FROM dealership;";

            statement = connection.prepareStatement(query);

            results = statement.executeQuery();

            while(results.next())
            {
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

            while(results.next())
            {
                dealerShip = new DealerShip(results.getString("dealership_id"), results.getString("namee"), results.getString("address"), results.getString("phone"));
                System.out.println(dealerShip);
            }
            loadDealership();
            boolean truee = true;
        while(truee)
        {
            System.out.println("Select what option you would like ?");
            System.out.println("1) Search for car by price range?");
            System.out.println("2) Search for car by make/model");
            System.out.println("3) Search for car by year range");
            System.out.println("4) Search for car by By color");
            System.out.println("5) Search for car by By mileage range");
            System.out.println("6) Add a vehicle");
            System.out.println("7) Delete a vehicle");
            System.out.println("8) Create Sales Contract");
            System.out.println("9) Create Lease Contract");
            System.out.println("0) Exit");

            int option = userInput.nextInt();

            switch (option)
            {
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
                    System.out.println("Enter the details of the car you want to enter");
                    System.out.println("Vin : ");
                    String vin = userInput.next();
                    System.out.println("Year: ");
                    int year = userInput.nextInt();
                    System.out.println("Make: ");
                    String makee = userInput.next();
                    System.out.println("Model: ");
                    String modell = userInput.next();
                    System.out.println("Color: ");
                    String colorr = userInput.next();
                    System.out.println("Odometer: ");
                    int odometer = userInput.nextInt();
                    System.out.println("Price");
                    double price = userInput.nextDouble();
                    Vehicles vehicle = new Vehicles(vin,year,makee,modell,colorr,odometer,price);
                    dealerShip.addVehicle(vehicle);
                    AddVehicle(vehicle);
                    break;
                case 7:
                    for(Vehicles car: dealerShip.getInventory()){
                        System.out.println(car);
                    }
                    System.out.println("What is the VIN of the car you want to delete");
                    String delete = userInput.next();
                    DeleteVehicle(delete);
                    break;
                case 8:
                    System.out.println("What is the vin?");
                    String vinn = userInput.next();
                    System.out.println("What is the date you completed this (YYYY-MM-DD)?");
                    String date = userInput.next();
                    System.out.println("What is the price you made this transaction ?");
                    double pricee = userInput.nextDouble();
                    SalesContract contract = new SalesContract(vinn,date,pricee);
                    SaveSaleContract(contract);
                case 9:
                    System.out.println("What is the vin?");
                    String vinnn = userInput.next();
                    System.out.println("What is the start date (YYYY-MM-DD)");
                    String datee = userInput.next();
                    System.out.println("What is the end date (YYYY-MM-DD)");
                    String date2 = userInput.next();
                    System.out.println("Monthly payment?");
                    String price2 = userInput.next();
                    LeaseContract contractt = new LeaseContract(vinnn, datee,date2,price2);
                    SaveLeaseContract(contractt);
                case 0:
                    truee = false;
                    break;
                default:
                    System.out.println("Wrong input, Try again");
            }
        }
        }catch(Exception e)
        {
            System.out.println();
        }
    }

    public static void loadDealership()
    {
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
    public static void SearchByPriceRange(int start, int end)
    {
        /*
        SQL query: SELECT * FROM vehicles WHERE price > start AND price < end;
         */
       for (Vehicles vehicles: dealerShip.getInventory()){
           if (vehicles.getPrice() > start && vehicles.getPrice() < end) {
               System.out.println(vehicles);
               System.out.println("==============");
           }
       }
    }
    public static void SearchByMakeModel(String make, String model)
    {
        /*
        SQL query : SELECT * FROM vehicles WHERE make LIKE 'make' AND model LIKE 'model';
         */
        for (Vehicles vehicles: dealerShip.getInventory()){
            if (vehicles.getMake().equalsIgnoreCase(make) && vehicles.getModel().equalsIgnoreCase(model)){
                System.out.println(vehicles);
                System.out.println("==============");
            }
        }
    }
    public static void SearchByYearRange(int start, int end)
    {
        /*
        SQL query: SELECT * FROM vehicles WHERE yearr > start AND yearr < end;
         */
        for (Vehicles vehicles: dealerShip.getInventory()){
                if (vehicles.getYear() > start && vehicles.getYear() < end) {
                System.out.println(vehicles);
                System.out.println("==============");
            }
        }
    }
    public static void SearchByColor(String color)
    {
        /*
        SQL query :SELECT * FROM vehicles WHERE color LIKE color;
         */
        for (Vehicles vehicles: dealerShip.getInventory()){
            if (vehicles.getColor().equalsIgnoreCase(color)){
                System.out.println(vehicles);
                System.out.println("==============");
            }
        }
    }
    public static void SearchByYMileageRange(int start, int end)
    {
        /*
        SQL query:  SELECT * FROM vehicles WHERE odometer > 2000 AND odometer < 10000;
         */
        for (Vehicles vehicles: dealerShip.getInventory()){
            if (vehicles.getOdometer() > start && vehicles.getOdometer() < end) {
                System.out.println(vehicles);
                System.out.println("==============");
            }
        }
    }
    public static void AddVehicle(Vehicles vehicle)
    {
        try {
            String query3 = "INSERT INTO vehicles(vin, yearr, make, model, color, odometer, price, sold) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            statement = connection.prepareStatement(query3,statement.RETURN_GENERATED_KEYS);

            statement.setString(1,vehicle.getVin());
            statement.setInt(2, vehicle.getYear());
            statement.setString(3,vehicle.getMake());
            statement.setString(4,vehicle.getModel());
            statement.setString(5,vehicle.getColor());
            statement.setInt(6, vehicle.getOdometer());
            statement.setDouble(7, vehicle.getPrice());
            statement.setBoolean(8,false);

            statement.executeUpdate();

            ResultSet generatedKeys =statement.getGeneratedKeys();
            if(generatedKeys.next()){
                int key = generatedKeys.getInt(1);
                System.out.println("Generated ID: " + key );
            }

            String query4 = "INSERT INTO inventory VALUES(?,?);";
            statement = connection.prepareStatement(query4);
            statement.setString(1,dealerShip.getDealership_id());
            statement.setString(2, vehicle.getVin());
            statement.executeUpdate();
        }catch(Exception e){
            System.out.println("No");
        }
    }
    public static void DeleteVehicle(String vin)
    {
        try
        {
            String query = "DELETE FROM inventory WHERE vin LIKE ?;";

            statement = connection.prepareStatement(query);

            statement.setString(1,vin);

            statement.executeUpdate();

            for (Vehicles car: dealerShip.getInventory())
            {
                if(car.getVin().equals(vin))
                {
                    dealerShip.getInventory().remove(car);
                }
            }

            String query2 = "DELETE FROM vehicles WHERE vin LIKE '?'";

            statement = connection.prepareStatement(query2);

            statement.setString(1,vin);

            statement.executeUpdate();
        }catch(Exception e){
            System.out.println("Hi");
        }

    }
    public static void SaveSaleContract(SalesContract contract)
    {
        try
        {
            String query = "INSERT INTO sales_contracts (vin, sale_date, price) VALUES(?,?,?)";
            statement = connection.prepareStatement(query);

            statement.setString(1, contract.getVin());

            statement.setString(2, contract.getDate());

            statement.setString(3, String.valueOf(contract.getPrice()));

            statement.executeUpdate();

            String queryy = "UPDATE vehicles SET sold = true WHERE vin LIKE ?";

            statement = connection.prepareStatement(queryy);

            statement.setString(1, contract.getVin());

            statement.executeUpdate();

        }catch(Exception e){
            System.out.println("Hi!");
        }
    }
    public static void SaveLeaseContract(LeaseContract contract)
    {
        try
        {
            String query ="INSERT INTO lease_contracts ((vin , lease_start, lease_end, monthly_payment) VALUES (?,?,?,?)";

            statement = connection.prepareStatement(query);

            statement.setString(1, contract.getVin());

            statement.setString(2, contract.getStartdate());

            statement.setString(3, contract.getEnddate());

            statement.setString(4, contract.getMonthlypayment());

            statement.executeUpdate();

            String queryy = "UPDATE vehicles SET sold = true WHERE vin LIKE ?";

            statement = connection.prepareStatement(queryy);

            statement.setString(1, contract.getVin());

            statement.executeUpdate();
        }catch(Exception e){
            System.out.println("Hi");
        }
    }
}

