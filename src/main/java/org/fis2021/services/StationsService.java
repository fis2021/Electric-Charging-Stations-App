package org.fis2021.services;

import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;
import org.fis2021.ApplicationHelper;
import org.fis2021.model.Stations;

import java.util.ArrayList;
import java.util.Objects;

public class StationsService {

    private static ObjectRepository<Stations> stationsRepository;

    public static void initStations() {
        stationsRepository = DatabaseService.getDatabase().getRepository(Stations.class);
    }

    public static ArrayList<String> getAllStationsFromCity(String city) {
        ArrayList<String> stationsList = new ArrayList<>();
        Cursor<Stations> cursor = stationsRepository.find();
        for(Stations stations : cursor) {
            if(stations.getCity().equals(city))
                stationsList.add(stations.getStationName());
        }
        return stationsList;
    }
}
