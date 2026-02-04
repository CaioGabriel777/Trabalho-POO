package br.edu.ifba.inf008.interfaces.models;

import java.sql.Timestamp;

public class Rental {
    private int rentalId;
    private int customerId;
    private int vehicleId;
    private String rentalType;
    private Timestamp startDate;
    private Timestamp scheduledEndDate;
    private Timestamp actualEndDate;
    private String pickupLocation;
    private String dropoffLocation;
    private double initialMileage;
    private double finalMileage;
    private double baseRate;
    private double insuranceFee;
    private double additionalFees;
    private double discountAmount;
    private double lateFee;
    private double damageFee;
    private double cleaningFee;
    private double totalAmount;
    private double paidAmount;
    private String rentalStatus;
    private String paymentStatus;
    private String notes;

    public Rental() {
        this.rentalType = "DAILY";
        this.rentalStatus = "ACTIVE";
        this.paymentStatus = "PENDING";
    }

    public int getRentalId() {
        return rentalId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public String getRentalType() {
        return rentalType;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public Timestamp getScheduledEndDate() {
        return scheduledEndDate;
    }

    public Timestamp getActualEndDate() {
        return actualEndDate;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public String getDropoffLocation() {
        return dropoffLocation;
    }

    public double getInitialMileage() {
        return initialMileage;
    }

    public double getFinalMileage() {
        return finalMileage;
    }

    public double getBaseRate() {
        return baseRate;
    }

    public double getInsuranceFee() {
        return insuranceFee;
    }

    public double getAdditionalFees() {
        return additionalFees;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public double getLateFee() {
        return lateFee;
    }

    public double getDamageFee() {
        return damageFee;
    }

    public double getCleaningFee() {
        return cleaningFee;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public String getRentalStatus() {
        return rentalStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setRentalType(String rentalType) {
        this.rentalType = rentalType;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public void setScheduledEndDate(Timestamp scheduledEndDate) {
        this.scheduledEndDate = scheduledEndDate;
    }

    public void setActualEndDate(Timestamp actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public void setDropoffLocation(String dropoffLocation) {
        this.dropoffLocation = dropoffLocation;
    }

    public void setInitialMileage(double initialMileage) {
        this.initialMileage = initialMileage;
    }

    public void setFinalMileage(double finalMileage) {
        this.finalMileage = finalMileage;
    }

    public void setBaseRate(double baseRate) {
        this.baseRate = baseRate;
    }

    public void setInsuranceFee(double insuranceFee) {
        this.insuranceFee = insuranceFee;
    }

    public void setAdditionalFees(double additionalFees) {
        this.additionalFees = additionalFees;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public void setLateFee(double lateFee) {
        this.lateFee = lateFee;
    }

    public void setDamageFee(double damageFee) {
        this.damageFee = damageFee;
    }

    public void setCleaningFee(double cleaningFee) {
        this.cleaningFee = cleaningFee;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public void setRentalStatus(String rentalStatus) {
        this.rentalStatus = rentalStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
