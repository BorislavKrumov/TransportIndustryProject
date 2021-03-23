package com.darkstyler.vehicles;

public class Car extends Vehicle {

    public Car(int id, double distanceShippment, double averageSpeed, double tripCost, int weight) {
        super(id, distanceShippment, averageSpeed, tripCost, weight, TransportType.ROAD);
    }

    @Override
    public double getFinalPrice() {
        return getTripCost() + getWeight() * 2.1;
    }

}
