package org.fis2021;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.fis2021.services.DatabaseService;
import org.fis2021.services.FileSystemService;

import java.nio.file.Files;
import java.nio.file.Path;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        initDirectory();
        DatabaseService.initDatabase();
        scene = new Scene(loadFXML("AddStation"), 600, 400);
        stage.setScene(scene);
        stage.setTitle("Electric Charging Stations Application - Login");
        stage.setResizable(false);
        stage.show();
    }

    private void initDirectory() {
        Path applicationHomePath = FileSystemService.APPLICATION_HOME_PATH;
        if(!Files.exists(applicationHomePath))
            applicationHomePath.toFile().mkdir();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}