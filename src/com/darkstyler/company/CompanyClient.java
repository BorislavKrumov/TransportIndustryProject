package com.darkstyler.company;

import com.darkstyler.vehicles.*;

import java.io.FileInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.EOFException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CompanyClient {
    private static final int PRINT_ALL_VEHICLES = 1;
    private static final int ADD_VEHICLE = 2;
    private static final int LIST_AIRPLANES = 3;
    private static final int LIST_CARS = 4;
    private static final int LIST_SHIPS = 5;
    private static final int LIST_TRAINS = 6;
    private static final int EXIT = 7;
    private static final int ADDING_OPTIONS = 4;
    public static void writeVehicleList(List<Vehicle> vehicles) {
        File vehicleList = new File("vehicle.txt");
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("vehicle.txt"))) {
            if (!vehicleList.exists()) {
                vehicleList.createNewFile();
            } else {

                for (Object obj : vehicles) {
                    objectOutputStream.writeObject(obj);
                    objectOutputStream.reset();
                }
                objectOutputStream.close();
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
            } catch (EOFException ex) {

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    protected static void printVehicleMenu() {
        String builder = "\n" + "Choose vehicle which will deliver the package" +
                "\n" + "Press 1: Airplane" +
                "\n" + "Press 2: Car" +
                "\n" + "Press 3: Ship" +
                "\n" + "Press 4: Train" ;
        System.out.println(builder);
    }

    private static void printHomeMenu() {
        String builder = "\n" + "Courier system" +
                "\n" + "This is the Main menu. Press one of the listed numbers to explore the program." +
                "\n" + "Press 1: To see all of the vehicles which have packages and additional information about them." +
                "\n" + "Press 2: To add vehicle with a package to be delivered." +
                "\n" + "Press 3: To get the list of all Airplanes which have shippments to be delivered." +
                "\n" + "Press 4: To get the list of all Cars which have shippments to be delivered." +
                "\n" + "Press 5: To get the list of all Ships which have shippments to be delivered." +
                "\n" + "Press 6: To get the list of all Trains which have shippments to be delivered." +
                "\n" + "Press 7: To exit.";
        System.out.println(builder);
    }

    private static int generateId(List<Vehicle> vehicleList) {
        if (vehicleList.size() == 0) {
            return 0;
        } else
            return vehicleList.get(vehicleList.size() - 1).getId() + 1;
    }

    private static Vehicle vehicleCreate(Scanner sc, TransportType transportType, List<Vehicle> listVehicle) {
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
        return transportType.createVehicle(generateId(listVehicle), distance, averageSpeed, tripCost, weight);
    }

    public void initialize() {
        List<Vehicle> listVehicle = readVehicleList();
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
                        if(controlKey <= ADDING_OPTIONS){
                            AddVehicle addVehicleMenu = AddVehicle.value(controlKey);
                            listVehicle.add(addVehicleMenu.addVehicleOption(sc, listVehicle));
                            writeVehicleList(listVehicle);
                        }
                }
                case LIST_AIRPLANES: {
                    printVehicleList(listVehicle, TransportType.AIR);
                }
                case LIST_CARS: {
                    printVehicleList(listVehicle, TransportType.ROAD);
                }
                case LIST_SHIPS: {
                    printVehicleList(listVehicle, TransportType.WATER);
                }
                case LIST_TRAINS: {
                    printVehicleList(listVehicle, TransportType.RAIL);
                }
            }
        }
    }
   private enum AddVehicle {
        ADD_AIRPLANE(1) {
            @Override
            public Vehicle addVehicleOption(Scanner sc, List<Vehicle> vehicles) {
                System.out.println("You are about to create a shippment with Airplane.");
                return vehicleCreate(sc, TransportType.AIR, vehicles);
            }
        },
        ADD_CAR(2) {
            @Override
            public Vehicle addVehicleOption(Scanner sc, List<Vehicle> vehicles) {
                System.out.println("You are about to create a shippment with Car.");
                return vehicleCreate(sc, TransportType.ROAD, vehicles);
            }
        },
        ADD_SHIP(3) {
            @Override
            public Vehicle addVehicleOption(Scanner sc, List<Vehicle> vehicles) {
                System.out.println("You are about to create a shippment with Ship.");
                return vehicleCreate(sc, TransportType.WATER, vehicles);
            }
        },
        ADD_TRAIN(4) {
            @Override
            public Vehicle addVehicleOption(Scanner sc, List<Vehicle> vehicles) {
                System.out.println("You are about to create a shippment with Train.");
                return vehicleCreate(sc,TransportType.RAIL,vehicles);
            }
        };
        int choice;
        AddVehicle(int choice) {
            this.choice = choice;
        }

        static AddVehicle value(int choice) {
            for (AddVehicle menu : AddVehicle.values()) {
                if (menu.getChoice() == choice) {
                    return menu;
                }
            }
            return null;
        }

        private int getChoice() {
            return choice;
        }
        protected abstract Vehicle addVehicleOption(Scanner sc, List<Vehicle> vehicles);
    }
}
