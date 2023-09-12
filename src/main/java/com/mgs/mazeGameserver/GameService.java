package com.mgs.mazeGameserver;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {
    public static final int VISIBILITY_LENGHT = 3;
    public static final int MAX_X_Y_IN_AREA = 3;
    public static int MAP_HEIGHT = 35;
    public static final int MAX_MAP_HEIGHT_AS_INDEX = MAP_HEIGHT - 1;
    public static int MAP_WIDTH = 49;
    public static final int MAX_MAP_WIDTH_AS_INDEX = MAP_WIDTH - 1;

    public static Cords getRandomCords(){
        int x = (int) (Math.random() * (MAX_MAP_WIDTH_AS_INDEX));
        int y = (int) (Math.random() * (MAX_MAP_HEIGHT_AS_INDEX));
        while (locationIsBusy(x, y)){
            x = (int) (Math.random() * (MAX_MAP_WIDTH_AS_INDEX));
            y = (int) (Math.random() * (MAX_MAP_HEIGHT_AS_INDEX));
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
        int maxVisibleY = getMaxVisibleY(currentPlayer.getPlayerCords().getY());
        int maxVisibleX = getMaxVisibleX(currentPlayer.getPlayerCords().getX());
        createElementArea(wholeMapRepresentation, mapVisibleAreaRepresentation, currentXMapPosition, currentYMapPosition,
                rowCounter, maxVisibleY, maxVisibleX);
        return mapVisibleAreaRepresentation;
    }

    public static List<List<VisibleAreaMapPoint>> getVisibleAreaByCords(Cords cords){
        List<List<Character>> wholeMapRepresentation = Game.getMapRepresentation();
        List<List<VisibleAreaMapPoint>> mapVisibleAreaRepresentation = new ArrayList<>();
        int currentXMapPosition = getFirstXOfVisibleArea(cords.getX());
        int currentYMapPosition = getFirstYOfVisibleArea(cords.getY());
        int rowCounter = 0;
        int maxVisibleY = getMaxVisibleY(cords.getY());
        int maxVisibleX = getMaxVisibleX(cords.getX());
        createElementArea(wholeMapRepresentation, mapVisibleAreaRepresentation, currentXMapPosition, currentYMapPosition, rowCounter, maxVisibleY, maxVisibleX);
        return mapVisibleAreaRepresentation;
    }

    private static void createElementArea(List<List<Character>> wholeMapRepresentation, List<List<VisibleAreaMapPoint>> mapVisibleAreaRepresentation, int currentXMapPosition,
                                          int currentYMapPosition, int rowCounter, int maxVisibleY, int maxVisibleX) {
        for (; currentYMapPosition <= maxVisibleY; currentYMapPosition++){
            mapVisibleAreaRepresentation.add(new ArrayList<>());
            for (int tempXPosition = currentXMapPosition; tempXPosition <= maxVisibleX; tempXPosition++){
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

    private static int getMaxVisibleX(int elementXCord) {
        int maxVisibleX = elementXCord + MAX_X_Y_IN_AREA;
        if (maxVisibleX >= MAP_WIDTH){
            maxVisibleX = MAX_MAP_WIDTH_AS_INDEX;
        }
        return maxVisibleX;
    }

    private static int getMaxVisibleY(int elementYCord) {
        int maxVisibleY = elementYCord + MAX_X_Y_IN_AREA;
        if (maxVisibleY >= MAP_HEIGHT){
            maxVisibleY = MAX_MAP_HEIGHT_AS_INDEX;
        }
        return maxVisibleY;
    }

    private static int getFirstYOfVisibleArea(int elementYCord) {
        int currentYMapPosition = elementYCord - VISIBILITY_LENGHT;
        if (currentYMapPosition < 0) {
            currentYMapPosition = 0;
        }
        return currentYMapPosition;
    }

    private static int getFirstXOfVisibleArea(int elementXCord) {
        int currentXMapPosition = elementXCord - VISIBILITY_LENGHT;
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

    public static void addPointsToPlayerIfStandOnValuableField(Player player) {
        if (player.standsOn == 'c' || player.standsOn == 't' || player.standsOn == 'T'){
            player.points += Game.getObjectsValue().get(player.standsOn);
            player.standsOn = ' ';
        }
    }

    public static char convertIntPlayerNumberToChar(Player attackedPlayer) {
        return (char) ((char) attackedPlayer.getNumber() + '0');
    }

    public static int convertCharToInt(char number) {
        return number - '0';
    }

    public static void servePlayerDeath(Player attackedPlayer) {
        Game.getMapRepresentation().get(attackedPlayer.getPlayerCords().getY()).set(attackedPlayer.getPlayerCords().getX(), attackedPlayer.standsOn);
        attackedPlayer.setNewLocation(new Cords(attackedPlayer.spawnPoint.getX(), attackedPlayer.spawnPoint.getY()));
        Game.getMapRepresentation().get(attackedPlayer.getPlayerCords().getY()).set(attackedPlayer.getPlayerCords().getX(), convertIntPlayerNumberToChar(attackedPlayer));
        attackedPlayer.deaths++;
    }

}
