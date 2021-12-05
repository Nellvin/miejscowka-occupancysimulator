package org.example.miejscowka.occupancysimulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		OccupancySimulatorLogic simulator = new OccupancySimulatorLogic(List.of(1L, 2L, 3L, 4L, 5L));

		simulator.openConnections();
		simulator.sendSimulatedOccupancies();
//		System.out.println("???");
	}

}
