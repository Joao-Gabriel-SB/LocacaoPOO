package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IDataController;
import br.edu.ifba.inf008.interfaces.IVehicle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


public class RentalDAO {
    ICore core = ICore.getInstance();
    IDataController dataController = core.getDataController();

    public void save(){

    }

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
}
