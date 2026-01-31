package br.edu.ifba.inf008.interfaces;

public interface IVehicleTypePlugin extends IPlugin{
    String getType();

    double finalValue(int days);
}