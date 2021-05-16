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
import org.fis2021.model.Company;
import org.fis2021.model.Stations;
import org.fis2021.services.CompanyService;
import org.fis2021.services.MailService;
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

    private final String user = "batmanslappsbutter.17@gmail.com";
    private final String pass = "abracadabra2021";
    private final String subject = "Report for malfunctioning station";
    private final String message = "The station " + ApplicationHelper.stationName + " is malfunctioning. PLease check!";


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

        reportButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Report malfunction");
                alert.setHeaderText("Reporting");
                alert.setContentText("Alert Message");
                alert.setContentText("Are you sure you want to report a malfunction at this station?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get().equals(ButtonType.OK)) {
                    Company company = CompanyService.getCompanyByName(ApplicationHelper.companyName);
                    if (company != null) {
                        MailService mailService = new MailService(user, pass, company.getEmail());
                        mailService.sendFromGMail(mailService.getUser(), mailService.getPass(), mailService.getRecipient(), subject, message);

                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Report sent");
                        alert.setHeaderText("Message");
                        alert.setContentText("Alert Message");
                        alert.setContentText("Report sent successfully!");
                        result = alert.showAndWait();
                    } else {
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Report sent");
                        alert.setHeaderText("Message");
                        alert.setContentText("Alert Message");
                        alert.setContentText("Report failed. Please try again later!");
                        result = alert.showAndWait();
                    }
                    if (result.get().equals(ButtonType.OK)) {
                        Stage stage = (Stage) reportButton.getScene().getWindow();
                        Scene scene = null;
                        try {
                            scene = new Scene(loadFXML("UserMainScene"), 1000, 700);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        stage.setTitle("Electric Charging Stations Application - Stations");
                        stage.setScene(scene);
                    }
                }
            }
        });

        root.getChildren().add(reportButton);
    }

    public void okButtonOnAction() throws IOException {

        if (hourBox.getValue() == null || minuteBox.getValue() == null || secondBox.getValue() == null) {
            selectBoxMessageLabel.setText("Please fill in the fields!");
        } else if (hourBox.getValue() == 0 && minuteBox.getValue() == 0 && secondBox.getValue() == 0) {
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
