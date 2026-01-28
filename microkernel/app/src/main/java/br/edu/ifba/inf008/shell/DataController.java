package br.edu.ifba.inf008.shell;

import br.edu.ifba.inf008.interfaces.IDataController;

import java.sql.Connection;
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

    @Override
    public List<String> getVehicle() {
        return List.of();
    }
}
