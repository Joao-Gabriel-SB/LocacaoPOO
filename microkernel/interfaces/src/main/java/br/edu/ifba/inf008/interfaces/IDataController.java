package br.edu.ifba.inf008.interfaces;

import java.sql.Connection;
import java.util.List;

public interface IDataController {
    List<String> getClientsEmails();
//    List<String> getVehicle();
    List<IVehicle> getVehicleList(String typeName);


    void runTransaction(ISqlTransac order);
    <T> T search(ISqlQuery<T> order);
}
