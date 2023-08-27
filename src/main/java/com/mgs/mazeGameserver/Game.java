package com.mgs.mazeGameserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Game {
    private static List<List<Character>> mapRepresentation;
    private static Game gameMap;
    private static List<Player> playerList;

    private Game(){
        mapRepresentation = new ArrayList<>();
        playerList = new ArrayList<>();
        int mapRepresentationRowCounter = 0;
        mapRepresentation.add(new ArrayList<>());
        for (int mazeElement = 0; mazeElement < RawMaze.maze.length(); mazeElement++){
            char currentElement = RawMaze.maze.charAt(mazeElement);
            if (currentElement == '\n'){
                mapRepresentation.add(new ArrayList<>());
                mapRepresentationRowCounter++;
            } else {
                mapRepresentation.get(mapRepresentationRowCounter).add(currentElement);
            }
        }
    }

    public static List<List<Character>> getMapRepresentation() {
        return mapRepresentation;
    }

    public static Game getInstance(){
        if (gameMap == null){
            gameMap = new Game();
        }
        return gameMap;
    }

    public static List<Player> getPlayerList() {
        return playerList;
    }

    public static void setPlayerList(List<Player> playerList) {
        Game.playerList = playerList;
    }

    public static int getFirstFreePlayerNumber(){
        if (playerList.size() == 0){
            return 1;
        }
        List<Integer> playerNumbers = new ArrayList<>();
        getAllPlayersNumbers(playerNumbers);
        Collections.sort(playerNumbers);
        Integer previousNumber = playerNumbers.get(0);
        Integer playerNumberBetweenOthers = checkPlayerNumberAvailabilityBetweenPlayers(playerNumbers, previousNumber);
        if (playerNumberBetweenOthers != null) return previousNumber;
        return playerNumberAtTheTopOfList(playerNumbers);
    }

    private static int playerNumberAtTheTopOfList(List<Integer> playerNumbers) {
        return playerNumbers.get(playerNumbers.size() - 1) + 1;
    }

    private static Integer checkPlayerNumberAvailabilityBetweenPlayers(List<Integer> playerNumbers, int previousNumber) {
        for (int currentNumberIndex = 1; currentNumberIndex < playerNumbers.size(); currentNumberIndex++){
            if (previousNumber < playerNumbers.get(currentNumberIndex) - 1){
                return previousNumber + 1;
            }
        }
        return null;
    }

    private static void getAllPlayersNumbers(List<Integer> playerNumbers) {
        for (Player player : playerList){
            playerNumbers.add(player.getNumber());
        }
    }
}
