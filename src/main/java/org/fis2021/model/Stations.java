package org.fis2021.model;

import java.util.Objects;

public class Stations {
    private String stationName;

    public Stations(String stationName) {
        this.stationName = stationName;
    }

    public Stations() {

    }

    public String getStationName() {
        return stationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stations)) return false;
        Stations stations = (Stations) o;
        return stationName.equals(stations.stationName);
    }

}
