package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.List;

public class LeasePlugin implements IPlugin {

    private IUIController uiController;
    private IDataController dataController;
    private IVehicleController vehicleController;
    private TableView<IVehicle> table;

    public boolean init() {

        this.uiController = ICore.getInstance().getUIController();
        this.dataController = ICore.getInstance().getDataController();
        this.vehicleController = ICore.getInstance().getVehicleController();

        MenuItem menuItem = uiController.createMenuItem("Locação", "Locação");

        menuItem.setOnAction(e ->
                uiController.createTab("Nova Locação", createMainLayout())
        );

        System.out.println(">>> PLUGIN DE LOCAÇÃO: Fui carregado com sucesso!");

        dataController.getVehicleList("SUV");
        return true;
    }

    private VBox createMainLayout() {
        VBox layout = new VBox(20);

        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                createEmailBox(),
                createVehicleBox(),
                createTableView(),
                createDatePicker(),
                createDatePicker(),
                createTextField("Local de Retirada", false),
                createTextField("R$ 0,00", true),
                createConfirmButton()
        );
        return layout;
    }

    private ComboBox<String> createEmailBox() {
        ComboBox<String> emails = new ComboBox<>();
        emails.setPromptText("Escolha um email:");

        try {
            emails.getItems().addAll(this.dataController.getClientsEmails());

        } catch (Exception e) {
            System.err.println("Falha ao buscar emails: " + e.getMessage());
        }
        return emails;
    }

    private ComboBox<String> createVehicleBox() {
        ComboBox<String> vehicles = new ComboBox<>();
        vehicles.setPromptText("Escolha um carro");

        try {
            vehicles.getItems().addAll(this.vehicleController.getVehiclesType());

            vehicles.setOnAction(e -> {
                String chosenType = vehicles.getValue();
                List<IVehicle> vehiclesList = this.dataController.getVehicleList(chosenType);
                this.table.getItems().clear();
                this.table.getItems().addAll(vehiclesList);
            });
        } catch (Exception e) {
            System.err.println("Erro ao buscar os carros: " + e.getMessage());
        }
        return vehicles;
    }

    private TableView<IVehicle> createTableView() {
        this.table = new TableView<>();

        TableColumn<IVehicle, String> colModel = new TableColumn<>("Modelo");

        colModel.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getModel()));

        TableColumn<IVehicle, String> colPlate = new TableColumn<>("Placa");
        colPlate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPlate()));

        this.table.getColumns().addAll(colModel, colPlate);

        return this.table;
    }

    private DatePicker createDatePicker() {
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Selecione uma data:");
//        datePicker.setMaxWidth(Double.MAX_VALUE);
        datePicker.setValue(LocalDate.now());
        return datePicker;
    }

    private TextField createTextField(String value, Boolean readOnly) {
        TextField textField = new TextField();
        if (readOnly) {
            textField.setText(value);
        } else {
            textField.setPromptText(value);
        }
        textField.setEditable(!readOnly);
        return textField;
    }

    private Button createConfirmButton() {
        Button confirmButton = new Button();
        confirmButton.setText("Confirmar");
        confirmButton.setMaxWidth(Double.MAX_VALUE);
        confirmButton.setPrefHeight(45);
        confirmButton.setStyle(
                "-fx-background-color: #3CB371;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;"
        );
        return confirmButton;
    }
}