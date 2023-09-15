package com.mgs.mazeGameserver;

public class DroppedCoin {
    private Cords cords;
    private int value;

    public DroppedCoin(Cords cords, int value){
        this.cords = cords;
        this.value = value;
    }

    public Cords getCords() {
        return cords;
    }

    public void setCords(Cords cords) {
        this.cords = cords;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
