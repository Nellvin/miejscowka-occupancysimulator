package org.example.miejscowka.occupancysimulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		OccupancySimulatorLogic simulator = new OccupancySimulatorLogic(List.of(1000001L, 1000002L, 1000003L, 1000004L, 1000000L));

		simulator.openConnections();
		simulator.sendSimulatedOccupancies();
	}

}
