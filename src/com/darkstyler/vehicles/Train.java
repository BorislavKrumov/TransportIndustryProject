package com.darkstyler.vehicles;

import com.darkstyler.util.Constants;

public class Train extends Vehicle {

    public Train(int id, double distanceShippment, double averageSpeed, double tripCost, int weight) {
        super(id, distanceShippment, averageSpeed, tripCost, weight, TransportType.RAIL);
    }

    @Override
    public double getFinalPrice() {
        return getTripCost() + getWeight() * Constants.TRAIN_MULTIPL;
    }
}
