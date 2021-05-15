package org.fis2021.services;

import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;
import org.fis2021.ApplicationHelper;
import org.fis2021.exceptions.StationAlreadyExistsException;
import org.fis2021.model.Stations;

import java.util.ArrayList;
import java.util.Objects;

public class StationsService {
    private static ObjectRepository<Stations> stationsRepository;

    public static void initStations() {
        stationsRepository = DatabaseService.getDatabase().getRepository(Stations.class);
    }

    public static ObjectRepository<Stations> getStationsRepository() {
        return stationsRepository;
    }

    private static void checkStationDoesNotAlreadyExist(String stationName) throws StationAlreadyExistsException {
        for (Stations stations : stationsRepository.find()) {
            if (Objects.equals(stationName, stations.getStationName()))
                throw new StationAlreadyExistsException(stationName);
        }
    }

    public static void addStation(String stationName, String city, String address) throws StationAlreadyExistsException {
        checkStationDoesNotAlreadyExist(stationName);
        stationsRepository.insert(new Stations(stationName, ApplicationHelper.companyName, city, address));
    }

    public static ArrayList<String> getAllStationsName() {
        ArrayList<String> stationsList = new ArrayList<>();
        Cursor<Stations> cursor = stationsRepository.find();
        for(Stations stations : cursor) {
            stationsList.add(stations.getStationName() );
        }
        return stationsList;
    }

    public static ArrayList<String> getAllStationsFromCompany() {
        ArrayList<String> stationsList = new ArrayList<>();
        Cursor<Stations> cursor = stationsRepository.find();
        for(Stations stations : cursor) {
            if(stations.getCompanyName().equals(ApplicationHelper.companyName))
                stationsList.add(stations.getStationName());
        }
        return stationsList;
    }

    public static ArrayList<Stations> getAllStations() {
        ArrayList<Stations> stationsList = new ArrayList<>();
        Cursor<Stations> cursor = stationsRepository.find();
        for(Stations stations : cursor) {
            if(stations.getCompanyName().equals(ApplicationHelper.companyName))
                stationsList.add(stations);
        }
        return stationsList;
    }
    public static ArrayList<String> getAllStationsFromCompanyCity() {
        ArrayList<String> stationsList = new ArrayList<>();
        Cursor<Stations> cursor = stationsRepository.find();
        for(Stations stations : cursor) {
            if(stations.getCompanyName().equals(ApplicationHelper.companyName))
                stationsList.add(stations.getCity());
        }
        return stationsList;
    }
  
    public static ArrayList<String> getAllAvailableStationsFromCity(String city) {
        ArrayList<String> stationsList = new ArrayList<>();
        Cursor<Stations> cursor = stationsRepository.find();
        for(Stations stations : cursor) {
            if(stations.getCity().equals(city) && !stations.getStationAvailability()) {
                stationsList.add(stations.getStationName());
            }
        }
        return stationsList;
    }

    public static ArrayList<String> getAllBusyStationsFromCity(String city) {
        ArrayList<String> stationsList = new ArrayList<>();
        Cursor<Stations> cursor = stationsRepository.find();
        for(Stations stations : cursor) {
            if(stations.getCity().equals(city) && stations.getStationAvailability()) {
                stationsList.add(stations.getStationName());
            }
        }
        return stationsList;
    }

    public static Stations getExactStationFromCity(String stationName, String city) {
        Cursor<Stations> cursor = stationsRepository.find();
        for(Stations stations : cursor) {
            if(stations.getCity().equals(city) && stations.getStationName().equals(stationName)) {
                return stations;
            }
        }
        return null;
    }

    public static void deleteStation(String stationName) {
        stationsRepository.remove(ObjectFilters.eq("stationName", stationName));
    }

}