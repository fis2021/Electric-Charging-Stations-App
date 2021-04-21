package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

import static org.fis2021.App.loadFXML;

public class LoginController {

    @FXML
    private TextField usernameTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private Button exitButton;

    @FXML
    private PasswordField setPasswordField;

    @FXML
    private Label loginMessage;

    public void exitButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void loginButtonOnAction(ActionEvent event) {
        String username = usernameTextField.getText();
        String password = setPasswordField.getText();
        if (username.isEmpty() && password.isEmpty()) {
            loginMessage.setText("Please enter an username and a password!");
        } else {
            if (username.isEmpty() || username == null) {
                loginMessage.setText("Please enter an username!");
            }
            else
                if (password.isEmpty() || password == null) {
                    loginMessage.setText("Please enter a password!");
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
