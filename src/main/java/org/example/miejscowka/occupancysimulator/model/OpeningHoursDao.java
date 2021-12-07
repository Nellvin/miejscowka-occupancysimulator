package org.example.miejscowka.occupancysimulator.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OpeningHoursDao extends JpaRepository<OpeningHoursEntity, Long> {

    Optional<OpeningHoursEntity> findById(Long id);

}
