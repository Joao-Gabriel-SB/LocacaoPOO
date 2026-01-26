package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IPlugin;
import br.edu.ifba.inf008.interfaces.IUIController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class LeasePlugin implements IPlugin {
    public boolean init() {

        IUIController uiController = ICore.getInstance().getUIController();

//        MenuItem menuItem = uiController.createMenuItem("Menu 2", "Nova locação");

//        menuItem.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                System.out.println("I've been clicked!");
//            }
//        });

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(new Label("Nova Locação"));

        uiController.createTab("Locação", layout);

        System.out.println(">>> PLUGIN DE LOCAÇÃO: Fui carregado com sucesso!");
        return true;
    }
}