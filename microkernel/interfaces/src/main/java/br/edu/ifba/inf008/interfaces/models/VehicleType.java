package br.edu.ifba.inf008.interfaces.models;

import java.util.HashMap;
import java.util.Map;

public class VehicleType {
    private int typeId;
    private String typeName;
    private double dailyRate;
    private double weeklyRate;
    private double monthlyRate;
    private double securityDeposit;
    private double insuranceRate;
    private double lateFeePerHour;
    private int maxPassengers;
    private int maxLuggage;
    private String specialFeatures;
    private Map<String, Double> additionalFees;

    public VehicleType() {
        this.additionalFees = new HashMap<>();
    }

    public int getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public double getWeeklyRate() {
        return weeklyRate;
    }

    public double getMonthlyRate() {
        return monthlyRate;
    }

    public double getSecurityDeposit() {
        return securityDeposit;
    }

    public double getInsuranceRate() {
        return insuranceRate;
    }

    public double getLateFeePerHour() {
        return lateFeePerHour;
    }

    public int getMaxPassengers() {
        return maxPassengers;
    }

    public int getMaxLuggage() {
        return maxLuggage;
    }

    public String getSpecialFeatures() {
        return specialFeatures;
    }

    public Map<String, Double> getAdditionalFees() {
        return additionalFees;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public void setWeeklyRate(double weeklyRate) {
        this.weeklyRate = weeklyRate;
    }

    public void setMonthlyRate(double monthlyRate) {
        this.monthlyRate = monthlyRate;
    }

    public void setSecurityDeposit(double securityDeposit) {
        this.securityDeposit = securityDeposit;
    }

    public void setInsuranceRate(double insuranceRate) {
        this.insuranceRate = insuranceRate;
    }

    public void setLateFeePerHour(double lateFeePerHour) {
        this.lateFeePerHour = lateFeePerHour;
    }

    public void setMaxPassengers(int maxPassengers) {
        this.maxPassengers = maxPassengers;
    }

    public void setMaxLuggage(int maxLuggage) {
        this.maxLuggage = maxLuggage;
    }

    public void setSpecialFeatures(String specialFeatures) {
        this.specialFeatures = specialFeatures;
    }

    public void setAdditionalFees(Map<String, Double> additionalFees) {
        this.additionalFees = additionalFees;
    }

    public void addAdditionalFee(String feeName, Double feeValue) {
        this.additionalFees.put(feeName, feeValue);
    }
}
