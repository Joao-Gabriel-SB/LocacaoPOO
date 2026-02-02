package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IVehicle;
import br.edu.ifba.inf008.interfaces.IVehicleTypePlugin;

public class ElectricPlugin implements IVehicleTypePlugin {
    @Override
    public String getType() {
        return "ELECTRIC";
    }

    @Override
    public double finalValue(int days, double baseRate, IVehicle vehicle) {
        double chargingFee = 25.00;
        double batteryDepletionFee = 50.00;

        return (baseRate * days) + chargingFee + batteryDepletionFee;
    }

    @Override
    public boolean init() {
        ICore.getInstance().getVehicleTypePluginController().registerVehicleType(this);
        return true;
    }
}
