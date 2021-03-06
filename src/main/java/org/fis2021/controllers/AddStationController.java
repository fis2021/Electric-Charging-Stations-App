package org.fis2021.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.fis2021.exceptions.StationAlreadyExistsException;
import org.fis2021.exceptions.UsernameAlreadyExistsException;
import org.fis2021.model.Company;
import org.fis2021.services.StationsService;

import java.io.IOException;
import java.util.ArrayList;

import static org.fis2021.App.loadFXML;

public class AddStationController {

    @FXML
    private TextField companyNameTextField;

    @FXML
    private TextField stationNameTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private ListView<AnchorPane> listView;

    @FXML
    private Label errorMessage;

    @FXML
    private Button addStationButton;

    @FXML
    private Button goBackToHomePageButton;

    private Company company;

    public void setCompany(Company c) {
        company = c;
    }

    private Company getCompany() {
        return company;
    }

    public void handleAddStation() {
        String stationName = stationNameTextField.getText();
        String city = cityTextField.getText();
        String address = addressTextField.getText();
        StationsService.initStations();
        if(stationName.isEmpty() || city.isEmpty() || address.isEmpty()) {
            errorMessage.setText("Please fill in all the fields!");
        }
        else {
            Label text = new Label();
            text.setText(stationName);
            try {
                StationsService.addStation(text.getText(),city,address);
                errorMessage.setText("Station Added Succesfully!");
            } catch(StationAlreadyExistsException ignored) {
                errorMessage.setText("Station already exists with the same username!");
            }
        }

    }

    public void goBackToTheHomePage(ActionEvent event) {
        try {
            Stage stage = (Stage) goBackToHomePageButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/fis2021/CompanyMainScene.fxml"));
            Parent homeRoot = loader.load();
            CompanyHomeController controller = loader.getController();
            controller.setCompany(company);
            Scene scene = new Scene(homeRoot, 800, 700);
            stage.setTitle("Electric Charging Stations Application - Add New Station ");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
