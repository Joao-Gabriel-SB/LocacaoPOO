package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IDataController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TableReportDAO {
    private final ICore core = ICore.getInstance();
    private final IDataController dataController = core.getDataController();

    public List<temporarydto> getValues() {
        List<temporarydto> values = new ArrayList<>();

        String sql = "SELECT \n" +
                "    r.rental_id,\n" +
                "    COALESCE(c.company_name, CONCAT(c.first_name, ' ', c.last_name)) as customer_name,\n" +
                "    c.customer_type,\n" +
                "    CONCAT(v.make, ' ', v.model) as vehicle,\n" +
                "    vt.type_name as vehicle_type,\n" +
                "    DATE_FORMAT(r.start_date, '%Y-%m-%d') as start_date,\n" +
                "    r.total_amount,\n" +
                "    r.rental_status,\n" +
                "    r.payment_status\n" +
                "FROM rentals r\n" +
                "JOIN customers c ON r.customer_id = c.customer_id\n" +
                "JOIN vehicles v ON r.vehicle_id = v.vehicle_id\n" +
                "JOIN vehicle_types vt ON v.type_id = vt.type_id\n" +
                "ORDER BY r.start_date DESC\n" +
                "LIMIT 100";

        return dataController.search(conn -> {
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        ResultSet rs = stmt.executeQuery();

                        while (rs.next()) {
                            values.add(
                                    new temporarydto(
                                            rs.getString("rental_id"),
                                            rs.getString("customer_name"),
                                            rs.getString("customer_type"),
                                            rs.getString("vehicle"),
                                            rs.getString("vehicle_type"),
                                            rs.getString("start_date"),
                                            rs.getDouble("total_amount"),
                                            rs.getString("rental_status"),
                                            rs.getString("payment_status")
                                    )
                            );
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return values;
                }
        );
    }
}
