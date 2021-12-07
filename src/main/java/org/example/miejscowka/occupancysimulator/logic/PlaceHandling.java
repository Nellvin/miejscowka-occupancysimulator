package org.example.miejscowka.occupancysimulator.logic;

import org.example.miejscowka.occupancysimulator.model.OpeningHoursDao;
import org.example.miejscowka.occupancysimulator.model.OpeningHoursEntity;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlaceHandling {

    @Inject
    private OpeningHoursDao openingHoursDao;

    public List<OpeningHoursEntity> findOpeningHours(List<Long> ids) {
        List<OpeningHoursEntity> openingHoursEntities = new ArrayList<>();
        for (Long id : ids) {
            openingHoursEntities.add(openingHoursDao.findById(id).orElse(null));
        }
        return openingHoursEntities;
    }
}
