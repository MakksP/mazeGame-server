package com.mgs.mazeGameserver;

public class Cords {
    private int x;
    private int y;

    public Cords(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Cords(){

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
