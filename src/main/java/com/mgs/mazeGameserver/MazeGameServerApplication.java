package com.mgs.mazeGameserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MazeGameServerApplication {

	public static void main(String[] args) {
		Game.getInstance();
		createAndRunTurnSystem();
		SpringApplication.run(MazeGameServerApplication.class, args);
	}

	private static void createAndRunTurnSystem() {
		TurnSystem turnSystem = new TurnSystem();
		Thread turnSystemThread = new Thread(turnSystem);
		turnSystemThread.start();
	}

}
