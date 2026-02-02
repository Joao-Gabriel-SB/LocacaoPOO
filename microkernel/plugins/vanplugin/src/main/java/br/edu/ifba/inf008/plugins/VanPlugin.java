package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IVehicle;
import br.edu.ifba.inf008.interfaces.IVehicleTypePlugin;

public class VanPlugin implements IVehicleTypePlugin {
    @Override
    public String getType() {
        return "VAN";
    }

    @Override
    public double finalValue(int days, double baseRate, IVehicle vehicle) {
        double extraPassengerFee = 10.00;
        double driverFee = 50.00;

        return (baseRate * days) + extraPassengerFee + driverFee;
    }

    @Override
    public boolean init() {
        ICore.getInstance().getVehicleTypePluginController().registerVehicleType(this);
        return true;
    }
}
