package com.mgs.mazeGameserver;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {
    public static final int VISIBILITY_LENGHT = 3;
    public static final int MAX_X_Y_IN_AREA = 7;
    public static int MAP_HEIGHT = 36;
    public static int MAP_WIDTH = 76;

    public static Cords getRandomCords(){
        int x = (int) (Math.random() * (MAP_WIDTH - 1));
        int y = (int) (Math.random() * (MAP_HEIGHT - 1));
        while (locationIsBusy(x, y)){
            x = (int) (Math.random() * (MAP_WIDTH - 1));
            y = (int) (Math.random() * (MAP_HEIGHT - 1));
        }
        return new Cords(x, y);
    }

    public static List<List<VisibleAreaMapPoint>> getVisibleAreaByPlayerId(int playerNumber){
        List<List<Character>> wholeMapRepresentation = Game.getMapRepresentation();
        List<List<VisibleAreaMapPoint>> mapVisibleAreaRepresentation = new ArrayList<>();
        Player currentPlayer = getPlayerById(playerNumber);
        int currentXMapPosition = getFirstXOfVisibleArea(currentPlayer.getPlayerCords().getX());
        int currentYMapPosition = getFirstYOfVisibleArea(currentPlayer.getPlayerCords().getY());
        int rowCounter = 0;
        int maxVisibleY = getMaxVisibleY(currentYMapPosition);
        createPlayerArea(wholeMapRepresentation, mapVisibleAreaRepresentation, currentXMapPosition, currentYMapPosition, rowCounter, maxVisibleY);
        return mapVisibleAreaRepresentation;
    }

    private static void createPlayerArea(List<List<Character>> wholeMapRepresentation, List<List<VisibleAreaMapPoint>> mapVisibleAreaRepresentation, int currentXMapPosition, int currentYMapPosition, int rowCounter, int maxVisibleY) {
        for (; currentYMapPosition < maxVisibleY; currentYMapPosition++){
            mapVisibleAreaRepresentation.add(new ArrayList<>());
            int maxVisibleX = getMaxVisibleX(currentXMapPosition);
            for (int tempXPosition = currentXMapPosition; tempXPosition < maxVisibleX; tempXPosition++){
                mapVisibleAreaRepresentation.get(rowCounter).add(getCurrentMapElement(wholeMapRepresentation, currentYMapPosition, tempXPosition));
            }
            rowCounter++;
        }
    }

    private static VisibleAreaMapPoint getCurrentMapElement(List<List<Character>> wholeMapRepresentation, int currentYMapPosition, int tempXPosition) {
        return new VisibleAreaMapPoint(new Cords(tempXPosition, currentYMapPosition), wholeMapRepresentation.get(currentYMapPosition).get(tempXPosition));
    }

    private static int getMaxVisibleX(int currentXMapPosition) {
        int maxVisibleX = currentXMapPosition + MAX_X_Y_IN_AREA;
        if (maxVisibleX > MAP_WIDTH){
            maxVisibleX = MAP_WIDTH;
        }
        return maxVisibleX;
    }

    private static int getMaxVisibleY(int currentYMapPosition) {
        int maxVisibleY = currentYMapPosition + MAX_X_Y_IN_AREA;
        if (maxVisibleY > MAP_HEIGHT){
            maxVisibleY = MAP_HEIGHT;
        }
        return maxVisibleY;
    }

    private static int getFirstYOfVisibleArea(int currentPlayer) {
        int currentYMapPosition = currentPlayer - VISIBILITY_LENGHT;
        if (currentYMapPosition < 0) {
            currentYMapPosition = 0;
        }
        return currentYMapPosition;
    }

    private static int getFirstXOfVisibleArea(int currentPlayer) {
        int currentXMapPosition = currentPlayer - VISIBILITY_LENGHT;
        if (currentXMapPosition < 0) {
            currentXMapPosition = 0;
        }
        return currentXMapPosition;
    }

    private static Player getPlayerById(int playerNumber) {
        for (Player player : Game.getPlayerList()){
            if (player.getNumber() == playerNumber){
                return player;
            }
        }
        return null;
    }

    private static boolean locationIsBusy(int x, int y) {
        return Game.getMapRepresentation().get(y).get(x) != ' ';
    }
}
