package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IReportPlugin;
import br.edu.ifba.inf008.interfaces.IUIController;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class TableReportPlugin implements IReportPlugin {

    private ICore core;
    private IUIController uiController;
    private TableReportDAO dao;

    @Override
    public boolean init() {
        core = ICore.getInstance();
        uiController = core.getUIController();
        dao = new TableReportDAO();

        MenuItem menu = uiController.createMenuItem("Relatório 2", "Relatório 2");
        menu.setOnAction( e->{
                    uiController.createTab("Relatório 2",mainLayout());
                }
        );
        return true;
    }

    private VBox mainLayout(){
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(50));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(createTitle("Relatório 2"),createTableView());
        return layout;
    }

    private Label createTitle(String text){
        Label title = new Label(text);
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");
        return title;
    }

    private TableView<TableRentalDTO> createTableView(){
        TableView<TableRentalDTO> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getItems().addAll(dao.getValues());
        VBox.setVgrow(table,Priority.ALWAYS);

        TableColumn<TableRentalDTO, String> columnId = new TableColumn<>("id");
        columnId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRentalId()));

        TableColumn<TableRentalDTO, String> columnClient = new TableColumn<>("Cliente");
        columnClient.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomerName()));

        TableColumn<TableRentalDTO, String> columnClientType = new TableColumn<>("Tipo de Cliente");
        columnClientType.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomerType()));

        TableColumn<TableRentalDTO, String> columnVehicle= new TableColumn<>("Veículo");
        columnVehicle.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getVehicle()));

        TableColumn<TableRentalDTO, String> columnVehicleType = new TableColumn<>("Tipo de Veículo");
        columnVehicleType.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getVehicleType()));

        TableColumn<TableRentalDTO, String> columnStartDate = new TableColumn<>("Data");
        columnStartDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStartDate()));

        TableColumn<TableRentalDTO, String> columnTotalAmount = new TableColumn<>("Quantidade");
        columnTotalAmount.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTotalAmount().toString()));

        TableColumn<TableRentalDTO, String> columnRentalStatus = new TableColumn<>("Aluguel");
        columnRentalStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRentalStatus()));

        TableColumn<TableRentalDTO, String> columnPaymentStatus = new TableColumn<>("Pagamento");
        columnPaymentStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPaymentStatus()));

        table.getColumns().addAll(columnId, columnClient, columnClientType, columnVehicle, columnVehicleType, columnStartDate,columnTotalAmount,columnRentalStatus,columnPaymentStatus);
        return table;
    }
}