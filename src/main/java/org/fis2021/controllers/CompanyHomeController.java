package org.fis2021.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.fis2021.exceptions.StationAlreadyExistsException;
import org.fis2021.exceptions.UsernameAlreadyExistsException;
import org.fis2021.model.Company;
import org.fis2021.model.Stations;
import org.fis2021.services.StationsService;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.fis2021.App.loadFXML;

public class CompanyHomeController {

    @FXML
    private Button newStationButton;

    @FXML
    private ListView<AnchorPane> listView;

    private Company company;

    public void setCompany(Company c) {
        company = c;
    }

    ObservableList list = FXCollections.observableArrayList();

    public void handleButtonAction() {
        try {
            Stage stage = (Stage) newStationButton.getScene().getWindow();
            Scene scene = new Scene(loadFXML("AddStation"), 650, 400);
            stage.setTitle("Electric Charging Stations Application - Add Station");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        ArrayList<String> stations = StationsService.getAllStations();
        list.addAll(stations);
        for(String station : stations) {
            int index = listView.getItems().size()+1;
            Label text = new Label();
            text.setText(String.format("%d. %s",index,station));

            Button button = new Button();
            button.setText("delete");

            AnchorPane container = new AnchorPane();
            container.getChildren().addAll(text, button);
            container.getChildren().get(1).setLayoutX(740);
            listView.getItems().add(container);

            button.setOnAction((event)->{
                StationsService.deleteStation(station);
                listView.getItems().remove(container);
            });
        }
    }
}
