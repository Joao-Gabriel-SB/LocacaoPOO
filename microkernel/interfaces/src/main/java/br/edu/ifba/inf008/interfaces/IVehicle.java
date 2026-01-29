package br.edu.ifba.inf008.interfaces;

public interface IVehicle extends IPlugin{
    String getType();
    String getId();
    String getModel();
    String getPlate();

    double finalValue(int days);
}
