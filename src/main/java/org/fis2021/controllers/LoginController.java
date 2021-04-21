package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import org.fis2021.exceptions.UsernameAlreadyExistsException;
import org.fis2021.services.UserService;

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
    void exit(ActionEvent event) {

    }

    public void exitButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void loginButtonOnAction(ActionEvent event) {

    }

    public void registerButtonOnAction(ActionEvent event) {

    }

}
