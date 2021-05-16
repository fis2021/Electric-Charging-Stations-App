package org.fis2021.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.fis2021.ApplicationHelper;
import org.fis2021.exceptions.StationAlreadyExistsException;
import org.fis2021.exceptions.UsernameAlreadyExistsException;
import org.fis2021.model.Company;
import org.fis2021.model.Stations;
import org.fis2021.services.CompanyService;
import org.fis2021.services.StationsService;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;

import static org.fis2021.App.loadFXML;

public class CompanyHomeController {

    @FXML
    private Button newStationButton;

    @FXML
    private Button overviewButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button popularityChartButton;

    @FXML
    private Button statisticButton;

    @FXML
    private ListView<AnchorPane> listView;

    private Company company;

    public void setCompany(Company c) {
        company = c;
    }

    ObservableList list = FXCollections.observableArrayList();

    public void handleButtonAction() {
        try {
            Stage stage = (Stage) newStationButton.getScene().getWindow();
            Scene scene = new Scene(loadFXML("AddStation"), 650, 400);
            stage.setTitle("Electric Charging Stations Application - Add Station");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleButtonActionOverview() {
        try {
            Stage stage = (Stage) overviewButton.getScene().getWindow();
            Scene scene = new Scene(loadFXML("StationsOverview"),800,700);
            stage.setTitle("Electric Charging Stations Application - Stations Overview");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handlePopularityChartButtonOnAction() {
        try {
            ApplicationHelper.companyName = company.getCompanyName();
            Stage stage = (Stage) popularityChartButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/fis2021/PopularityChart.fxml"));
            Parent homeRoot = loader.load();
            PopularityController controller = loader.getController();
            controller.setCompany(company);
            Scene scene = new Scene(homeRoot, 400, 500);
            stage.setTitle("Electric Charging Stations Application - Company Home Page");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logoutButtonOnAction() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get().equals(ButtonType.OK)) {
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            Scene scene = new Scene(loadFXML("login"), 600, 400);
            stage.setTitle("Electric Charging Stations Application - Stations Overview");
            stage.setScene(scene);
        }
    }

    public void initialize() {
        ArrayList<String> stations = StationsService.getAllStationsFromCompany();
        list.addAll(stations);
        for(String station : stations) {
            int index = listView.getItems().size()+1;
            Label text = new Label();
            text.setText(String.format("%d. %s",index,station));

            Button button = new Button();
            button.setText("delete");

            AnchorPane container = new AnchorPane();
            container.getChildren().addAll(text, button);
            container.getChildren().get(1).setLayoutX(740);
            listView.getItems().add(container);

            button.setOnAction((event)->{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Station");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this station?");
                Button yes_button = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                yes_button.setDefaultButton(false);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get().equals(ButtonType.OK)) {
                    StationsService.deleteStation(station);
                    listView.getItems().remove(container);
                }
            });
        }
    }

    public void handleButtonActionStatistic() {
        try {
            Stage stage = (Stage) statisticButton.getScene().getWindow();
            Scene scene = new Scene(loadFXML("StatisticReport"),400,400);
            stage.setTitle("Electric Charging Stations Application - Statistic Report");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
