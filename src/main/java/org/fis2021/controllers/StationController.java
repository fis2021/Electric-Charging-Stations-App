package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.fis2021.ApplicationHelper;

import java.io.IOException;

import static org.fis2021.App.loadFXML;


public class StationController {

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


    private final Integer[] hour = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private final Integer[] min = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,
            26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59};
    private final Integer[] sec = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,
            26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59};


    public void initialize() {
        hourBox.getItems().addAll(hour);
        minuteBox.getItems().addAll(min);
        secondBox.getItems().addAll(sec);
        System.out.println(ApplicationHelper.stationName);
        stationNameLabel.setText(ApplicationHelper.stationName);
    }

    public void okButtonOnAction() throws IOException {

        if (hourBox.getValue() == null || minuteBox.getValue() == null || secondBox.getValue() == null) {
            selectBoxMessageLabel.setText("Please fill in the fields for time!");
        } else if (hourBox.getValue() == 0 && minuteBox.getValue() == 0 && secondBox.getValue() == 0) {
            selectBoxMessageLabel.setText("The time cannot be 0!");
        } else {
            ApplicationHelper.stationHour = hourBox.getValue();
            ApplicationHelper.stationMinute = minuteBox.getValue();
            ApplicationHelper.stationSecond = secondBox.getValue();
            ApplicationHelper.stationSelected = true;
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
