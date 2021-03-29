package com.darkstyler.company;

import com.darkstyler.model.parking.AirportParking;
import com.darkstyler.model.vehicles.*;
import com.darkstyler.util.DbConnector;

import java.io.FileInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.EOFException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CompanyClient {
    private static final int PRINT_ALL_VEHICLES = 1;
    private static final int ADD_VEHICLE = 2;
    private static final int LIST_TYPE = 3;
    private static final int SORT = 4;
    private static final int EXIT = 7;
    private static final int PARK = 5;
    private static final int UNPARK= 6;
    private static final int VEHICLE_OPTIONS = 4;
    private static final int SORTING_OPTIONS = 5;
    AirportParking airportParking = new AirportParking(4,15,3,2);
    static Connection connection = DbConnector.getInstance().getConnection();


    //Database operations
    public static void insertFromFileToDb(List<Vehicle> vehicleList){
        try {
            String query = "INSERT INTO VEHICLE (id,distance,speed,trip_cost,weight,transport_type) values(?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            for(Vehicle vehicle : vehicleList){
                statement.setInt(1,vehicle.getId()+1);
                statement.setDouble(2,vehicle.getDistanceShippment());
                statement.setDouble(3,vehicle.getAverageSpeed());
                statement.setDouble(4,vehicle.getTripCost());
                statement.setInt(5,vehicle.getWeight());
                statement.setString(6,vehicle.getTransportType().toString());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException throwables) {
            System.out.println("There are elements in the file and in the database that are duplicated or the file is empty.");
        }

    }

    public static void addVehicleToDb(Vehicle vehicle) {
        try {
            String query = "INSERT INTO VEHICLE (id,distance,speed,trip_cost,weight,transport_type) values(?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, vehicle.getId() + 1);
            statement.setDouble(2, vehicle.getDistanceShippment());
            statement.setDouble(3, vehicle.getAverageSpeed());
            statement.setDouble(4, vehicle.getTripCost());
            statement.setInt(5, vehicle.getWeight());
            statement.setString(6, vehicle.getTransportType().toString());
            statement.addBatch();
            statement.executeBatch();
        } catch (SQLException throwables) {
            System.out.println("You can't add vehicle to database right now. Reload the program and try again.");
        }
    }
    public static List<Vehicle> readVehiclesFromDB(){
        List<Vehicle> vehicleList = new ArrayList<>();
        try{
            String query = "Select * from vehicle";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
               TransportType transportType = TransportType.valueOf(rs.getString("transport_type"));
               Vehicle vehicle = transportType.createVehicle(rs.getInt("id")-1,rs.getDouble("distance"),rs.getDouble("speed"),rs.getDouble("trip_cost"),rs.getInt("weight"));
               vehicleList.add(vehicle);
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return vehicleList;
    }
    //List operations
    public static void writeVehicleList(List<Vehicle> vehicles) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("vehicle.txt"))) {
                for (Object obj : vehicles) {
                    objectOutputStream.writeObject(obj);
                    objectOutputStream.reset();
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        public static List<Vehicle> readVehicleList() {
        List<Vehicle> vehicleArrayList = new ArrayList<>();
        File vehicleList = new File("vehicle.txt");
        if (!vehicleList.exists()) {
            writeVehicleList(vehicleArrayList);
        }
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("vehicle.txt"))) {

            try {
                while (objectInputStream.available() != -1) {
                    Vehicle vehicle = (Vehicle) objectInputStream.readObject();
                    vehicleArrayList.add(vehicle);
                }
            } catch (EOFException ignored) {

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        insertFromFileToDb(vehicleArrayList);
        return vehicleArrayList;
    }

    private static void printVehicleList(List<Vehicle> listVehicle) {
        if (listVehicle.size() > 0) {
            for (Vehicle vehicle : listVehicle) {
                System.out.println(vehicle.toString());
            }
        } else {
            System.out.println("There are no vehicles with shipments.");
        }
    }

    private static void printVehicleList(List<Vehicle> listVehicle, TransportType type) {
        if (listVehicle.size() > 0) {
            for (Vehicle vehicle : listVehicle) {
                if (vehicle.getTransportType() == type) {
                    System.out.println(vehicle.toString());
                }
            }
        } else {
            System.out.println("There are no vehicles with shipments.");
        }
    }
    //methods for accepting only one parameter
    private static int readOnlyIntegers(Scanner sc) {
        System.out.println("\nEnter a number.");
        int temp;
        try {
            temp = sc.nextInt();
        } catch (Exception e) {
            System.out.print("Only integeres are allowed.");
            sc.nextLine();
            temp = readOnlyIntegers(sc);
        }
        return temp;
    }

    private static double readOnlyDoubles(Scanner sc) {
        System.out.println("\nEnter а number.");
        double temp;
        try {
            temp = sc.nextDouble();
        } catch (Exception e) {
            System.out.print("Only integeres and doubles are allowed.");
            sc.nextLine();
            temp = readOnlyIntegers(sc);
        }
        return temp;
    }
    //Printing methods
    protected static void printVehicleMenu() {
        String builder = "\n" + "Choose vehicle which will deliver the package" +
                "\n" + "Press 1: Airplane" +
                "\n" + "Press 2: Car" +
                "\n" + "Press 3: Ship" +
                "\n" + "Press 4: Train" ;
        System.out.println(builder);
    }
    private static void printListTypeMenu(){
        String builder = "\n" + "Choose which list of vehicle to be printed:" +
                "\n" + "Press 1: Airplane" +
                "\n" + "Press 2: Car" +
                "\n" + "Press 3: Ship" +
                "\n" + "Press 4: Train" ;
        System.out.println(builder);
    }
    private static void printSortMenu(){
        String builder = "\n" + "Choose by which paramater you want to sort" +
                "\n" + "Press 1: Distance" +
                "\n" + "Press 2: Speed" +
                "\n" + "Press 3: Cost" +
                "\n" + "Press 4: Time" +
                "\n" + "Press 5: Weight";
        System.out.println(builder);
    }
    private static void printHomeMenu() {
        String builder = "\n" + "Courier system" +
                "\n" + "This is the Main menu. Press one of the listed numbers to explore the program." +
                "\n" + "Press 1: To see all of the vehicles which have packages and additional information about them." +
                "\n" + "Press 2: To add vehicle with a package to be delivered." +
                "\n" + "Press 3: To see all vehicles of certain type." +
                "\n" + "Press 4: To see all vehicle sorted by specific parameter." +
                "\n" + "Press 5: To park a vehicle" +
                "\n" + "Press 6: To remove the vehicle from the parking lot"+
                "\n" + "Press 7: Exit." ;
        System.out.println(builder);
    }

    private static int generateId(List<Vehicle> vehicleList) {
        if (vehicleList.size() == 0) {
            return 0;
        } else
            return vehicleList.get(vehicleList.size() - 1).getId() + 1;
    }

    private static Vehicle vehicleCreate(Scanner sc,TransportType transportType, List<Vehicle> listVehicle) {
        double distance;
        double averageSpeed;
        double tripCost;
        int weight;
        System.out.println("Enter the required distance to travel:");
        distance = readOnlyDoubles(sc);
        System.out.println("Enter the average speed of the vehicle:");
        averageSpeed = readOnlyDoubles(sc);
        System.out.println("Enter the cost of the shippment for the company:");
        tripCost = readOnlyDoubles(sc);
        System.out.println("Enter the weight of the shipment as an integer:");
        weight = readOnlyIntegers(sc);
        //Adding vehicle to DB
        addVehicleToDb(transportType.createVehicle(generateId(listVehicle), distance, averageSpeed, tripCost, weight));
        return transportType.createVehicle(generateId(listVehicle), distance, averageSpeed, tripCost, weight);
    }

    public void initialize() {
        List<Vehicle> listVehicle = readVehiclesFromDB();
        int controlKey = 0;
        while (controlKey != EXIT) {
            printHomeMenu();
            Scanner sc = new Scanner(System.in);
            controlKey = readOnlyIntegers(sc);
            switch (controlKey) {
                case PRINT_ALL_VEHICLES: {
                    printVehicleList(listVehicle);
                    break;
                }
                case ADD_VEHICLE: {
                        printVehicleMenu();
                        controlKey = readOnlyIntegers(sc);
                        if(controlKey <= VEHICLE_OPTIONS){
                            VehicleOptions addVehicleMenu = VehicleOptions.value(controlKey);
                            listVehicle.add(addVehicleMenu.addVehicleOption(sc, listVehicle));
                            writeVehicleList(listVehicle);
                            break;
                        }
                }
                case LIST_TYPE: {
                    printListTypeMenu();
                    controlKey = readOnlyIntegers(sc);
                    if(controlKey <= VEHICLE_OPTIONS){
                        VehicleOptions printVehicleMenu = VehicleOptions.value(controlKey);
                        printVehicleMenu.printType(listVehicle);
                        break;
                    }
                }
                case SORT:{
                    printSortMenu();
                    controlKey = readOnlyIntegers(sc);
                    SortingOptions sortingOptions = SortingOptions.value(controlKey);
                    if (controlKey <= SORTING_OPTIONS) {
                        List<Vehicle> sorted = new ArrayList<>(listVehicle);
                        Collections.sort(sorted,SortingOptions.getComparator(sortingOptions));
                        printVehicleList(sorted);
                        break;
                    }
                }
                case PARK:{
                        System.out.println("Please enter the id of the vehicle to be parked.");
                        Vehicle vehicle = listVehicle.get(readOnlyIntegers(sc));
                        airportParking.addVehicle(vehicle);
                        break;
                }
                case UNPARK:{
                    System.out.println("Please enter the id of the vehicle you wish to leave the car park.");
                    Vehicle vehicle = listVehicle.get(readOnlyIntegers(sc));
                    System.out.println("Please enter the elapsed minutes.");
                    double time = readOnlyDoubles(sc);
                    airportParking.receipt(vehicle,time);
                    break;
                }
            }
        }
    }
   private enum VehicleOptions {
        AIRPLANE(1,TransportType.AIR),
        CAR(2,TransportType.ROAD),
        SHIP(3,TransportType.WATER),
        TRAIN(4,TransportType.RAIL) ;
        int choice;
        TransportType type;
        VehicleOptions(int choice, TransportType type) {
            this.choice = choice;
            this.type = type;
        }

        static VehicleOptions value(int choice) {
            for (VehicleOptions menu : VehicleOptions.values()) {
                if (menu.getChoice() == choice) {
                    return menu;
                }
            }
            return null;
        }

        private int getChoice() {
            return choice;
        }
        protected Vehicle addVehicleOption(Scanner sc, List<Vehicle> vehicles){
            System.out.println("You are about to create a shippment with "+this.name());
            return vehicleCreate(sc,type,vehicles);
        }
        private void printType(List<Vehicle> listVehicle){
            printVehicleList(listVehicle, this.type);
        }
    }
    private enum SortingOptions implements Comparator<Vehicle> {
        DISTANCE(1){
            @Override
            public int compare(Vehicle o1, Vehicle o2) {
                return Double.compare(o1.getDistanceShippment(), o2.getDistanceShippment());
            }
        },SPEED(2){
            @Override
            public int compare(Vehicle o1, Vehicle o2) {
                return Double.compare(o1.getAverageSpeed(), o2.getAverageSpeed());
            }
        },COST(3){
            @Override
            public int compare(Vehicle o1, Vehicle o2) {
                return Double.compare(o1.getTripCost(), o2.getTripCost());
            }
        },TIME(4){
            @Override
            public int compare(Vehicle o1, Vehicle o2) {
                return Integer.compare(o1.getTime(), o2.getTime());
            }
        },WEIGHT(5){
            @Override
            public int compare(Vehicle o1, Vehicle o2) {
                return Integer.compare(o1.getWeight(), o2.getWeight());
            }
        };
        private final int choice;
        SortingOptions(int choice) {
            this.choice = choice;
        }

        public int getChoice() {
            return choice;
        }

        static SortingOptions value(int choice) {
            for (SortingOptions menu : SortingOptions.values()) {
                if (menu.getChoice() == choice) {
                    return menu;
                }
            }
            return null;
        }
        public static Comparator<Vehicle> getComparator(final SortingOptions... multipleOptions) {
            return (o1, o2) -> {
                for (SortingOptions option : multipleOptions) {
                    int result = option.compare(o1, o2);
                    if (result != 0) {
                        return result;
                    }
                }
                return 0;
            };
        }
    }
}
