package org.fis2021.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.fis2021.services.StationsService;

import java.util.ArrayList;

public class StationsOverviewController {

    @FXML
    private Label companyName;

    @FXML
    private Label totalStations;

    @FXML
    private ListView<?> listView;

    ObservableList list = FXCollections.observableArrayList();


}

