package com.mgs.mazeGameserver;

public class Player {
    private Cords playerCords;
    private int points;
    private int storedPoints;
    private int number;
    private int deaths;
    private String name;
    
    public Player(Cords cords, int playerNumber, String nick){
        this.playerCords = cords;
        points = 0;
        storedPoints = 0;
        number = playerNumber;
        deaths = 0;
        name = nick;
    }

    public Cords getPlayerCords() {
        return playerCords;
    }

    public void setPlayerCords(Cords playerCords) {
        this.playerCords = playerCords;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getStoredPoints() {
        return storedPoints;
    }

    public void setStoredPoints(int storedPoints) {
        this.storedPoints = storedPoints;
    }

    public String getName() {
        return name;
    }
}
