package br.edu.ifba.inf008.shell;

import br.edu.ifba.inf008.interfaces.IVehicle;
import br.edu.ifba.inf008.interfaces.IVehicleTypePlugin;

public class VehicleDTO implements IVehicle {
    private final String id;
    private final String make;
    private final String model;
    private final String year;
    private final String fuel_type;
    private final String transmission;
    private final String mileage;
    private String type;

    public VehicleDTO(String id, String make, String model, String year, String fuel_type, String transmission, String mileage){
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.fuel_type = fuel_type;
        this.transmission = transmission;
        this.mileage = mileage;
//        this.type = type;
    }

    public String getId() {return id;}

    public String getMake() {return make;}

    public String getModel() {return model;}

    public String getYear() {return year;}

    public String getFuelType() {return fuel_type;}

    public String getTransmission() {return transmission;}

    public String getMileage() {return mileage;}

    public String getType() {return type;}
}
