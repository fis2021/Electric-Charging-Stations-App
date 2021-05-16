package org.fis2021.controllers;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.fis2021.exceptions.UserNotFoundException;
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
class CompanyHomeControllerTest {
    @BeforeEach
    void setUp() throws IOException, UsernameAlreadyExistsException {
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
    void travelToOverviewStations(FxRobot robot) throws UsernameAlreadyExistsException {
        CompanyService.addCompany("Compania1", "Romania", "Lalelelor", "Compa1", "12345", "compania1@gmail.com", "0256458697", "+40 265" );
        robot.clickOn("#username");
        robot.write("Compa1");
        robot.clickOn("#password");
        robot.write("12345");
        robot.clickOn("#role");
        robot.clickOn("Company Administrator");
        robot.clickOn("#login");
        robot.clickOn("#overviewButton");
        robot.clickOn("#backHomeController");
        FxAssert.verifyThat("#logoutButton", NodeMatchers.isVisible());
    }

    @Test
    void travelToAddNewStation(FxRobot robot) throws UsernameAlreadyExistsException {
        CompanyService.addCompany("Compania1", "Romania", "Lalelelor", "Compa1", "12345", "compania1@gmail.com", "0256458697", "+40 265" );
        robot.clickOn("#username");
        robot.write("Compa1");
        robot.clickOn("#password");
        robot.write("12345");
        robot.clickOn("#role");
        robot.clickOn("Company Administrator");
        robot.clickOn("#login");
        robot.clickOn("#addNewStation");
        robot.clickOn("#stationName");
        robot.write("Statie 789");
        robot.clickOn("#cityName");
        robot.write("Timisoara");
        robot.clickOn("#address");
        robot.write("Str Sinaia");
        robot.clickOn("#addStation");
        robot.clickOn("#returnToHome");
        robot.clickOn("delete");
        robot.clickOn("OK");
        FxAssert.verifyThat("#logoutButton", NodeMatchers.isVisible());
    }

    @Test
    void testLogoutButton(FxRobot robot) throws UsernameAlreadyExistsException{
        CompanyService.addCompany("Compania1", "Romania", "Lalelelor", "Compa1", "12345", "compania1@gmail.com", "0256458697", "+40 265" );
        robot.clickOn("#username");
        robot.write("Compa1");
        robot.clickOn("#password");
        robot.write("12345");
        robot.clickOn("#role");
        robot.clickOn("Company Administrator");
        robot.clickOn("#login");
        robot.clickOn("#logoutButton");
        robot.clickOn("OK");
        FxAssert.verifyThat("#exit", NodeMatchers.isVisible());
    }

    @Test
    void testPopularityNavigation(FxRobot robot) throws UsernameAlreadyExistsException{
        CompanyService.addCompany("Compania1", "Romania", "Lalelelor", "Compa1", "12345", "compania1@gmail.com", "0256458697", "+40 265" );
        robot.clickOn("#username");
        robot.write("Compa1");
        robot.clickOn("#password");
        robot.write("12345");
        robot.clickOn("#role");
        robot.clickOn("Company Administrator");
        robot.clickOn("#login");
        robot.clickOn("#popularityButton");
        robot.clickOn("#returnHomeScene");
        FxAssert.verifyThat("#logoutButton", NodeMatchers.isVisible());
    }
}