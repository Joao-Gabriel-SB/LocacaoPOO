package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IVehicle;
import br.edu.ifba.inf008.interfaces.IVehicleTypePlugin;

public class SuvPlugin implements IVehicleTypePlugin {
    @Override
    public String getType() {
        return "SUV";
    }

    @Override
    public double finalValue(int days, double baseRate, IVehicle vehicle) {
        double offroadFee = 50.00;
        return (baseRate * days) + offroadFee;
    }

    @Override
    public boolean init() {
        ICore.getInstance().getVehicleTypePluginController().registerVehicleType(this);
        return true;
    }
}
