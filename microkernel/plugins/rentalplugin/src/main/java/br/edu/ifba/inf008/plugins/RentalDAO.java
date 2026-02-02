package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IDataController;
import br.edu.ifba.inf008.interfaces.IVehicle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class RentalDAO {
    ICore core = ICore.getInstance();
    IDataController dataController = core.getDataController();

    public List<IVehicle> getVehiclesByType(String type){

        return dataController.search( conn -> {

           List<IVehicle> list = new ArrayList<>();

           String sql = "SELECT v.vehicle_id, v.make, v.model, v.year, v.fuel_type, v.transmission, v.mileage " +
                   "FROM vehicles v " +
                   "INNER JOIN vehicle_types vt ON v.type_id = vt.type_id " +
                   "WHERE vt.type_name = ? AND v.status = 'AVAILABLE'";

           try (PreparedStatement stmt = conn.prepareStatement(sql)){

                stmt.setString(1,type);

                try (ResultSet rs = stmt.executeQuery()){
                    while (rs.next()){
                        list.add( core.buildNewVehicle(
                                rs.getString("vehicle_id"),
                                rs.getString("make"),
                                rs.getString("model"),
                                rs.getString("year"),
                                rs.getString("fuel_type"),
                                rs.getString("transmission"),
                                rs.getString("mileage")
                        ));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }
            return list;
        });
    }
    public List<String> getClientsEmails(){
        return dataController.search(conn -> {
           List<String> emails = new ArrayList<>();

           try (PreparedStatement stmt = conn.prepareStatement("SELECT email FROM customers")){
               ResultSet rs = stmt.executeQuery();
               while (rs.next()){
                   emails.add(
                           rs.getString("email")
                   );
               }
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }
           return emails;
        });
    }

    public int save(RentalDTO dto, double totalAmount, String initialMileage) {

        return dataController.search(conn -> {

            try {
                int customerId = -1;
                String findCustomerSql = "SELECT customer_id FROM customers WHERE email = ?";

                try (PreparedStatement findStmt = conn.prepareStatement(findCustomerSql)) {
                    findStmt.setString(1, dto.getEmail());

                    try (ResultSet rs = findStmt.executeQuery()) {
                        if (rs.next()) {
                            customerId = rs.getInt("customer_id");
                        }
                    }
                }

                if (customerId == -1) {
                    throw new RuntimeException("Cliente não encontrado para o email: " + dto.getEmail());
                }

                java.sql.Timestamp startTs =
                        java.sql.Timestamp.valueOf(java.time.LocalDateTime.parse(dto.getStartDate()));
                java.sql.Timestamp endTs =
                        java.sql.Timestamp.valueOf(java.time.LocalDateTime.parse(dto.getScheduledEndDate()));

                double baseRate = parseDecimal(dto.getBaseRate());
                double insuranceFee = parseDecimal(dto.getInsuranceFee());
                double initMileage = parseDecimal(initialMileage);

                String insertSql =
                        "INSERT INTO rentals (" +
                                "customer_id, vehicle_id, rental_type, start_date, scheduled_end_date, " +
                                "pickup_location, initial_mileage, base_rate, insurance_fee, total_amount" +
                                ") VALUES (?, ?, 'DAILY', ?, ?, ?, ?, ?, ?, ?)";

                int rentalId;

                try (PreparedStatement ins = conn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS)) {

                    ins.setInt(1, customerId);
                    ins.setInt(2, Integer.parseInt(dto.getVehicleId()));
                    ins.setTimestamp(3, startTs);
                    ins.setTimestamp(4, endTs);
                    ins.setString(5, dto.getPickupLocation());
                    ins.setBigDecimal(6, java.math.BigDecimal.valueOf(initMileage));
                    ins.setBigDecimal(7, java.math.BigDecimal.valueOf(baseRate));
                    ins.setBigDecimal(8, java.math.BigDecimal.valueOf(insuranceFee));
                    ins.setBigDecimal(9, java.math.BigDecimal.valueOf(totalAmount));

                    int affected = ins.executeUpdate();
                    if (affected == 0) {
                        throw new RuntimeException("Falha ao inserir rental.");
                    }

                    try (ResultSet keys = ins.getGeneratedKeys()) {
                        if (keys.next()) {
                            rentalId = keys.getInt(1);
                        } else {
                            throw new RuntimeException("Falha ao obter rental_id.");
                        }
                    }
                }

                String updateVehicleSql =
                        "UPDATE vehicles SET status = 'RENTED' WHERE vehicle_id = ?";

                try (PreparedStatement upd = conn.prepareStatement(updateVehicleSql)) {
                    upd.setInt(1, Integer.parseInt(dto.getVehicleId()));
                    upd.executeUpdate();
                }

                return rentalId;

            } catch (SQLException e) {
                throw new RuntimeException("Erro ao salvar locação: " + e.getMessage(), e);
            }

        });
    }



    private double parseDecimal(String s) {
        if (s == null || s.isBlank()) return 0.0;
        return Double.parseDouble(s.replace(",", "."));
    }
}
