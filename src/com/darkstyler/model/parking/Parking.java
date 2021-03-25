package com.darkstyler.model.parking;

import com.darkstyler.model.vehicles.Vehicle;

public interface Parking {
    public abstract void addVehicle(Vehicle vehicle);
    public abstract void receipt(Vehicle vehicle, double minutes);
}
