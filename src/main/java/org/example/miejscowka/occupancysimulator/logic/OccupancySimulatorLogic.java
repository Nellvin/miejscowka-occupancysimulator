package org.example.miejscowka.occupancysimulator.logic;

import org.example.miejscowka.occupancysimulator.api.OccupancySimulatorServiceImpl;
import org.example.miejscowka.occupancysimulator.model.OpeningHoursEntity;
import org.example.miejscowka.occupancysimulator.model.SimulateOccupancyTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class OccupancySimulatorLogic {

    @Inject
    private PlaceHandling placeHandling;
    @Inject
    private OccupancySimulatorServiceImpl occupancySimulatorService;

    @Value("${app.service.domain}")
    private String serviceDomain = "localhost";
    @Value("${server.port}")
    private int servicePort = 8060;
    @Value("${app.service.api.path}")
    private String serviceApiPath = "occupancy/relative";

    private static final Logger LOG = LoggerFactory.getLogger(OccupancySimulatorLogic.class);
    private List<Long> placesId;
    private final Random randomGen;
    private List<OpeningHoursEntity> openingHours;


    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        placesId = List.of(1000001L, 1000002L, 1000003L, 1000004L, 1000000L);
        openingHours = placeHandling.findOpeningHours(placesId);
        occupancySimulatorService.openConnections();
        sendSimulatedOccupancies();
    }

    public OccupancySimulatorLogic(List<Long> placesId) {
        this.placesId = placesId;
        randomGen = new Random();
    }


    public void sendSimulatedOccupancies() {
        int occupancyChange;
        int stdDeviation = 2;
        SimulateOccupancyTo simulateOccupancyTo;
        LocalDateTime now;
        while (true) {
            for (int i = 1; i < placesId.size(); i++) {
                now = LocalDateTime.now();
                if (checkDateCondition(now, placesId.get(i))) {
                    try {
                        occupancyChange = (int) (stdDeviation * randomGen.nextGaussian());
                        simulateOccupancyTo = new SimulateOccupancyTo(occupancyChange, placesId.get(i), LocalDateTime.now());
                        occupancySimulatorService.sendRequest(simulateOccupancyTo);
                        Thread.sleep(2 * 1000);
                    } catch (InterruptedException e) {
                        LOG.warn(e.toString());
                    }
                }
            }
            try {
                Thread.sleep(15 * 1000);
            } catch (InterruptedException e) {
                LOG.warn(e.toString());
            }
        }
    }

    private boolean checkDateCondition(LocalDateTime now, Long placeId) {
        OpeningHoursEntity ohForPlace = openingHours.stream().filter(openingHoursEntity -> openingHoursEntity.getPlaceId().equals(placeId)).findFirst().orElse(null);
        if (ohForPlace != null) {
            int nowHour = now.getHour();
            int nowDay = now.getDayOfWeek().getValue();
            if (nowDay == 1) {
                if (nowHour > getHour(ohForPlace.getMondayOpeningHour()) && nowHour < getHour(ohForPlace.getMondayClosingHour())) {
                    return true;
                }
            } else if (nowDay == 2) {
                if (nowHour > getHour(ohForPlace.getTuesdayOpeningHour()) && nowHour < getHour(ohForPlace.getTuesdayClosingHour())) {
                    return true;
                }
            } else if (nowDay == 3) {
                if (nowHour > getHour(ohForPlace.getWednesdayOpeningHour()) && nowHour < getHour(ohForPlace.getWednesdayClosingHour())) {
                    return true;
                }
            } else if (nowDay == 4) {
                if (nowHour > getHour(ohForPlace.getThursdayOpeningHour()) && nowHour < getHour(ohForPlace.getThursdayClosingHour())) {
                    return true;
                }
            } else if (nowDay == 5) {
                if (nowHour > getHour(ohForPlace.getFridayOpeningHour()) && nowHour < getHour(ohForPlace.getFridayClosingHour())) {
                    return true;
                }
            } else if (nowDay == 6) {
                if (nowHour > getHour(ohForPlace.getSaturdayOpeningHour()) && nowHour < getHour(ohForPlace.getSaturdayClosingHour())) {
                    return true;
                }
            } else if (nowDay == 7) {
                if (nowHour > getHour(ohForPlace.getSundayOpeningHour()) && nowHour < getHour(ohForPlace.getSundayClosingHour())) {
                    return true;
                }
            }
        }
        return false;
    }

    private Integer getHour(String ohForPlaceHours) {
        if (ohForPlaceHours == null || ohForPlaceHours.isEmpty())
            return null;
        return Integer.parseInt(ohForPlaceHours.substring(0, ohForPlaceHours.indexOf(":")));
    }
}
