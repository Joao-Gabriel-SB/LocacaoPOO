package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.List;

public class LeasePlugin implements IPlugin {

    private IUIController uiController;
    private IDataController dataController;
    private IVehicleTypePluginController vehicleController;
    private RentalDAO dao;
    private TableView<IVehicle> table;

    public boolean init() {

        this.uiController = ICore.getInstance().getUIController();
        this.dataController = ICore.getInstance().getDataController();
        this.vehicleController = ICore.getInstance().getVehicleTypePluginController();
        this.dao = new RentalDAO();

        MenuItem menuItem = uiController.createMenuItem("Locação", "Locação");

        menuItem.setOnAction(e ->
                uiController.createTab("Nova Locação", createMainLayout())
        );

        System.out.println(">>> PLUGIN DE LOCAÇÃO: Fui carregado com sucesso!");

//        dataController.getVehicleList("SUV");
        return true;
    }

    private VBox createMainLayout() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));

        layout.getChildren().addAll(
                createEmailBox(),
                createVehicleBox(),
                createTableView(),
                new Separator(),
                createLabeledDatePicker("Início Locação:"),
                createLabeledDatePicker("Fim Locação:"),
                createLabeledTextField("Local Retirada:", "Ex: Downtown", 100),
                createLabeledCurrencyField("Valor Diária:", "0.00"),
                createLabeledCurrencyField("Valor Seguro:", "0.00"),
                createConfirmButton()
        );
        return layout;
    }

    private ComboBox<String> createEmailBox() {
        ComboBox<String> emails = new ComboBox<>();
        emails.setPromptText("Escolha um email:");

        try {
            emails.getItems().addAll(this.dao.getClientsEmails());

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
                List<IVehicle> vehiclesList = this.dao.getVehiclesByType(chosenType);
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

        TableColumn<IVehicle, String> colMake = new TableColumn<>("Marca");
        colMake.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMake()));

        TableColumn<IVehicle, String> colModel = new TableColumn<>("Modelo");
        colModel.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getModel()));

        TableColumn<IVehicle, String> colYear = new TableColumn<>("Ano");
        colYear.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getYear()));

        TableColumn<IVehicle, String> colFuelType = new TableColumn<>("Combustível");
        colFuelType.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFuelType()));

        TableColumn<IVehicle, String> colTransmission = new TableColumn<>("Câmbio");
        colTransmission.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTransmission()));

        TableColumn<IVehicle, String> colMileage = new TableColumn<>("Quilometragem");
        colMileage.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMileage()));

        this.table.getColumns().addAll(colMake, colModel, colYear, colFuelType, colTransmission, colMileage);

        return this.table;
    }

    private HBox createLabeledDatePicker(String labelText) {
        Label label = new Label(labelText);
        label.setPrefWidth(120);

        DatePicker datePicker = new DatePicker();
        datePicker.setPrefWidth(250);
        datePicker.setValue(LocalDate.now());

        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.getChildren().addAll(label, datePicker);
        return hbox;
    }

    private HBox createLabeledCurrencyField(String labelText, String prompt) {
        Label label = new Label(labelText);
        label.setPrefWidth(120);

        TextField textField = new TextField();
        textField.setPromptText(prompt);
        textField.setPrefWidth(250);

        String regex = "\\d{0,8}([\\.,]\\d{0,2})?";
        textField.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches(regex) ? change : null));

        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.getChildren().addAll(label, textField);
        return hbox;
    }

    private HBox createLabeledTextField(String labelText, String prompt, int limit) {
        Label label = new Label(labelText);
        label.setPrefWidth(120);

        TextField textField = new TextField();
        textField.setPromptText(prompt);
        textField.setPrefWidth(250);

        textField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.length() > limit) textField.setText(oldValue);
        });

        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.getChildren().addAll(label, textField);
        return hbox;
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