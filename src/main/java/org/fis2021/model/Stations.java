package org.fis2021.model;

import java.util.Objects;

public class Stations {
    private String stationName, companyName, city, address;

    public Stations(String stationName) {
        this.stationName = stationName;
    }

    public Stations() {

    }

    public String getStationName() {
        return stationName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stations)) return false;
        Stations stations = (Stations) o;
        return stationName.equals(stations.stationName);
    }

}
