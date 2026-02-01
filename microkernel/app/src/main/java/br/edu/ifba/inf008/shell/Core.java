package br.edu.ifba.inf008.shell;

import br.edu.ifba.inf008.interfaces.*;

public class Core extends ICore {
    private Core() {}

    public static boolean init() {
        if (instance != null) {
            System.out.println("Fatal error: core is already initialized!");
            System.exit(-1);
        }

        instance = new Core();
        UIController.launch(UIController.class);

        return true;
    }
    public IDataController getDataController() { return dataController; };
    public IVehicleTypePluginController getVehicleTypePluginController() { return vehicleController; }


    public IUIController getUIController() {
        return UIController.getInstance();
    }

    public IAuthenticationController getAuthenticationController() {
        return authenticationController;
    }

    public IIOController getIOController() {
        return ioController;
    }

    public IPluginController getPluginController() {
        return pluginController;
    }

    @Override
    public IVehicle buildNewVehicle(String id, String make, String model, String year, String fuel_type, String transmission, String mileage) {
        return new VehicleDTO(id,make,model,year,fuel_type,transmission,mileage);
    }

    @Override
    public IRental buildRental(String email, String type_name, String vehicle_id, String start_date, String scheduled_end_date, String pickup_location, String base_rate, String insurance_fee) {
        return new RentalDTO(email, type_name, vehicle_id, start_date, scheduled_end_date, pickup_location, base_rate, insurance_fee);
    }

    private final IAuthenticationController authenticationController = new AuthenticationController();
    private final IIOController ioController = new IOController();
    private final IPluginController pluginController = new PluginController();
    private final IDataController dataController = new DataController();
    private final IVehicleTypePluginController vehicleController = new VehicleTypePluginController();
}
