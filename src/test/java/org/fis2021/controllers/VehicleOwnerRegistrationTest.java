package org.fis2021.controllers;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.fis2021.services.CompanyService;
import org.fis2021.services.DatabaseService;
import org.fis2021.services.FileSystemService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fis2021.App.loadFXML;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class VehicleOwnerRegistrationTest {

    @BeforeEach
    void setUp() throws IOException {
        FileSystemService.APPLICATION_FOLDER = ".test-registration";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        DatabaseService.initDatabase();
        CompanyService.initCompany();
    }

    @Start
    void start(Stage stage) throws IOException{
        Scene scene = new Scene(loadFXML("Login"), 600, 400);
        stage.setTitle("Electric Charging Stations App - Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @AfterEach
    void tearDown() {
        DatabaseService.getDatabase().close();
    }

    @Test
    void testEveryFieldIsCompletedAndVehicleOwnerIsRegistered(FxRobot robot) {
        robot.clickOn("#register");
        robot.clickOn("#firstNameVehicleOwner");
        robot.write("Andrei");
        robot.clickOn("#lastNameVehicleOwner");
        robot.write("Marcel");
        robot.clickOn("#emailVehicleOwner");
        robot.write("andreimarcel@gmail.com");
        robot.clickOn("#usernameVehicleOwner");
        robot.write("andrmarc");
        robot.clickOn("#carVehicleOwner");
        robot.write("Tesla");
        robot.clickOn("#evTypeVehicleOwner");
        robot.write("full electric");
        robot.clickOn("#fabricationYearVehicleOwner");
        robot.write("2021");
        robot.clickOn("#passwordVehicleOwner");
        robot.write("12345");
        robot.clickOn("#registerVehicleOwner");
        assertThat(robot.lookup("#warningLabel").queryLabeled().getText()).isEqualTo("Account created successfully!");
    }

    @Test
    void testFieldsAreEmpty(FxRobot robot) {
        robot.clickOn("#register");
        robot.clickOn("#registerVehicleOwner");
        assertThat(robot.lookup("#warningLabel").queryLabeled().getText()).isEqualTo("Please fill in all the fields!");
    }

    @Test
    void testSwitchToCompanyRegister(FxRobot robot) {
        robot.clickOn("#register");
        robot.clickOn("#switchToCompany");
    }

    @Test
    void testBackToLogin(FxRobot robot) {
        robot.clickOn("#register");
        robot.clickOn("#returnToLoginVehicleOwner");
    }
}