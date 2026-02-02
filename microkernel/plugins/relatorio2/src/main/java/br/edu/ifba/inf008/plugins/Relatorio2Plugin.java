package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.IPlugin;
import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IUIController;
import br.edu.ifba.inf008.interfaces.DatabaseConnection;

import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Relatorio2Plugin implements IPlugin {

    @Override
    public boolean init() {
        IUIController uiController = ICore.getInstance().getUIController();

        MenuItem menuItem = uiController.createMenuItem("Reports", "Rental Data");
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                VBox content = createReportContent();
                uiController.createTab("Report - Rentals", content);
            }
        });

        return true;
    }

    private VBox createReportContent() {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));

        Label titleLabel = new Label("General Rental Data");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TableView<RentalReport> tableView = new TableView<>();
        tableView.setPrefHeight(500);


        TableColumn<RentalReport, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> cellData.getValue().rentalIdProperty().asObject());
        idCol.setPrefWidth(50);

        TableColumn<RentalReport, String> customerCol = new TableColumn<>("Customer");
        customerCol.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
        customerCol.setPrefWidth(150);

        TableColumn<RentalReport, String> customerTypeCol = new TableColumn<>("Customer Type");
        customerTypeCol.setCellValueFactory(cellData -> cellData.getValue().customerTypeProperty());
        customerTypeCol.setPrefWidth(100);

        TableColumn<RentalReport, String> vehicleCol = new TableColumn<>("Vehicle");
        vehicleCol.setCellValueFactory(cellData -> cellData.getValue().vehicleProperty());
        vehicleCol.setPrefWidth(150);

        TableColumn<RentalReport, String> vehicleTypeCol = new TableColumn<>("Vehicle Type");
        vehicleTypeCol.setCellValueFactory(cellData -> cellData.getValue().vehicleTypeProperty());
        vehicleTypeCol.setPrefWidth(100);

        TableColumn<RentalReport, String> startDateCol = new TableColumn<>("Start Date");
        startDateCol.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
        startDateCol.setPrefWidth(100);

        TableColumn<RentalReport, Double> totalCol = new TableColumn<>("Total Amount");
        totalCol.setCellValueFactory(cellData -> cellData.getValue().totalAmountProperty().asObject());
        totalCol.setPrefWidth(100);

        TableColumn<RentalReport, String> rentalStatusCol = new TableColumn<>("Status");
        rentalStatusCol.setCellValueFactory(cellData -> cellData.getValue().rentalStatusProperty());
        rentalStatusCol.setPrefWidth(100);

        TableColumn<RentalReport, String> paymentStatusCol = new TableColumn<>("Payment");
        paymentStatusCol.setCellValueFactory(cellData -> cellData.getValue().paymentStatusProperty());
        paymentStatusCol.setPrefWidth(100);

        tableView.getColumns().addAll(idCol, customerCol, customerTypeCol, vehicleCol,
                vehicleTypeCol, startDateCol, totalCol,
                rentalStatusCol, paymentStatusCol);


        ObservableList<RentalReport> data = loadReportData();
        tableView.setItems(data);

        Label countLabel = new Label("Total records: " + data.size());
        countLabel.setStyle("-fx-font-style: italic;");
        
        vbox.getChildren().addAll(titleLabel, tableView, countLabel);
        return vbox;
    }

    private ObservableList<RentalReport> loadReportData() {
        List<RentalReport> reports = new ArrayList<>();


        String sql = """
                SELECT
                    r.rental_id,
                    COALESCE(c.company_name, CONCAT(c.first_name, ' ', c.last_name)) as customer_name,
                    c.customer_type,
                    CONCAT(v.make, ' ', v.model) as vehicle,
                    vt.type_name as vehicle_type,
                    DATE_FORMAT(r.start_date, '%d/%m/%Y') as start_date,
                    r.total_amount,
                    r.rental_status,
                    r.payment_status
                FROM rentals r
                JOIN customers c ON r.customer_id = c.customer_id
                JOIN vehicles v ON r.vehicle_id = v.vehicle_id
                JOIN vehicle_types vt ON v.type_id = vt.type_id
                ORDER BY r.start_date DESC
                LIMIT 100
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                RentalReport report = new RentalReport(
                        rs.getInt("rental_id"),
                        rs.getString("customer_name"),
                        rs.getString("customer_type"),
                        rs.getString("vehicle"),
                        rs.getString("vehicle_type"),
                        rs.getString("start_date"),
                        rs.getDouble("total_amount"),
                        rs.getString("rental_status"),
                        rs.getString("payment_status"));
                reports.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return FXCollections.observableArrayList(reports);
    }


    public static class RentalReport {
        private final SimpleIntegerProperty rentalId;
        private final SimpleStringProperty customerName;
        private final SimpleStringProperty customerType;
        private final SimpleStringProperty vehicle;
        private final SimpleStringProperty vehicleType;
        private final SimpleStringProperty startDate;
        private final SimpleDoubleProperty totalAmount;
        private final SimpleStringProperty rentalStatus;
        private final SimpleStringProperty paymentStatus;

        public RentalReport(int rentalId, String customerName, String customerType,
                String vehicle, String vehicleType, String startDate,
                double totalAmount, String rentalStatus, String paymentStatus) {
            this.rentalId = new SimpleIntegerProperty(rentalId);
            this.customerName = new SimpleStringProperty(customerName);
            this.customerType = new SimpleStringProperty(customerType);
            this.vehicle = new SimpleStringProperty(vehicle);
            this.vehicleType = new SimpleStringProperty(vehicleType);
            this.startDate = new SimpleStringProperty(startDate);
            this.totalAmount = new SimpleDoubleProperty(totalAmount);
            this.rentalStatus = new SimpleStringProperty(rentalStatus);
            this.paymentStatus = new SimpleStringProperty(paymentStatus);
        }

        public SimpleIntegerProperty rentalIdProperty() {
            return rentalId;
        }

        public SimpleStringProperty customerNameProperty() {
            return customerName;
        }

        public SimpleStringProperty customerTypeProperty() {
            return customerType;
        }

        public SimpleStringProperty vehicleProperty() {
            return vehicle;
        }

        public SimpleStringProperty vehicleTypeProperty() {
            return vehicleType;
        }

        public SimpleStringProperty startDateProperty() {
            return startDate;
        }

        public SimpleDoubleProperty totalAmountProperty() {
            return totalAmount;
        }

        public SimpleStringProperty rentalStatusProperty() {
            return rentalStatus;
        }

        public SimpleStringProperty paymentStatusProperty() {
            return paymentStatus;
        }
    }
}
