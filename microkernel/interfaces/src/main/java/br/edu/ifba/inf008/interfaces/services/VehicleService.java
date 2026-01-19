package br.edu.ifba.inf008.interfaces.services;

import br.edu.ifba.inf008.interfaces.DatabaseConnection;
import br.edu.ifba.inf008.interfaces.models.Vehicle;
import br.edu.ifba.inf008.interfaces.models.VehicleType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehicleService {

    public List<Vehicle> getAvailableVehiclesByType(String typeName) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = """
                SELECT v.vehicle_id, v.license_plate, v.make, v.model, v.year, v.type_id,
                       vt.type_name, v.color, v.fuel_type, v.transmission, v.mileage,
                       v.status, v.current_location
                FROM vehicles v
                JOIN vehicle_types vt ON v.type_id = vt.type_id
                WHERE vt.type_name = ? AND v.status = 'AVAILABLE'
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, typeName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setVehicleId(rs.getInt("vehicle_id"));
                vehicle.setLicensePlate(rs.getString("license_plate"));
                vehicle.setMake(rs.getString("make"));
                vehicle.setModel(rs.getString("model"));
                vehicle.setYear(rs.getInt("year"));
                vehicle.setTypeId(rs.getInt("type_id"));
                vehicle.setTypeName(rs.getString("type_name"));
                vehicle.setColor(rs.getString("color"));
                vehicle.setFuelType(rs.getString("fuel_type"));
                vehicle.setTransmission(rs.getString("transmission"));
                vehicle.setMileage(rs.getDouble("mileage"));
                vehicle.setStatus(rs.getString("status"));
                vehicle.setCurrentLocation(rs.getString("current_location"));
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicles;
    }

    public VehicleType getVehicleType(String typeName) {
        String sql = "SELECT * FROM vehicle_types WHERE type_name = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, typeName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                VehicleType type = new VehicleType();
                type.setTypeId(rs.getInt("type_id"));
                type.setTypeName(rs.getString("type_name"));
                type.setDailyRate(rs.getDouble("daily_rate"));
                type.setWeeklyRate(rs.getDouble("weekly_rate"));
                type.setMonthlyRate(rs.getDouble("monthly_rate"));
                type.setSecurityDeposit(rs.getDouble("security_deposit"));
                type.setInsuranceRate(rs.getDouble("insurance_rate"));
                type.setLateFeePerHour(rs.getDouble("late_fee_per_hour"));
                type.setMaxPassengers(rs.getInt("max_passengers"));
                type.setMaxLuggage(rs.getInt("max_luggage"));
                type.setSpecialFeatures(rs.getString("special_features"));

                // Parse JSON additional_fees
                String additionalFeesJson = rs.getString("additional_fees");
                if (additionalFeesJson != null) {
                    type.setAdditionalFees(parseAdditionalFees(additionalFeesJson));
                }

                return type;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateVehicleStatus(int vehicleId, String status) {
        String sql = "UPDATE vehicles SET status = ? WHERE vehicle_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, vehicleId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public double getVehicleMileage(int vehicleId) {
        String sql = "SELECT mileage FROM vehicles WHERE vehicle_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, vehicleId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("mileage");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private Map<String, Double> parseAdditionalFees(String json) {
        Map<String, Double> fees = new HashMap<>();
        json = json.replace("{", "").replace("}", "").replace("\"", "");
        String[] pairs = json.split(",");

        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                try {
                    Double value = Double.parseDouble(keyValue[1].trim());
                    fees.put(key, value);
                } catch (NumberFormatException e) {
                }
            }
        }

        return fees;
    }
}
