package com.mgs.mazeGameserver;

import java.util.List;

public class Player extends MovingElement{
    public Cords spawnPoint;
    public int points;
    public int storedPoints;
    public int number;
    public int deaths;
    public String name;
    public boolean knowCampsiteLocation;
    
    public Player(Cords cords, int playerNumber, String nick){
        this.cords = cords;
        this.spawnPoint = new Cords(cords.getX(), cords.getY());
        points = 0;
        storedPoints = 0;
        number = playerNumber;
        deaths = 0;
        name = nick;
        standsOn = 'S';
        knowCampsiteLocation = false;
    }

    public Player(){

    }

    public Cords getPlayerCords() {
        return cords;
    }

    public int getNumber() {
        return number;
    }

    public static Player getPlayerById(int playerNumber) {
        for (Player player : Game.getPlayerList()){
            if (player.getNumber() == playerNumber){
                return player;
            }
        }
        return null;
    }

    public static void clearPlayerFromMap(Player player) {
        Game.getMapRepresentation().get(player.cords.getY()).set(player.cords.getX(), player.standsOn);
    }

    public static void deletePlayerById(int playerId){
        Player playerToRemove = Player.getPlayerById(playerId);
        Game.getPlayerList().remove(playerToRemove);
        Player.clearPlayerFromMap(playerToRemove);
    }
    public static void addPlayerToMap(Player player) {
        GameService.addPointsToPlayerIfStandOnValuableField(player);
        Game.getMapRepresentation().get(player.getPlayerCords().getY()).set(player.getPlayerCords().getX(), Character.forDigit(player.getNumber(), 10));
    }

    public void movePlayer(MoveDirection direction){
        if (direction == MoveDirection.UP){
            moveElementUp();
        } else if (direction == MoveDirection.RIGHT){
            moveElementRight();
        } else if (direction == MoveDirection.DOWN){
            moveElementDown();
        } else if (direction == MoveDirection.LEFT){
            moveElementLeft();
        }
        if (!this.knowCampsiteLocation){
            if (playerSeeCampsite()){
                this.knowCampsiteLocation = true;
            }
        }
        if (playerEnteredIntoBeast() || playerEnteredIntoOtherPlayer()){
            GameService.servePlayerDeath(this);
            if (playerEnteredIntoOtherPlayer()){
                serveOtherPlayerDeath();
            }
            makePlayerStandOnAsEmpty(this);
        } else if (playerEnteredIntoCampsite()){
            servePlayerEnterToCampsite();
        }
    }

    private void servePlayerEnterToCampsite() {
        this.storedPoints += this.points;
        this.points = 0;
    }

    private boolean playerEnteredIntoCampsite() {
        return this.standsOn == 'A';
    }

    private boolean playerSeeCampsite(){
        List<List<VisibleAreaMapPoint>> visibleArea = GameService.getVisibleAreaByCords(this.getPlayerCords());
        for (List<VisibleAreaMapPoint> visibleAreaRow : visibleArea){
            for (VisibleAreaMapPoint singleElement : visibleAreaRow){
                if (singleElement.getElement() == 'A'){
                    return true;
                }
            }
        }
        return false;
    }

    public int getPoints(){
        return points;
    }

    public void setPoints(int value){
        points = value;
    }

    private void serveOtherPlayerDeath() {
        Player otherPlayer = getPlayerById(GameService.convertCharToInt(this.standsOn));
        GameService.servePlayerDeath(otherPlayer);
        makePlayerStandOnAsEmpty(otherPlayer);
    }

    private boolean playerEnteredIntoOtherPlayer() {
        return Game.elementIsPlayer(this.standsOn);
    }

    private void makePlayerStandOnAsEmpty(Player player) {
        player.standsOn = ' ';
    }

    private boolean playerEnteredIntoBeast() {
        return this.standsOn == '*';
    }

}
