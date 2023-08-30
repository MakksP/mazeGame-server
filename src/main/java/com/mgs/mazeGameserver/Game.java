package com.mgs.mazeGameserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
    public static final int FIRST_AVAILABLE_PLAYER_NUMBER = 1;
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
        if (playerListIsEmpty()){
            return 1;
        } else if (onePlayerInList()){
            return findFirstDifferentPlayerNumberThanFirstPlayer();
        }
        List<Integer> playerNumbers = new ArrayList<>();
        getAllPlayersNumbers(playerNumbers);
        Collections.sort(playerNumbers);
        Integer previousNumber = playerNumbers.get(0);
        if (playerListBeginsFromMoreThanFirstNumber(previousNumber)){
            return FIRST_AVAILABLE_PLAYER_NUMBER;
        }
        Integer playerNumberBetweenOthers = checkPlayerNumberAvailabilityBetweenPlayers(playerNumbers, previousNumber);
        if (playerNumberBetweenOthers != null) return playerNumberBetweenOthers;
        return playerNumberAtTheTopOfList(playerNumbers);
    }

    private static boolean onePlayerInList() {
        return playerList.size() == 1;
    }

    private static boolean playerListIsEmpty() {
        return playerList.size() == 0;
    }

    private static boolean playerListBeginsFromMoreThanFirstNumber(Integer previousNumber) {
        return previousNumber > 1;
    }

    private static int findFirstDifferentPlayerNumberThanFirstPlayer() {
        for (int newPlayerNumber = 1; ; newPlayerNumber++){
            if (newPlayerNumber != playerList.get(0).getNumber()){
                return newPlayerNumber;
            }
        }
    }

    public static void deletePlayerById(int playerId){
        Player playerToRemove = getPlayerById(playerId);
        Game.playerList.remove(playerToRemove);
        clearPlayerFromMap(playerToRemove.getPlayerCords());
    }

    private static int playerNumberAtTheTopOfList(List<Integer> playerNumbers) {
        return playerNumbers.get(playerNumbers.size() - 1) + 1;
    }

    private static Integer checkPlayerNumberAvailabilityBetweenPlayers(List<Integer> playerNumbers, int previousNumber) {
        for (int currentNumberIndex = 1; currentNumberIndex < playerNumbers.size(); currentNumberIndex++){
            if (previousNumber < playerNumbers.get(currentNumberIndex) - 1){
                return previousNumber + 1;
            }
            previousNumber = playerNumbers.get(currentNumberIndex);
        }
        return null;
    }

    private static void getAllPlayersNumbers(List<Integer> playerNumbers) {
        for (Player player : playerList){
            playerNumbers.add(player.getNumber());
        }
    }

    public static Player getPlayerById(int playerNumber) {
        for (Player player : Game.getPlayerList()){
            if (player.getNumber() == playerNumber){
                return player;
            }
        }
        return null;
    }

    public static void clearPlayerFromMap(Cords movingPlayerCords) {
        Game.getMapRepresentation().get(movingPlayerCords.getY()).set(movingPlayerCords.getX(), ' ');
    }
}
