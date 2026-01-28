package br.edu.ifba.inf008.interfaces;

import java.util.List;

public interface IVehicleController {
    void registerVehicleType(IVehicle plugin);
    IVehicle getPluginByType(String vehicleType);

    List<String>  getVehiclesType();
}
