package com.mgs.mazeGameserver;

public class Player {
    public Cords playerCords;
    public int points;
    public int storedPoints;
    public int number;
    public int deaths;
    public String name;
    
    public Player(Cords cords, int playerNumber, String nick){
        this.playerCords = cords;
        points = 0;
        storedPoints = 0;
        number = playerNumber;
        deaths = 0;
        name = nick;
    }

    public Player(){

    }

    public Cords getPlayerCords() {
        return playerCords;
    }

    public int getNumber() {
        return number;
    }

}
