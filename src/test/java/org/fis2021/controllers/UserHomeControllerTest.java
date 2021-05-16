package org.fis2021.controllers;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.fis2021.exceptions.UsernameAlreadyExistsException;
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
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class UserHomeControllerTest {
    @BeforeEach
    void setUp() throws IOException, UsernameAlreadyExistsException {
        FileSystemService.APPLICATION_FOLDER = ".test-registration";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        DatabaseService.initDatabase();
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
    void testLogoutButton(FxRobot robot) throws UsernameAlreadyExistsException{
        VehicleOwnerService.addVehicleOwner("Grigore", "Balbagian", "grib.balbi@gmail.com", "balbagi", "123456789", "Tesla Model X", "Full electric", "2020");
        robot.clickOn("#username");
        robot.write("balbagi");
        robot.clickOn("#password");
        robot.write("123456789");
        robot.clickOn("#role");
        robot.clickOn("Vehicle Owner");
        robot.clickOn("#login");
        robot.clickOn("#logoutButton");
        robot.clickOn("OK");
        FxAssert.verifyThat("#exit", NodeMatchers.isVisible());
    }

    @Test
    void testMostPopularStationButton(FxRobot robot) throws UsernameAlreadyExistsException{
        VehicleOwnerService.addVehicleOwner("Grigore", "Balbagian", "grib.balbi@gmail.com", "balbagi", "123456789", "Tesla Model X", "Full electric", "2020");
        robot.clickOn("#username");
        robot.write("balbagi");
        robot.clickOn("#password");
        robot.write("123456789");
        robot.clickOn("#role");
        robot.clickOn("Vehicle Owner");
        robot.clickOn("#login");
        robot.clickOn("#popularButton");
        robot.clickOn("#logoutButton");
        robot.clickOn("OK");
        FxAssert.verifyThat("#exit", NodeMatchers.isVisible());
    }

    @Test
    void testSelectRegionTextField(FxRobot robot) throws UsernameAlreadyExistsException{
        VehicleOwnerService.addVehicleOwner("Grigore", "Balbagian", "grib.balbi@gmail.com", "balbagi", "123456789", "Tesla Model X", "Full electric", "2020");
        robot.clickOn("#username");
        robot.write("balbagi");
        robot.clickOn("#password");
        robot.write("123456789");
        robot.clickOn("#role");
        robot.clickOn("Vehicle Owner");
        robot.clickOn("#login");
        robot.clickOn("#selectField");
        robot.write("Timisoara");
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("Timisoara");
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#logoutButton");
        robot.clickOn("OK");
        FxAssert.verifyThat("#exit", NodeMatchers.isVisible());
    }
}