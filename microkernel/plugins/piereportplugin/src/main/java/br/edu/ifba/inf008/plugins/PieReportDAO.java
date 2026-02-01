package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IDataController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PieReportDAO {
    ICore core = ICore.getInstance();
    IDataController dataController = ICore.getInstance().getDataController();

    public List<PieReportDTO> getAnalyticsData() {
        List<PieReportDTO> values = new ArrayList<>();


        String sql = "SELECT \n" +
                "    v.fuel_type,\n" +
                "    COUNT(*) AS vehicle_count,\n" +
                "    SUM(CASE WHEN v.status = 'AVAILABLE' THEN 1 ELSE 0 END) AS available_count,\n" +
                "    SUM(CASE WHEN v.status = 'RENTED' THEN 1 ELSE 0 END) AS rented_count,\n" +
                "    ROUND(COUNT(*) * 100.0 / SUM(COUNT(*)) OVER (), 2) AS fleet_percentage,\n" +
                "    CASE v.fuel_type\n" +
                "        WHEN 'GASOLINE' THEN '#FF6B6B'\n" +
                "        WHEN 'DIESEL' THEN '#4ECDC4'\n" +
                "        WHEN 'ELECTRIC' THEN '#45B7D1'\n" +
                "        WHEN 'HYBRID' THEN '#96CEB4'\n" +
                "        WHEN 'CNG' THEN '#FFEAA7'\n" +
                "        ELSE '#D9D9D9'\n" +
                "    END AS chart_color\n" +
                "FROM vehicles v\n" +
                "GROUP BY v.fuel_type\n" +
                "ORDER BY vehicle_count DESC";


        return dataController.search(conn -> {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    values.add(new PieReportDTO(
                            rs.getString("fuel_type"),
                            rs.getInt("vehicle_count"),
                            rs.getInt("available_count"),
                            rs.getInt("rented_count"),
                            rs.getDouble("fleet_percentage"),
                            rs.getString("chart_color")
                    ));
                }
                return values;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
