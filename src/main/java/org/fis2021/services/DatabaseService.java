package org.fis2021.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.fis2021.model.Company;
import org.fis2021.model.VehicleOwner;

import static org.fis2021.services.FileSystemService.getPathToFile;

public class DatabaseService {

    private static Nitrite database;
    private static ObjectRepository<VehicleOwner> vehicleOwnerRepository;
    private static ObjectRepository<Company> companyRepository;

    public static void initDatabase() {
        database = Nitrite.builder()
                .filePath(getPathToFile("register.db").toFile())
                .openOrCreate("test", "test");
        vehicleOwnerRepository = database.getRepository(VehicleOwner.class);
        companyRepository = database.getRepository(Company.class);
    }
    public static Nitrite getDatabase() {
        return database;
    }
}