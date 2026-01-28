package br.edu.ifba.inf008.interfaces;

public interface IVehicle extends IPlugin{
    String getType();

    double finalValue(int days);
}
