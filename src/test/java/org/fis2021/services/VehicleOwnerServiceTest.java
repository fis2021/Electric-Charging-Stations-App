package org.fis2021.services;

import org.apache.commons.io.FileUtils;
import org.fis2021.exceptions.UserNotFoundException;
import org.fis2021.exceptions.UsernameAlreadyExistsException;
import org.fis2021.model.Company;
import org.fis2021.model.VehicleOwner;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class VehicleOwnerServiceTest {
    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-registration";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        DatabaseService.initDatabase();
        VehicleOwnerService.initVehicleOwner();
    }

    @AfterEach
    void tearDown() {
        DatabaseService.getDatabase().close();
    }

    @Test
    @DisplayName("Database is initialized, there are no vehicle owners persisted!")
    void testDatabaseIsInitializedAndNoVehicleOwnerIsPersisted() {
        assertThat(VehicleOwnerService.getAllVehicleOwners()).isNotNull();
        assertThat(VehicleOwnerService.getAllVehicleOwners()).isEmpty();
    }

    @Test
    @DisplayName("vehicle Owner is added to the database!")
    void testVehicleOwnerIsAddedToTheDatabase() throws UsernameAlreadyExistsException {
        VehicleOwnerService.addVehicleOwner("Andrei", "Marcel", "andrei.marcel@gmail.com", "andrmarc", "12345", "Tesla", "full electric", "2020" );
        assertThat(VehicleOwnerService.getAllVehicleOwners()).isNotEmpty();
        assertThat(VehicleOwnerService.getAllVehicleOwners().size()).isEqualTo(1);
        VehicleOwner vehicleOwner = VehicleOwnerService.getAllVehicleOwners().get(0);
        assertThat(vehicleOwner.getFirstname()).isEqualTo("Andrei");
        assertThat(vehicleOwner.getLastname()).isEqualTo("Marcel");
        assertThat(vehicleOwner.getEmail()).isEqualTo("andrei.marcel@gmail.com");
        assertThat(vehicleOwner.getUsername()).isEqualTo("andrmarc");
        assertThat(vehicleOwner.getPassword()).isEqualTo(VehicleOwnerService.encodePassword("andrmarc", "12345"));
        assertThat(vehicleOwner.getCarBrand()).isEqualTo("Tesla");
        assertThat(vehicleOwner.getEvType()).isEqualTo("full electric");
        assertThat(vehicleOwner.getFabricationYear()).isEqualTo("2020");
    }

    @Test
    @DisplayName("Two vehicle owners with the same username cannot be added!")
    void testVehicleOwnerCannotBeAddedTwice() {
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            VehicleOwnerService.addVehicleOwner("Andrei", "Marcel", "andrei.marcel@gmail.com", "andrmarc", "12345", "Tesla", "full electric", "2020" );
            VehicleOwnerService.addVehicleOwner("Andrei", "Marcel", "andrei.marcel@gmail.com", "andrmarc", "12345", "Tesla", "full electric", "2020" );
        });
    }

    @Test
    @DisplayName("Vehicle Owner cannot be found!")
    void testVehicleOwnerCannotBeFound() {
        assertThrows(UserNotFoundException.class, () -> {
            VehicleOwnerService.addVehicleOwner("Andrei", "Marcel", "andrei.marcel@gmail.com", "andrmarc1", "12345", "Tesla", "full electric", "2020" );
            VehicleOwnerService.addVehicleOwner("Andrei", "Marcel", "andrei.marcel@gmail.com", "andrmarc2", "12345", "Tesla", "full electric", "2020" );
            VehicleOwnerService.getVehicleOwner("andrmarc3");
        });
    }

    @Test
    @DisplayName("Vehicle owner already exists!")
    void testVehicleOwnerAlreadyExists() {
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            VehicleOwnerService.addVehicleOwner("Andrei", "Marcel", "andrei.marcel@gmail.com", "andrmarc", "12345", "Tesla", "full electric", "2020" );
            VehicleOwnerService.checkVehicleOwnerDoesNotAlreadyExist("andrmarc");
        });
    }

    @Test
    @DisplayName("Password is encoded!")
    void testPasswordIsEncoded() throws UsernameAlreadyExistsException {
        VehicleOwnerService.addVehicleOwner("Andrei", "Marcel", "andrei.marcel@gmail.com", "andrmarc", "12345", "Tesla", "full electric", "2020" );
        VehicleOwner vehicleOwner = VehicleOwnerService.getAllVehicleOwners().get(0);
        assertThat(vehicleOwner.getPassword()).isEqualTo(VehicleOwnerService.encodePassword("andrmarc", "12345"));
    }
}