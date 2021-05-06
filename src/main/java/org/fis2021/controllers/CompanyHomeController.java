package org.fis2021.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import org.fis2021.exceptions.UsernameAlreadyExistsException;
import org.fis2021.model.Stations;
import org.fis2021.services.StationsService;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CompanyHomeController {

    @FXML
    private ListView<AnchorPane> listView;

    ObservableList list = FXCollections.observableArrayList();

    public void handleButtonAction() {
//        list.removeAll();
//        ArrayList<String> stations = StationsService.getAllStations();
//        list.addAll(stations);

        int index = listView.getItems().size()+1;

//        listView.getItems().addAll(list);
        Label text = new Label();
        text.setText(index + ". station x");

        Button button = new Button();
        button.setText("delete");

        AnchorPane container = new AnchorPane();
        container.getChildren().addAll(text, button);
        container.getChildren().get(1).setLayoutX(740);

        try {
            StationsService.addStation(text.getText());
        } catch(UsernameAlreadyExistsException ignored) {

        }


        listView.getItems().add(container);
    }
    public void initialize() {
        ArrayList<String> stations = StationsService.getAllStations();
        for(String station : stations) {
            int index = listView.getItems().size()+1;
            Label text = new Label();
            text.setText(station);

            Button button = new Button();
            button.setText("delete");

            AnchorPane container = new AnchorPane();
            container.getChildren().addAll(text, button);
            container.getChildren().get(1).setLayoutX(740);
            listView.getItems().add(container);
        }
    }
}
