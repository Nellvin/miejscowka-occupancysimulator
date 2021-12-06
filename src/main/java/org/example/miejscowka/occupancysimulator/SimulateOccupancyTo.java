package org.example.miejscowka.occupancysimulator;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SimulateOccupancyTo implements Serializable {

    private int numberOfPeople;
    private Long placeId;
    private LocalDateTime time;

    public SimulateOccupancyTo() {
    }

    public SimulateOccupancyTo(int numberOfPeople, Long placeId, LocalDateTime time) {
        this.numberOfPeople = numberOfPeople;
        this.placeId = placeId;
        this.time = time;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}


