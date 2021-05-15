package org.fis2021.exceptions;

public class StationAlreadyExistsException extends Exception{
    private String stationName;

    public StationAlreadyExistsException(String stationName) {
        super(String.format("An account with the username %s already exists!", stationName));
        this.stationName = stationName;
    }

    public String getStationName() {
        return stationName;
    }
}
