package org.fis2021.services;

import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;
import org.fis2021.exceptions.UsernameAlreadyExistsException;
import org.fis2021.model.Company;
import org.fis2021.model.Stations;

import java.util.ArrayList;
import java.util.Objects;

public class StationsService {
    private static ObjectRepository<Stations> stationsRepository;

    public static void initStations() {
        stationsRepository = DatabaseService.getDatabase().getRepository(Stations.class);
    }

    private static void checkStationDoesNotAlreadyExist(String stationName) throws UsernameAlreadyExistsException {
        for (Stations stations : stationsRepository.find()) {
            if (Objects.equals(stationName, stations.getStationName()))
                throw new UsernameAlreadyExistsException(stationName);
        }
    }

    public static void addStation(String stationName) throws UsernameAlreadyExistsException {
        checkStationDoesNotAlreadyExist(stationName);
        stationsRepository.insert(new Stations(stationName));
    }

    public static ArrayList<String> getAllStations() {
        ArrayList<String> stationsList = new ArrayList<>();
        Cursor<Stations> cursor = stationsRepository.find();
        for(Stations stations : cursor) {
            stationsList.add(stations.getStationName());
        }
        return stationsList;
    }
}
