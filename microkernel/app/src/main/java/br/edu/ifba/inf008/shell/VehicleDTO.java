package br.edu.ifba.inf008.shell;

import br.edu.ifba.inf008.interfaces.IVehicle;

public class VehicleDTO implements IVehicle {
    private String id;
    private String model;
    private String plate;

    public VehicleDTO(String id, String model, String plate){
        this.id = id;
        this.model = model;
        this.plate = plate;
    }

    public String getType() { return ""; }
    public String getId() { return id; }
    public String getModel() { return model; }
    public String getPlate() { return plate; }

    @Override
    public double finalValue(int days) {
        return 0;
    }

    @Override
    public boolean init() {
        return false;
    }
}
