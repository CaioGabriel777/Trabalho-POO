package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.IVehiclePlugin;
import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IUIController;
import br.edu.ifba.inf008.interfaces.ui.RentalForm;

import javafx.scene.control.MenuItem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.Map;

/**
 * Plugin para veículos do tipo ELECTRIC.
 * Taxas adicionais: charging_fee, battery_depletion_fee
 */
public class EletricoPlugin implements IVehiclePlugin {

    private static final String TYPE_NAME = "ELECTRIC";

    @Override
    public boolean init() {
        IUIController uiController = ICore.getInstance().getUIController();

        MenuItem menuItem = uiController.createMenuItem("Locação", "Elétrico");
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                RentalForm form = new RentalForm(TYPE_NAME, EletricoPlugin.this);
                uiController.createTab("Locação - Elétrico", form);
            }
        });

        return true;
    }

    @Override
    public String getTypeName() {
        return TYPE_NAME;
    }

    @Override
    public double calculateTotal(double baseRate, int days, double insuranceFee, Map<String, Double> additionalFees) {
        double total = (baseRate * days) + insuranceFee;

        // Adiciona taxas com sufixo "_fee" (inclui charging_fee e battery_depletion_fee
        // para ELECTRIC)
        for (Map.Entry<String, Double> entry : additionalFees.entrySet()) {
            if (entry.getKey().endsWith("_fee")) {
                total += entry.getValue();
            }
        }

        return total;
    }
}
