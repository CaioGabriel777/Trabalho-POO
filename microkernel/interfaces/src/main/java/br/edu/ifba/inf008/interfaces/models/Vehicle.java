package br.edu.ifba.inf008.interfaces.models;

public class Vehicle {
    private int vehicleId;
    private String licensePlate;
    private String make;
    private String model;
    private int year;
    private int typeId;
    private String typeName;
    private String color;
    private String fuelType;
    private String transmission;
    private double mileage;
    private String status;
    private String currentLocation;

    public Vehicle() {
    }

    // Getters
    public int getVehicleId() {
        return vehicleId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getColor() {
        return color;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getTransmission() {
        return transmission;
    }

    public double getMileage() {
        return mileage;
    }

    public String getStatus() {
        return status;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    // Setters
    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getFullName() {
        return make + " " + model;
    }
}
