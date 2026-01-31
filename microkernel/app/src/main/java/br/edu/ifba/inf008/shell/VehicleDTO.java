package br.edu.ifba.inf008.shell;

import br.edu.ifba.inf008.interfaces.IVehicle;

public class VehicleDTO implements IVehicle {
    private String id;
    private String make;
    private String model;
    private String year;
    private String fuel_type;
    private String transmission;
    private String mileage;

    public VehicleDTO(String id, String make, String model, String year, String fuel_type, String transmission, String mileage){
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.fuel_type = fuel_type;
        this.transmission = transmission;
        this.mileage = mileage;
    }

    public String getType() { return ""; }
    public String getId() { return id; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public String getYear() { return year; }
    public String getFuelType() { return fuel_type; }
    public String getTransmission() { return transmission; }
    public String getMileage() { return mileage; }

    @Override
    public double finalValue(int days) {
        return 0;
    }

    @Override
    public boolean init() {
        return false;
    }
}
