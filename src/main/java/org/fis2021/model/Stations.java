package org.fis2021.model;

import org.dizitart.no2.objects.Id;

import java.util.Objects;

public class Stations {
    @Id
    private String stationName;

    private String companyName, city, address;

    private boolean stationAvailability;

    public Stations(String stationName, String companyName, String city, String address) {
        this.stationName = stationName;
        this.companyName = companyName;
        this.city = city;
        this.address = address;
        this.stationAvailability = false;
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

    public void setStationAvailability(boolean select) {
        this.stationAvailability = select;
    }

    public boolean getStationAvailability() {
        return this.stationAvailability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stations)) return false;
        Stations stations = (Stations) o;
        return stationName.equals(stations.stationName);
    }

}
