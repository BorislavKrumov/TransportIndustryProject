package com.darkstyler.vehicles;

import com.darkstyler.util.Constants;

public class Airplane extends Vehicle {

    public Airplane(int id,double distanceShippment, double averageSpeed, double tripCost, int weight) {
        super(id,distanceShippment, averageSpeed, tripCost, weight, TransportType.AIR);
    }

    @Override
    public double getFinalPrice() {
        return getTripCost() + getWeight() * Constants.AIRPLANE_MULTIPL;
    }

}
