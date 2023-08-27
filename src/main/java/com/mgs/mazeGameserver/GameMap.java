package com.mgs.mazeGameserver;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private static List<List<Character>> mapRepresentation;
    private static GameMap gameMap;


    private GameMap(){
        mapRepresentation = new ArrayList<>();
        int mapRepresentationRowCounter = 0;
        for (int mazeElement = 0; mazeElement < RawMaze.maze.length(); mazeElement++){
            char currentElement = RawMaze.maze.charAt(mazeElement);
            if (currentElement == '\n'){
                mapRepresentation.add(new ArrayList<>());
            } else {
                mapRepresentation.get(mapRepresentationRowCounter).add(currentElement);
            }
        }
    }

    public static List<List<Character>> getMapRepresentation() {
        return mapRepresentation;
    }

    public static GameMap getInstance(){
        if (gameMap == null){
            gameMap = new GameMap();
        }
        return gameMap;
    }

}
