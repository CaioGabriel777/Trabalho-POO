package br.edu.ifba.inf008.interfaces;

import java.util.Map;

/**
 * Interface para plugins de tipos de veículos.
 * Cada tipo de veículo implementa seu próprio cálculo polimórfico de valor
 * total.
 */
public interface IVehiclePlugin extends IPlugin {

    /**
     * Retorna o nome do tipo de veículo (ex: "ECONOMY", "LUXURY", etc.)
     */
    String getTypeName();

    /**
     * Calcula o valor total da locação usando polimorfismo.
     * Fórmula base: (baseRate * days) + insuranceFee + taxas adicionais com sufixo
     * "_fee"
     * 
     * @param baseRate       Valor da diária
     * @param days           Número de dias de locação
     * @param insuranceFee   Valor do seguro
     * @param additionalFees Mapa com taxas adicionais do tipo de veículo
     * @return Valor total calculado
     */
    double calculateTotal(double baseRate, int days, double insuranceFee, Map<String, Double> additionalFees);
}
