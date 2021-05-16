package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.fis2021.ApplicationHelper;
import org.fis2021.model.Company;
import org.fis2021.model.Stations;
import org.fis2021.model.VehicleOwner;
import org.fis2021.services.CompanyService;
import org.fis2021.services.StationsService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class PopularityController {
    @FXML
    private Button returnToCompanyHomeController;

    @FXML
    private ListView<AnchorPane> listView;

    private Company company;

    private VehicleOwner vehicleOwner;

    public void initialize() {
        ArrayList<String> company =  CompanyService.getAllCompaniesName();
        for(String comp :company) {
            Label text = new Label();
            text.setText(String.format(" %s ", comp));
            AnchorPane container = new AnchorPane();
            container.getChildren().addAll(text);
            listView.getItems().add(container);
        }
    }

    public void setVehicleOwner(VehicleOwner v) {
        vehicleOwner = v;
    }

    public void setCompany(Company c) {
        company = c;
    }

    public void returnHome() {
        if(company==null) {
            returnToUserHome();
        }
        else
            returnToCompanyHomeControllerOnAction();
    }

    public void returnToUserHome() {
        try {
            Stage stage = (Stage) returnToCompanyHomeController.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/fis2021/UserMainScene.fxml"));
            Parent homeRoot = loader.load();
            UserHomeController controller = loader.getController();
            controller.setVehicleOwner(vehicleOwner);
            Scene scene = new Scene(homeRoot, 1000, 700);
            stage.setTitle("Electric Charging Stations Application - Popularity Chart");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
