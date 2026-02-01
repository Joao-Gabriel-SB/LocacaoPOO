package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IVehicleTypePlugin;

public class EconomyPlugin implements IVehicleTypePlugin {
    @Override
    public String getType() {
        return "ECONOMY";
    }

    @Override
    public double finalValue(int days) {
        return 0;
    }

    @Override
    public boolean init() {
        ICore.getInstance().getVehicleTypePluginController().registerVehicleType(this);
        return true;
    }
}
