package com.darkstyler.vehicles;

import java.io.Serializable;
import java.text.DecimalFormat;


public abstract class Vehicle implements Serializable {
    private int id;
    private double distanceShippment;
    private double averageSpeed;
    private double tripCost;
    private int time;
    private int weight;
    private TransportType transportType;

    public Vehicle(int id, double distanceShippment, double averageSpeed, double tripCost, int weight, TransportType transportType) {
        this.id = id;
        this.distanceShippment = distanceShippment;
        this.averageSpeed = averageSpeed;
        this.tripCost = tripCost;
        this.weight = weight;
        this.transportType = transportType;
        this.time = (int) (this.distanceShippment / this.averageSpeed);
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public int getId() {
        return id;
    }

    public double getTripCost() {
        return tripCost;
    }

    public abstract double getFinalPrice();

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        String builder = "\n" + "The package to be delivered weighs:" + decimalFormat.format(weight) + " kg" +
                "\n" + "This is " + transportType.name() + " type of transport" +
                "\n" + "Vehicle id:" + id +
                "\n" + "The distance to the final distanation is " + decimalFormat.format(distanceShippment) + " km" +
                "\n" + "The average speed will be " + decimalFormat.format(averageSpeed) + "km/h." +
                "\n" + "The average arrival time will be: " + time + " hours" +
                "\n" + "The trip will cost: " + decimalFormat.format(tripCost) + "$ for the company." +
                "\n" + "The final price for the client will be: " + decimalFormat.format(getFinalPrice()) + "$";
        return builder;
    }
}
