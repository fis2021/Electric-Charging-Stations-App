package org.fis2021.services;

import org.apache.commons.io.FileUtils;
import org.fis2021.exceptions.StationAlreadyExistsException;
import org.fis2021.model.Stations;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StationsServiceTest {

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-registration";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        DatabaseService.initDatabase();
        StationsService.initStations();
    }

    @AfterEach
    void tearDown() {
        DatabaseService.getDatabase().close();
    }


    @Test
    @DisplayName("Database is initialized, there are no companies persisted!")
    void testDatabaseIsInitializedAndNoStationIsPersisted() {
        assertThat(StationsService.getAllStations()).isNotNull();
        assertThat(StationsService.getAllStations()).isEmpty();
    }

    @Test
    @DisplayName("Two stations with the same name cannot be added!")
    void testCompanyCannotBeAddedTwice() {
        assertThrows(StationAlreadyExistsException.class, () -> {
            StationsService.addStation("Station 1", "Timisoara", "str. Lalelelor");
            StationsService.addStation("Station 1", "Timisoara", "str. Lalelelor");
        });
    }

    @Test
    @DisplayName("Statino already exists!")
    void testCompanyAlreadyExists() {
        assertThrows(StationAlreadyExistsException.class, () -> {
            StationsService.addStation("Station 1", "Timisoara", "str. Lalelelor");
            StationsService.checkStationDoesNotAlreadyExist("Station 1");
        });
    }
}