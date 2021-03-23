package com.darkstyler.vehicles;

import com.darkstyler.util.Constants;

public class Ship extends Vehicle {

    public Ship(int id, double distanceShippment, double averageSpeed, double tripCost, int weight) {
        super(id, distanceShippment, averageSpeed, tripCost, weight, TransportType.WATER);
    }

    @Override
    public double getFinalPrice() {
        return getTripCost() + getWeight() * Constants.SHIP_MULTIPL;
    }

}
