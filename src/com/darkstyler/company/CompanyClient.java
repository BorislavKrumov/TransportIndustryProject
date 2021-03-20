package com.darkstyler.company;

import com.darkstyler.vehicles.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CompanyClient {
    public static void writeVehicleList(List<Vehicle> vehicles){
        File vehicleList = new File("vehicle.txt");

        try{
            if(!vehicleList.exists()){
                vehicleList.createNewFile();
            }
            else{
                FileOutputStream fos = new FileOutputStream("vehicle.txt");
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
                for(Object obj : vehicles){
                    objectOutputStream.writeObject(obj);
                    objectOutputStream.reset();
                }
                objectOutputStream.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static List<Vehicle> readVehicleList()  {
        List<Vehicle> vehicleArrayList = new ArrayList<>();
        try {
            File vehicleList = new File("vehicle.txt");
            if(!vehicleList.exists()){
                vehicleList.createNewFile();
            }
            try{
                FileInputStream fis = new FileInputStream("vehicle.txt");
                ObjectInputStream objectInputStream = new ObjectInputStream(fis);
                while(fis.available() != -1){
                    Vehicle vehicle = (Vehicle) objectInputStream.readObject();
                    vehicleArrayList.add(vehicle);
                }
            }
            catch (EOFException ex){

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return vehicleArrayList;
    }
    private static void printVehicleList(List<Vehicle> listVehicle){
        if(listVehicle.size() > 0){
            for(Vehicle vehicle : listVehicle){
                System.out.println(vehicle.toString());
            }
        }
        else {
            System.out.println("There are no vehicles with shipments.");
        }
    }
    private static void printVehicleList(List<Vehicle> listVehicle,TransportType type){
        if(listVehicle.size() > 0){
            for(Vehicle vehicle : listVehicle){
                if(vehicle.getTransportType() == TransportType.Air){
                    System.out.println(vehicle.toString());
                }
            }
        }
        else {
            System.out.println("There are no vehicles with shipments.");
        }
    }
    private static int readOnlyIntegers(Scanner sc){
        System.out.println("\nEnter a number.");
        int temp = 0;
        try
        {
            temp = sc.nextInt();
        }
        catch(Exception e)
        {
            System.out.printf("Only integeres are allowed.");
            sc.nextLine();
            temp = readOnlyIntegers(sc);
        }
        return temp;
    }
    private static double readOnlyDoubles(Scanner sc){
        System.out.println("\nEnter а number.");
        double temp = 0;
        try
        {
            temp = sc.nextDouble();
        }
        catch(Exception e)
        {
            System.out.printf("Only integeres and doubles are allowed.");
            sc.nextLine();
            temp = readOnlyIntegers(sc);
        }
        return temp;
    }
    public static void printVehicleMenu(){
        String builder = "\n" + "Choose vehicle which will deliver the package" +
                "\n" + "Press 1: Airplane" +
                "\n" + "Press 2: Car" +
                "\n" + "Press 3: Ship" +
                "\n" + "Press 4: Train" +
                "\n" + "Press 5: Back to Main menu";
        System.out.println(builder);
   }
    private static void printHomeMenu(){
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
    private static int generateId(List<Vehicle>vehicleList){
        int id;
        if(vehicleList.size() == 0){
            return 0;
        }
        else
            return vehicleList.get(vehicleList.size()-1).getId()+1;
    }
    public void initialize(){
        List<Vehicle> listVehicle = readVehicleList();
        int controlKey = 0;
        while(controlKey != 7){
            printHomeMenu();
            Scanner sc = new Scanner(System.in);
            controlKey = readOnlyIntegers(sc);
            switch (controlKey){
                case 1: {
                    printVehicleList(listVehicle);
                    break;
                }
                case 2:{
                    while(controlKey != 5) {
                        printVehicleMenu();
                        double distance;
                        double averageSpeed;
                        double tripCost;
                        int weight;
                        controlKey = readOnlyIntegers(sc);
                        switch (controlKey) {
                            case 1: {
                                System.out.println("You are about to create a shippment with Airplane.");
                                System.out.println("Enter the required distance to travel:");
                                distance = readOnlyDoubles(sc);
                                System.out.println("Enter the average speed of the vehicle:");
                                averageSpeed = readOnlyDoubles(sc);
                                System.out.println("Enter the cost of the shippment for the company:");
                                tripCost = readOnlyDoubles(sc);
                                System.out.println("Enter the weight of the shipment as an integer:");
                                weight = readOnlyIntegers(sc);
                                Vehicle vehicle = new Airplane(generateId(listVehicle), distance, averageSpeed, tripCost, weight);
                                listVehicle.add(vehicle);
                                break;
                            }
                            case 2: {
                                System.out.println("You are about to create a shippment with Car.");
                                System.out.println("Enter the required distance to travel:");
                                distance = readOnlyDoubles(sc);
                                System.out.println("Enter the average speed of the vehicle:");
                                averageSpeed =readOnlyDoubles(sc);
                                System.out.println("Enter the cost of the shippment for the company:");
                                tripCost = readOnlyDoubles(sc);
                                System.out.println("Enter the weight of the shipment as an integer:");
                                weight = readOnlyIntegers(sc);
                                Vehicle vehicle = new Car(generateId(listVehicle), distance, averageSpeed, tripCost, weight);
                                listVehicle.add(vehicle);
                                break;
                            }
                            case 3:{
                                System.out.println("You are about to create a shippment with Ship.");
                                System.out.println("Enter the required distance to travel:");
                                distance =readOnlyDoubles(sc);
                                System.out.println("Enter the average speed of the vehicle:");
                                averageSpeed = readOnlyDoubles(sc);
                                System.out.println("Enter the cost of the shippment for the company:");
                                tripCost = readOnlyDoubles(sc);
                                System.out.println("Enter the weight of the shipment as an integer:");
                                weight = readOnlyIntegers(sc);
                                Vehicle vehicle  = new Ship(generateId(listVehicle),distance,averageSpeed,tripCost,weight);
                                listVehicle.add(vehicle);
                                break;
                            }
                            case 4:{
                                System.out.println("You are about to create a shippment with Train.");
                                System.out.println("Enter the required distance to travel:");
                                distance = readOnlyDoubles(sc);
                                System.out.println("Enter the average speed of the vehicle:");
                                averageSpeed = readOnlyDoubles(sc);
                                System.out.println("Enter the cost of the shippment for the company:");
                                tripCost = readOnlyDoubles(sc);
                                System.out.println("Enter the weight of the shipment as an integer:");
                                weight = readOnlyIntegers(sc);
                                Vehicle vehicle  = new Train(generateId(listVehicle),distance,averageSpeed,tripCost,weight);
                                listVehicle.add(vehicle);
                                break;
                            }
                        }
                    }
                    writeVehicleList(listVehicle);
                }
                case 3:{
                    printVehicleList(listVehicle,TransportType.Air);
                }
                case 5:{
                    printVehicleList(listVehicle,TransportType.Road);
                }
                case 6:{
                    printVehicleList(listVehicle,TransportType.Water);
                }
                case 7:{
                    printVehicleList(listVehicle,TransportType.Rail);
                }
            }
        }

    }
}
