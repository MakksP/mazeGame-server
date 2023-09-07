package com.mgs.mazeGameserver;

public class Player extends MovingElement{
    public Cords spawnPoint;
    public int points;
    public int storedPoints;
    public int number;
    public int deaths;
    public String name;
    
    public Player(Cords cords, int playerNumber, String nick){
        this.cords = cords;
        this.spawnPoint = cords;
        points = 0;
        storedPoints = 0;
        number = playerNumber;
        deaths = 0;
        name = nick;
        standsOn = ' ';
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

}
