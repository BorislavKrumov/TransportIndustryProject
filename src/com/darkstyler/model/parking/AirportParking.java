package com.darkstyler.model.parking;

import com.darkstyler.model.vehicles.TransportType;
import com.darkstyler.model.vehicles.Vehicle;
import com.darkstyler.util.Constants;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AirportParking implements Parking{

    private Map<TransportType,Set<Vehicle>> parkedVehicles;
    private Map<TransportType,Integer> emptyCells;


    public AirportParking(int planes, int cars, int ships, int trains){
        int capacity = planes + cars + ships + trains;
        this.parkedVehicles = new HashMap<>(capacity);
        parkedVehicles.put(TransportType.AIR,new HashSet<>());
        parkedVehicles.put(TransportType.ROAD,new HashSet<>());
        parkedVehicles.put(TransportType.WATER,new HashSet<>());
        parkedVehicles.put(TransportType.RAIL,new HashSet<>());
        this.emptyCells = new HashMap<>();
        emptyCells.put(TransportType.AIR,planes);
        emptyCells.put(TransportType.ROAD,cars);
        emptyCells.put(TransportType.WATER,ships);
        emptyCells.put(TransportType.RAIL,trains);
    }
    @Override
    public void addVehicle(Vehicle vehicle) {

        int cellsLeft = emptyCells.get(vehicle.getTransportType());
        if(cellsLeft<=0){
            System.out.println("There are no free cells left.");
        }
        else{
            emptyCells.put(vehicle.getTransportType(),cellsLeft -1);
            parkedVehicles.get(vehicle.getTransportType()).add(vehicle);
            System.out.println(MessageFormat.format("Vehicle with id:{0} was parked.", vehicle.getId()));
        }
    }
    @Override
    public void receipt(Vehicle vehicle, double minutes){
            emptyCells.put(vehicle.getTransportType(),emptyCells.get(vehicle.getTransportType())+1);
            boolean isRemoved =parkedVehicles.get(vehicle.getTransportType()).remove(vehicle);
            if(isRemoved == true){
                double price = minutes * Constants.AIRPLANE_MULTIPL;
                System.out.println(MessageFormat.format("Vehicle with id:{0} owes:{1}$ to the Airport parking.", vehicle.getId(), price));
            }
            else{
                System.out.println("There is no vehicle with id:" + vehicle.getId() + " in the Parking");
            }
    }
}
