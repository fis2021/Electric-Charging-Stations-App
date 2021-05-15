package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.fis2021.model.Company;

import java.io.IOException;

public class PopularityController {
    @FXML
    private Button returnToCompanyHomeController;

    private Company company;

    public void returnToCompanyHomeControllerOnAction() {
        try {
            Stage stage = (Stage) returnToCompanyHomeController.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/fis2021/CompanyMainScene.fxml"));
            Parent homeRoot = loader.load();
            CompanyHomeController controller = loader.getController();
            controller.setCompany(company);
            Scene scene = new Scene(homeRoot, 800, 700);
            stage.setTitle("Electric Charging Stations Application - Popularity Chart");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
