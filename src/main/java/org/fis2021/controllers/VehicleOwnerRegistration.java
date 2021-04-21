package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class VehicleOwnerRegistration {

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

    }

    @FXML
    void registerVehicleOwner(ActionEvent event) {

    }

}
