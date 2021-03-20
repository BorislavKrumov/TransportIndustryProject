package com.darkstyler.vehicles;

public class Car extends Vehicle {

    private double additionalTax;
    public Car(int id,double distanceShippment, double averageSpeed, double tripCost, int weight) {
        super(id,distanceShippment, averageSpeed, tripCost, weight, TransportType.Road);
    }

    @Override
    public double getFinalPrice() {
        return getTripCost() + getWeight() * 2.1;
    }

}
