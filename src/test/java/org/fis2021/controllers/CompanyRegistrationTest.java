package org.fis2021.controllers;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.fis2021.services.CompanyService;
import org.fis2021.services.DatabaseService;
import org.fis2021.services.FileSystemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

import static org.fis2021.App.loadFXML;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class CompanyRegistrationTest {

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
    void testEveryFieldIsCompletedAndCompanyIsRegistered(FxRobot robot) {
        robot.clickOn("#register");
        robot.clickOn("#switchToCompany");
        robot.clickOn("#companyName");
        robot.write("Company1");
        robot.clickOn("#countryOfOrigin");
        robot.write("Timisoara");
        robot.clickOn("#addressCompany");
        robot.write("Str Sinaia");
        robot.clickOn("#usernameCompany");
        robot.write("Compa1");
        robot.clickOn("#emailCompany");
        robot.write("comp1@gmail.com");
        robot.clickOn("#telephoneNumberCompany");
        robot.write("0256412369");
        robot.clickOn("#faxNumberCompany");
        robot.write("+44125");
        robot.clickOn("#passwordCompany");
        robot.write("12345");
        robot.clickOn("#registerCompany");
        assertThat(robot.lookup("#warningLabel").queryLabeled().getText()).isEqualTo("Account created successfully!");
    }

    @Test
    void testFieldsAreEmpty(FxRobot robot) {
        robot.clickOn("#register");
        robot.clickOn("#switchToCompany");
        robot.clickOn("#registerCompany");
        assertThat(robot.lookup("#warningLabel").queryLabeled().getText()).isEqualTo("Please fil in all the fields!");
    }

    @Test
    void testSwitchToVehicleOwnerRegister(FxRobot robot) {
        robot.clickOn("#register");
        robot.clickOn("#switchToCompany");
    }

    @Test
    void testBackToLogin(FxRobot robot) {
        robot.clickOn("#register");
        robot.clickOn("#switchToCompany");
        robot.clickOn("#switchToLogin");
    }

}