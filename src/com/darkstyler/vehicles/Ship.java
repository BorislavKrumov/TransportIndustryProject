package com.darkstyler.vehicles;

public class Ship extends Vehicle {

    public Ship(int id,double distanceShippment, double averageSpeed, double tripCost, int weight) {
        super(id,distanceShippment, averageSpeed, tripCost, weight, TransportType.Water);
    }


    @Override
    public double getFinalPrice() {
        return getTripCost() + getWeight() * 1.2;
    }

}
