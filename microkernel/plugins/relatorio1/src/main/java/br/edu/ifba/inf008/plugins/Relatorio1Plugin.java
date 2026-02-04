package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.IPlugin;
import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IUIController;
import br.edu.ifba.inf008.interfaces.DatabaseConnection;

import javafx.scene.control.MenuItem;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Relatorio1Plugin implements IPlugin {

    @Override
    public boolean init() {
        IUIController uiController = ICore.getInstance().getUIController();

        MenuItem menuItem = uiController.createMenuItem("Reports", "Fuel Distribution");
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                VBox content = createReportContent();
                uiController.createTab("Report - Fuel", content);
            }
        });

        return true;
    }

    private VBox createReportContent() {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));

        Label titleLabel = new Label("Vehicle Distribution by Fuel Type");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        String sql = """
                SELECT
                    v.fuel_type,
                    COUNT(*) AS vehicle_count,
                    SUM(CASE WHEN v.status = 'AVAILABLE' THEN 1 ELSE 0 END) AS available_count,
                    SUM(CASE WHEN v.status = 'RENTED' THEN 1 ELSE 0 END) AS rented_count,
                    ROUND(COUNT(*) * 100.0 / SUM(COUNT(*)) OVER (), 2) AS fleet_percentage,
                    CASE v.fuel_type
                        WHEN 'GASOLINE' THEN '#FF6B6B'
                        WHEN 'DIESEL' THEN '#4ECDC4'
                        WHEN 'ELECTRIC' THEN '#45B7D1'
                        WHEN 'HYBRID' THEN '#96CEB4'
                        WHEN 'CNG' THEN '#FFEAA7'
                        ELSE '#D9D9D9'
                    END AS chart_color
                FROM vehicles v
                GROUP BY v.fuel_type
                ORDER BY vehicle_count DESC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String fuelType = rs.getString("fuel_type");
                int count = rs.getInt("vehicle_count");
                double percentage = rs.getDouble("fleet_percentage");

                String label = String.format("%s (%d - %.1f%%)", fuelType, count, percentage);
                PieChart.Data data = new PieChart.Data(label, count);
                pieChartData.add(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Error loading data: " + e.getMessage());
            errorLabel.setStyle("-fx-text-fill: red;");
            vbox.getChildren().addAll(titleLabel, errorLabel);
            return vbox;
        }

        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Fuel Types");
        pieChart.setLabelsVisible(true);
        pieChart.setLegendVisible(true);
        pieChart.setPrefSize(600, 400);

        vbox.getChildren().addAll(titleLabel, pieChart);
        return vbox;
    }
}
