package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IVehicle;
import br.edu.ifba.inf008.interfaces.IVehicleTypePlugin;

public class LuxuryPlugin implements IVehicleTypePlugin {
    @Override
    public String getType() {
        return "LUXURY";
    }

    @Override
    public double finalValue(int days, double baseRate, IVehicle vehicle) {
        double conciergeFee = 100.00;
        double chauffeurFee = 200.00;

        return (baseRate * days) + conciergeFee + chauffeurFee;
    }

    @Override
    public boolean init() {
        ICore.getInstance().getVehicleTypePluginController().registerVehicleType(this);
        return true;
    }
}
