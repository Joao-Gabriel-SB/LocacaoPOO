package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IReportPlugin;
import br.edu.ifba.inf008.interfaces.IUIController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.List;


public class PieReportPlugin implements IReportPlugin {

    IUIController uiController;
    PieReportDAO dao;
    List<PieReportDTO> values;

    @Override
    public boolean init() {
        uiController = ICore.getInstance().getUIController();
        dao = new PieReportDAO();
        values = dao.getAnalyticsData();

        MenuItem menuItem = uiController.createMenuItem("Relatório 1", "Relatório 1");

        menuItem.setOnAction(e ->
                uiController.createTab("relatório 1", MainLayout())
        );

        return true;
    }

    public VBox MainLayout(){
        VBox layout = new VBox();
        layout.setPadding(new Insets(20));
        layout.getChildren().add(
                createPieChart()
        );

        return layout;
    }

    public PieChart createPieChart() {
        PieChart pieChart = new PieChart();
        pieChart.setTitle("Distribuição por tipo de combustível");
        VBox.setVgrow(pieChart, Priority.ALWAYS);
        values.forEach(dto -> {
                    String label = String.format("%s %s%%",dto.fuel_type,dto.percentage);
                    String color = "-fx-pie-color: " + dto.color + ";";
                    String details = String.format("Tipo: %s\nTotal: %d\nDisponíveis: %d\nAlugados: %d", dto.fuel_type, dto.total,dto.available,dto.rented);

                    PieChart.Data slice = new PieChart.Data(label, dto.total);
                    pieChart.getData().add(slice);

                    Tooltip tooltip = new Tooltip(details);
                    tooltip.setShowDelay(Duration.millis(0));

                    slice.getNode().setStyle(color);
                    Tooltip.install(slice.getNode(), tooltip);
                }
        );
        return pieChart;
    }

}
