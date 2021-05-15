package org.fis2021.services;

import org.apache.commons.io.FileUtils;
import org.fis2021.exceptions.UserNotFoundException;
import org.fis2021.exceptions.UsernameAlreadyExistsException;
import org.fis2021.model.Company;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

class CompanyServiceTest {

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-registration";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        DatabaseService.initDatabase();
        CompanyService.initCompany();
    }

    @AfterEach
    void tearDown() {
        DatabaseService.getDatabase().close();
    }

    @Test
    @DisplayName("Database is initialized, there are no companies persisted!")
    void testDatabaseIsInitializedAndNoCompanyIsPersisted() {
        assertThat(CompanyService.getAllCompanies()).isNotNull();
        assertThat(CompanyService.getAllCompanies()).isEmpty();
    }

    @Test
    @DisplayName("Company is added to the database!")
    void testCompanyIsAddedToTheDatabase() throws UsernameAlreadyExistsException {
        CompanyService.addCompany("Compania1", "Romania", "Lalelelor", "Compa1", "12345", "compania1@gmail.com", "0256458697", "+40 265" );
        assertThat(CompanyService.getAllCompanies()).isNotEmpty();
        assertThat(CompanyService.getAllCompanies().size()).isEqualTo(1);
        Company company = CompanyService.getAllCompanies().get(0);
        assertThat(company.getCompanyName()).isEqualTo("Compania1");
        assertThat(company.getCountryOfOrigin()).isEqualTo("Romania");
        assertThat(company.getAddress()).isEqualTo("Lalelelor");
        assertThat(company.getUsername()).isEqualTo("Compa1");
        assertThat(company.getPassword()).isEqualTo(CompanyService.encodePassword("Compa1", "12345"));
        assertThat(company.getEmail()).isEqualTo("compania1@gmail.com");
        assertThat(company.getTelephoneNumber()).isEqualTo("0256458697");
        assertThat(company.getFaxNumber()).isEqualTo("+40 265");
    }

    @Test
    @DisplayName("Two companies with the same username cannot be added!")
    void testCompanyCannotBeAddedTwice() {
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            CompanyService.addCompany("Compania1", "Romania", "Lalelelor", "Compa1", "12345", "compania1@gmail.com", "0256458697", "+40 265" );
            CompanyService.addCompany("Compania1", "Romania", "Lalelelor", "Compa1", "12345", "compania1@gmail.com", "0256458697", "+40 265" );
        });
    }

    @Test
    @DisplayName("Company cannot be found!")
    void testCompanyCannotBeFound() {
        assertThrows(UserNotFoundException.class, () -> {
            CompanyService.addCompany("Compania1", "Romania", "Lalelelor", "Compa1", "12345", "compania1@gmail.com", "0256458697", "+40 265" );
            CompanyService.addCompany("Compania1", "Romania", "Lalelelor", "Compa2", "12345", "compania1@gmail.com", "0256458697", "+40 265" );
            CompanyService.getCompany("Compa3");
        });
    }

    @Test
    @DisplayName("Company already exists!")
    void testCompanyAlreadyExists() {
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            CompanyService.addCompany("Compania1", "Romania", "Lalelelor", "Compa1", "12345", "compania1@gmail.com", "0256458697", "+40 265" );
            CompanyService.checkCompanyDoesNotAlreadyExist("Compa1");
        });
    }

    @Test
    @DisplayName("Password is encoded!")
    void testPasswordIsEncoded() throws UsernameAlreadyExistsException {
        CompanyService.addCompany("Compania1", "Romania", "Lalelelor", "Compa1", "12345", "compania1@gmail.com", "0256458697", "+40 265" );
        Company company = CompanyService.getAllCompanies().get(0);
        assertThat(company.getPassword()).isEqualTo(CompanyService.encodePassword("Compa1", "12345"));
    }
}