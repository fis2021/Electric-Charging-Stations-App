package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.fis2021.exceptions.UsernameAlreadyExistsException;
import org.fis2021.services.UserService;

public class RegistrationController {

    @FXML
    private Text registrationMessage;
    @FXML
    public PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private ChoiceBox role;

    @FXML
    public void handleRegisterAction() {
        try {
            UserService.addUser(usernameField.getText(),passwordField.getText(),(String) role.getValue());
            registrationMessage.setText("Account created succesfully!");
        } catch (UsernameAlreadyExistsException e) {
            registrationMessage.setText("e.getMessage");
        }
    }
}
