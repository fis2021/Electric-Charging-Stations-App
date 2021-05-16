package org.fis2021.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.dizitart.no2.objects.ObjectRepository;
import org.fis2021.ApplicationHelper;
import org.fis2021.model.Stations;
import org.fis2021.services.StationsService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static org.fis2021.App.loadFXML;


public class StationController {

    @FXML
    private AnchorPane stationAnchorPane;

    @FXML
    private ComboBox<Integer> hourBox;

    @FXML
    private ComboBox<Integer> minuteBox;

    @FXML
    private ComboBox<Integer> secondBox;

    @FXML
    private Button okButton;

    @FXML
    private Button backButton;

    @FXML
    private Label selectBoxMessageLabel;

    @FXML
    private Label stationNameLabel;

    @FXML
    private Label stationStreetLabel;


    private final Integer[] hour = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private final Integer[] min = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,
            26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59};
    private final Integer[] sec = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,
            26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59};


    public void initialize() {
        hourBox.getItems().addAll(hour);
        minuteBox.getItems().addAll(min);
        secondBox.getItems().addAll(sec);
        stationNameLabel.setText(ApplicationHelper.stationName);
        Stations station = StationsService.getExactStationFromCity(ApplicationHelper.stationName, ApplicationHelper.stationCity);
        if (station != null) {
            stationStreetLabel.setText(station.getAddress());
        }

        AnchorPane root = stationAnchorPane;

        Button reportButton = new Button();

        reportButton.setText("Report malfunction");
        reportButton.setStyle("-fx-background-color: #ff0000; -fx-border-color: #990000; -fx-text-fill: WHITE");
        reportButton.setAlignment(Pos.CENTER);
        reportButton.wrapTextProperty().setValue(true);

        reportButton.setPrefHeight(45.0);
        reportButton.setPrefWidth(135.0);
        reportButton.setLayoutX(250.0);
        reportButton.setLayoutY(280.0);

        Image image = new Image("pictures/malfunction.png");
        ImageView iview = new ImageView(image);
        iview.setFitHeight(40);
        iview.setPreserveRatio(true);
        reportButton.setGraphic(iview);

        root.getChildren().add(reportButton);
    }

    public void okButtonOnAction() throws IOException {

        if (hourBox.getValue() == null || minuteBox.getValue() == null || secondBox.getValue() == null) {
            selectBoxMessageLabel.setText("Please fill in the fields!");
        }
        if (hourBox.getValue() == 0 && minuteBox.getValue() == 0 && secondBox.getValue() == 0) {
            selectBoxMessageLabel.setText("The time cannot be 0!");
        } else {
            Stations station = StationsService.getExactStationFromCity(ApplicationHelper.stationName, ApplicationHelper.stationCity);
            if (station != null) {
                station.setHour(hourBox.getValue());
                station.setMinute(minuteBox.getValue());
                station.setSecond(secondBox.getValue());
                station.setStationAvailability(true);

                ObjectRepository<Stations> objRepo = StationsService.getStationsRepository();
                objRepo.update(station);
            }

            // Go back to the user main scene
            Stage stage = (Stage) okButton.getScene().getWindow();
            Scene scene = new Scene(loadFXML("UserMainScene"), 1000, 700);
            stage.setTitle("Electric Charging Stations Application - Stations");
            stage.setScene(scene);
        }
    }

    public void backButtonOnAction() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(loadFXML("UserMainScene"), 1000, 700);
        stage.setTitle("Electric Charging Stations Application - Stations");
        stage.setScene(scene);
    }
}
