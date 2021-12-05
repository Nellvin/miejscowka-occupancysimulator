package org.example.miejscowka.occupancysimulator;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SimulateOccupancyTo implements Serializable {

    private int occupancyChange;
    private Long placeId;
    private LocalDateTime time;

    public SimulateOccupancyTo() {
    }

    public SimulateOccupancyTo(int numberOfPeople, Long placeId, LocalDateTime time) {
        this.occupancyChange = numberOfPeople;
        this.placeId = placeId;
        this.time = time;
    }

    public int getOccupancyChange() {
        return occupancyChange;
    }

    public void setOccupancyChange(int occupancyChange) {
        this.occupancyChange = occupancyChange;
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


