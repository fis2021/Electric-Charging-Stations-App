package org.fis2021.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

import org.controlsfx.control.textfield.TextFields;

import org.fis2021.exceptions.UserNotFoundException;

import org.fis2021.model.Company;
import org.fis2021.model.Stations;
import org.fis2021.model.VehicleOwner;

import org.fis2021.services.StationsService;
import org.fis2021.services.VehicleOwnerService;
import org.fis2021.services.VehicleOwnerService;
import org.fis2021.services.CompanyService;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static org.fis2021.App.loadFXML;
import static org.fis2021.services.CompanyService.initCompany;
import static org.fis2021.services.VehicleOwnerService.initVehicleOwner;


public class UserHomeController implements Initializable {
    @FXML
    private TextField selectRegion;

    @FXML
    private Button exitButton;

    @FXML
    private Button popularButton;

    @FXML
    private Button logOutButton;

    @FXML
    private AnchorPane availableAnchorPane;

    @FXML
    private AnchorPane busyAnchorPane;


    private final String[] regions = {"Abrud","Adjud","Agnita","Aiud","Alba Iulia","Alesd","Alexandria","Amara","Anina","Aninoasa","Arad","Ardud","Avrig","Azuga",
            "Babadag","Babeni","Bacau","Baia de Arama","Baia de Aries","Baia Mare","Baia Sprie","Baicoi","Baile Govora","Baile Herculane","Baile Olanesti","Baile Tusnad",
            "Bailesti","Balan","Balcesti","Bals","Baraolt","Barlad","Bechet","Beclean","Beius","Berbesti","Beresti","Bicaz","Bistrita","Blaj","Bocsa","Boldesti-Scaeni",
            "Bolintin-Vale","Borsa","Borsec","Botosani","Brad","Bragadiru","Braila","Brasov","Breaza","Brezoi","Brosteni","Bucecea","Bucuresti","Budesti","Buftea","Buhusi",
            "Bumbesti-Jiu","Busteni","Buzau","Buzias","Cajvana","Calafat","Calan","Calarasi","Calimanesti","Campeni","Campia Turzii","Campina","Campulung Moldovenesc",
            "Campulung","Caracal","Caransebes","Carei","Cavnic","Cazanesti","Cehu Silvaniei","Cernavoda","Chisineu-Cris","Chitila","Ciacova","Cisnadie","Cluj-Napoca",
            "Codlea","Comanesti","Comarnic","Constanta","Copsa Mica","Corabia","Costesti","Covasna","Craiova","Cristuru Secuiesc","Cugir","Curtea de Arges","Curtici",
            "Dabuleni","Darabani","Darmanesti","Dej","Deta","Deva","Dolhasca","Dorohoi","Draganesti-Olt","Dragasani","Dragomiresti","Drobeta-Turnu Severin","Dumbraveni",
            "Eforie","Fagaras","Faget","Falticeni","Faurei","Fetesti","Fieni","Fierbinti-Targ","Filiasi","Flamanzi","Focsani","Frasin","Fundulea","Gaesti","Galati","Gataia",
            "Geoagiu","Gheorgheni","Gherla","Ghimbav","Giurgiu","Gura Humorului","Harlau","Harsova","Hateg","Horezu","Huedin","Hunedoara","Husi","Ianca","Iasi","Iernut","Ineu",
            "insuratei","intorsura Buzaului","Isaccea","Jibou","Jimbolia","Lehliu Gara","Lipova","Liteni","Livada","Ludus","Lugoj","Lupeni","Macin","Magurele","Mangalia",
            "Marasesti","Marghita","Medgidia","Medias","Miercurea Ciuc","Miercurea Nirajului","Miercurea Sibiului","Mihailesti","Milisauti","Mioveni","Mizil","Moinesti",
            "Moldova Noua","Moreni","Motru","Murfatlar","Murgeni","Nadlac","Nasaud","Navodari","Negresti","Negresti-Oas","Negru Voda","Nehoiu","Novaci","Nucet","Ocna Mures",
            "Ocna Sibiului","Ocnele Mari","Odobesti","Odorheiu Secuiesc","Oltenita","Onesti","Oradea","Orastie","Oravita","Orsova","Otelu Rosu","Otopeni","Ovidiu","Panciu",
            "Pancota","Pantelimon","Pascani","Patarlagele","Pecica","Petrila","Petrosani","Piatra Neamt","Piatra-Olt","Pitesti","Ploiesti","Plopeni","Podu Iloaiei","Pogoanele",
            "Popesti-Leordeni","Potcoava","Predeal","Pucioasa","Racari","Radauti","Ramnicu Sarat","Ramnicu Valcea","Rasnov","Recas","Reghin","Resita","Roman","Rosiorii de Vede",
            "Rovinari","Roznov","Rupea","Sacele","Sacueni","Salcea","Saliste","Salistea de Sus","Salonta","Sangeorgiu de Padure","Sangeorz-Bai","Sannicolau Mare","Santana",
            "Sarmasu","Satu Mare","Saveni","Scornicesti","Sebes","Sebis","Segarcea","Seini","Sfantu Gheorghe","Sibiu","Sighetu Marmatiei","Sighisoara","Simeria","simleu Silvaniei",
            "Sinaia","Siret","Slanic","Slanic-Moldova","Slatina","Slobozia","Solca","somcuta Mare","Sovata","stefanesti, Arges","stefanesti, Botosani","stei","Strehaia",
            "Suceava","Sulina","Talmaciu","tandarei","Targoviste","Targu Bujor","Targu Carbunesti","Targu Frumos","Targu Jiu","Targu Lapus","Targu Mures","Targu Neamt",
            "Targu Ocna","Targu Secuiesc","Tarnaveni","Tasnad","Tautii-Magheraus","Techirghiol","Tecuci","Teius","ticleni","Timisoara","Tismana","Titu","Toplita","Topoloveni",
            "Tulcea","Turceni","Turda","Turnu Magurele","Ulmeni","Ungheni","Uricani","Urlati","Urziceni","Valea lui Mihai","Valenii de Munte","Vanju Mare","Vascau","Vaslui",
            "Vatra Dornei","Vicovu de Sus","Victoria","Videle","Viseu de Sus","Vlahita","Voluntari","Vulcan","Zalau","Zarnesti","Zimnicea","Zlatna"};


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TextFields.bindAutoCompletion(selectRegion, regions);
    }

    public void customizeNewButtonForStation(Ellipse ellipseStation, Button buttonStation, double ellipseX, double ellipseY, String buttonText, double buttonX, double buttonY) {
        // Ellipse
        ellipseStation.setCenterX(ellipseX);
        ellipseStation.setCenterY(ellipseY);
        ellipseStation.setRadiusX(40.0);
        ellipseStation.setRadiusY(30.0);

        ellipseStation.setStroke(Color.BLACK);
        ellipseStation.setFill(Color.web("#009900b2"));

        // Button
        buttonStation.setText(buttonText);
        buttonStation.setStyle("-fx-background-color: transparent;");

        buttonStation.setPrefHeight(50.0);
        buttonStation.setPrefWidth(80.0);
        buttonStation.setLayoutX(buttonX);
        buttonStation.setLayoutY(buttonY);
    }

    public void selectRegionOnAction() {
        ArrayList<String> stations = StationsService.getAllStationsFromCity(selectRegion.getText());

        AnchorPane root = availableAnchorPane;

        double posButtonX = 45.0;
        double posButtonY = 110.0;
        double posEllipseX = 85.0;
        double posEllipseY = 135.0;
        int row = 1;

        for (String station: stations) {
            Ellipse ellipse = new Ellipse();
            Button button = new Button();

            if (posEllipseX > 415 && posButtonX > 375) {
                posButtonX = 45.0;
                posEllipseX = 85.0;

                posButtonY = posButtonY + 90.0;
                posEllipseY = posEllipseY + 90.0;

                row = row + 1;
            }

            if (row == 7) {
                break;
            }

            customizeNewButtonForStation(ellipse, button, posEllipseX, posEllipseY, station, posButtonX, posButtonY);

            posEllipseX = posEllipseX + 110.0;
            posButtonX = posButtonX + 110.0;

            root.getChildren().addAll(ellipse, button);
        }
    }


    public void popularButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) popularButton.getScene().getWindow();
    }

    public void logOutButtonOnAction() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log out");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get().equals(ButtonType.OK)) {
            Stage stage = (Stage) logOutButton.getScene().getWindow();
            Scene scene = new Scene(loadFXML("login"), 600, 400);
            stage.setTitle("Electric Charging Stations Application - Stations Overview");
            stage.setScene(scene);
        }
    }

    public void exitButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
