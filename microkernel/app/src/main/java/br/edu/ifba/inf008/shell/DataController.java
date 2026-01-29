package br.edu.ifba.inf008.shell;

import br.edu.ifba.inf008.interfaces.IDataController;
import br.edu.ifba.inf008.interfaces.IVehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class DataController implements IDataController {

    public List<String> getClientsEmails() {
        List<String> emails = new ArrayList<>();
        String url = "jdbc:mariadb://localhost:3307/car_rental_system";

        try (Connection conn = getConnection(url, "root", "root")) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT email FROM customers");
            while (rs.next()) {
                emails.add(rs.getString("email"));
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar e-mails: " + e.getMessage());
        }
        return emails;
    }

    public List<IVehicle> getVehicleList(String typeName) {
        List<IVehicle> vehicleList = new ArrayList<>();

        String sql = "SELECT v.vehicle_id, v.model, v.license_plate " +
                "FROM vehicles v " +
                "INNER JOIN vehicle_types vt ON v.type_id = vt.type_id " +
                "WHERE vt.type_name = ?";

        String url = "jdbc:mariadb://localhost:3307/car_rental_system";

        try (Connection conn = getConnection(url, "root", "root");
             PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, typeName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                vehicleList.add(new VehicleDTO(
                        rs.getString("vehicle_id"),
                        rs.getString("model"),
                        rs.getString("license_plate")
                ));
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar lista de veículos: " + e.getMessage());
            e.printStackTrace();
        }
        return vehicleList;
    }


    public List<String> getVehicle() {
        return List.of();
    }
}
