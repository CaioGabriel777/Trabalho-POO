package br.edu.ifba.inf008.interfaces.services;

import br.edu.ifba.inf008.interfaces.DatabaseConnection;
import br.edu.ifba.inf008.interfaces.models.Rental;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class RentalService {

    private VehicleService vehicleService = new VehicleService();

    public boolean createRental(Rental rental) {
        String sql = """
                INSERT INTO rentals (customer_id, vehicle_id, rental_type, start_date, scheduled_end_date,
                                     pickup_location, initial_mileage, base_rate, insurance_fee,
                                     additional_fees, total_amount, rental_status, payment_status)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'ACTIVE', 'PENDING')
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, rental.getCustomerId());
            stmt.setInt(2, rental.getVehicleId());
            stmt.setString(3, rental.getRentalType());
            stmt.setTimestamp(4, rental.getStartDate());
            stmt.setTimestamp(5, rental.getScheduledEndDate());
            stmt.setString(6, rental.getPickupLocation());
            stmt.setDouble(7, rental.getInitialMileage());
            stmt.setDouble(8, rental.getBaseRate());
            stmt.setDouble(9, rental.getInsuranceFee());
            stmt.setDouble(10, rental.getAdditionalFees());
            stmt.setDouble(11, rental.getTotalAmount());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    rental.setRentalId(generatedKeys.getInt(1));
                }

                vehicleService.updateVehicleStatus(rental.getVehicleId(), "RENTED");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
