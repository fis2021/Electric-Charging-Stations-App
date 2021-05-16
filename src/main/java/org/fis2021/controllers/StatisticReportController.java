package org.fis2021.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.fis2021.ApplicationHelper;
import org.fis2021.model.Company;
import org.fis2021.services.CompanyService;
import org.fis2021.services.MailService;

import java.io.IOException;
import java.util.Optional;

import static org.fis2021.App.loadFXML;

public class StatisticReportController {

    @FXML
    private Button backButton;

    @FXML
    private Button okButton;

    @FXML
    private Label selectDateLabel;

    @FXML
    private DatePicker datePicker;


    public void backButtonOnAction() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(loadFXML("CompanyMainScene"), 800, 700);
        stage.setTitle("Electric Charging Stations Application - Company Home Page");
        stage.setScene(scene);
    }


    public void okButtonOnAction() throws IOException {
        if (datePicker.getValue() == null) {
            selectDateLabel.setText("Select a date first.");
        } else {
            String companyPass = "something_wild";
            String mailSubject = "Statistic report";
            String mailMessage = "From date: " + datePicker.getValue() + "\nToday was a good day" +
                    "\n\nA lot of people choose out services!\n\n\nHave a nice weekend!";

            Company company = CompanyService.getCompanyByName(ApplicationHelper.companyName);
            if (company != null) {
                MailService mailService = new MailService(company.getEmail(), companyPass, company.getEmail());
                mailService.sendFromGMail(company.getEmail(), companyPass, company.getEmail(), mailSubject, mailMessage);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Statistic report");
                alert.setHeaderText("Sending statistic report to HQ");
                alert.setContentText("Alert Message");
                alert.setContentText("Are you sure you want to send the report?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get().equals(ButtonType.OK)) {
                    Stage stage = (Stage) backButton.getScene().getWindow();
                    Scene scene = new Scene(loadFXML("CompanyMainScene"), 800, 700);
                    stage.setTitle("Electric Charging Stations Application - Company Home Page");
                    stage.setScene(scene);
                }
            }
        }
    }

}
