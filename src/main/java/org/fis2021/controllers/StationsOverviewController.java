package org.fis2021.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.fis2021.ApplicationHelper;
import org.fis2021.model.Stations;
import org.fis2021.services.StationsService;

import java.util.ArrayList;
import java.util.List;

public class StationsOverviewController {

    @FXML
    private Label companyName;

    @FXML
    private Label totalStations;

    @FXML
    private ListView<?> listView;

    public void initialize() {
        companyName.setText(ApplicationHelper.companyName);
        ArrayList<Stations> list = StationsService.getAllStations();
        totalStations.setText(String.valueOf(list.size()));
    }


}

