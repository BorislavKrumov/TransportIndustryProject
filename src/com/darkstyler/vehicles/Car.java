package com.darkstyler.vehicles;

import com.darkstyler.util.Constants;

public class Car extends Vehicle {

    public Car(int id, double distanceShippment, double averageSpeed, double tripCost, int weight) {
        super(id, distanceShippment, averageSpeed, tripCost, weight, TransportType.ROAD);
    }

    @Override
    public double getFinalPrice() {
        return getTripCost() + getWeight() * Constants.CAR_MULTIPL;
    }

}
