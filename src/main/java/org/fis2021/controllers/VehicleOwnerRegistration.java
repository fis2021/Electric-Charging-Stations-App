package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import org.fis2021.exceptions.UsernameAlreadyExistsException;
import org.fis2021.services.VehicleOwnerService;

import java.io.IOException;
import static org.fis2021.App.loadFXML;
import static org.fis2021.services.VehicleOwnerService.initVehicleOwner;

public class VehicleOwnerRegistration {

    @FXML
    private Button vehicleOwnerButton;

    @FXML
    private Button registerButton;

    @FXML
    private Button exitButton;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField carBrandAndModelTextField;

    @FXML
    private TextField evTypeTextField;

    @FXML
    private TextField fabricationYearTextField;

    @FXML
    private PasswordField setPasswordField;

    @FXML
    private Label registrationMessage;

    @FXML
    void exitButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void switchToCompanyRegister(ActionEvent event) {
        try {
            Stage stage = (Stage) vehicleOwnerButton.getScene().getWindow();
            Scene scene = new Scene(loadFXML("CompanyRegister"), 800, 700);
            stage.setTitle("Electric Charging Stations Application - Company Registration");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void registerVehicleOwner(ActionEvent event) {
        try {
            Stage stage = (Stage) registerButton.getScene().getWindow();
            Scene scene = new Scene(loadFXML("VehicleOwnerRegister"), 800, 700);
            stage.setTitle("Electric Charging Stations Application - Vehicle Owner Registration");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void registerOwner() {
        initVehicleOwner();
        try {
            VehicleOwnerService.addVehicleOwner(firstNameTextField.getText(), lastNameTextField.getText(), emailTextField.getText(), usernameTextField.getText(), setPasswordField.getText(), carBrandAndModelTextField.getText(), evTypeTextField.getText(), fabricationYearTextField.getText());
            registrationMessage.setText("Account created successfully!");
        } catch (UsernameAlreadyExistsException e) {
            registrationMessage.setText(e.getMessage());
        }
    }
}
