package br.edu.ifba.inf008.interfaces;

import java.util.Map;

public interface IVehiclePlugin extends IPlugin {

    String getTypeName();

    double calculateTotal(double baseRate, int days, double insuranceFee, Map<String, Double> additionalFees);
}
