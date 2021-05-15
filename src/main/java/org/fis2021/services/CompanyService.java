package org.fis2021.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectFilter;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;
import org.fis2021.exceptions.UserNotFoundException;
import org.fis2021.exceptions.UsernameAlreadyExistsException;
import org.fis2021.model.Company;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

import static org.fis2021.services.FileSystemService.getPathToFile;

public class CompanyService {

    private static ObjectRepository<Company> companyRepository;

    public static void initCompany() {
        companyRepository = DatabaseService.getDatabase().getRepository(Company.class);
    }

    public static void addCompany(String companyName, String countryOfOrigin, String address, String username, String password, String email, String telephoneNumber, String faxNumber) throws UsernameAlreadyExistsException {
        checkCompanyDoesNotAlreadyExist(username);
        companyRepository.insert(new Company(companyName, countryOfOrigin, address, username, encodePassword(username,password), email, telephoneNumber, faxNumber));
    }

    public static void checkCompanyDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {
        for (Company company : companyRepository.find()) {
            if (Objects.equals(username, company.getUsername()))
                throw new UsernameAlreadyExistsException(username);
        }
    }

    public static Company getCompany(String username) throws UserNotFoundException {
        Cursor<Company> cursor = companyRepository.find(ObjectFilters.eq("username", username));
        for(Company company : cursor) {
            return company;
        }
        throw new UserNotFoundException(username);
    }

    public static List<Company> getAllCompanies() {
        return companyRepository.find().toList();
    }

    public static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
    }

    public static String getHashedUserPassword(String username) throws UserNotFoundException {
        return getCompany(username).getPassword();
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