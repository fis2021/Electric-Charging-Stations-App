package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.fis2021.ApplicationHelper;
import org.fis2021.model.Company;
import org.fis2021.model.Stations;
import org.fis2021.services.StationsService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class StationsOverviewController {

    @FXML
    private Label companyName;

    @FXML
    private Label totalStations;

    @FXML
    private ListView<AnchorPane> listView;

    @FXML
    private Button backHomeControllerButton;

    @FXML
    private Company company;

    public void backToTheHomeController() {
        try {
            Stage stage = (Stage) backHomeControllerButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/fis2021/CompanyMainScene.fxml"));
            Parent homeRoot = loader.load();
            CompanyHomeController controller = loader.getController();
            controller.setCompany(company);
            Scene scene = new Scene(homeRoot, 800, 700);
            stage.setTitle("Electric Charging Stations Application - Stations Overview");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        companyName.setText(ApplicationHelper.companyName);
        ArrayList<Stations> list = StationsService.getAllStations();
        totalStations.setText(String.valueOf(list.size()));
        ArrayList<String> stations = StationsService.getAllStationsFromCompanyCity();
        Collections.sort(stations);
        String previous = "";
        for(String station : stations) {
            //int index = listView.getItems().size();
            if(previous.equals(station)) {
                continue;
            }
            int index = 0;
            Label text = new Label();
            for(String station1 : stations) {
                if(station1.equals(station)) {
                    index++;
                }
            }
            text.setText(String.format(" %s - %d", station, index));
            AnchorPane container = new AnchorPane();
            container.getChildren().addAll(text);
            listView.getItems().add(container);
            previous = station;
        }
    }

}

