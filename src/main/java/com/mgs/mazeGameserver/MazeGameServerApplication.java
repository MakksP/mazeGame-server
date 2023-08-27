package com.mgs.mazeGameserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MazeGameServerApplication {

	public static void main(String[] args) {
		GameMap.getInstance();
		SpringApplication.run(MazeGameServerApplication.class, args);
	}

}
