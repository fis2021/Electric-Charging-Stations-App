package org.fis2021.controllers;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.fis2021.exceptions.UserNotFoundException;
import org.fis2021.exceptions.UsernameAlreadyExistsException;
import org.fis2021.model.Company;
import org.fis2021.model.VehicleOwner;
import org.fis2021.services.CompanyService;
import org.fis2021.services.DatabaseService;
import org.fis2021.services.FileSystemService;
import org.fis2021.services.VehicleOwnerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

import java.io.IOException;

import static org.fis2021.App.loadFXML;
import static org.testfx.assertions.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class LoginControllerTest {
    @BeforeEach
    void setUp() throws IOException, UsernameAlreadyExistsException {
        FileSystemService.APPLICATION_FOLDER = ".test-registration";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        DatabaseService.initDatabase();
        CompanyService.initCompany();
        VehicleOwnerService.initVehicleOwner();
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
    void testVehicleOwnerNotRegistered(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("andras");
        robot.clickOn("#password");
        robot.write("12345");
        robot.clickOn("#role");
        robot.clickOn("Vehicle Owner");
        robot.clickOn("#login");
        assertThat(robot.lookup("#errorMessage").queryLabeled().getText()).isEqualTo("The username andras is not registered");
    }

    @Test
    void testCompanyNotRegistered(FxRobot robot) {
        robot.clickOn("#username");
        robot.write("Compa1");
        robot.clickOn("#password");
        robot.write("12345");
        robot.clickOn("#role");
        robot.clickOn("Company Administrator");
        robot.clickOn("#login");
        assertThat(robot.lookup("#errorMessage").queryLabeled().getText()).isEqualTo("The username Compa1 is not registered");
    }

    @Test
    void testNoUsername(FxRobot robot) {
        robot.clickOn("#role");
        robot.clickOn("Vehicle Owner");
        robot.clickOn("#password");
        robot.write("12345");
        robot.clickOn("#login");
        assertThat(robot.lookup("#errorMessage").queryLabeled().getText()).isEqualTo("The username  is not registered");
    }

    @Test
    void testLoginCompany(FxRobot robot) throws UsernameAlreadyExistsException, UserNotFoundException {
        CompanyService.addCompany("Compania1", "Romania", "Lalelelor", "Compa1", "12345", "compania1@gmail.com", "0256458697", "+40 265" );

        robot.clickOn("#username");
        robot.write("Compa1");
        robot.clickOn("#password");
        robot.write("12345");
        robot.clickOn("#role");
        robot.clickOn("Company Administrator");
        robot.clickOn("#login");
        FxAssert.verifyThat("#logoutButton", NodeMatchers.isVisible());

    }

    @Test
    void testLoginVehicleOwner(FxRobot robot) throws UsernameAlreadyExistsException, UserNotFoundException {
        VehicleOwnerService.addVehicleOwner("Andrei", "Marcel", "andrei.marcel@gmail.com", "andrmarc", "12345", "Tesla", "full electric", "2020" );

        robot.clickOn("#username");
        robot.write("andrmarc");
        robot.clickOn("#password");
        robot.write("12345");
        robot.clickOn("#role");
        robot.clickOn("Vehicle Owner");
        robot.clickOn("#login");
        FxAssert.verifyThat("#logoutButtonUser", NodeMatchers.isVisible());
    }

    @Test
    void testNavigateToRegister(FxRobot robot) {
        robot.clickOn("#register");
        FxAssert.verifyThat("#returnToLoginVehicleOwner", NodeMatchers.isVisible());
    }

    @Test
    void testExitButton(FxRobot robot) {
        robot.clickOn("#exit");
    }
}