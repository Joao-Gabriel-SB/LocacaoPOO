package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IReportPlugin;
import br.edu.ifba.inf008.interfaces.IUIController;
import javafx.scene.chart.PieChart;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;


public class PieReportPlugin implements IReportPlugin {

    IUIController uiController = ICore.getInstance().getUIController();
    @Override
    public boolean init() {
        MenuItem menuItem = uiController.createMenuItem("Relatório 1", "Relatório 1");

        VBox teste = new VBox(15);
        teste.getChildren().add(pieChart());

        menuItem.setOnAction(e->
                uiController.createTab("relatório 1",teste)
        );

        return true;
    }

    public PieChart pieChart(){
        PieChart pieChart = new PieChart();
        PieChart.Data teste = new PieChart.Data("SUV", 15);
        PieChart.Data teste2 = new PieChart.Data("SUV", 16);

        pieChart.getData().addAll(teste,teste2);
        return pieChart;
    }

}
