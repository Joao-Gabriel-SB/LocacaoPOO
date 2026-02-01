package br.edu.ifba.inf008.interfaces;

public abstract class ICore
{
    public static ICore getInstance() {
        return instance;
    }

    public abstract IUIController getUIController();
    public abstract IAuthenticationController getAuthenticationController();
    public abstract IIOController getIOController();
    public abstract IPluginController getPluginController();
    public abstract IDataController getDataController();
    public abstract IVehicleTypePluginController getVehicleTypePluginController();
    public abstract IVehicle buildNewVehicle(String id, String make, String model, String year, String fuel_type, String transmission, String mileage);

    protected static ICore instance = null;
}
