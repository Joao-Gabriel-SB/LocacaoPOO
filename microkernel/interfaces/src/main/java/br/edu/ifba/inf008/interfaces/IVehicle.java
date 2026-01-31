package br.edu.ifba.inf008.interfaces;

public interface IVehicle extends IPlugin{
    String getType();
    String getId();
    String getMake();
    String getModel();
    String getYear();
    String getFuelType();
    String getTransmission();
    String getMileage();

    double finalValue(int days);
}
