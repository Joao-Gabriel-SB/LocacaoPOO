package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IVehicle;
import br.edu.ifba.inf008.interfaces.IVehicleTypePlugin;

public class CompactPlugin implements IVehicleTypePlugin {
    @Override
    public String getType() {
        return "COMPACT";
    }

    @Override
    public double finalValue(int days, double baseRate, IVehicle vehicle) {
        return baseRate * days;
    }

    @Override
    public boolean init() {
        ICore.getInstance().getVehicleTypePluginController().registerVehicleType(this);
        return true;
    }
}
