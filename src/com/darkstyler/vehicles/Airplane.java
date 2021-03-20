package com.darkstyler.vehicles;

public class Airplane extends Vehicle {

    public Airplane(int id,double distanceShippment, double averageSpeed, double tripCost, int weight) {
        super(id,distanceShippment, averageSpeed, tripCost, weight, TransportType.Air);
    }

    @Override
    public double getFinalPrice() {
        return getTripCost() + getWeight() * 3.4;
    }

}
