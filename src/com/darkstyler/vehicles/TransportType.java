package com.darkstyler.vehicles;

public enum TransportType {
    RAIL{
        @Override
        public Vehicle createVehicle(int id,double distance, double averageSpeed, double tripCost, int weight) {
            return new Train(id,distance,averageSpeed,tripCost,weight);
        }
    }, ROAD{
        @Override
        public Vehicle createVehicle(int id, double distance, double averageSpeed, double tripCost, int weight) {
            return new Car(id,distance,averageSpeed,tripCost,weight);
        }
    }, AIR{
        @Override
        public Vehicle createVehicle(int id, double distance, double averageSpeed, double tripCost, int weight) {
            return new Airplane(id,distance,averageSpeed,tripCost,weight);
        }
    }, WATER{
        @Override
        public Vehicle createVehicle(int id, double distance, double averageSpeed, double tripCost, int weight) {
            return new Ship(id,distance,averageSpeed,tripCost,weight);
        }
    };

    public abstract Vehicle createVehicle(int id,double distance, double averageSpeed, double tripCost, int weight);
}
