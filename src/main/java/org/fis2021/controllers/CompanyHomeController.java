package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;

public class CompanyHomeController {

    @FXML
    private ListView<AnchorPane> listView;

    public void handleButtonAction() {
        int index = listView.getItems().size()+1;

        Label text = new Label();
        text.setText(index + ". station x");

        Button button = new Button();
        button.setText("delete");

        AnchorPane container = new AnchorPane();
        container.getChildren().addAll(text, button);
        container.getChildren().get(1).setLayoutX(740);

        listView.getItems().add(container);
    }
}
