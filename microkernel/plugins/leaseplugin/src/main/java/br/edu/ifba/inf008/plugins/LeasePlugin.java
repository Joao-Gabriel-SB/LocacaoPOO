package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class LeasePlugin implements IPlugin {

    private IUIController uiController;
    private IDataController dataController;
    ICore core = ICore.getInstance();
    private IVehicleTypePluginController vehicleController;
    private RentalDAO dao;
    private TableView<IVehicle> table;

    private ComboBox<String> emails;
    private ComboBox<String> vehicles;
    private TextField pickupLocationField;
    private TextField baseRateField;
    private TextField insuranceFeeField;

    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private Spinner<LocalTime> startTimeSpinner;

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
                createLabeledDateTimePicker("Início Locação:", true),
                createLabeledDateTimePicker("Fim Locação:", false),
                createLabeledTextField("Local Retirada:", "Ex: Downtown", 100)
        );

        baseRateField = createLabeledCurrencyField("Valor Diária:", "0.00", layout);
        insuranceFeeField = createLabeledCurrencyField("Valor Seguro:", "0.00", layout);

        layout.getChildren().add(createConfirmButton());
        return layout;
    }

    private ComboBox<String> createEmailBox() {
        this.emails = new ComboBox<>();
        emails.setPromptText("Escolha um email:");

        try {
            emails.getItems().addAll(this.dao.getClientsEmails());

        } catch (Exception e) {
            System.err.println("Falha ao buscar emails: " + e.getMessage());
        }
        return this.emails;
    }

    private ComboBox<String> createVehicleBox() {
        this.vehicles = new ComboBox<>();
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
        return this.vehicles;
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

    private Spinner<LocalTime> createTimeSpinner() {
        Spinner<LocalTime> spinner = new Spinner<>();

        SpinnerValueFactory<LocalTime> valueFactory = new SpinnerValueFactory<>() {

            {
                setValue(LocalTime.now().withSecond(0).withNano(0));
            }

            @Override
            public void decrement(int steps) {
                setValue(getValue().minusMinutes(steps * 15));
            }

            @Override
            public void increment(int steps) {
                setValue(getValue().plusMinutes(steps * 15));
            }
        };

        spinner.setValueFactory(valueFactory);
        spinner.setEditable(true);
        spinner.setPrefWidth(90);

        return spinner;
    }

    private HBox createLabeledDateTimePicker(String labelText, boolean isStart) {
        Label label = new Label(labelText);
        label.setPrefWidth(120);

        DatePicker datePicker = new DatePicker(LocalDate.now());
        datePicker.setPrefWidth(160);

        Spinner<LocalTime> timeSpinner = createTimeSpinner();
        timeSpinner.setPrefWidth(90);

        if (isStart) {
            this.startDatePicker = datePicker;
            this.startTimeSpinner = timeSpinner;
        } else {
            this.endDatePicker = datePicker;
        }

        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER_LEFT);

        if (isStart) {
            hbox.getChildren().addAll(label, datePicker, timeSpinner);
        } else {
            hbox.getChildren().addAll(label, datePicker);
        }

        return hbox;
    }


    private TextField createLabeledCurrencyField(String labelText, String prompt, VBox parent) {
        Label label = new Label(labelText);
        label.setPrefWidth(120);

        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setPrefWidth(250);

        String regex = "\\d{0,8}([\\.,]\\d{0,2})?";
        field.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches(regex) ? change : null));

        HBox hbox = new HBox(10, label, field);
        hbox.setAlignment(Pos.CENTER_LEFT);

        parent.getChildren().add(hbox);

        return field;
    }

    private HBox createLabeledTextField(String labelText, String prompt, int limit) {
        Label label = new Label(labelText);
        label.setPrefWidth(120);

        this.pickupLocationField = new TextField();
        pickupLocationField.setPromptText(prompt);
        pickupLocationField.setPrefWidth(250);

        pickupLocationField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.length() > limit) pickupLocationField.setText(oldValue);
        });

        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.getChildren().addAll(label, pickupLocationField);
        return hbox;
    }

    private Button createConfirmButton() {
        Button confirmButton = new Button("Confirmar");
        confirmButton.setMaxWidth(Double.MAX_VALUE);
        confirmButton.setPrefHeight(45);
        confirmButton.setStyle(
                "-fx-background-color: #3CB371;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;"
        );

        confirmButton.setOnAction(e -> handleConfirm());

        return confirmButton;
    }

    private void handleConfirm() {

        IVehicle selectedVehicle = table.getSelectionModel().getSelectedItem();

        LocalDateTime startDate = LocalDateTime.of(
                startDatePicker.getValue(),
                startTimeSpinner.getValue()
        );

        LocalDateTime endDate = LocalDateTime.of(
                endDatePicker.getValue(),
                startTimeSpinner.getValue()
        );

        RentalDTO dto = new RentalDTO(
                emails.getValue(),
                vehicles.getValue(),
                selectedVehicle.getId(),
                startDate.toString(),
                endDate.toString(),
                pickupLocationField.getText(),
                baseRateField.getText(),
                insuranceFeeField.getText()
        );

        showConfirmationWindow(dto);
    }

    private void showConfirmationWindow(RentalDTO dto) {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Confirmar Locação");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        layout.getChildren().addAll(
                new Label("Email: " + dto.getEmail()),
                new Label("Veículo: " + dto.getVehicleId()),
                new Label("Início: " + dto.getStartDate()),
                new Label("Fim: " + dto.getScheduledEndDate()),
                new Label("Local: " + dto.getPickupLocation()),
                new Label("Diária: " + dto.getBaseRate()),
                new Label("Seguro: " + dto.getInsuranceFee())
        );

        Button confirm = new Button("Confirmar");
        Button cancel = new Button("Cancelar");

        confirm.setOnAction(e -> {
            // dao.save(dto); ← aqui você grava no banco
            stage.close();
        });

        cancel.setOnAction(e -> stage.close());

        layout.getChildren().add(new HBox(10, confirm, cancel));

        stage.setScene(new Scene(layout));
        stage.showAndWait();
    }
}