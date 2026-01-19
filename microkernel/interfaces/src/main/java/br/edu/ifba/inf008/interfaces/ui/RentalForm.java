package br.edu.ifba.inf008.interfaces.ui;

import br.edu.ifba.inf008.interfaces.models.Customer;
import br.edu.ifba.inf008.interfaces.models.Rental;
import br.edu.ifba.inf008.interfaces.models.Vehicle;
import br.edu.ifba.inf008.interfaces.models.VehicleType;
import br.edu.ifba.inf008.interfaces.services.CustomerService;
import br.edu.ifba.inf008.interfaces.services.RentalService;
import br.edu.ifba.inf008.interfaces.services.VehicleService;
import br.edu.ifba.inf008.interfaces.IVehiclePlugin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

/**
 * Formulário reutilizável para locação de veículos.
 * Usado por todos os plugins de tipos de veículos.
 */
public class RentalForm extends VBox {

    private ComboBox<Customer> customerComboBox;
    private TableView<Vehicle> vehicleTable;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private TextField pickupLocationField;
    private TextField baseRateField;
    private TextField insuranceFeeField;
    private Label totalLabel;
    private Button confirmButton;

    private String typeName;
    private IVehiclePlugin plugin;
    private VehicleType vehicleType;

    private CustomerService customerService = new CustomerService();
    private VehicleService vehicleService = new VehicleService();
    private RentalService rentalService = new RentalService();

    public RentalForm(String typeName, IVehiclePlugin plugin) {
        this.typeName = typeName;
        this.plugin = plugin;
        this.vehicleType = vehicleService.getVehicleType(typeName);

        setPadding(new Insets(20));
        setSpacing(15);

        initializeComponents();
        loadData();
    }

    private void initializeComponents() {
        
        Label titleLabel = new Label("Locação de Veículo - " + typeName);
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label customerLabel = new Label("Cliente (Email):");
        customerComboBox = new ComboBox<>();
        customerComboBox.setPromptText("Selecione o cliente");
        customerComboBox.setPrefWidth(300);

        Label vehicleLabel = new Label("Veículos Disponíveis:");
        vehicleTable = new TableView<>();
        vehicleTable.setPrefHeight(200);

        TableColumn<Vehicle, String> makeCol = new TableColumn<>("Marca");
        makeCol.setCellValueFactory(new PropertyValueFactory<>("make"));

        TableColumn<Vehicle, String> modelCol = new TableColumn<>("Modelo");
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));

        TableColumn<Vehicle, Integer> yearCol = new TableColumn<>("Ano");
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<Vehicle, String> fuelCol = new TableColumn<>("Combustível");
        fuelCol.setCellValueFactory(new PropertyValueFactory<>("fuelType"));

        TableColumn<Vehicle, String> transCol = new TableColumn<>("Câmbio");
        transCol.setCellValueFactory(new PropertyValueFactory<>("transmission"));

        TableColumn<Vehicle, Double> mileageCol = new TableColumn<>("Quilometragem");
        mileageCol.setCellValueFactory(new PropertyValueFactory<>("mileage"));

        vehicleTable.getColumns().addAll(makeCol, modelCol, yearCol, fuelCol, transCol, mileageCol);
        vehicleTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);

        startDatePicker = new DatePicker(LocalDate.now());
        endDatePicker = new DatePicker(LocalDate.now().plusDays(1));
        pickupLocationField = new TextField();
        pickupLocationField.setPromptText("Local de retirada");
        baseRateField = new TextField();
        baseRateField.setPromptText("Valor da diária");
        insuranceFeeField = new TextField();
        insuranceFeeField.setPromptText("Valor do seguro");

        formGrid.add(new Label("Data Início:"), 0, 0);
        formGrid.add(startDatePicker, 1, 0);
        formGrid.add(new Label("Data Fim:"), 2, 0);
        formGrid.add(endDatePicker, 3, 0);

        formGrid.add(new Label("Local de Retirada:"), 0, 1);
        formGrid.add(pickupLocationField, 1, 1);

        formGrid.add(new Label("Diária (R$):"), 0, 2);
        formGrid.add(baseRateField, 1, 2);
        formGrid.add(new Label("Seguro (R$):"), 2, 2);
        formGrid.add(insuranceFeeField, 3, 2);

        totalLabel = new Label("Valor Total: R$ 0,00");
        totalLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2196F3;");

        Button calculateButton = new Button("Calcular Total");
        calculateButton.setOnAction(e -> calculateTotal());

        confirmButton = new Button("Confirmar Locação");
        confirmButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        confirmButton.setOnAction(e -> confirmRental());

        getChildren().addAll(
                titleLabel,
                customerLabel, customerComboBox,
                vehicleLabel, vehicleTable,
                formGrid,
                calculateButton, totalLabel,
                confirmButton);
    }

    private void loadData() {

        List<Customer> customers = customerService.getAllCustomers();
        customerComboBox.setItems(FXCollections.observableArrayList(customers));

        List<Vehicle> vehicles = vehicleService.getAvailableVehiclesByType(typeName);
        vehicleTable.setItems(FXCollections.observableArrayList(vehicles));

        if (vehicleType != null) {
            baseRateField.setText(String.valueOf(vehicleType.getDailyRate()));
            insuranceFeeField.setText(String.valueOf(vehicleType.getInsuranceRate()));
        }
    }

    private void calculateTotal() {
        try {
            double baseRate = Double.parseDouble(baseRateField.getText());
            double insuranceFee = Double.parseDouble(insuranceFeeField.getText());

            LocalDate start = startDatePicker.getValue();
            LocalDate end = endDatePicker.getValue();
            int days = (int) ChronoUnit.DAYS.between(start, end);
            if (days <= 0)
                days = 1;

            Map<String, Double> additionalFees = vehicleType != null ? vehicleType.getAdditionalFees() : Map.of();

            double total = plugin.calculateTotal(baseRate, days, insuranceFee, additionalFees);

            totalLabel.setText(String.format("Valor Total: R$ %.2f", total));
        } catch (NumberFormatException e) {
            showAlert("Erro", "Por favor, insira valores numéricos válidos.");
        }
    }

    private void confirmRental() {
        Customer selectedCustomer = customerComboBox.getValue();
        Vehicle selectedVehicle = vehicleTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            showAlert("Erro", "Por favor, selecione um cliente.");
            return;
        }

        if (selectedVehicle == null) {
            showAlert("Erro", "Por favor, selecione um veículo.");
            return;
        }

        try {
            double baseRate = Double.parseDouble(baseRateField.getText());
            double insuranceFee = Double.parseDouble(insuranceFeeField.getText());
            String pickupLocation = pickupLocationField.getText();

            if (pickupLocation.isEmpty()) {
                showAlert("Erro", "Por favor, informe o local de retirada.");
                return;
            }

            LocalDate start = startDatePicker.getValue();
            LocalDate end = endDatePicker.getValue();
            int days = (int) ChronoUnit.DAYS.between(start, end);
            if (days <= 0)
                days = 1;

            Map<String, Double> additionalFees = vehicleType != null ? vehicleType.getAdditionalFees() : Map.of();

            double total = plugin.calculateTotal(baseRate, days, insuranceFee, additionalFees);

            double additionalFeesSum = 0;
            for (Map.Entry<String, Double> entry : additionalFees.entrySet()) {
                if (entry.getKey().endsWith("_fee")) {
                    additionalFeesSum += entry.getValue();
                }
            }

            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirmar Locação");
            confirmAlert.setHeaderText("Deseja confirmar a locação?");
            confirmAlert.setContentText(String.format(
                    "Cliente: %s\nVeículo: %s\nPeríodo: %s a %s\nValor Total: R$ %.2f",
                    selectedCustomer.getEmail(),
                    selectedVehicle.getFullName(),
                    start.toString(),
                    end.toString(),
                    total));

            if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                Rental rental = new Rental();
                rental.setCustomerId(selectedCustomer.getCustomerId());
                rental.setVehicleId(selectedVehicle.getVehicleId());
                rental.setRentalType("DAILY");
                rental.setStartDate(Timestamp.valueOf(start.atStartOfDay()));
                rental.setScheduledEndDate(Timestamp.valueOf(end.atStartOfDay()));
                rental.setPickupLocation(pickupLocation);
                rental.setInitialMileage(selectedVehicle.getMileage());
                rental.setBaseRate(baseRate);
                rental.setInsuranceFee(insuranceFee);
                rental.setAdditionalFees(additionalFeesSum);
                rental.setTotalAmount(total);

                if (rentalService.createRental(rental)) {
                    showAlert("Sucesso", "Locação registrada com sucesso!\nID: " + rental.getRentalId());
                    List<Vehicle> vehicles = vehicleService.getAvailableVehiclesByType(typeName);
                    vehicleTable.setItems(FXCollections.observableArrayList(vehicles));
                } else {
                    showAlert("Erro", "Erro ao registrar a locação.");
                }
            }
        } catch (NumberFormatException e) {
            showAlert("Erro", "Por favor, insira valores numéricos válidos.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(title.equals("Erro") ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
