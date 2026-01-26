package br.edu.ifba.inf008.shell;

import br.edu.ifba.inf008.interfaces.*;
import javafx.application.Application;
import javafx.application.Platform;

public class Core extends ICore {
    private Core() {}

    public static boolean init() {
        if (instance != null) {
            System.out.println("Fatal error: core is already initialized!");
            System.exit(-1);
        }

        instance = new Core();
        UIController.launch(UIController.class);

        System.out.println("E-mails carregados: " + (Core.instance).getClientesEmails());

        return true;
    }

    public java.util.List<String> getClientesEmails() {

        java.util.List<String> emails = new java.util.ArrayList<>();
        String url = "jdbc:mariadb://localhost:3307/car_rental_system";

        try (java.sql.Connection conn = java.sql.DriverManager.getConnection(url, "root", "root")) {
            java.sql.ResultSet rs = conn.createStatement().executeQuery("SELECT email FROM customers");
            while (rs.next()) {
                emails.add(rs.getString("email"));
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar e-mails: " + e.getMessage());
        }
        return emails;
    }

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

    private IAuthenticationController authenticationController = new AuthenticationController();
    private IIOController ioController = new IOController();
    private IPluginController pluginController = new PluginController();
}
