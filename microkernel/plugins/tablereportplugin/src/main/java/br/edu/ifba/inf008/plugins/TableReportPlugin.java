package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IReportPlugin;
import br.edu.ifba.inf008.interfaces.IUIController;
import br.edu.ifba.inf008.interfaces.IVehicle;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;

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

    private TableView<temporarydto> createTableView(){
        TableView<temporarydto> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getItems().addAll(dao.getValues());
        VBox.setVgrow(table,Priority.ALWAYS);

        TableColumn<temporarydto, String> columnId = new TableColumn<>("id");
        columnId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().rentalId()));

        TableColumn<temporarydto, String> columnClient = new TableColumn<>("Cliente");
        columnClient.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().customerName()));

        TableColumn<temporarydto, String> columnClientType = new TableColumn<>("Tipo de Cliente");
        columnClientType.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().customerType()));

        TableColumn<temporarydto, String> columnVehicle= new TableColumn<>("Veículo");
        columnVehicle.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().vehicle()));

        TableColumn<temporarydto, String> columnVehicleType = new TableColumn<>("Tipo de Veículo");
        columnVehicleType.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().vehicleType()));

        TableColumn<temporarydto, String> columnStartDate = new TableColumn<>("Data");
        columnStartDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().startDate()));

        TableColumn<temporarydto, String> columnTotalAmount = new TableColumn<>("Quantidade");
        columnTotalAmount.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().totalAmount().toString()));

        TableColumn<temporarydto, String> columnRentalStatus = new TableColumn<>("Aluguel");
        columnRentalStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().rentalStatus()));

        TableColumn<temporarydto, String> columnPaymentStatus = new TableColumn<>("Pagamento");
        columnPaymentStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().paymentStatus()));

        table.getColumns().addAll(columnId, columnClient, columnClientType, columnVehicle, columnVehicleType, columnStartDate,columnTotalAmount,columnRentalStatus,columnPaymentStatus);
        return table;
    }
}