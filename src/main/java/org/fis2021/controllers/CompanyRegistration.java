package org.fis2021.controllers;


import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import org.fis2021.exceptions.UsernameAlreadyExistsException;
import org.fis2021.services.CompanyService;


import java.awt.*;
import java.io.IOException;

import static org.fis2021.App.loadFXML;
import static org.fis2021.services.CompanyService.initCompany;


public class CompanyRegistration {

    @FXML
    private Button registerButton;

    @FXML
    private Button companyButton;

    @FXML
    private Button exitButton;

    @FXML
    private TextField companyNameTextField;

    @FXML
    private TextField countryOfOriginTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField telephoneNumberTextField;

    @FXML
    private TextField faxNumberTextField;

    @FXML
    private PasswordField setPasswordField;

    @FXML
    private Label registrationMessage;

    public void exitButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void registerCompany(ActionEvent event) {
        try {
            Stage stage = (Stage) registerButton.getScene().getWindow();
            Scene scene = new Scene(loadFXML("CompanyRegister"), 800, 700);
            stage.setTitle("Electric Charging Stations Application - Company Registration");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToVehicleOwnerRegister(ActionEvent event) {
        try {
            Stage stage = (Stage) companyButton.getScene().getWindow();
            Scene scene = new Scene(loadFXML("VehicleOwnerRegister"), 800, 700);
            stage.setTitle("Electric Charging Stations Application - Vehicle Owner Registration");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerComp() {
        initCompany();
        try {
            CompanyService.addCompany(companyNameTextField.getText(), countryOfOriginTextField.getText(), addressTextField.getText(), usernameTextField.getText(), setPasswordField.getText(), emailTextField.getText(), telephoneNumberTextField.getText(), faxNumberTextField.getText());
            registrationMessage.setText("Account created successfully!");
        } catch (UsernameAlreadyExistsException e) {
            registrationMessage.setText(e.getMessage());
        }
    }
}
