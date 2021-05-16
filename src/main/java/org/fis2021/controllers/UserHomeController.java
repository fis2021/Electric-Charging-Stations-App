package org.fis2021.controllers;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

import org.controlsfx.control.textfield.TextFields;

import org.dizitart.no2.objects.ObjectRepository;
import org.fis2021.ApplicationHelper;

import org.fis2021.model.Stations;
import org.fis2021.services.StationsService;
import org.fis2021.services.TimerService;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

import static org.fis2021.App.loadFXML;


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

    @FXML
    private Label availableLabel;

    @FXML
    private Label busyLabel;


    private final Hashtable<String,Label> countdownLabels = new Hashtable<>();

    private final DecimalFormat dFormat = new DecimalFormat("00");

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

    public void customizeButtonAvailableStation(Ellipse ellipseStation, Button buttonStation, double ellipseX, double ellipseY, String buttonText, double buttonX, double buttonY) {
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

    public void customizeBusyStation(Ellipse ellipseStation, Label labelName, Label labelCountdown, double ellipseX, double ellipseY,
                                     String labelText, double labelNameX, double labelNameY, double labelCountdownX, double labelCountdownY) {
        // Ellipse
        ellipseStation.setCenterX(ellipseX);
        ellipseStation.setCenterY(ellipseY);
        ellipseStation.setRadiusX(40.0);
        ellipseStation.setRadiusY(30.0);

        ellipseStation.setStroke(Color.BLACK);
        ellipseStation.setFill(Color.web("#990000b2"));

        // Label name
        labelName.setText(labelText);
        labelName.setStyle("-fx-background-color: transparent;");
        labelName.setTextFill(Color.WHITE);
        labelName.setAlignment(Pos.CENTER);

        labelName.setPrefHeight(25.0);
        labelName.setPrefWidth(80.0);
        labelName.setLayoutX(labelNameX);
        labelName.setLayoutY(labelNameY);

        // Label countdown
        //labelCountdown.setText() will be set in the moveStationToBusy func
        labelCountdown.setStyle("-fx-background-color: transparent;");
        labelCountdown.setTextFill(Color.WHITE);
        labelCountdown.setAlignment(Pos.CENTER);

        labelCountdown.setPrefHeight(25.0);
        labelCountdown.setPrefWidth(80.0);
        labelCountdown.setLayoutX(labelCountdownX);
        labelCountdown.setLayoutY(labelCountdownY);
    }

    public void selectRegionOnAction() {
        moveStationToBusy();

        ArrayList<String> stationsAvailable = StationsService.getAllAvailableStationsFromCity(selectRegion.getText());

        AnchorPane root = availableAnchorPane;
        root.getChildren().clear();
        root.getChildren().add(availableLabel);

        double posButtonX = 45.0;
        double posButtonY = 110.0;
        double posEllipseX = 85.0;
        double posEllipseY = 135.0;
        int row = 1;

        for (String station: stationsAvailable) {
            Ellipse ellipse = new Ellipse();
            Button button = new Button();


            // Add button at the right place
            if (posEllipseX > 415 && posButtonX > 375) {
                posButtonX = 45.0;
                posEllipseX = 85.0;
                posButtonY += 90.0;
                posEllipseY += 90.0;
                row = row + 1;
            }
            if (row == 7) {
                break;
            }

            customizeButtonAvailableStation(ellipse, button, posEllipseX, posEllipseY, station, posButtonX, posButtonY);
            posEllipseX += 110.0;
            posButtonX += 110.0;

            // Root takes children into custody
            root.getChildren().addAll(ellipse, button);

            // Create event for button
            button.setOnAction(actionEvent -> {
                ApplicationHelper.stationName = station;
                ApplicationHelper.stationCity = selectRegion.getText();
                try {
                    Stage stage = (Stage) button.getScene().getWindow();
                    Scene scene = new Scene(loadFXML("StationScene"), 400, 400);
                    stage.setTitle("Electric Charging Stations Application - Station Scene");
                    stage.setScene(scene);
                } catch (IOException ignored) { }
            } );
        }
    }

    public void moveStationToBusy() {
        ArrayList<String> stationsBusy = StationsService.getAllBusyStationsFromCity(selectRegion.getText());

        AnchorPane root = busyAnchorPane;
        root.getChildren().clear();
        root.getChildren().add(busyLabel);

        double posLabelNameX = 45.0;
        double posLabelNameY = 110.0;
        double posLabelCountdownX = 45.0;
        double posLabelCountdownY = 135.0;
        double posEllipseX = 85.0;
        double posEllipseY = 135.0;
        int row = 1;

        for (String station : stationsBusy) {
            Ellipse ellipse = new Ellipse();
            Label labelName = new Label();
            Label labelCountdown = new Label();

            // Add busy station at the right place
            if (posEllipseX > 415 && posLabelNameX > 375 && posLabelCountdownX > 375) {
                posEllipseX = 85.0;
                posLabelNameX = 45.0;
                posLabelCountdownX = 45.0;
                posEllipseY += 90.0;
                posLabelNameY += 90.0;
                posLabelCountdownY += 90.0;
                row = row + 1;
            }
            if (row == 7) {
                break;
            }

            customizeBusyStation(ellipse, labelName, labelCountdown, posEllipseX, posEllipseY, station, posLabelNameX, posLabelNameY, posLabelCountdownX, posLabelCountdownY);
            posEllipseX += 110.0;
            posLabelNameX += 110.0;
            posLabelCountdownX += 110.0;

            // Memorize the count labels
            countdownLabels.put(station, labelCountdown);

            // Root takes children into custody
            root.getChildren().addAll(ellipse, labelName, labelCountdown);
        }

        // Start countdown
        startCountDown();
    }

    public void startCountDown () {
        ArrayList<String> stations = StationsService.getAllBusyStations();
        Set<String> setOfStations = countdownLabels.keySet();

        for (String station : setOfStations) {
            if (stations.contains(station)) {
                Stations dbStation = StationsService.getExactStationFromCity(station, selectRegion.getText());
                if (dbStation != null) {
                    // Generate countdown process
                    TimerService timerCountdown = new TimerService(countdownLabels.get(station), dbStation);
                    timerCountdown.startTimer(dbStation.getHour() * 3600 + dbStation.getMinute() * 60 + dbStation.getSecond());
                    timerCountdown.initialize();
                }
            }
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
            stage.setTitle("Electric Charging Stations Application - Stations");
            stage.setScene(scene);
        }
    }

    public void exitButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
