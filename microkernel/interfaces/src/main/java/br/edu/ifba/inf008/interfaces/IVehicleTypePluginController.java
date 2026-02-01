package br.edu.ifba.inf008.interfaces;

import java.util.List;

public interface IVehicleTypePluginController {
    void registerVehicleType(IVehicleTypePlugin plugin);
    IVehicleTypePlugin getPluginByType(String vehicleType);

    List<String>  getVehiclesType();
}
