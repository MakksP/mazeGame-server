package com.mgs.mazeGameserver;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {
    public static final int VISIBILITY_LENGHT = 3;
    public static final int MAX_X_Y_IN_AREA = 7;
    public static int MAP_HEIGHT = 35;
    public static int MAP_WIDTH = 49;

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
        Player currentPlayer = Player.getPlayerById(playerNumber);
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

    public static void createWholeMapArea(List<List<Character>> wholeMapRepresentation, List<List<VisibleAreaMapPoint>> mapVisibleAreaRepresentation){
        for (int rowIndex = 0; rowIndex < MAP_HEIGHT; rowIndex++) {
            mapVisibleAreaRepresentation.add(new ArrayList<>());
            for (int columnIndex = 0; columnIndex < MAP_WIDTH; columnIndex++) {
                mapVisibleAreaRepresentation.get(rowIndex).add(getCurrentMapElement(wholeMapRepresentation, rowIndex, columnIndex));
            }
        }
    }

    private static VisibleAreaMapPoint getCurrentMapElement(List<List<Character>> wholeMapRepresentation, int yMapPosition, int xMapPosition) {
        return new VisibleAreaMapPoint(new Cords(xMapPosition, yMapPosition), wholeMapRepresentation.get(yMapPosition).get(xMapPosition));
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

    private static boolean locationIsBusy(int x, int y) {
        return Game.getMapRepresentation().get(y).get(x) != ' ';
    }

    public static boolean elementOnRightIsWall(Cords movingElementCords) {
        return Game.getMapRepresentation().get(movingElementCords.getY()).get(movingElementCords.getX() + 1) == '#';
    }

    public static boolean cordsOutOfBoundsAfterGoRight(Cords movingElementCords) {
        return movingElementCords.getX() + 1 > GameService.MAP_WIDTH - 1;
    }

    public static boolean elementBelowIsWall(Cords movingElementCords) {
        return Game.getMapRepresentation().get(movingElementCords.getY() + 1).get(movingElementCords.getX()) == '#';
    }

    public static boolean cordsOutOfBoundsAfterGoDown(Cords movingElementCords) {
        return movingElementCords.getY() + 1 > GameService.MAP_HEIGHT - 1;

    }

    public static boolean elementOnLeftIsWall(Cords movingElementCords) {
        return Game.getMapRepresentation().get(movingElementCords.getY()).get(movingElementCords.getX() - 1) == '#';
    }

    public static boolean cordsOutOfBoundsAfterGoLeft(Cords movingElementCords) {
        return movingElementCords.getX() - 1 < 0;
    }

    public static boolean elementAboveIsWall(Cords movingElementCords) {
        return Game.getMapRepresentation().get(movingElementCords.getY() - 1).get(movingElementCords.getX()) == '#';
    }

    public static boolean cordsOutOfBoundsAfterGoUp(Cords movingElementCords) {
        return movingElementCords.getY() - 1 < 0;
    }


    public static void moveElementLeft(MovingElement element) {
        int newPlayerX = element.cords.getX() - 1;
        element.standsOn = Game.getMapRepresentation().get(element.cords.getY()).get(newPlayerX);
        element.cords.setX(newPlayerX);
    }

    public static void moveElementDown(MovingElement element) {
        int newPlayerY = element.cords.getY() + 1;
        element.standsOn = Game.getMapRepresentation().get(newPlayerY).get(element.cords.getX());
        element.cords.setY(element.cords.getY() + 1);
    }

    public static void moveElementRight(MovingElement element) {
        int newPlayerX = element.cords.getX() + 1;
        element.standsOn = Game.getMapRepresentation().get(element.cords.getY()).get(newPlayerX);
        element.cords.setX(newPlayerX);
    }

    public static void moveElementUp(MovingElement element) {
        int newPlayerY = element.cords.getY() - 1;
        element.standsOn = Game.getMapRepresentation().get(newPlayerY).get(element.cords.getX());
        element.cords.setY(newPlayerY);
    }
}
