package br.edu.ifba.inf008.shell;

import br.edu.ifba.inf008.interfaces.IVehicleTypePlugin;
import br.edu.ifba.inf008.interfaces.IVehicleTypePluginController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehicleTypePluginController implements IVehicleTypePluginController {

    private final Map<String, IVehicleTypePlugin> pluginMap = new HashMap<>();

    public void registerVehicleType(IVehicleTypePlugin plugin) {
        String key = plugin.getType().toUpperCase();
        this.pluginMap.put(key,plugin);
        System.out.println("Plugin:" + plugin.getType() + " registrado!");
    }

    public IVehicleTypePlugin getPluginByType(String vehicleType) {
        return this.pluginMap.get(vehicleType);
    }

    public List<String> getVehiclesType() {
        return new ArrayList<>(this.pluginMap.keySet());
    }

}
