package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import org.fis2021.exceptions.UserNotFoundException;
import org.fis2021.model.VehicleOwner;
import org.fis2021.services.VehicleOwnerService;

import org.fis2021.services.VehicleOwnerService;
import org.fis2021.services.CompanyService;

import java.io.IOException;

import static org.fis2021.App.loadFXML;
import static org.fis2021.services.CompanyService.initCompany;
import static org.fis2021.services.StationsService.initStations;
import static org.fis2021.services.VehicleOwnerService.initVehicleOwner;

public class LoginController {

    @FXML
    private TextField usernameTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private ChoiceBox roleBox;

    @FXML
    private Button exitButton;

    @FXML
    private PasswordField setPasswordField;

    @FXML
    private Label loginMessage;

    public void initialize() {
        roleBox.getItems().addAll("Vehicle Owner", "Company Administrator");
    }

    public void exitButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void loginButtonOnAction(ActionEvent event) {
        initVehicleOwner();
        initCompany();
        initStations();
        if(roleBox.getValue() == null) {
            loginMessage.setText("Please choose a role!");
        }
        if(((String) roleBox.getValue()).equals("Vehicle Owner")) {
            initVehicleOwner();

            if (usernameTextField.getText().isEmpty() && setPasswordField.getText().isEmpty()) {
                loginMessage.setText("Please enter an username and a password!");
            } else {
                if (usernameTextField.getText().isEmpty() || usernameTextField == null) {
                    loginMessage.setText("Please enter an username!");
                }
                else
                if (setPasswordField.getText().isEmpty() || setPasswordField == null) {
                    loginMessage.setText("Please enter a password!");
                }
            }

            String encoded_password = VehicleOwnerService.encodePassword(usernameTextField.getText(), setPasswordField.getText());

            try {
                String stored_password = VehicleOwnerService.getHashedUserPassword(usernameTextField.getText());
                if(stored_password.equals(encoded_password)) {
                    loginMessage.setText(String.format("Successfully logged in as %s!", usernameTextField.getText()));
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    Scene scene = new Scene(loadFXML("UserMainScene"), 1000, 700);
                    stage.setTitle("Electric Charging Stations Application - User Home Page");
                    stage.setScene(scene);
                }
                else {
                    loginMessage.setText("Invalid credentials!");
                }
            } catch (UserNotFoundException | IOException e) {
                loginMessage.setText(e.getMessage());
            }

        }

        if(((String) roleBox.getValue()).equals("Company Administrator")) {
            initCompany();

            if (usernameTextField.getText().isEmpty() && setPasswordField.getText().isEmpty()) {
                loginMessage.setText("Please enter an username and a password!");
            } else {
                if (usernameTextField.getText().isEmpty() || usernameTextField == null) {
                    loginMessage.setText("Please enter an username!");
                }
                else
                if (setPasswordField.getText().isEmpty() || setPasswordField == null) {
                    loginMessage.setText("Please enter a password!");
                }
            }

            String encoded_password = CompanyService.encodePassword(usernameTextField.getText(), setPasswordField.getText());

            try {
                String stored_password = CompanyService.getHashedUserPassword(usernameTextField.getText());
                if(stored_password.equals(encoded_password)) {
                    loginMessage.setText(String.format("Succesfully logged in as %s!",usernameTextField.getText()));
                }
                else {
                    loginMessage.setText("Invalid credentials!");
                }
            } catch (UserNotFoundException e) {
                loginMessage.setText(e.getMessage());
            }

        }

    }

    public void registerButtonOnAction(ActionEvent event) {
        try {
            Stage stage = (Stage) registerButton.getScene().getWindow();
            Scene scene = new Scene(loadFXML("VehicleOwnerRegister"), 800, 700);
            stage.setTitle("Electric Charging Stations Application - Vehicle Owner Registration");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
