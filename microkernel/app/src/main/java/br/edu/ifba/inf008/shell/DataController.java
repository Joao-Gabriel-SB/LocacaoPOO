package br.edu.ifba.inf008.shell;

import br.edu.ifba.inf008.interfaces.IDataController;
import br.edu.ifba.inf008.interfaces.ISqlQuery;
import br.edu.ifba.inf008.interfaces.ISqlTransac;
import br.edu.ifba.inf008.interfaces.IVehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class DataController implements IDataController {

    private String url = "jdbc:mariadb://localhost:3307/car_rental_system";
    private String user = "root";
    private String pass = "root";

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

        String sql = "SELECT v.vehicle_id, v.make, v.model, v.year, v.fuel_type, v.transmission, v.mileage " +
                "FROM vehicles v " +
                "INNER JOIN vehicle_types vt ON v.type_id = vt.type_id " +
                "WHERE vt.type_name = ? AND v.status = 'AVAILABLE'";

        String url = "jdbc:mariadb://localhost:3307/car_rental_system";

        try (Connection conn = getConnection(url, "root", "root");
             PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, typeName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                vehicleList.add(new VehicleDTO(
                        rs.getString("vehicle_id"),
                        rs.getString("make"),
                        rs.getString("model"),
                        rs.getString("year"),
                        rs.getString("fuel_type"),
                        rs.getString("transmission"),
                        rs.getString("mileage")
                ));
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar lista de veículos: " + e.getMessage());
            e.printStackTrace();
        }
        return vehicleList;
    }

    @Override
    public <T> T search(ISqlQuery<T> order) {
        try (Connection conn = getConnection(url,user, pass)){
            return order.search(conn);
        } catch (Exception e) {
            throw new RuntimeException("Erro na busca do plugin", e);
        }
    }

    @Override
    public void runTransaction(ISqlTransac order) {
        try (Connection conn = getConnection(url, user, pass)){
            order.runTransaction(conn);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
