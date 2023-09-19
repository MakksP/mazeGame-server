package com.mgs.mazeGameserver;

public class JoinInfo {
    public Cords campsiteLocation;
    public int playerNumber;

    public JoinInfo(Cords campsiteLocation, int playerNumber){
        this.campsiteLocation = campsiteLocation;
        this.playerNumber = playerNumber;
    }

    public JoinInfo(){

    }
}
