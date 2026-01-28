package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IVehicle;

public class CompactPlugin implements IVehicle {
    @Override
    public String getType() {
        return "COMPACT";
    }

    @Override
    public double finalValue(int days) {
        return 0;
    }

    @Override
    public boolean init() {
        ICore.getInstance().getVehicleController().registerVehicleType(this);
        return true;
    }
}
