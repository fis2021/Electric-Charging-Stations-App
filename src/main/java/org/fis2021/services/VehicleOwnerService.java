package org.fis2021.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.fis2021.exceptions.UsernameAlreadyExistsException;
import org.fis2021.model.VehicleOwner;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static org.fis2021.services.FileSystemService.getPathToFile;

public class VehicleOwnerService {

    private static ObjectRepository<VehicleOwner> vehicleOwnerRepository;

    public static void initVehicleOwner() {
        vehicleOwnerRepository = DatabaseService.getDatabase().getRepository(VehicleOwner.class);
    }

    public static void addVehicleOwner(String firstname, String lastname,String email, String username, String password, String carBrand, String evType, String fabricationYear) throws UsernameAlreadyExistsException {
        checkVehicleOwnerDoesNotAlreadyExist(username);
        vehicleOwnerRepository.insert(new VehicleOwner(firstname, lastname, username, encodePassword(username, password), email, carBrand, evType, fabricationYear ));
    }

    private static void checkVehicleOwnerDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {
        for (VehicleOwner vehicleOwner : vehicleOwnerRepository.find()) {
            if (Objects.equals(username, vehicleOwner.getUsername()))
                throw new UsernameAlreadyExistsException(username);
        }
    }

    private static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
    }

    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }
}